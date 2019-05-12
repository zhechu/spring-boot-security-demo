package com.wise.demo.config.properties;

import lombok.Getter;
import lombok.Setter;

/**
 * 社交登录配置属性
 * @author lingyuwang
 *
 */
@Setter
@Getter
public class SocialProperties {

	/**
	 * 社交登录功能拦截的url
	 */
	private String filterProcessesUrl = "/auth";

	private QQProperties qq = new QQProperties();

	private WeixinProperties weixin = new WeixinProperties();

}
