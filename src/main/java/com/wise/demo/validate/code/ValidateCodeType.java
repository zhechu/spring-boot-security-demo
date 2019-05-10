package com.wise.demo.validate.code;

import com.wise.demo.config.SecurityConstants;

/**
 * 校验码类型
 * @author lingyuwang
 *
 */
public enum ValidateCodeType {
	
	/**
	 * 图片验证码
	 */
	IMAGE {
		@Override
		public String getParamNameOnValidate() {
			return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_IMAGE;
		}
	};

	/**
	 * 校验时从请求中获取的参数的名字
	 * @return
	 */
	public abstract String getParamNameOnValidate();

}