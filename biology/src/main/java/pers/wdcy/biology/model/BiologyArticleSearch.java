package pers.wdcy.biology.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class BiologyArticleSearch implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7149585472339428010L;

	@Schema(description = "名称")
	private String articleName;
	
	@Schema(description = "类型")
	private Integer articleType;
	
	@Schema(description = "归属")
	private Integer affiliation;
	
	@Schema(description = "作者")
	private String author;
	
	@Schema(description = "对应生物编码")
	private String biologyCode;
	
	@Schema(description = "简介")
	private String intro;
	
	@Schema(description = "是否可用")
	private boolean enabled;
	
	@Schema(description = "销售状态")
	private Integer salesStatus;
	
}
