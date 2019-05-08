package com.wise.async.listener.queue.demo.web.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.wise.async.listener.queue.demo.vo.ResultVo;

import lombok.extern.slf4j.Slf4j;

/**
 * 安全控制器
 * @author lingyuwang
 *
 */
@RestController
@Slf4j
public class SecurityController {

	private RequestCache requestCache = new HttpSessionRequestCache();

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	/**
	 * 当需要身份认证时，跳转到这里
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/authentication/require", method = RequestMethod.GET)
	@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
	public ResultVo<Object> requireAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		SavedRequest savedRequest = requestCache.getRequest(request, response);

		if (savedRequest != null) {
			String targetUrl = savedRequest.getRedirectUrl();
			
			log.info("引发跳转的请求是：{}", targetUrl);
			
			if (StringUtils.endsWithIgnoreCase(targetUrl, ".html")) {
				redirectStrategy.sendRedirect(request, response, "/signIn.html");
			}
		}

		return new ResultVo<Object>(HttpStatus.UNAUTHORIZED.value(), "访问的服务需要身份认证，请引导用户到登录页");
	}

}
