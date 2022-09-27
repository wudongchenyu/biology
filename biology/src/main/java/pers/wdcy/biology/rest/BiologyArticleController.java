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
import pers.wdcy.biology.entity.BiologyArticle;
import pers.wdcy.biology.model.BiologyArticleRevise;
import pers.wdcy.biology.model.BiologyArticleSearch;
import pers.wdcy.biology.service.BiologyArticleService;
import reactor.core.publisher.Mono;

@Tag(name = "艺术品")
@RestController
@RequestMapping(path = "article", consumes = MediaType.APPLICATION_NDJSON_VALUE, produces = MediaType.APPLICATION_NDJSON_VALUE)
public class BiologyArticleController {

	private @Autowired BiologyArticleService articleService;
	
	@Operation(description = "新增艺术品")
	@PostMapping(path = "register")
	public Mono<Result<Boolean>> register(@RequestBody BiologyArticle article) {
		return articleService.register(article);
	}
	
	@Operation(description = "删除艺术品")
	@GetMapping(path = "delete")
	public Mono<Result<Boolean>> delete(@RequestParam String code) {
		return articleService.delete(code);
	}

	@Operation(description = "修改艺术品")
	@PostMapping(path = "revise")
	public Mono<Result<Boolean>> revise(@RequestBody BiologyArticleRevise species) {
		return articleService.revise(species);
	}

	@Operation(description = "回收艺术品")
	@GetMapping(path = "recycle")
	public Mono<Result<Boolean>> recycle(@RequestParam String code) {
		return articleService.recycle(code);
	}

	@Operation(description = "恢复艺术品")
	@GetMapping(path = "recover")
	public Mono<Result<Boolean>> recover(@RequestParam String code) {
		return articleService.recover(code);
	}
	
	@Operation(description = "艺术品详情查询")
	@GetMapping(path = "find/detail")
	public Mono<Result<BiologyArticle>> findDetail(@RequestParam String code) {
		return articleService.findDetail(code);
	}
	
	@Operation(description = "艺术品列表查询")
	@PostMapping(path = "find/list")
	public Mono<Result<List<BiologyArticle>>> findList(@RequestBody BiologyArticleSearch species) {
		return articleService.findList(species);
	}
	
	@Operation(description = "艺术品分页查询")
	@PostMapping(path = "find/page")
	public Mono<PageResult<List<BiologyArticle>>> findPage(@RequestBody PageModel<BiologyArticleSearch> species) {
		return articleService.findPage(species);
	}
	
}
