package pers.wdcy.biology.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class BiologyPermissionSearch implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6234415795157867885L;

	@Schema(description = "ID")
	private String code;
	
	@Schema(description = "名称")
	private String permissionName;
	
	@Schema(description = "类型")
	private String type;

	@Schema(description = "URL")
	private String url;

	@Schema(description = "父级")
	private String parent;
	
	@Schema(description = "简介")
	private String intro;
	
}
