package com.wise.demo.validate.code;

import org.springframework.security.core.AuthenticationException;

/**
 * 验证码异常类
 * @author lingyuwang
 *
 */
public class ValidateCodeException extends AuthenticationException {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -7285211528095468156L;

	public ValidateCodeException(String msg) {
		super(msg);
	}

}
