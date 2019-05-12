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
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.social.security.SpringSocialConfigurer;

import com.wise.demo.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.wise.demo.config.constants.SecurityConstants;
import com.wise.demo.config.properties.SecurityProperties;
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
	
	@Autowired
	private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

	@Autowired
	private SpringSocialConfigurer socialSecurityConfig;

	@Autowired
	private InvalidSessionStrategy invalidSessionStrategy;

	@Autowired
	private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;

	@Autowired
	private LogoutSuccessHandler logoutSuccessHandler;
	
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
		http.apply(smsCodeAuthenticationSecurityConfig) // 短信验证码配置
			.and()
			.apply(socialSecurityConfig) // 社交登录配置
			.and()
			.addFilterBefore(validateCodeFilter, AbstractPreAuthenticatedProcessingFilter.class) // 验证码过滤器
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
			.sessionManagement() // sessoion 管理
				.invalidSessionStrategy(invalidSessionStrategy) // session 失效策略
				.maximumSessions(securityProperties.getBrowser().getSession().getMaximumSessions()) // 同一用户最大session数
				.maxSessionsPreventsLogin(securityProperties.getBrowser().getSession().isMaxSessionsPreventsLogin()) // 达到最大session数是否阻止登录，默认为false
				.expiredSessionStrategy(sessionInformationExpiredStrategy) // 并发登录导致session失效时，默认的处理策略
				.and()
				.and()
			.logout() // 登出处理
				.logoutUrl("/signOut") // 登出请求 URL
				.logoutSuccessHandler(logoutSuccessHandler) // 登出成功处理器
				.deleteCookies("JSESSIONID") // 登出后删除的 Cookie
				.and()
		    .authorizeRequests() // 表示要进行请求授权
		    .antMatchers(
		    		SecurityConstants.DEFAULT_UNAUTHENTICATION_URL, // 未登录的处理请求 URL
		    		securityProperties.getBrowser().getSignInPage(), // 登录页
		    		securityProperties.getBrowser().getSignOutUrl(), // 登出页
		    		securityProperties.getBrowser().getSignUpUrl(), // 注册页
		    		SecurityConstants.DEFAULT_USER_REGIST_URL, // 注册请求 URL
		    		SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*" // 验证码请求 URL 路径
		    		).permitAll() // 表示登录页不需通过权限验证
		    .anyRequest() // 所有请求
		    .authenticated() // 需要认证后才能访问
		    .and()
		    .csrf().disable() // 禁用 CSRF 检查
		    ;
		
	}

}
