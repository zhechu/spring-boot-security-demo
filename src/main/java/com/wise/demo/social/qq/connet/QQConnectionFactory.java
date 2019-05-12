package com.wise.demo.social.qq.connet;

import org.springframework.social.connect.support.OAuth2ConnectionFactory;

import com.wise.demo.social.qq.api.QQ;

/**
 * QQ 连接工厂
 * @author lingyuwang
 *
 */
public class QQConnectionFactory extends OAuth2ConnectionFactory<QQ> {

	public QQConnectionFactory(String providerId, String appId, String appSecret) {
		super(providerId, new QQServiceProvider(appId, appSecret), new QQAdapter());
	}

}
