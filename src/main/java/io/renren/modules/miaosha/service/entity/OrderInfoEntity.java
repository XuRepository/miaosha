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
@TableName("order_info")
public class OrderInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private Long id;
	/**
	 * 用户ID
	 */
	private Long userId;
	/**
	 * 商品ID
	 */
	private Long goodsId;
	/**
	 * 收货地址ID
	 */
	private Long deliveryAddrId;
	/**
	 *
	 */
	private String goodsImg;
	/**
	 * 冗余过来的商品名称
	 */
	private String goodsName;
	/**
	 * 商品数量
	 */
	private Integer goodsCount;
	/**
	 * 商品单价
	 */
	private BigDecimal goodsPrice;
	/**
	 * 1pc, 2android, 3ios
	 */
	private Integer orderChannel;
	/**
	 * 订单状态, 0新建未支付, 1已支付, 2已发货, 3已收货, 4已退款, 5已完成
	 */
	private Integer status;
	/**
	 * 订单的创建时间
	 */
	private Date createDate;
	/**
	 * 支付时间
	 */
	private Date payDate;
	/**
	 *
	 */
	private String address;

}
