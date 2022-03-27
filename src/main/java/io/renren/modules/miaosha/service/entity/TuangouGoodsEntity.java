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
@TableName("tuangou_goods")
public class TuangouGoodsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 秒杀的商品表
	 */
	@TableId
	private Long id;
	/**
	 * 商品id
	 */
	private Long goodsId;
	/**
	 * 秒杀价
	 */
	private BigDecimal miaoshaPrice;
	/**
	 * 库存数量
	 */
	private Integer stockCount;
	/**
	 * 秒杀开始时间
	 */
	private Date startDate;
	/**
	 * 秒杀结束时间
	 */
	private Date endDate;

}
