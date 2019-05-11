package com.wise.demo.validate.code.sms;

/**
 * 短信发送接口
 * @author lingyuwang
 *
 */
public interface SmsValidateCodeSender {
	
	/**
	 * @param mobile
	 * @param code
	 */
	void send(String mobile, String code);

}
