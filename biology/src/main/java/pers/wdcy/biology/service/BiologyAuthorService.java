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
import pers.wdcy.biology.entity.BiologyAuthor;
import pers.wdcy.biology.model.BiologyAuthorRevise;
import pers.wdcy.biology.model.BiologyAuthorSearch;
import pers.wdcy.biology.repository.BiologyAuthorRepository;
import pers.wdcy.biology.util.IdUtils;
import reactor.core.publisher.Mono;
import reactor.function.TupleUtils;

@Slf4j
@Service
public class BiologyAuthorService {

	private @Autowired BiologyAuthorRepository authorRepository;
	
	private @Autowired R2dbcEntityTemplate template;

	public Mono<Result<Boolean>> register(BiologyAuthor author) {
		return Mono.just(author)
				.map(a -> {
					a.setCode(IdUtils.id());
					a.setFresh(true);
					a.setCreationTime(LocalDateTime.now());
					return a;
				})
				.flatMap(authorRepository::save)
				.map(BiologyAuthor::getCode)
				.map(StringUtils::hasText)
				.map(ResultUtils::success);
	}

	public Mono<Result<Boolean>> delete(String code) {
		return authorRepository.deleteById(code).then(Mono.just(ResultUtils.success(true)))
				.switchIfEmpty(Mono.just(ResultUtils.success(true))).doOnError(error -> log.error("删除作者失败", error))
				.onErrorResume(error -> Mono.just(failed("删除作者失败", false)));
	}
	
	public Mono<Result<Boolean>> revise(BiologyAuthorRevise revise) {
		return authorRepository.findById(revise.getCode()).map(spe -> {
			BeanUtils.copyProperties(revise, spe);
			spe.setFresh(false);
			spe.setOperationTime(LocalDateTime.now());
			spe.setOperator("");
			return spe; 
		}).flatMap(authorRepository::save).map(BiologyAuthor::getCode).map(StringUtils::hasText)
				.map(ResultUtils::success).switchIfEmpty(Mono.just(failed()))
				.doOnError(error -> log.error("修改作者失败", error))
				.onErrorResume(error -> Mono.just(error("修改作者失败", false)));
	}

	public Mono<Result<Boolean>> recycle(String code) {
		return authorRepository.recycle(code).map(ResultUtils::success).switchIfEmpty(Mono.just(failed()))
				.doOnError(error -> log.error("回收作者失败", error))
				.onErrorResume(error -> Mono.just(error("回收作者失败", false)));
	}

	public Mono<Result<Boolean>> recover(String code) {
		return authorRepository.recover(code).map(ResultUtils::success).switchIfEmpty(Mono.just(failed()))
				.doOnError(error -> log.error("恢复作者失败", error))
				.onErrorResume(error -> Mono.just(error("恢复作者失败", false)));
	}

	public Mono<Result<BiologyAuthor>> findDetail(String code) {
		return authorRepository.findById(code).map(ResultUtils::success).switchIfEmpty(Mono.just(failed()))
				.doOnError(error -> log.error("作者详情查询失败", error))
				.onErrorResume(error -> Mono.just(error("作者详情查询失败", null)));
	}

	public Mono<Result<List<BiologyAuthor>>> findList(BiologyAuthorSearch species) {
		List<Criteria> criterias = new ArrayList<>();
		criterias.add(Criteria.where("code").isNotNull());
		this.buildCriteria(species, criterias);

		return template.select(Query.query(CriteriaDefinition.from(criterias)), BiologyAuthor.class).collectList()
				.map(ResultUtils::success).switchIfEmpty(Mono.just(failed()))
				.doOnError(error -> log.error("作者列表查询失败", error))
				.onErrorResume(error -> Mono.just(error("作者列表查询失败", null)));
	}

	public Mono<PageResult<List<BiologyAuthor>>> findPage(PageModel<BiologyAuthorSearch> search) {
		List<Criteria> criterias = new ArrayList<>();
		criterias.add(Criteria.where("code").isNotNull());

		this.buildCriteria(search.getParamer(), criterias);
		
		return template
				.select(Query.query(CriteriaDefinition.from(criterias)).offset(search.offset()).limit(search.getSize()),
						BiologyAuthor.class)
				.collectList()
				.zipWith(template.count(Query.query(CriteriaDefinition.from(criterias)), BiologyAuthor.class))
				.map(TupleUtils.function((bs, count) -> {
					return new PageResult<List<BiologyAuthor>>(ResultUtils.success(bs), search.getCurrent(),
							search.getSize(), count);
				}))
				.switchIfEmpty(Mono.just(new PageResult<List<BiologyAuthor>>(ResultUtils.losing(), search.getCurrent(),
						search.getSize(), 0)))
				.doOnError(error -> log.error("作者列表查询失败", error)).onErrorResume(
						error -> Mono.just(new PageResult<List<BiologyAuthor>>(ResultUtils.error("作者列表查询失败", null),
								search.getCurrent(), search.getSize(), 0)));
	}

	private void buildCriteria(BiologyAuthorSearch species, List<Criteria> criterias) {
		if (Objects.nonNull(species) && StringUtils.hasText(species.getAuthorName())) {
			criterias.add(Criteria.where("author_name").is(species.getAuthorName()));
		}
		
		if (Objects.nonNull(species) && StringUtils.hasText(species.getAuthorUrl())) {
			criterias.add(Criteria.where("author_url").is(species.getAuthorUrl()));
		}
		
		if (Objects.nonNull(species) && StringUtils.hasText(species.getIntro())) {
			criterias.add(Criteria.where("intro").is(species.getIntro()));
		}
		
		if (Objects.nonNull(species) && Objects.nonNull(species.getAffiliation())) {
			criterias.add(Criteria.where("affiliation").is(species.getAffiliation()));
		}
		
		if (Objects.nonNull(species) && Objects.nonNull(species.getAuthorType())) {
			criterias.add(Criteria.where("author_type").is(species.getAuthorType()));
		}
		
		if (Objects.nonNull(species) && Objects.nonNull(species.getEnabled())) {
			criterias.add(Criteria.where("enabled").is(species.getEnabled()));
		}
	}
	
}
