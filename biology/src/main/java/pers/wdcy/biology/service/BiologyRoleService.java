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
import pers.wdcy.biology.entity.BiologyRole;
import pers.wdcy.biology.model.BiologyRoleRevise;
import pers.wdcy.biology.model.BiologyRoleSearch;
import pers.wdcy.biology.repository.BiologyRoleRepository;
import pers.wdcy.biology.util.IdUtils;
import reactor.core.publisher.Mono;
import reactor.function.TupleUtils;

@Slf4j
@Service
public class BiologyRoleService {
	
	private @Autowired BiologyRoleRepository roleRepository;
	
	private @Autowired R2dbcEntityTemplate template;

	public Mono<Result<Boolean>> register(BiologyRole bcr) {
		return Mono.just(bcr)
				.map(b -> {
					b.setCode(IdUtils.id());
					b.setFresh(true);
					return b;
				})
				.flatMap(roleRepository::save)
				.map(BiologyRole::getCode)
				.map(StringUtils::hasText)
				.map(ResultUtils::success)
				.switchIfEmpty(Mono.just(failed()))
				.doOnError(error -> log.error("新增角色失败", error))
				.onErrorResume(error -> Mono.just(error("新增角色失败", false)));
	}

	public Mono<Result<Boolean>> delete(String code) {
		return roleRepository.deleteById(code).then(Mono.just(ResultUtils.success(true)))
				.switchIfEmpty(Mono.just(ResultUtils.success(true))).doOnError(error -> log.error("删除角色失败", error))
				.onErrorResume(error -> Mono.just(failed("删除角色失败", false)));
	}
	
	public Mono<Result<Boolean>> revise(BiologyRoleRevise revise) {
		return roleRepository.findById(revise.getCode()).map(spe -> {
			spe.setFresh(false);
			return spe;
		}).flatMap(roleRepository::save).map(BiologyRole::getCode).map(StringUtils::hasText)
				.map(ResultUtils::success).switchIfEmpty(Mono.just(failed()))
				.doOnError(error -> log.error("修改角色失败", error))
				.onErrorResume(error -> Mono.just(error("修改角色失败", false)));
	}

	public Mono<Result<Boolean>> recycle(String code) {
		return roleRepository.recycle(code).map(ResultUtils::success).switchIfEmpty(Mono.just(failed()))
				.doOnError(error -> log.error("回收角色失败", error))
				.onErrorResume(error -> Mono.just(error("回收角色失败", false)));
	}

	public Mono<Result<Boolean>> recover(String code) {
		return roleRepository.recover(code).map(ResultUtils::success).switchIfEmpty(Mono.just(failed()))
				.doOnError(error -> log.error("恢复角色失败", error))
				.onErrorResume(error -> Mono.just(error("恢复角色失败", false)));
	}

	public Mono<Result<BiologyRole>> findDetail(String code) {
		return roleRepository.findById(code).map(ResultUtils::success).switchIfEmpty(Mono.just(failed()))
				.doOnError(error -> log.error("角色详情查询失败", error))
				.onErrorResume(error -> Mono.just(error("角色详情查询失败", null)));
	}

	public Mono<Result<List<BiologyRole>>> findList(BiologyRoleSearch species) {
		List<Criteria> criterias = new ArrayList<>();
		criterias.add(Criteria.where("code").isNotNull());

		if (Objects.nonNull(species) && StringUtils.hasText(species.getCode())) {
			criterias.add(Criteria.where("code").is(species.getCode()));
		}

		return template.select(Query.query(CriteriaDefinition.from(criterias)), BiologyRole.class).collectList()
				.map(ResultUtils::success).switchIfEmpty(Mono.just(failed()))
				.doOnError(error -> log.error("角色列表查询失败", error))
				.onErrorResume(error -> Mono.just(error("角色列表查询失败", null)));
	}

	public Mono<PageResult<List<BiologyRole>>> findPage(PageModel<BiologyRoleSearch> search) {
		List<Criteria> criterias = new ArrayList<>();
		criterias.add(Criteria.where("code").isNotNull());

		BiologyRoleSearch species = search.getParamer();

		if (Objects.nonNull(species) && StringUtils.hasText(species.getCode())) {
			criterias.add(Criteria.where("code").is(species.getCode()));
		}
		
		return template
				.select(Query.query(CriteriaDefinition.from(criterias)).offset(search.offset()).limit(search.getSize()),
						BiologyRole.class)
				.collectList()
				.zipWith(template.count(Query.query(CriteriaDefinition.from(criterias)), BiologyRole.class))
				.map(TupleUtils.function((bs, count) -> {
					return new PageResult<List<BiologyRole>>(ResultUtils.success(bs), search.getCurrent(),
							search.getSize(), count);
				}))
				.switchIfEmpty(Mono.just(new PageResult<List<BiologyRole>>(ResultUtils.losing(), search.getCurrent(),
						search.getSize(), 0)))
				.doOnError(error -> log.error("角色列表查询失败", error)).onErrorResume(
						error -> Mono.just(new PageResult<List<BiologyRole>>(ResultUtils.error("角色列表查询失败", null),
								search.getCurrent(), search.getSize(), 0)));
	}

}
