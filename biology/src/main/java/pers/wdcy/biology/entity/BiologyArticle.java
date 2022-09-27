package pers.wdcy.biology.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "生物艺术品")
@Table(value = "public.biology_article")
public class BiologyArticle implements Persistable<String>, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8911516650744618249L;

	@Id
	@Schema(description = "ID")
	private String code;
	
	@Column
	@Schema(description = "名称")
	private String articleName;
	
	@Column
	@Schema(description = "头像")
	private String articleIcons;
	
	@Column
	@Schema(description = "类型")
	private int articleType;
	
	@Column
	@Schema(description = "归属")
	private int affiliation;
	
	@Column
	@Schema(description = "作者")
	private String author;
	
	@Column
	@Schema(description = "对应生物编码")
	private String biologyCode;
	
	@Column
	@Schema(description = "简介")
	private String intro;
	
	@Column
	@Schema(description = "是否可用")
	private boolean enabled;
	
	@Column
	@Schema(description = "销售状态")
	private int salesStatus;
	
	@Column
	@Schema(description = "优惠价")
	private BigDecimal discounts;
	
	@Column
	@Schema(description = "发售单价")
	private BigDecimal salePrice;
	
	@Column
	@Schema(description = "发售时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime saleTime;
	
	@Column
	@Schema(description = "停售时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime selloutTime;
	
	@Column
	@Schema(description = "发布时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime publishTime;
	
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
