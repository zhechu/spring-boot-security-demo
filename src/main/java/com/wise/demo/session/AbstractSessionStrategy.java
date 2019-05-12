package com.wise.demo.session;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wise.demo.config.properties.SecurityProperties;
import com.wise.demo.vo.ResultVo;

import lombok.extern.slf4j.Slf4j;

/**
 * 抽象的session失效处理器
 * @author lingyuwang
 *
 */
@Slf4j
public class AbstractSessionStrategy {

	/**
	 * 跳转的url
	 */
	private String destinationUrl;
	
	/**
	 * 系统配置信息
	 */
	private SecurityProperties securityPropertie;
	
	/**
	 * 重定向策略
	 */
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	/**
	 * 跳转前是否创建新的session
	 */
	private boolean createNewSession = true;

	private ObjectMapper objectMapper = new ObjectMapper();

	public AbstractSessionStrategy(SecurityProperties securityPropertie) {
		String invalidSessionUrl = securityPropertie.getBrowser().getSession().getSessionInvalidUrl();
		Assert.isTrue(UrlUtils.isValidRedirectUrl(invalidSessionUrl), "url must start with '/' or with 'http(s)'");
		Assert.isTrue(StringUtils.endsWithIgnoreCase(invalidSessionUrl, ".html"), "url must end with '.html'");
		this.destinationUrl = invalidSessionUrl;
		this.securityPropertie = securityPropertie;
	}

	protected void onSessionInvalid(HttpServletRequest request, HttpServletResponse response) throws IOException {

		log.info("session失效");
		
		if (createNewSession) {
			request.getSession();
		}

		String sourceUrl = request.getRequestURI();
		String targetUrl;

		if (StringUtils.endsWithIgnoreCase(sourceUrl, ".html")) {
			if(StringUtils.equals(sourceUrl, securityPropertie.getBrowser().getSignInPage())
					|| StringUtils.equals(sourceUrl, securityPropertie.getBrowser().getSignOutUrl())){
				targetUrl = sourceUrl;
			} else {
				targetUrl = destinationUrl;
			}
			
			log.info("跳转到:{}", targetUrl);
			
			redirectStrategy.sendRedirect(request, response, targetUrl);
		} else {
			Object result = buildResponseContent(request);
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(objectMapper.writeValueAsString(result));
		}

	}

	protected Object buildResponseContent(HttpServletRequest request) {
		String message = "session已失效";
		if (isConcurrency()) {
			message = message + "，有可能是并发登录导致的";
		}
		return new ResultVo<Object>(HttpStatus.UNAUTHORIZED.value(), message);
	}

	/**
	 * session失效是否是并发导致的
	 * 
	 * @return
	 */
	protected boolean isConcurrency() {
		return false;
	}

	public void setCreateNewSession(boolean createNewSession) {
		this.createNewSession = createNewSession;
	}

}
