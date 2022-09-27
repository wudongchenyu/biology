package pers.wdcy.biology.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class BiologyArticleRevise implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2483657758839705872L;

	
	@Schema(description = "ID")
	private String code;
	
	@Schema(description = "名称")
	private String articleName;
	
	@Schema(description = "头像")
	private String articleIcons;
	
	@Schema(description = "类型")
	private int articleType;
	
	@Schema(description = "归属")
	private int affiliation;
	
	@Schema(description = "作者")
	private String author;
	
	@Schema(description = "对应生物编码")
	private String biologyCode;
	
	@Schema(description = "简介")
	private String intro;
	
	@Schema(description = "是否可用")
	private boolean enabled;
	
	@Schema(description = "销售状态")
	private int salesStatus;
	
	@Schema(description = "优惠价")
	private BigDecimal discounts;
	
	@Schema(description = "发售单价")
	private BigDecimal salePrice;
	
	@Schema(description = "发售时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime saleTime;
	
	@Schema(description = "停售时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime selloutTime;
	
	@Schema(description = "发布时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime publishTime;
}
