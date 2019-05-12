package com.wise.demo.config.properties;

import com.wise.demo.config.constants.SecurityConstants;

import lombok.Getter;
import lombok.Setter;

/**
 * 浏览器环境配置属性
 * @author lingyuwang
 *
 */
@Setter
@Getter
public class BrowserProperties {
	
	/**
	 * 登录页面，当引发登录行为的 URL 以 html 结尾时，会跳到这里配置的 URL 上
	 */
	private String signInPage = SecurityConstants.DEFAULT_SIGN_IN_PAGE_URL;
	
	/**
	 * 社交登录，如果需要用户注册，跳转的页面
	 */
	private String signUpUrl = SecurityConstants.DEFAULT_SIGN_UP_PAGE_URL;
	
	/**
	 * '记住我'功能的有效时间，默认1小时
	 */
	private int rememberMeSeconds = 3600;
	
	
}
