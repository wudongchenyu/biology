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
import com.wdcy.result.Result;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import pers.wdcy.biology.entity.BiologyAuthor;
import pers.wdcy.biology.model.BiologyAuthorRevise;
import pers.wdcy.biology.model.BiologyAuthorSearch;
import pers.wdcy.biology.service.BiologyAuthorService;
import reactor.core.publisher.Mono;

@Tag(name = "作者")
@RestController
@RequestMapping(path = "author", consumes = MediaType.APPLICATION_NDJSON_VALUE, produces = MediaType.APPLICATION_NDJSON_VALUE)
public class BiologyAuthorController {

private @Autowired BiologyAuthorService authorService;
	
	@Operation(description = "新增作者")
	@PostMapping(path = "register")
	public Mono<Result<Boolean>> register(@RequestBody BiologyAuthor author) {
		return authorService.register(author);
	}
	
	@Operation(description = "删除作者")
	@GetMapping(path = "delete")
	public Mono<Result<Boolean>> delete(@RequestParam String code) {
		return authorService.delete(code);
	}

	@Operation(description = "修改作者")
	@PostMapping(path = "revise")
	public Mono<Result<Boolean>> revise(@RequestBody BiologyAuthorRevise species) {
		return authorService.revise(species);
	}

	@Operation(description = "回收作者")
	@GetMapping(path = "recycle")
	public Mono<Result<Boolean>> recycle(@RequestParam String code) {
		return authorService.recycle(code);
	}

	@Operation(description = "恢复作者")
	@GetMapping(path = "recover")
	public Mono<Result<Boolean>> recover(@RequestParam String code) {
		return authorService.recover(code);
	}
	
	@Operation(description = "作者详情查询")
	@GetMapping(path = "find/detail")
	public Mono<Result<BiologyAuthor>> findDetail(@RequestParam String code) {
		return authorService.findDetail(code);
	}
	
	@Operation(description = "作者列表查询")
	@PostMapping(path = "find/list")
	public Mono<Result<List<BiologyAuthor>>> findList(@RequestBody BiologyAuthorSearch species) {
		return authorService.findList(species);
	}
	
	@Operation(description = "作者分页查询")
	@PostMapping(path = "find/page")
	public Mono<PageResult<List<BiologyAuthor>>> findPage(@RequestBody PageModel<BiologyAuthorSearch> species) {
		return authorService.findPage(species);
	}
	
}
