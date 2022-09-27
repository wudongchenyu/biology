package pers.wdcy.gitlab.operate.entity;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Table(value = "public.gitlab_branch")
public class GitlabBranch implements Persistable<Long>, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1932190134194474541L;

	@Id
	@Schema(description = "ID")
	private Long id;
	
	private String name;
	
	private String weburl;
	
	@Transient
	private boolean isnew;
	
	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public boolean isNew() {
		return this.isnew || null != this.id;
	}
	
	public GitlabBranch asnew() {
		this.isnew = true;
		return this;
	}

}
