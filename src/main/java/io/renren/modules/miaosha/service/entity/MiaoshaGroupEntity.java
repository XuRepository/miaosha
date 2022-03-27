package io.renren.modules.miaosha.service.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

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
@TableName("miaosha_group")
public class MiaoshaGroupEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private Long groupId;
	/**
	 *
	 */
	private Integer headCount;
	/**
	 *
	 */
	private Integer targetCount;

}
