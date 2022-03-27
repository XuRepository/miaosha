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
@TableName("miaosha_user")
public class MiaoshaUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户ID,手机号码
	 */
	@TableId
	private Long id;
	/**
	 *
	 */
	private String nickname;
	/**
	 * MD5(MD5(pass明文+固定salt) + salt)
	 */
	private String password;
	/**
	 *
	 */
	private String salt;
	/**
	 * 头像,云存储的ID
	 */
	private String head;
	/**
	 * 注册时间
	 */
	private Date registerDate;
	/**
	 * 上次登录时间
	 */
	private Date lastLoginDate;
	/**
	 * 登录次数
	 */
	private Integer loginCount;
	/**
	 *
	 */
	private String address;
	/**
	 *
	 */
	private Long groupId;

}
