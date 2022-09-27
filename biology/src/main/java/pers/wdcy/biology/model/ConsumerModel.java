package pers.wdcy.biology.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "用户详情")
public class ConsumerModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 859134238697724495L;

	@Schema(description = "编号")
	private String code;
	
	@Schema(description = "姓名")
	private String name;
	
	@Schema(description = "性别")
	private String gender;
	
	@Schema(description = "手机号")
	private String mobile;
	
	@Schema(description = "邮箱")
	private String email;
	
	@Schema(description = "备注")
	private String comment;
	
	@Schema(description = "头像")
	private String headerIcon;
	
	@Schema(description = "是否可用")
	private boolean enabled;
	
	
}
