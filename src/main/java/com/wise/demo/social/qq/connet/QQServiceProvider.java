package com.wise.demo.social.qq.connet;

import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

import com.wise.demo.social.qq.api.QQ;
import com.wise.demo.social.qq.api.QQImpl;

/**
 * QQ 服务提供者
 * @author lingyuwang
 *
 */
public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQ> {

	/**
	 * 应用 ID
	 */
	private String appId;
	
	/**
	 * 授权 URL
	 */
	private static final String URL_AUTHORIZE = "https://graph.qq.com/oauth2.0/authorize";
	
	/**
	 * 获取 access_token URL
	 */
	private static final String URL_ACCESS_TOKEN = "https://graph.qq.com/oauth2.0/token";
	
	public QQServiceProvider(String appId, String appSecret) {
		super(new QQOAuth2Template(appId, appSecret, URL_AUTHORIZE, URL_ACCESS_TOKEN));
		this.appId = appId;
	}
	
	@Override
	public QQ getApi(String accessToken) {
		return new QQImpl(accessToken, appId);
	}

}
