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
import pers.wdcy.biology.entity.BiologyRolePermission;
import pers.wdcy.biology.model.BiologyRolePermissionRevise;
import pers.wdcy.biology.model.BiologyRolePermissionSearch;
import pers.wdcy.biology.repository.BiologyRolePermissionRepository;
import pers.wdcy.biology.util.IdUtils;
import reactor.core.publisher.Mono;
import reactor.function.TupleUtils;

@Slf4j
@Service
public class BiologyRolePermissionService {
	
	private @Autowired BiologyRolePermissionRepository rolePermissionRepository;
	
	private @Autowired R2dbcEntityTemplate template;

	public Mono<Result<Boolean>> register(BiologyRolePermission bcr) {
		return Mono.just(bcr)
				.map(b -> {
					b.setCode(IdUtils.id());
					b.setFresh(true);
					return b;
				})
				.flatMap(rolePermissionRepository::save)
				.map(BiologyRolePermission::getCode)
				.map(StringUtils::hasText)
				.map(ResultUtils::success)
				.switchIfEmpty(Mono.just(failed()))
				.doOnError(error -> log.error("新增角色权限失败", error))
				.onErrorResume(error -> Mono.just(error("新增角色权限失败", false)));
	}

	public Mono<Result<Boolean>> delete(String code) {
		return rolePermissionRepository.deleteById(code).then(Mono.just(ResultUtils.success(true)))
				.switchIfEmpty(Mono.just(ResultUtils.success(true))).doOnError(error -> log.error("删除角色权限失败", error))
				.onErrorResume(error -> Mono.just(failed("删除角色权限类失败", false)));
	}
	
	public Mono<Result<Boolean>> revise(BiologyRolePermissionRevise revise) {
		return rolePermissionRepository.findById(revise.getCode()).map(spe -> {
			spe.setFresh(false);
			return spe;
		}).flatMap(rolePermissionRepository::save).map(BiologyRolePermission::getCode).map(StringUtils::hasText)
				.map(ResultUtils::success).switchIfEmpty(Mono.just(failed()))
				.doOnError(error -> log.error("修改角色权限失败", error))
				.onErrorResume(error -> Mono.just(error("修改角色权限失败", false)));
	}

	public Mono<Result<BiologyRolePermission>> findDetail(String code) {
		return rolePermissionRepository.findById(code).map(ResultUtils::success).switchIfEmpty(Mono.just(failed()))
				.doOnError(error -> log.error("角色权限详情查询失败", error))
				.onErrorResume(error -> Mono.just(error("角色权限详情查询失败", null)));
	}

	public Mono<Result<List<BiologyRolePermission>>> findList(BiologyRolePermissionSearch species) {
		List<Criteria> criterias = new ArrayList<>();
		criterias.add(Criteria.where("code").isNotNull());

		if (Objects.nonNull(species) && StringUtils.hasText(species.getCode())) {
			criterias.add(Criteria.where("code").is(species.getCode()));
		}

		return template.select(Query.query(CriteriaDefinition.from(criterias)), BiologyRolePermission.class).collectList()
				.map(ResultUtils::success).switchIfEmpty(Mono.just(failed()))
				.doOnError(error -> log.error("角色权限列表查询失败", error))
				.onErrorResume(error -> Mono.just(error("角色权限列表查询失败", null)));
	}

	public Mono<PageResult<List<BiologyRolePermission>>> findPage(PageModel<BiologyRolePermissionSearch> search) {
		List<Criteria> criterias = new ArrayList<>();
		criterias.add(Criteria.where("code").isNotNull());

		BiologyRolePermissionSearch species = search.getParamer();

		if (Objects.nonNull(species) && StringUtils.hasText(species.getCode())) {
			criterias.add(Criteria.where("code").is(species.getCode()));
		}
		
		return template
				.select(Query.query(CriteriaDefinition.from(criterias)).offset(search.offset()).limit(search.getSize()),
						BiologyRolePermission.class)
				.collectList()
				.zipWith(template.count(Query.query(CriteriaDefinition.from(criterias)), BiologyRolePermission.class))
				.map(TupleUtils.function((bs, count) -> {
					return new PageResult<List<BiologyRolePermission>>(ResultUtils.success(bs), search.getCurrent(),
							search.getSize(), count);
				}))
				.switchIfEmpty(Mono.just(new PageResult<List<BiologyRolePermission>>(ResultUtils.losing(), search.getCurrent(),
						search.getSize(), 0)))
				.doOnError(error -> log.error("角色权限列表查询失败", error)).onErrorResume(
						error -> Mono.just(new PageResult<List<BiologyRolePermission>>(ResultUtils.error("角色权限列表查询失败", null),
								search.getCurrent(), search.getSize(), 0)));
	}

}
