package com.wise.demo.validate.code;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

/**
 * 验证码信息封装类
 * @author lingyuwang
 *
 */
@Setter
@Getter
public class ValidateCode implements Serializable {
	
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1588203828504660915L;

	private String code;
	
	private LocalDateTime expireTime;
	
	public ValidateCode(String code, int expireIn){
		this.code = code;
		this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
	}
	
	public ValidateCode(String code, LocalDateTime expireTime){
		this.code = code;
		this.expireTime = expireTime;
	}
	
	public boolean isExpried() {
		return LocalDateTime.now().isAfter(expireTime);
	}

}
