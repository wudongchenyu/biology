package pers.wdcy.biology.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class BiologyDetailRevise implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8930112368446773940L;

	@Schema(description = "ID")
	private String code;
	
	@Schema(description = "名称")
	private String biologyName;
	
	@Schema(description = "别名")
	private String otherName;
	
	@Schema(description = "学名")
	private String scientificName;
	
	@Schema(description = "英文名")
	private String englishName;
	
	@Schema(description = "所属域")
	private String belongDomain;
	
	@Schema(description = "所属界")
	private String belongKingdom;
	
	@Schema(description = "所属门")
	private String belongPhylum;
	
	@Schema(description = "所属亚门")
	private String belongSubphylum;
	
	@Schema(description = "所属纲")
	private String belongClass;
	
	@Schema(description = "所属亚纲")
	private String belongSubclass;
	
	@Schema(description = "所属目")
	private String belongOrder;
	
	@Schema(description = "所属亚目")
	private String belongSuborder;
	
	@Schema(description = "所属科")
	private String belongFamily;
	
	@Schema(description = "所属亚科")
	private String belongSubfamily;
	
	@Schema(description = "所属属")
	private String belongGenus;

	@Schema(description = "所属亚属")
	private String belongSubgenus;
	
	@Schema(description = "所属种")
	private String belongSpecies;
	
	@Schema(description = "所属亚种")
	private String belongSubspecies;
	
	@Schema(description = "简介")
	private String intro;
	
	@Schema(description = "保护级别")
	private String protectionLevel;
	
}
