package pers.wdcy.biology.service;

import static com.wdcy.result.ResultUtils.error;
import static com.wdcy.result.ResultUtils.failed;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
import pers.wdcy.biology.entity.BiologyConsumerRole;
import pers.wdcy.biology.model.BiologyConsumerRoleRevise;
import pers.wdcy.biology.model.BiologyConsumerRoleSearch;
import pers.wdcy.biology.repository.BiologyConsumerRoleRepository;
import pers.wdcy.biology.util.IdUtils;
import reactor.core.publisher.Mono;
import reactor.function.TupleUtils;

@Slf4j
@Service
public class BiologyConsumerRoleService {
	
	private @Autowired BiologyConsumerRoleRepository consumerRoleRepository;
	
	private @Autowired R2dbcEntityTemplate template;

	public Mono<Result<Boolean>> register(BiologyConsumerRole bcr) {
		return Mono.just(bcr)
				.map(b -> {
					b.setCode(IdUtils.id());
					b.setFresh(true);
					return b;
				})
				.flatMap(consumerRoleRepository::save)
				.map(BiologyConsumerRole::getCode)
				.map(StringUtils::hasText)
				.map(ResultUtils::success)
				.switchIfEmpty(Mono.just(failed()))
				.doOnError(error -> log.error("新增用户角色失败", error))
				.onErrorResume(error -> Mono.just(error("新增用户角色失败", false)));
	}

	public Mono<Result<Boolean>> delete(String code) {
		return consumerRoleRepository.deleteById(code).then(Mono.just(ResultUtils.success(true)))
				.switchIfEmpty(Mono.just(ResultUtils.success(true))).doOnError(error -> log.error("删除用户角色失败", error))
				.onErrorResume(error -> Mono.just(failed("删除用户角色类失败", false)));
	}
	
	public Mono<Result<Boolean>> revise(BiologyConsumerRoleRevise revise) {
		return consumerRoleRepository.findById(revise.getCode()).map(spe -> {
			spe.setFresh(false);
			spe.setConsumerCode(revise.getConsumerCode());
			spe.setRoleCode(revise.getRoleCode());
			return spe;
		}).flatMap(consumerRoleRepository::save).map(BiologyConsumerRole::getCode).map(StringUtils::hasText)
				.map(ResultUtils::success).switchIfEmpty(Mono.just(failed()))
				.doOnError(error -> log.error("修改用户角色失败", error))
				.onErrorResume(error -> Mono.just(error("修改用户角色失败", false)));
	}

	public Mono<Result<BiologyConsumerRole>> findDetail(String code) {
		return consumerRoleRepository.findById(code).map(ResultUtils::success).switchIfEmpty(Mono.just(failed()))
				.doOnError(error -> log.error("用户角色详情查询失败", error))
				.onErrorResume(error -> Mono.just(error("用户角色详情查询失败", null)));
	}

	public Mono<Result<List<BiologyConsumerRole>>> findList(BiologyConsumerRoleSearch species) {
		List<Criteria> criterias = new ArrayList<>();
		criterias.add(Criteria.where("code").isNotNull());

		if (Objects.nonNull(species) && StringUtils.hasText(species.getCode())) {
			criterias.add(Criteria.where("code").is(species.getCode()));
		}
		
		if (Objects.nonNull(species) && StringUtils.hasText(species.getRoleCode())) {
			criterias.add(Criteria.where("role_code").is(species.getRoleCode()));
		}
		
		if (Objects.nonNull(species) && StringUtils.hasText(species.getConsumerCode())) {
			criterias.add(Criteria.where("consumer_code").is(species.getConsumerCode()));
		}

		return template.select(Query.query(CriteriaDefinition.from(criterias)), BiologyConsumerRole.class).collectList()
				.map(ResultUtils::success).switchIfEmpty(Mono.just(failed()))
				.doOnError(error -> log.error("用户角色列表查询失败", error))
				.onErrorResume(error -> Mono.just(error("用户角色列表查询失败", null)));
	}

	public Mono<PageResult<List<BiologyConsumerRole>>> findPage(PageModel<BiologyConsumerRoleSearch> search) {
		List<Criteria> criterias = new ArrayList<>();
		criterias.add(Criteria.where("code").isNotNull());

		BiologyConsumerRoleSearch species = search.getParamer();

		if (Objects.nonNull(species) && StringUtils.hasText(species.getCode())) {
			criterias.add(Criteria.where("code").is(species.getCode()));
		}
		
		if (Objects.nonNull(species) && StringUtils.hasText(species.getRoleCode())) {
			criterias.add(Criteria.where("role_code").is(species.getRoleCode()));
		}
		
		if (Objects.nonNull(species) && StringUtils.hasText(species.getConsumerCode())) {
			criterias.add(Criteria.where("consumer_code").is(species.getConsumerCode()));
		}
		
		return template
				.select(Query.query(CriteriaDefinition.from(criterias)).offset(search.offset()).limit(search.getSize()),
						BiologyConsumerRole.class)
				.collectList()
				.zipWith(template.count(Query.query(CriteriaDefinition.from(criterias)), BiologyConsumerRole.class))
				.map(TupleUtils.function((bs, count) -> {
					return new PageResult<List<BiologyConsumerRole>>(ResultUtils.success(bs), search.getCurrent(),
							search.getSize(), count);
				}))
				.switchIfEmpty(Mono.just(new PageResult<List<BiologyConsumerRole>>(ResultUtils.losing(), search.getCurrent(),
						search.getSize(), 0)))
				.doOnError(error -> log.error("用户角色列表查询失败", error)).onErrorResume(
						error -> Mono.just(new PageResult<List<BiologyConsumerRole>>(ResultUtils.error("用户角色列表查询失败", null),
								search.getCurrent(), search.getSize(), 0)));
	}

}
