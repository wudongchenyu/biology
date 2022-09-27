package pers.wdcy.biology.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class MenusSearch implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8372803197197620278L;

	@Schema(description = "ID")
	private String code;
	
	@Schema(description = "名称")
	private String menusName;
	
	@Schema(description = "父级")
	private String parent;
	
	@Schema(description = "层级")
	private int level;
	
	@Schema(description = "简介")
	private String intro;
	
	@Schema(description = "是否可用")
	private Boolean enabled;
	
	@Schema(description = "创建人")
	private String creator;
	
	@Schema(description = "操作人")
	private String operator;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Schema(description = "创建时间-开始")
	private LocalDateTime creationStartTime;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Schema(description = "创建时间-结束")
	private LocalDateTime creationEndTime;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Schema(description = "操作时间-开始")
	private LocalDateTime operationStartTime;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Schema(description = "操作时间-结束")
	private LocalDateTime operationEndTime;
	
}
