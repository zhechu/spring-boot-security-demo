package com.wise.demo.config.properties;

import lombok.Getter;
import lombok.Setter;

/**
 * 短信验证码配置属性
 * @author lingyuwang
 *
 */
@Setter
@Getter
public class SmsCodeProperties {
	
	/**
	 * 验证码长度
	 */
	private int length = 6;
	
	/**
	 * 过期时间
	 */
	private int expireIn = 60;
	
	/**
	 * 要拦截的 URL，多个 URl 用逗号隔开，ant pattern
	 */
	private String url = "/authentication/form";

}
