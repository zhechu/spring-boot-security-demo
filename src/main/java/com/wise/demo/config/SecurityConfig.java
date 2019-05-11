package com.wise.demo.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.wise.demo.security.CustomAuthenticationFailureHandler;
import com.wise.demo.security.CustomAuthenticationSuccessHandler;
import com.wise.demo.validate.code.ValidateCodeFilter;

/**
 * 安全配置类
 * @author lingyuwang
 *
 */
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
	
	@Autowired
	private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

	@Autowired
	private ValidateCodeFilter validateCodeFilter;

	@Autowired
	private DataSource dataSource;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private SecurityProperties securityProperties;
	
	/**
	 * 记住我功能的 token 存取器配置
	 * @return
	 */
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
		tokenRepository.setDataSource(dataSource);
//		tokenRepository.setCreateTableOnStartup(true); // 再次启动服务时，不需配置 true，否则会报表已存在异常
		return tokenRepository;
	}
	
	/**
	 * 密码处理器
	 * @return
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
//		http.httpBasic() // 开启 http basic 认证
		http.addFilterBefore(validateCodeFilter, AbstractPreAuthenticatedProcessingFilter.class) // 验证码过滤器
			.formLogin() // 开启表单认证
			    .loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL) // 未登录的处理
			    .loginProcessingUrl(SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_FORM) // 登录处理 url
			    .successHandler(customAuthenticationSuccessHandler) // 登录成功的处理器
			    .failureHandler(customAuthenticationFailureHandler) // 登录失败的处理器
			    .and()
			.rememberMe() // 记住我的功能
				.tokenRepository(persistentTokenRepository()) // token 持久化仓库
				.tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds()) // token 有效时间
				.userDetailsService(userDetailsService) // 用户详情
			.and()
		    .authorizeRequests() // 表示要进行请求授权
		    .antMatchers(
		    		SecurityConstants.DEFAULT_UNAUTHENTICATION_URL, 
		    		SecurityConstants.DEFAULT_SIGN_IN_PAGE_URL, 
		    		SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*").permitAll() // 表示登录页不需通过权限验证
		    .anyRequest() // 所有请求
		    .authenticated() // 需要认证后才能访问
		    .and()
		    .csrf().disable() // 禁用 CSRF 检查
		    ;
		
	}

}
