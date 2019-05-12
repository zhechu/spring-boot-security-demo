package com.wise.demo.config.properties;

import org.springframework.boot.autoconfigure.social.SocialProperties;

import lombok.Getter;
import lombok.Setter;

/**
 * 微信登录配置属性
 * @author lingyuwang
 *
 */
@Setter
@Getter
public class WeixinProperties extends SocialProperties {
	
	/**
	 * 第三方id，用来决定发起第三方登录的url，默认是 weixin。
	 */
	private String providerId = "weixin";

	/**
	 * 应用id。
	 */
	private String appId = "qq";
	
	/**
	 * 应用密钥。
	 */
	private String appSecret = "qq";

}
