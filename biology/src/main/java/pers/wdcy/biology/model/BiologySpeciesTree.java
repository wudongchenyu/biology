package pers.wdcy.biology.model;

import java.io.Serializable;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class BiologySpeciesTree implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3140975596273460243L;

	@Schema(description = "ID")
	private String code;
	
	@Schema(description = "名称")
	private String speciesName;
	
	@Schema(description = "父级")
	private String parent;
	
	@Schema(description = "层级")
	private int level;
	
	@Schema(description = "来源")
	private String source;
	
	@Schema(description = "是否可用")
	private boolean enabled;
	
	private List<BiologySpeciesTree> children;
	
}
