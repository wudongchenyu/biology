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
import pers.wdcy.biology.entity.BiologyPermission;
import pers.wdcy.biology.model.BiologyPermissionRevise;
import pers.wdcy.biology.model.BiologyPermissionSearch;
import pers.wdcy.biology.repository.BiologyPermissionRepository;
import pers.wdcy.biology.util.IdUtils;
import reactor.core.publisher.Mono;
import reactor.function.TupleUtils;

@Slf4j
@Service
public class BiologyPermissionService {
	
	private @Autowired BiologyPermissionRepository detailRepository;
	
	private @Autowired R2dbcEntityTemplate template;

	public Mono<Result<Boolean>> register(BiologyPermission bcr) {
		return Mono.just(bcr)
				.map(b -> {
					b.setCode(IdUtils.id());
					b.setFresh(true);
					b.setEnabled(true);
					return b;
				})
				.flatMap(detailRepository::save)
				.map(BiologyPermission::getCode)
				.map(StringUtils::hasText)
				.map(ResultUtils::success)
				.switchIfEmpty(Mono.just(failed()))
				.doOnError(error -> log.error("新增权限失败", error))
				.onErrorResume(error -> Mono.just(error("新增权限失败", false)));
	}

	public Mono<Result<Boolean>> delete(String code) {
		return detailRepository.deleteById(code).then(Mono.just(ResultUtils.success(true)))
				.switchIfEmpty(Mono.just(ResultUtils.success(true))).doOnError(error -> log.error("删除权限失败", error))
				.onErrorResume(error -> Mono.just(failed("删除权限类失败", false)));
	}
	
	public Mono<Result<Boolean>> revise(BiologyPermissionRevise revise) {
		return detailRepository.findById(revise.getCode()).map(spe -> {
			BeanUtils.copyProperties(revise, spe);
			spe.setFresh(false);
			spe.setOperationTime(LocalDateTime.now());
			spe.setOperator("");
			return spe;
		}).flatMap(detailRepository::save).map(BiologyPermission::getCode).map(StringUtils::hasText)
				.map(ResultUtils::success).switchIfEmpty(Mono.just(failed()))
				.doOnError(error -> log.error("修改权限失败", error))
				.onErrorResume(error -> Mono.just(error("修改权限失败", false)));
	}

	public Mono<Result<Boolean>> recycle(String code) {
		return detailRepository.recycle(code).map(ResultUtils::success).switchIfEmpty(Mono.just(failed()))
				.doOnError(error -> log.error("回收权限失败", error))
				.onErrorResume(error -> Mono.just(error("回收权限失败", false)));
	}

	public Mono<Result<Boolean>> recover(String code) {
		return detailRepository.recover(code).map(ResultUtils::success).switchIfEmpty(Mono.just(failed()))
				.doOnError(error -> log.error("恢复权限失败", error))
				.onErrorResume(error -> Mono.just(error("恢复权限失败", false)));
	}

	public Mono<Result<BiologyPermission>> findDetail(String code) {
		return detailRepository.findById(code).map(ResultUtils::success).switchIfEmpty(Mono.just(failed()))
				.doOnError(error -> log.error("权限详情查询失败", error))
				.onErrorResume(error -> Mono.just(error("权限详情查询失败", null)));
	}

	public Mono<Result<List<BiologyPermission>>> findList(BiologyPermissionSearch species) {
		List<Criteria> criterias = new ArrayList<>();
		criterias.add(Criteria.where("code").isNotNull());

		if (Objects.nonNull(species) && StringUtils.hasText(species.getCode())) {
			criterias.add(Criteria.where("code").is(species.getCode()));
		}

		return template.select(Query.query(CriteriaDefinition.from(criterias)), BiologyPermission.class).collectList()
				.map(ResultUtils::success).switchIfEmpty(Mono.just(failed()))
				.doOnError(error -> log.error("权限列表查询失败", error))
				.onErrorResume(error -> Mono.just(error("权限列表查询失败", null)));
	}

	public Mono<PageResult<List<BiologyPermission>>> findPage(PageModel<BiologyPermissionSearch> search) {
		List<Criteria> criterias = new ArrayList<>();
		criterias.add(Criteria.where("code").isNotNull());

		BiologyPermissionSearch species = search.getParamer();

		if (Objects.nonNull(species) && StringUtils.hasText(species.getCode())) {
			criterias.add(Criteria.where("code").is(species.getCode()));
		}
		
		return template
				.select(Query.query(CriteriaDefinition.from(criterias)).offset(search.offset()).limit(search.getSize()),
						BiologyPermission.class)
				.collectList()
				.zipWith(template.count(Query.query(CriteriaDefinition.from(criterias)), BiologyPermission.class))
				.map(TupleUtils.function((bs, count) -> {
					return new PageResult<List<BiologyPermission>>(ResultUtils.success(bs), search.getCurrent(),
							search.getSize(), count);
				}))
				.switchIfEmpty(Mono.just(new PageResult<List<BiologyPermission>>(ResultUtils.losing(), search.getCurrent(),
						search.getSize(), 0)))
				.doOnError(error -> log.error("权限列表查询失败", error)).onErrorResume(
						error -> Mono.just(new PageResult<List<BiologyPermission>>(ResultUtils.error("权限列表查询失败", null),
								search.getCurrent(), search.getSize(), 0)));
	}

}
