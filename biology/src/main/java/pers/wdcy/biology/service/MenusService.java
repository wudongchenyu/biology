package pers.wdcy.biology.service;

import static com.wdcy.result.ResultUtils.error;
import static com.wdcy.result.ResultUtils.failed;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
import com.wdcy.result.Paramer;
import com.wdcy.result.Result;
import com.wdcy.result.ResultUtils;

import lombok.extern.slf4j.Slf4j;
import pers.wdcy.biology.entity.Menus;
import pers.wdcy.biology.model.MenusRegister;
import pers.wdcy.biology.model.MenusRevise;
import pers.wdcy.biology.model.MenusSearch;
import pers.wdcy.biology.model.MenusTree;
import pers.wdcy.biology.model.MenusTreeSearch;
import pers.wdcy.biology.repository.MenusRepository;
import pers.wdcy.biology.util.IdUtils;
import reactor.core.publisher.Mono;
import reactor.function.TupleUtils;

@Slf4j
@Service
public class MenusService {

	private @Autowired MenusRepository menusRepository;

	private @Autowired R2dbcEntityTemplate template;
	
	public Mono<Result<List<Menus>>> index() {
		return menusRepository.findAllByLevel(1)
				.collectList()
				.map(ResultUtils::success).switchIfEmpty(Mono.just(failed()))
				.doOnError(error -> log.error("查询首页菜单失败", error))
				.onErrorResume(error -> Mono.just(error("查询首页菜单失败", null)));
	}

	public Mono<Result<Boolean>> register(MenusRegister species) {
		return Mono.just(species)
				.map(spe -> {
					Menus bs = new Menus();
					BeanUtils.copyProperties(spe, bs);
					bs.setCode(IdUtils.id());
					bs.setCreationTime(LocalDateTime.now());
					bs.setCreator(null);
					bs.setEnabled(true);
					bs.setFresh(true);
					return bs;
				})
				.flatMap(menusRepository::save).map(Menus::getCode)
				.map(StringUtils::hasText).map(ResultUtils::success).switchIfEmpty(Mono.just(failed()))
				.doOnError(error -> log.error("新增生物分类失败", error))
				.onErrorResume(error -> Mono.just(error("新增生物分类失败", false)));
	}

	public Mono<Result<Boolean>> delete(String code) {
		return menusRepository.deleteById(code).then(Mono.just(ResultUtils.success(true)))
				.switchIfEmpty(Mono.just(ResultUtils.success(true))).doOnError(error -> log.error("删除生物分类失败", error))
				.onErrorResume(error -> Mono.just(failed("删除生物分类失败", false)));
	}

	public Mono<Result<Boolean>> revise(MenusRevise species) {
		return menusRepository.findById(species.getCode()).map(spe -> {
			spe.setIntro(species.getIntro());
			spe.setFresh(false);
			spe.setLevel(species.getLevel());
			spe.setOperationTime(LocalDateTime.now());
			spe.setParent(species.getParent());
			spe.setMenusName(species.getMenusName());
			return spe;
		}).flatMap(menusRepository::save).map(Menus::getCode).map(StringUtils::hasText)
				.map(ResultUtils::success).switchIfEmpty(Mono.just(failed()))
				.doOnError(error -> log.error("修改生物分类失败", error))
				.onErrorResume(error -> Mono.just(error("修改生物分类失败", false)));
	}

	public Mono<Result<Boolean>> recycle(String code) {
		return menusRepository.recycle(code).map(ResultUtils::success).switchIfEmpty(Mono.just(failed()))
				.doOnError(error -> log.error("回收生物分类失败", error))
				.onErrorResume(error -> Mono.just(error("回收生物分类失败", false)));
	}

	public Mono<Result<Boolean>> recover(String code) {
		return menusRepository.recover(code).map(ResultUtils::success).switchIfEmpty(Mono.just(failed()))
				.doOnError(error -> log.error("恢复生物分类失败", error))
				.onErrorResume(error -> Mono.just(error("恢复生物分类失败", false)));
	}

	public Mono<Result<Menus>> findDetail(String code) {
		return menusRepository.findById(code).map(ResultUtils::success).switchIfEmpty(Mono.just(failed()))
				.doOnError(error -> log.error("生物分类详情查询失败", error))
				.onErrorResume(error -> Mono.just(error("生物分类详情查询失败", null)));
	}

	public Mono<Result<List<Menus>>> findList(MenusSearch species) {
		List<Criteria> criterias = new ArrayList<>();
		criterias.add(Criteria.where("code").isNotNull());

		if (Objects.nonNull(species) && StringUtils.hasText(species.getCode())) {
			criterias.add(Criteria.where("code").is(species.getCode()));
		}

		if (Objects.nonNull(species) && StringUtils.hasText(species.getCreator())) {
			criterias.add(Criteria.where("creator").is(species.getCreator()));
		}

		if (Objects.nonNull(species) && StringUtils.hasText(species.getIntro())) {
			criterias.add(Criteria.where("intro").is(species.getIntro()));
		}

		if (Objects.nonNull(species) && StringUtils.hasText(species.getOperator())) {
			criterias.add(Criteria.where("operator").is(species.getOperator()));
		}

		if (Objects.nonNull(species) && StringUtils.hasText(species.getMenusName())) {
			criterias.add(Criteria.where("menus_name").like("%" + species.getMenusName() + "%"));
		}

		if (Objects.nonNull(species) && Objects.nonNull(species.getLevel())) {
			criterias.add(Criteria.where("level").is(species.getLevel()));
		}

		if (Objects.nonNull(species) && Objects.nonNull(species.getCreationStartTime())) {
			criterias.add(Criteria.where("creation_time").greaterThanOrEquals(species.getCreationStartTime()));
		}

		if (Objects.nonNull(species) && Objects.nonNull(species.getCreationEndTime())) {
			criterias.add(Criteria.where("creation_time").lessThanOrEquals(species.getCreationEndTime()));
		}

		if (Objects.nonNull(species) && Objects.nonNull(species.getOperationStartTime())) {
			criterias.add(Criteria.where("operation_time").greaterThanOrEquals(species.getOperationStartTime()));
		}

		if (Objects.nonNull(species) && Objects.nonNull(species.getOperationEndTime())) {
			criterias.add(Criteria.where("operation_time").lessThanOrEquals(species.getOperationEndTime()));
		}

		return template.select(Query.query(CriteriaDefinition.from(criterias)), Menus.class).collectList()
				.map(ResultUtils::success).switchIfEmpty(Mono.just(failed()))
				.doOnError(error -> log.error("生物分类列表查询失败", error))
				.onErrorResume(error -> Mono.just(error("生物分类列表查询失败", null)));
	}

