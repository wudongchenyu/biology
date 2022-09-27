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
import pers.wdcy.biology.entity.BiologyDetail;
import pers.wdcy.biology.model.BiologyDetailRevise;
import pers.wdcy.biology.model.BiologyDetailSearch;
import pers.wdcy.biology.service.BiologyDetailService;
import reactor.core.publisher.Mono;

@Tag(name = "生物详情")
@RestController
@RequestMapping(path = "detail", consumes = MediaType.APPLICATION_NDJSON_VALUE, produces = MediaType.APPLICATION_NDJSON_VALUE)
public class BiologyDetailController {

	private @Autowired BiologyDetailService detailRoleService;
	
	@Operation(description = "新增用户角色")
	@PostMapping(path = "register")
	public Mono<Result<Boolean>> register(@RequestBody BiologyDetail bcr) {
		return detailRoleService.register(bcr);
	}
	
	@Operation(description = "删除用户角色")
	@GetMapping(path = "delete")
	public Mono<Result<Boolean>> delete(@RequestParam String code) {
		return detailRoleService.delete(code);
	}

	@Operation(description = "修改用户角色")
	@PostMapping(path = "revise")
	public Mono<Result<Boolean>> revise(@RequestBody BiologyDetailRevise species) {
		return detailRoleService.revise(species);
	}

	@Operation(description = "回收用户角色")
	@GetMapping(path = "recycle")
	public Mono<Result<Boolean>> recycle(@RequestParam String code) {
		return detailRoleService.recycle(code);
	}

	@Operation(description = "恢复用户角色")
	@GetMapping(path = "recover")
	public Mono<Result<Boolean>> recover(@RequestParam String code) {
		return detailRoleService.recover(code);
	}
	
	@Operation(description = "用户角色详情查询")
	@GetMapping(path = "find/detail")
	public Mono<Result<BiologyDetail>> findDetail(@RequestParam String code) {
		return detailRoleService.findDetail(code);
	}
	
	@Operation(description = "用户角色列表查询")
	@PostMapping(path = "find/list")
	public Mono<Result<List<BiologyDetail>>> findList(@RequestBody BiologyDetailSearch species) {
		return detailRoleService.findList(species);
	}
	
	@Operation(description = "用户角色分页查询")
	@PostMapping(path = "find/page")
	public Mono<PageResult<List<BiologyDetail>>> findPage(@RequestBody PageModel<BiologyDetailSearch> species) {
		return detailRoleService.findPage(species);
	}
	
}
