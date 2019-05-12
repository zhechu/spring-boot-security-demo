package com.wise.demo.social;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Component;

/**
 * 默认绑定社交用户到本应用（不配此类则会跳转到注册页面）
 * @author lingyuwang
 *
 */
@Component
public class CustomConnectionSignUp implements ConnectionSignUp {

	@Override
	public String execute(Connection<?> connection) {
		// 根据社交用户信息默认创建用户并返回用户唯一标识
		return connection.getDisplayName();
	}

}