	public Mono<PageResult<List<Menus>>> findPage(PageModel<MenusSearch> search) {
		List<Criteria> criterias = new ArrayList<>();
		criterias.add(Criteria.where("code").isNotNull());

		MenusSearch species = search.getParamer();

		if (Objects.nonNull(species) && StringUtils.hasText(species.getCode())) {
			criterias.add(Criteria.where("code").is(species.getCode()));
		}

		if (Objects.nonNull(species) && StringUtils.hasText(species.getCreator())) {
			criterias.add(Criteria.where("creator").is(species.getCreator()));
		}

		if (Objects.nonNull(species) && StringUtils.hasText(species.getIntro())) {
			criterias.add(Criteria.where("intro").is(species.getIntro()));
		}

		if (Objects.nonNull(species) && StringUtils.hasText(species.getOperator())) {
			criterias.add(Criteria.where("operator").is(species.getOperator()));
		}

		if (Objects.nonNull(species) && StringUtils.hasText(species.getMenusName())) {
			criterias.add(Criteria.where("menus_name").like("%" + species.getMenusName() + "%"));
		}

		if (Objects.nonNull(species) && Objects.nonNull(species.getLevel())) {
			criterias.add(Criteria.where("level").is(species.getLevel()));
		}

		if (Objects.nonNull(species) && Objects.nonNull(species.getCreationStartTime())) {
			criterias.add(Criteria.where("creation_time").greaterThanOrEquals(species.getCreationStartTime()));
		}

		if (Objects.nonNull(species) && Objects.nonNull(species.getCreationEndTime())) {
			criterias.add(Criteria.where("creation_time").lessThanOrEquals(species.getCreationEndTime()));
		}

		if (Objects.nonNull(species) && Objects.nonNull(species.getOperationStartTime())) {
			criterias.add(Criteria.where("operation_time").greaterThanOrEquals(species.getOperationStartTime()));
		}

		if (Objects.nonNull(species) && Objects.nonNull(species.getOperationEndTime())) {
			criterias.add(Criteria.where("operation_time").lessThanOrEquals(species.getOperationEndTime()));
		}
		return template
				.select(Query.query(CriteriaDefinition.from(criterias)).offset(search.offset()).limit(search.getSize()),
						Menus.class)
				.collectList()
				.zipWith(template.count(Query.query(CriteriaDefinition.from(criterias)), Menus.class))
				.map(TupleUtils.function((bs, count) -> {
					return new PageResult<List<Menus>>(ResultUtils.success(bs), search.getCurrent(),
							search.getSize(), count);
				}))
				.switchIfEmpty(Mono.just(new PageResult<List<Menus>>(ResultUtils.losing(), search.getCurrent(),
						search.getSize(), 0)))
				.doOnError(error -> log.error("生物分类列表查询失败", error)).onErrorResume(
						error -> Mono.just(new PageResult<List<Menus>>(ResultUtils.error("生物分类列表查询失败", null),
								search.getCurrent(), search.getSize(), 0)));
	}

	public Mono<Result<List<MenusTree>>> findTree(Paramer<MenusTreeSearch> search) {
		return menusRepository.findAll()
				.map(specie -> this.transform(specie))
				.collectList()
				.map(species -> {
					
					List<MenusTree> collect = species.stream().filter(specie -> Objects.nonNull(search.getData()) 
							&& StringUtils.hasText(search.getData().getParent()) ? specie.getParent().equals(search.getData().getParent()) : !StringUtils.hasText(specie.getParent()))
					.map(specie -> {
						specie.setChildren(this.acquireChild(specie, species));
						return specie;
					}).collect(Collectors.toList());
					return collect;
				})
				.map(ResultUtils::success).switchIfEmpty(Mono.just(failed()))
				.doOnError(error -> log.error("生物分类树形结构查询失败", error))
				.onErrorResume(error -> Mono.just(error("生物分类树形结构查询失败", null)));
	}
	
	private MenusTree transform(Menus specie) {
		MenusTree tree = new MenusTree();
		BeanUtils.copyProperties(specie, tree);
		return tree;
	}

	private List<MenusTree> acquireChild(MenusTree parent, List<MenusTree> species) {
		return species.stream().filter(specie -> StringUtils.hasText(specie.getParent()) && specie.getParent().equals(parent.getCode()))
				.map(specie -> {
					specie.setChildren(this.acquireChild(specie, species));
					return specie;
				}).collect(Collectors.toList());
	}

}
