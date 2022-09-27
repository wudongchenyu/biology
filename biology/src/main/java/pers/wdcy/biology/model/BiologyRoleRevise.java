package pers.wdcy.biology.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class BiologyRoleRevise implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 948622447937616965L;

	@Schema(description = "ID")
	private String code;
	
	@Schema(description = "名称")
	private String roleName;
	
	@Schema(description = "简介")
	private String intro;
	
}
