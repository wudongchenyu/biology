package pers.wdcy.biology.entity;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "用户")
@Table(value = "public.biology_consumer")
public class Consumer implements Persistable<String>,Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6541511373921063019L;

	@Id
	@Schema(description = "编号")
	private String code;
	
	@Column
	@Schema(description = "姓名")
	private String name;
	
	@Column
	@Schema(description = "性别")
	private String gender;
	
	@Column
	@Schema(description = "手机号")
	private String mobile;
	
	@Column
	@Schema(description = "邮箱")
	private String email;
	
	@Column
	@Schema(description = "备注")
	private String comment;
	
	@Column
	@Schema(description = "头像")
	private String headerIcon;
	
	@Schema(description = "是否可用")
	private boolean enabled;
	
	@Transient
	@JsonIgnore
	@Schema(description = "是否是新的")
	private boolean fresh;
	
	@JsonIgnore
	@Override
	public String getId() {
		return this.code;
	}

	@Override
	public boolean isNew() {
		return fresh || this.code == null;
	}

}
