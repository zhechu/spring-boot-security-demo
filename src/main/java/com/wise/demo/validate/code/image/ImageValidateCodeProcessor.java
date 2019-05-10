package com.wise.demo.validate.code.image;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import com.wise.demo.validate.code.AbstractValidateCodeProcessor;

/**
 * 图片验证码处理器
 * @author lingyuwang
 *
 */
@Component
public class ImageValidateCodeProcessor extends AbstractValidateCodeProcessor<ImageValidateCode> {

	/**
	 * 发送图形验证码，将其写到响应中
	 */
	@Override
	protected void send(ServletWebRequest request, ImageValidateCode imageCode) throws Exception {
		ImageIO.write(imageCode.getImage(), "JPEG", request.getResponse().getOutputStream());
	}

}
