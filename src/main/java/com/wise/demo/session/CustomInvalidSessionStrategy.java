package com.wise.demo.session;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.session.InvalidSessionStrategy;

import com.wise.demo.config.properties.SecurityProperties;

/**
 * 默认的session失效处理策略
 * @author lingyuwang
 *
 */
public class CustomInvalidSessionStrategy extends AbstractSessionStrategy implements InvalidSessionStrategy {

	public CustomInvalidSessionStrategy(SecurityProperties securityProperties) {
		super(securityProperties);
	}

	@Override
	public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		onSessionInvalid(request, response);
	}

}
