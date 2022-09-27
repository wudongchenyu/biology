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
import pers.wdcy.biology.entity.BiologyConsumerRole;
import pers.wdcy.biology.model.BiologyConsumerRoleRevise;
import pers.wdcy.biology.model.BiologyConsumerRoleSearch;
import pers.wdcy.biology.service.BiologyConsumerRoleService;
import reactor.core.publisher.Mono;

@Tag(name = "用户角色")
@RestController
@RequestMapping(path = "consumer/role", consumes = MediaType.APPLICATION_NDJSON_VALUE, produces = MediaType.APPLICATION_NDJSON_VALUE)
public class BiologyConsumerRoleController {

	private @Autowired BiologyConsumerRoleService consumerRoleService;
	
	@Operation(description = "新增用户角色")
	@PostMapping(path = "register")
	public Mono<Result<Boolean>> register(@RequestBody BiologyConsumerRole bcr) {
		return consumerRoleService.register(bcr);
	}
	
	@Operation(description = "删除用户角色")
	@GetMapping(path = "delete")
	public Mono<Result<Boolean>> delete(@RequestParam String code) {
		return consumerRoleService.delete(code);
	}

	@Operation(description = "修改用户角色")
	@PostMapping(path = "revise")
	public Mono<Result<Boolean>> revise(@RequestBody BiologyConsumerRoleRevise species) {
		return consumerRoleService.revise(species);
	}
	
	@Operation(description = "用户角色详情查询")
	@GetMapping(path = "find/detail")
	public Mono<Result<BiologyConsumerRole>> findDetail(@RequestParam String code) {
		return consumerRoleService.findDetail(code);
	}
	
	@Operation(description = "用户角色列表查询")
	@PostMapping(path = "find/list")
	public Mono<Result<List<BiologyConsumerRole>>> findList(@RequestBody BiologyConsumerRoleSearch species) {
		return consumerRoleService.findList(species);
	}
	
	@Operation(description = "用户角色分页查询")
	@PostMapping(path = "find/page")
	public Mono<PageResult<List<BiologyConsumerRole>>> findPage(@RequestBody PageModel<BiologyConsumerRoleSearch> species) {
		return consumerRoleService.findPage(species);
	}
	
}
