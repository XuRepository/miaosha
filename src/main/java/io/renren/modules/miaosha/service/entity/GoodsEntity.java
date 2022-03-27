package io.renren.modules.miaosha.service.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 *
 *
 * @author miaosha
 * @email sunlightcs@gmail.com
 * @date 2022-03-27 19:10:12
 */
@Data
@TableName("goods")
public class GoodsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 商品ID
	 */
	@TableId
	private Long id;
	/**
	 * 商品名称
	 */
	private String goodsName;
	/**
	 * 商品标题
	 */
	private String goodsTitle;
	/**
	 * 商品的图片
	 */
	private String goodsImg;
	/**
	 * 商品的详情介绍
	 */
	private String goodsDetail;
	/**
	 * 商品单价
	 */
	private BigDecimal goodsPrice;
	/**
	 * 商品库存, -1表示没有限制
	 */
	private Integer goodsStock;

}
