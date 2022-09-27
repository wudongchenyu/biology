package pers.wdcy.biology.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class MenusRegister implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7716951994373423587L;
	
	@Schema(description = "名称")
	private String menusName;
	
	@Schema(description = "父级")
	private String parent;
	
	@Schema(description = "层级")
	private int level;
	
	@Schema(description = "简介")
	private String intro;
	
}
