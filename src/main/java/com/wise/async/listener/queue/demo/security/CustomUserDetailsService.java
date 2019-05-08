package com.wise.async.listener.queue.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 自定义用户详情服务
 * @author lingyuwang
 *
 */
@Component
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		log.info("登录用户名：{}", username);
		
		// 模拟获取数据库存储的密码
		String password = passwordEncoder.encode("123456");

		log.info("数据库存储的密码是：{}", password);
		
		return new User(
				username, 
				password, 
				true, // 账号是否没有被删除（可以表示账号不存在）
				true, // 账号是否没有过期
				true, // 凭证是否没有过期
				true, // 账号是否没有被锁定
				AuthorityUtils.commaSeparatedStringToAuthorityList("admin") // 以逗号分割的权限值
				);
	}

}
