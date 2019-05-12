package com.wise.demo.config.properties;

import com.wise.demo.config.constants.SecurityConstants;

import lombok.Getter;
import lombok.Setter;

/**
 * session管理相关配置属性
 * @author lingyuwang
 *
 */
@Setter
@Getter
public class SessionProperties {
	
	/**
	 * 同一个用户在系统中的最大session数，默认1
	 */
	private int maximumSessions = 1;
	
	/**
	 * 达到最大session时是否阻止新的登录请求，默认为false，不阻止，新的登录会将老的登录失效掉
	 */
	private boolean maxSessionsPreventsLogin = false;
	
	/**
	 * session失效时跳转的地址
	 */
	private String sessionInvalidUrl = SecurityConstants.DEFAULT_SESSION_INVALID_URL;

	/**
	 * session 的 cookie 名称
	 */
	private String cookieName = "WISESESSIONID";
	
	/**
	 * session 的 cookie path
	 */
	private String cookiePath = "/";
	
}
