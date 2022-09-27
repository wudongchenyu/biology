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
import pers.wdcy.biology.entity.BiologyPermission;
import pers.wdcy.biology.model.BiologyPermissionRevise;
import pers.wdcy.biology.model.BiologyPermissionSearch;
import pers.wdcy.biology.service.BiologyPermissionService;
import reactor.core.publisher.Mono;

@Tag(name = "权限")
@RestController
@RequestMapping(path = "permisson", consumes = MediaType.APPLICATION_NDJSON_VALUE, produces = MediaType.APPLICATION_NDJSON_VALUE)
public class BiologyPermissionController {

	private @Autowired BiologyPermissionService permissionRoleService;
	
	@Operation(description = "新增用户角色")
	@PostMapping(path = "register")
	public Mono<Result<Boolean>> register(@RequestBody BiologyPermission bcr) {
		return permissionRoleService.register(bcr);
	}
	
	@Operation(description = "删除用户角色")
	@GetMapping(path = "delete")
	public Mono<Result<Boolean>> delete(@RequestParam String code) {
		return permissionRoleService.delete(code);
	}

	@Operation(description = "修改用户角色")
	@PostMapping(path = "revise")
	public Mono<Result<Boolean>> revise(@RequestBody BiologyPermissionRevise species) {
		return permissionRoleService.revise(species);
	}

	@Operation(description = "回收用户角色")
	@GetMapping(path = "recycle")
	public Mono<Result<Boolean>> recycle(@RequestParam String code) {
		return permissionRoleService.recycle(code);
	}

	@Operation(description = "恢复用户角色")
	@GetMapping(path = "recover")
	public Mono<Result<Boolean>> recover(@RequestParam String code) {
		return permissionRoleService.recover(code);
	}
	
	@Operation(description = "用户角色详情查询")
	@GetMapping(path = "find/detail")
	public Mono<Result<BiologyPermission>> findDetail(@RequestParam String code) {
		return permissionRoleService.findDetail(code);
	}
	
	@Operation(description = "用户角色列表查询")
	@PostMapping(path = "find/list")
	public Mono<Result<List<BiologyPermission>>> findList(@RequestBody BiologyPermissionSearch species) {
		return permissionRoleService.findList(species);
	}
	
	@Operation(description = "用户角色分页查询")
	@PostMapping(path = "find/page")
	public Mono<PageResult<List<BiologyPermission>>> findPage(@RequestBody PageModel<BiologyPermissionSearch> species) {
		return permissionRoleService.findPage(species);
	}
	
}
