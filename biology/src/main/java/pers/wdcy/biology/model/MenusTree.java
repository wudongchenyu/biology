package pers.wdcy.biology.model;

import java.io.Serializable;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class MenusTree implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8578806566525968693L;

	@Schema(description = "ID")
	private String code;
	
	@Schema(description = "名称")
	private String menusName;
	
	@Schema(description = "父级")
	private String parent;
	
	@Schema(description = "层级")
	private int level;
	
	@Schema(description = "子集")
	private List<MenusTree> children;
}
