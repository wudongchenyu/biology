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
@Schema(description = "角色权限")
@Table(value = "public.biology_role_permission")
public class BiologyRolePermission implements Persistable<String>,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8353047230740553572L;

	@Id
	@Schema(description = "ID")
	private String code;
	
	@Column
	@Schema(description = "角色编码")
	private String roleCode;
	
	@Column
	@Schema(description = "权限编码")
	private String permissionCode;
	
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
