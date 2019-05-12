package com.wise.demo.config.properties;

import lombok.Getter;
import lombok.Setter;

/**
 * 图片验证码配置属性
 * @author lingyuwang
 *
 */
@Setter
@Getter
public class ImageCodeProperties {
	
	/**
	 * 图片宽
	 */
	private int width = 47;
	
	/**
	 * 图片高
	 */
	private int height = 23;
	
	/**
	 * 验证码长度
	 */
	private int length = 4;
	
	/**
	 * 过期时间
	 */
	private int expireIn = 60;

	/**
	 * 要拦截的url，多个url用逗号隔开，ant pattern
	 */
	private String url = "/authentication/form";

}
