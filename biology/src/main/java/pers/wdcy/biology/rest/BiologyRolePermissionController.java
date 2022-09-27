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
import pers.wdcy.biology.entity.BiologyRolePermission;
import pers.wdcy.biology.model.BiologyRolePermissionRevise;
import pers.wdcy.biology.model.BiologyRolePermissionSearch;
import pers.wdcy.biology.service.BiologyRolePermissionService;
import reactor.core.publisher.Mono;

@Tag(name = "角色权限管理")
@RestController
@RequestMapping(path = "role/permission", consumes = MediaType.APPLICATION_NDJSON_VALUE, produces = MediaType.APPLICATION_NDJSON_VALUE)
public class BiologyRolePermissionController {

	private @Autowired BiologyRolePermissionService rolePermissionService;
	
	@Operation(description = "新增用户角色权限")
	@PostMapping(path = "register")
	public Mono<Result<Boolean>> register(@RequestBody BiologyRolePermission bcr) {
		return rolePermissionService.register(bcr);
	}
	
	@Operation(description = "删除用户角色权限")
	@GetMapping(path = "delete")
	public Mono<Result<Boolean>> delete(@RequestParam String code) {
		return rolePermissionService.delete(code);
	}

	@Operation(description = "修改用户角色权限")
	@PostMapping(path = "revise")
	public Mono<Result<Boolean>> revise(@RequestBody BiologyRolePermissionRevise species) {
		return rolePermissionService.revise(species);
	}

	@Operation(description = "用户角色权限详情查询")
	@GetMapping(path = "find/detail")
	public Mono<Result<BiologyRolePermission>> findDetail(@RequestParam String code) {
		return rolePermissionService.findDetail(code);
	}
	
	@Operation(description = "用户角色权限列表查询")
	@PostMapping(path = "find/list")
	public Mono<Result<List<BiologyRolePermission>>> findList(@RequestBody BiologyRolePermissionSearch species) {
		return rolePermissionService.findList(species);
	}
	
	@Operation(description = "用户角色权限分页查询")
	@PostMapping(path = "find/page")
	public Mono<PageResult<List<BiologyRolePermission>>> findPage(@RequestBody PageModel<BiologyRolePermissionSearch> species) {
		return rolePermissionService.findPage(species);
	}
	
}
