package pers.wdcy.biology.service;

import static com.wdcy.result.ResultUtils.error;
import static com.wdcy.result.ResultUtils.failed;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.CriteriaDefinition;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.wdcy.result.PageModel;
import com.wdcy.result.PageResult;
import com.wdcy.result.Result;
import com.wdcy.result.ResultUtils;

import lombok.extern.slf4j.Slf4j;
import pers.wdcy.biology.entity.BiologyDetail;
import pers.wdcy.biology.model.BiologyDetailRevise;
import pers.wdcy.biology.model.BiologyDetailSearch;
import pers.wdcy.biology.repository.BiologyDetailRepository;
import pers.wdcy.biology.util.IdUtils;
import reactor.core.publisher.Mono;
import reactor.function.TupleUtils;

@Slf4j
@Service
public class BiologyDetailService {
	
	private @Autowired BiologyDetailRepository detailRepository;
	
	private @Autowired R2dbcEntityTemplate template;

	public Mono<Result<Boolean>> register(BiologyDetail bcr) {
		return Mono.just(bcr)
				.map(b -> {
					b.setCode(IdUtils.id());
					b.setFresh(true);
					b.setCreationTime(LocalDateTime.now());
					b.setCreator("");
					return b;
				})
				.flatMap(detailRepository::save)
				.map(BiologyDetail::getCode)
				.map(StringUtils::hasText)
				.map(ResultUtils::success)
				.switchIfEmpty(Mono.just(failed()))
				.doOnError(error -> log.error("新增生物详情失败", error))
				.onErrorResume(error -> Mono.just(error("新增生物详情失败", false)));
	}

	public Mono<Result<Boolean>> delete(String code) {
		return detailRepository.deleteById(code).then(Mono.just(ResultUtils.success(true)))
				.switchIfEmpty(Mono.just(ResultUtils.success(true))).doOnError(error -> log.error("删除生物详情失败", error))
				.onErrorResume(error -> Mono.just(failed("删除生物详情类失败", false)));
	}
	
	public Mono<Result<Boolean>> revise(BiologyDetailRevise revise) {
		return detailRepository.findById(revise.getCode()).map(spe -> {
			BeanUtils.copyProperties(revise, spe);
			spe.setFresh(false);
			spe.setOperationTime(LocalDateTime.now());
			spe.setOperator("");
			return spe; 
		}).flatMap(detailRepository::save).map(BiologyDetail::getCode).map(StringUtils::hasText)
				.map(ResultUtils::success).switchIfEmpty(Mono.just(failed()))
				.doOnError(error -> log.error("修改生物详情失败", error))
				.onErrorResume(error -> Mono.just(error("修改生物详情失败", false)));
	}

	public Mono<Result<Boolean>> recycle(String code) {
		return detailRepository.recycle(code).map(ResultUtils::success).switchIfEmpty(Mono.just(failed()))
				.doOnError(error -> log.error("回收生物详情失败", error))
				.onErrorResume(error -> Mono.just(error("回收生物详情失败", false)));
	}

	public Mono<Result<Boolean>> recover(String code) {
		return detailRepository.recover(code).map(ResultUtils::success).switchIfEmpty(Mono.just(failed()))
				.doOnError(error -> log.error("恢复生物详情失败", error))
				.onErrorResume(error -> Mono.just(error("恢复生物详情失败", false)));
	}

	public Mono<Result<BiologyDetail>> findDetail(String code) {
		return detailRepository.findById(code).map(ResultUtils::success).switchIfEmpty(Mono.just(failed()))
				.doOnError(error -> log.error("生物详情详情查询失败", error))
				.onErrorResume(error -> Mono.just(error("生物详情详情查询失败", null)));
	}

	public Mono<Result<List<BiologyDetail>>> findList(BiologyDetailSearch species) {
		List<Criteria> criterias = new ArrayList<>();
		criterias.add(Criteria.where("code").isNotNull());
		this.buildCriteria(species, criterias);

		return template.select(Query.query(CriteriaDefinition.from(criterias)), BiologyDetail.class).collectList()
				.map(ResultUtils::success).switchIfEmpty(Mono.just(failed()))
				.doOnError(error -> log.error("生物详情列表查询失败", error))
				.onErrorResume(error -> Mono.just(error("生物详情列表查询失败", null)));
	}

