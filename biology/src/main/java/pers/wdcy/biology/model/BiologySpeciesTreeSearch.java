package pers.wdcy.biology.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class BiologySpeciesTreeSearch implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1673684765996182637L;

	@Schema(description = "父级")
	private String parent;
	
	@Schema(description = "层级")
	private Integer level;
	
}
