package com.wise.demo.validate.code.sms;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import com.wise.demo.config.properties.SecurityProperties;
import com.wise.demo.validate.code.ValidateCode;
import com.wise.demo.validate.code.ValidateCodeGenerator;

/**
 * 短信验证码生成器
 * @author lingyuwang
 *
 */
@Component
public class SmsValidateCodeGenerator implements ValidateCodeGenerator {

	@Autowired
	private SecurityProperties securityProperties;
	
	@Override
	public ValidateCode generate(ServletWebRequest request) {
		String code = RandomStringUtils.randomNumeric(securityProperties.getCode().getSms().getLength());
		return new ValidateCode(code, securityProperties.getCode().getSms().getExpireIn());
	}

}
