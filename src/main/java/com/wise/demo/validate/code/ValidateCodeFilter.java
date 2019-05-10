package com.wise.demo.validate.code;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import com.wise.demo.config.SecurityConstants;
import com.wise.demo.config.SecurityProperties;

import lombok.extern.slf4j.Slf4j;

/**
 * 校验验证码的过滤器
 * @author lingyuwang
 *
 */
@Component
@Slf4j
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {

	/**
	 * 验证码校验失败处理器
	 */
	@Autowired
	private AuthenticationFailureHandler authenticationFailureHandler;
	
	/**
	 * 安全配置信息
	 */
	@Autowired
	private SecurityProperties securityProperties;
	
	/**
	 * 系统中的校验码处理器
	 */
	@Autowired
	private ValidateCodeProcessorHolder validateCodeProcessorHolder;
	
	/**
	 * 存放所有需要校验验证码的 URL
	 */
	private Map<String, ValidateCodeType> urlMap = new HashMap<>();
	
	/**
	 * 验证请求 URL 与配置的 URL 是否匹配的工具类
	 */
	private AntPathMatcher pathMatcher = new AntPathMatcher();

	/**
	 * 初始化要拦截的 URL 配置信息
	 */
	@Override
	public void afterPropertiesSet() throws ServletException {
		super.afterPropertiesSet();

		urlMap.put(SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_FORM, ValidateCodeType.IMAGE);
		addUrlToMap(securityProperties.getCode().getImage().getUrl(), ValidateCodeType.IMAGE);
	}

	/**
	 * 将系统中配置的需要校验验证码的URL根据校验的类型放入 map
	 * 
	 * @param urlString
	 * @param type
	 */
	protected void addUrlToMap(String urlString, ValidateCodeType type) {
		if (StringUtils.isNotBlank(urlString)) {
			String[] urls = StringUtils.splitByWholeSeparatorPreserveAllTokens(urlString, ",");
			for (String url : urls) {
				urlMap.put(url, type);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.filter.OncePerRequestFilter#doFilterInternal(
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		ValidateCodeType type = getValidateCodeType(request);
		if (type != null) {
			log.info("校验请求(" + request.getRequestURI() + ")中的验证码,验证码类型" + type);
			try {
				validateCodeProcessorHolder.findValidateCodeProcessor(type)
						.validate(new ServletWebRequest(request, response));
				log.info("验证码校验通过");
			} catch (ValidateCodeException exception) {
				authenticationFailureHandler.onAuthenticationFailure(request, response, exception);
				return;
			}
		}

		chain.doFilter(request, response);

	}

	/**
	 * 获取校验码的类型，若当前请求不需要校验，则返回 null
	 * 
	 * @param request
	 * @return
	 */
	private ValidateCodeType getValidateCodeType(HttpServletRequest request) {
		if (!StringUtils.equalsIgnoreCase(request.getMethod(), "GET")) {
			Set<String> urls = urlMap.keySet();
			for (String url : urls) {
				if (pathMatcher.match(url, request.getRequestURI())) {
					return urlMap.get(url);
				}
			}
		}
		return null;
	}

}
