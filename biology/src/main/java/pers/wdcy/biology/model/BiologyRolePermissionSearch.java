package pers.wdcy.biology.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class BiologyRolePermissionSearch implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2299648598529479793L;

	@Schema(description = "ID")
	private String code;
	
	@Schema(description = "角色编码")
	private String roleCode;
	
	@Schema(description = "权限编码")
	private String permissionCode;
	
}
