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
@Schema(description = "生物")
@Table(value = "public.biology_detail")
public class BiologyDetail implements Persistable<String>,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2732180470716665737L;

	@Id
	@Schema(description = "ID")
	private String code;
	
	@Column
	@Schema(description = "名称")
	private String biologyName;
	
	@Column
	@Schema(description = "别名")
	private String otherName;
	
	@Column
	@Schema(description = "学名")
	private String scientificName;
	
	@Column
	@Schema(description = "英文名")
	private String englishName;
	
	@Column
	@Schema(description = "所属域")
	private String belongDomain;
	
	@Column
	@Schema(description = "所属界")
	private String belongKingdom;
	
	@Column
	@Schema(description = "所属门")
	private String belongPhylum;
	
	@Column
	@Schema(description = "所属亚门")
	private String belongSubphylum;
	
	@Column
	@Schema(description = "所属纲")
	private String belongClass;
	
	@Column
	@Schema(description = "所属亚纲")
	private String belongSubclass;
	
	@Column
	@Schema(description = "所属目")
	private String belongOrder;
	
	@Column
	@Schema(description = "所属亚目")
	private String belongSuborder;
	
	@Column
	@Schema(description = "所属科")
	private String belongFamily;
	
	@Column
	@Schema(description = "所属亚科")
	private String belongSubfamily;
	
	@Column
	@Schema(description = "所属属")
	private String belongGenus;

	@Column
	@Schema(description = "所属亚属")
	private String belongSubgenus;
	
	@Column
	@Schema(description = "所属种")
	private String belongSpecies;
	
	@Column
	@Schema(description = "所属亚种")
	private String belongSubspecies;
	
	@Column
	@Schema(description = "简介")
	private String intro;
	
	@Column
	@Schema(description = "保护级别")
	private String protectionLevel;
	
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
