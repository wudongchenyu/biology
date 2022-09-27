package pers.wdcy.biology.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class BiologyAuthorSearch implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1006063884478867930L;

	@Schema(description = "名称")
	private String authorName;
	
	@Schema(description = "URL")
	private String authorUrl;
	
	@Schema(description = "类型")
	private Integer authorType;
	
	@Schema(description = "归属分类")
	private Integer affiliation;
	
	@Schema(description = "简介")
	private String intro;
	
	@Schema(description = "是否可用")
	private Boolean enabled;
	
}
