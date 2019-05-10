package com.wise.demo.config;

import com.wise.demo.web.controller.SecurityController;

/**
 * Security 常量
 * @author lingyuwang
 *
 */
public interface SecurityConstants {
	
	/**
	 * 应用安全配置属性前缀
	 */
	String APPLICATION_SECURITY_PROPERTIES_PREFIX = "application.security";
	
	/**
	 * 默认的处理验证码的 URL 前缀
	 */
	String DEFAULT_VALIDATE_CODE_URL_PREFIX = "/code";
	
	/**
	 * 当请求需要身份认证时，默认跳转的 URL
	 * 
	 * @see SecurityController
	 */
	String DEFAULT_UNAUTHENTICATION_URL = "/authentication/require";
	
	/**
	 * 默认的用户名密码登录请求处理 URL
	 */
	String DEFAULT_SIGN_IN_PROCESSING_URL_FORM = "/authentication/form";
	
	/**
	 * 默认登录页面
	 * 
	 * @see SecurityController
	 */
	String DEFAULT_SIGN_IN_PAGE_URL = "/signIn.html";
	
	/**
	 * 验证图片验证码时，http请求中默认的携带图片验证码信息的参数的名称
	 */
	String DEFAULT_PARAMETER_NAME_CODE_IMAGE = "imageCode";
	
}
