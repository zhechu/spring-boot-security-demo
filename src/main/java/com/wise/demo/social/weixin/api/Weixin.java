package com.wise.demo.social.weixin.api;

/**
 * 微信API调用接口
 * @author lingyuwang
 *
 */
public interface Weixin {

	WeixinUserInfo getUserInfo(String openId);
	
}
