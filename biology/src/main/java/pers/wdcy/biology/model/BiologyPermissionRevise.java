package pers.wdcy.biology.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class BiologyPermissionRevise implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5486349930601532580L;

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
