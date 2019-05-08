/**
 * 
 */
package com.wise.async.listener.queue.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.wise.async.listener.queue.demo.security.CustomAuthenticationFailureHandler;
import com.wise.async.listener.queue.demo.security.CustomAuthenticationSuccessHandler;

/**
 * 安全配置类
 * @author lingyuwang
 *
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
	
	@Autowired
	private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
	
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
		
		http.formLogin() // 开启表单认证
		    .loginPage("/authentication/require") // 未登录的处理
		    .loginProcessingUrl("/authentication/form") // 登录处理 url
		    .successHandler(customAuthenticationSuccessHandler) // 登录成功的处理器
		    .failureHandler(customAuthenticationFailureHandler) // 登录失败的处理器
//		http.httpBasic() // 开启 http basic 认证
		    .and()
		    .authorizeRequests() // 表示要进行请求授权
		    .antMatchers("/authentication/require", "/signIn.html").permitAll() // 表示登录页不需通过权限验证
		    .anyRequest() // 所有请求
		    .authenticated() // 需要认证后才能访问
		    .and()
		    .csrf().disable() // 禁用 CSRF 检查
		    ;
		
	}

}
