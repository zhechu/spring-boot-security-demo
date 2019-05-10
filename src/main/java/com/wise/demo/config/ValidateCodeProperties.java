package com.wise.demo.config;

import lombok.Getter;
import lombok.Setter;

/**
 * 验证码配置属性
 * @author lingyuwang
 *
 */
@Setter
@Getter
public class ValidateCodeProperties {
	
	/**
	 * 图片验证码配置
	 */
	private ImageCodeProperties image = new ImageCodeProperties();
	
}
