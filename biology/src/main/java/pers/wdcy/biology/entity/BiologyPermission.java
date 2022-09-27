package pers.wdcy.biology.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "权限")
@Table(value = "public.biology_permission")
public class BiologyPermission implements Persistable<String>,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5274407435512552774L;

	@Id
	@Schema(description = "ID")
	private String code;
	
	@Column
	@Schema(description = "名称")
	private String permissionName;
	
	@Column
	@Schema(description = "类型")
	private String type;

	@Column
	@Schema(description = "URL")
	private String url;

	@Column
	@Schema(description = "父级")
	private String parent;
	
	@Column
	@Schema(description = "简介")
	private String intro;
	
	@Column
	@Schema(description = "是否可用")
	private boolean enabled;
	
	@Transient
	@JsonIgnore
	@Schema(description = "是否是新的")
	private boolean fresh;
	
	@Column
	@Schema(description = "创建人")
	private String creator;
	
	@Column
	@Schema(description = "操作人")
	private String operator;
	
	@Column
	@Schema(description = "创建时间")
	private LocalDateTime creationTime;
	
	@Column
	@Schema(description = "操作时间")
	private LocalDateTime operationTime;
	
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
