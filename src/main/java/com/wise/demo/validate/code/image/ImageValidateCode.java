package com.wise.demo.validate.code.image;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

import com.wise.demo.validate.code.ValidateCode;

import lombok.Getter;
import lombok.Setter;

/**
 * 图片验证码
 * @author lingyuwang
 *
 */
@Setter
@Getter
public class ImageValidateCode extends ValidateCode {
	
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -6020470039852318468L;
	
	private BufferedImage image; 
	
	public ImageValidateCode(BufferedImage image, String code, int expireIn){
		super(code, expireIn);
		this.image = image;
	}
	
	public ImageValidateCode(BufferedImage image, String code, LocalDateTime expireTime){
		super(code, expireTime);
		this.image = image;
	}
	
}
