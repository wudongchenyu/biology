package pers.wdcy.biology.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class BiologyConsumerRoleSearch implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5376227346125208407L;

	@Schema(description = "ID")
	private String code;
	
	@Schema(description = "角色编码")
	private String roleCode;
	
	@Schema(description = "用户编码")
	private String consumerCode;
	
}
