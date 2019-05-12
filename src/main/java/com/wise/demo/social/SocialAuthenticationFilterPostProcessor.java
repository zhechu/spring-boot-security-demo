package com.wise.demo.social;

import org.springframework.social.security.SocialAuthenticationFilter;

/**
 * SocialAuthenticationFilter后处理器，用于在不同环境下个性化社交登录的配置
 * @author lingyuwang
 *
 */
public interface SocialAuthenticationFilterPostProcessor {
	
	/**
	 * @param socialAuthenticationFilter
	 */
	void process(SocialAuthenticationFilter socialAuthenticationFilter);

}
