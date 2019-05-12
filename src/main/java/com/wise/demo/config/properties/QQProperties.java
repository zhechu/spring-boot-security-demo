package com.wise.demo.config.properties;

import lombok.Getter;
import lombok.Setter;

/**
 * QQ 登录配置属性
 * @author lingyuwang
 *
 */
@Setter
@Getter
public class QQProperties {

	/**
	 * 第三方id，用来决定发起第三方登录的url，默认是 qq。
	 */
	private String providerId = "qq";
	
	/**
	 * 应用id。
	 */
	private String appId = "qq";
	
	/**
	 * 应用密钥。
	 */
	private String appSecret = "qq";

}
