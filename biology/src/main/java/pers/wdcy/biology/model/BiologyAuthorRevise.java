package pers.wdcy.biology.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class BiologyAuthorRevise implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3933023011573778998L;

	@Schema(description = "ID")
	private String code;
	
	@Schema(description = "名称")
	private String authorName;
	
	@Schema(description = "URL")
	private String authorUrl;
	
	@Schema(description = "类型")
	private int authorType;
	
	@Schema(description = "归属分类")
	private int affiliation;
	
	@Schema(description = "简介")
	private String intro;
	
	@Schema(description = "是否可用")
	private boolean enabled;

}
