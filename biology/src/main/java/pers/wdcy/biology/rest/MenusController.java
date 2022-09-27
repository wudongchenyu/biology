package pers.wdcy.biology.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wdcy.result.PageModel;
import com.wdcy.result.PageResult;
import com.wdcy.result.Paramer;
import com.wdcy.result.Result;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import pers.wdcy.biology.entity.Menus;
import pers.wdcy.biology.model.MenusRegister;
import pers.wdcy.biology.model.MenusRevise;
import pers.wdcy.biology.model.MenusSearch;
import pers.wdcy.biology.model.MenusTree;
import pers.wdcy.biology.model.MenusTreeSearch;
import pers.wdcy.biology.service.MenusService;
import reactor.core.publisher.Mono;

@Tag(name = "菜单")
@RestController
@RequestMapping(path = "menus", consumes = MediaType.APPLICATION_NDJSON_VALUE, produces = MediaType.APPLICATION_NDJSON_VALUE)
public class MenusController {
	
	private @Autowired MenusService menusService;
	
	@Operation(description = "首页")
	@GetMapping(path = "index")
	public Mono<Result<List<Menus>>> index() {
		return menusService.index();
	}
	
	@Operation(description = "新增生物分类")
	@PostMapping(path = "register")
	public Mono<Result<Boolean>> register(@RequestBody MenusRegister species) {
		return menusService.register(species);
	}

	@Operation(description = "删除生物分类")
	@GetMapping(path = "delete")
	public Mono<Result<Boolean>> delete(@RequestParam String code) {
		return menusService.delete(code);
	}

	@Operation(description = "修改生物分类")
	@PostMapping(path = "revise")
	public Mono<Result<Boolean>> revise(@RequestBody MenusRevise species) {
		return menusService.revise(species);
	}

	@Operation(description = "回收生物分类")
	@GetMapping(path = "recycle")
	public Mono<Result<Boolean>> recycle(@RequestParam String code) {
		return menusService.recycle(code);
	}

	@Operation(description = "恢复生物分类")
	@GetMapping(path = "recover")
	public Mono<Result<Boolean>> recover(@RequestParam String code) {
		return menusService.recover(code);
	}
	
	@Operation(description = "生物分类详情查询")
	@GetMapping(path = "find/detail")
	public Mono<Result<Menus>> findDetail(@RequestParam String code) {
		return menusService.findDetail(code);
	}
	
	@Operation(description = "生物分类列表查询")
	@PostMapping(path = "find/list")
	public Mono<Result<List<Menus>>> findList(@RequestBody MenusSearch species) {
		return menusService.findList(species);
	}
	
	@Operation(description = "生物分类分页查询")
	@PostMapping(path = "find/page")
	public Mono<PageResult<List<Menus>>> findPage(@RequestBody PageModel<MenusSearch> species) {
		return menusService.findPage(species);
	}
	
	@Operation(description = "生物分类树形结构查询")
	@PostMapping(path = "find/tree")
	public Mono<Result<List<MenusTree>>> findTree(@RequestBody Paramer<MenusTreeSearch> species) {
		return menusService.findTree(species);
	}

}
