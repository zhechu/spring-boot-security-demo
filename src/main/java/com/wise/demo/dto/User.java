package com.wise.demo.dto;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户传输对象
 * @author lingyuwang
 *
 */
@Setter
@Getter
public class User {
	
	@NotBlank(message = "用户名不能为空")
	private String username;
	
	@NotBlank(message = "密码不能为空")
	private String password;
	
}