	public Mono<PageResult<List<BiologyDetail>>> findPage(PageModel<BiologyDetailSearch> search) {
		List<Criteria> criterias = new ArrayList<>();
		criterias.add(Criteria.where("code").isNotNull());

		this.buildCriteria(search.getParamer(), criterias);
		
		return template
				.select(Query.query(CriteriaDefinition.from(criterias)).offset(search.offset()).limit(search.getSize()),
						BiologyDetail.class)
				.collectList()
				.zipWith(template.count(Query.query(CriteriaDefinition.from(criterias)), BiologyDetail.class))
				.map(TupleUtils.function((bs, count) -> {
					return new PageResult<List<BiologyDetail>>(ResultUtils.success(bs), search.getCurrent(),
							search.getSize(), count);
				}))
				.switchIfEmpty(Mono.just(new PageResult<List<BiologyDetail>>(ResultUtils.losing(), search.getCurrent(),
						search.getSize(), 0)))
				.doOnError(error -> log.error("生物详情列表查询失败", error)).onErrorResume(
						error -> Mono.just(new PageResult<List<BiologyDetail>>(ResultUtils.error("生物详情列表查询失败", null),
								search.getCurrent(), search.getSize(), 0)));
	}

	private void buildCriteria(BiologyDetailSearch species, List<Criteria> criterias) {
		if (Objects.nonNull(species) && StringUtils.hasText(species.getCode())) {
			criterias.add(Criteria.where("code").is(species.getCode()));
		}
		
		if (Objects.nonNull(species) && StringUtils.hasText(species.getBelongClass())) {
			criterias.add(Criteria.where("belong_class").is(species.getBelongClass()));
		}
		
		if (Objects.nonNull(species) && StringUtils.hasText(species.getBelongDomain())) {
			criterias.add(Criteria.where("belong_domain").is(species.getBelongDomain()));
		}
		
		if (Objects.nonNull(species) && StringUtils.hasText(species.getBelongFamily())) {
			criterias.add(Criteria.where("belong_family").is(species.getBelongFamily()));
		}
		
		if (Objects.nonNull(species) && StringUtils.hasText(species.getBelongGenus())) {
			criterias.add(Criteria.where("belong_genus").is(species.getBelongGenus()));
		}
		
		if (Objects.nonNull(species) && StringUtils.hasText(species.getBelongKingdom())) {
			criterias.add(Criteria.where("belong_kingdom").is(species.getBelongKingdom()));
		}
		
		if (Objects.nonNull(species) && StringUtils.hasText(species.getBelongOrder())) {
			criterias.add(Criteria.where("belong_order").is(species.getBelongOrder()));
		}
		
		if (Objects.nonNull(species) && StringUtils.hasText(species.getBelongPhylum())) {
			criterias.add(Criteria.where("belong_phylum").is(species.getBelongPhylum()));
		}
		
		if (Objects.nonNull(species) && StringUtils.hasText(species.getBelongSpecies())) {
			criterias.add(Criteria.where("belong_species").is(species.getBelongSpecies()));
		}
		
		if (Objects.nonNull(species) && StringUtils.hasText(species.getBelongSubclass())) {
			criterias.add(Criteria.where("belong_subclass").is(species.getBelongSubclass()));
		}
		
		if (Objects.nonNull(species) && StringUtils.hasText(species.getBelongSubfamily())) {
			criterias.add(Criteria.where("belong_subfamily").is(species.getBelongSubfamily()));
		}
		
		if (Objects.nonNull(species) && StringUtils.hasText(species.getBelongSubgenus())) {
			criterias.add(Criteria.where("belong_subgenus").is(species.getBelongSubgenus()));
		}
		
		if (Objects.nonNull(species) && StringUtils.hasText(species.getBelongSuborder())) {
			criterias.add(Criteria.where("belong_suborder").is(species.getBelongSuborder()));
		}
		
		if (Objects.nonNull(species) && StringUtils.hasText(species.getBelongSubphylum())) {
			criterias.add(Criteria.where("belong_subphylum").is(species.getBelongSubphylum()));
		}
		
		if (Objects.nonNull(species) && StringUtils.hasText(species.getBelongSubspecies())) {
			criterias.add(Criteria.where("belong_subspecies").is(species.getBelongSubspecies()));
		}
		
		if (Objects.nonNull(species) && StringUtils.hasText(species.getBiologyName())) {
			criterias.add(Criteria.where("biology_name").like("%" + species.getBiologyName() + "%"));
		}
		
		if (Objects.nonNull(species) && StringUtils.hasText(species.getEnglishName())) {
			criterias.add(Criteria.where("english_name").like("%" + species.getEnglishName() + "%"));
		}
		
		if (Objects.nonNull(species) && StringUtils.hasText(species.getIntro())) {
			criterias.add(Criteria.where("intro").is(species.getIntro()));
		}
		
		if (Objects.nonNull(species) && StringUtils.hasText(species.getOtherName())) {
			criterias.add(Criteria.where("other_name").like("%" + species.getOtherName() + "%"));
		}
		
		if (Objects.nonNull(species) && StringUtils.hasText(species.getProtectionLevel())) {
			criterias.add(Criteria.where("protection_level").is(species.getProtectionLevel()));
		}
		
		if (Objects.nonNull(species) && StringUtils.hasText(species.getScientificName())) {
			criterias.add(Criteria.where("scientific_name").is(species.getScientificName()));
		}
	}

}
