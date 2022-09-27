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
import pers.wdcy.biology.entity.BiologySpecies;
import pers.wdcy.biology.model.BiologySpeciesRegister;
import pers.wdcy.biology.model.BiologySpeciesRevise;
import pers.wdcy.biology.model.BiologySpeciesSearch;
import pers.wdcy.biology.model.BiologySpeciesTree;
import pers.wdcy.biology.model.BiologySpeciesTreeSearch;
import pers.wdcy.biology.service.BiologySpeciesService;
import reactor.core.publisher.Mono;

@Tag(name = "生物分类")
@RestController
@RequestMapping(path = "species", consumes = MediaType.APPLICATION_NDJSON_VALUE, produces = MediaType.APPLICATION_NDJSON_VALUE)
public class BiologySpeciesController {
	
	private @Autowired BiologySpeciesService biologySpeciesService;
	
	@Operation(description = "新增生物分类")
	@PostMapping(path = "register")
	public Mono<Result<Boolean>> register(@RequestBody BiologySpeciesRegister species) {
		return biologySpeciesService.register(species);
	}

	@Operation(description = "删除生物分类")
	@GetMapping(path = "delete")
	public Mono<Result<Boolean>> delete(@RequestParam String code) {
		return biologySpeciesService.delete(code);
	}

	@Operation(description = "修改生物分类")
	@PostMapping(path = "revise")
	public Mono<Result<Boolean>> revise(@RequestBody BiologySpeciesRevise species) {
		return biologySpeciesService.revise(species);
	}

	@Operation(description = "回收生物分类")
	@GetMapping(path = "recycle")
	public Mono<Result<Boolean>> recycle(@RequestParam String code) {
		return biologySpeciesService.recycle(code);
	}

	@Operation(description = "恢复生物分类")
	@GetMapping(path = "recover")
	public Mono<Result<Boolean>> recover(@RequestParam String code) {
		return biologySpeciesService.recover(code);
	}
	
	@Operation(description = "生物分类详情查询")
	@GetMapping(path = "find/detail")
	public Mono<Result<BiologySpecies>> findDetail(@RequestParam String code) {
		return biologySpeciesService.findDetail(code);
	}
	
	@Operation(description = "生物分类列表查询")
	@PostMapping(path = "find/list")
	public Mono<Result<List<BiologySpecies>>> findList(@RequestBody BiologySpeciesSearch species) {
		return biologySpeciesService.findList(species);
	}
	
	@Operation(description = "生物分类分页查询")
	@PostMapping(path = "find/page")
	public Mono<PageResult<List<BiologySpecies>>> findPage(@RequestBody PageModel<BiologySpeciesSearch> species) {
		return biologySpeciesService.findPage(species);
	}
	
	@Operation(description = "生物分类树形结构查询")
	@PostMapping(path = "find/tree")
	public Mono<Result<List<BiologySpeciesTree>>> findTree(@RequestBody Paramer<BiologySpeciesTreeSearch> species) {
		return biologySpeciesService.findTree(species);
	}

}
