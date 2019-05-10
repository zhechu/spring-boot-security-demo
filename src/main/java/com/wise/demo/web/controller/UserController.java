package com.wise.demo.web.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * 用户控制器
 * @author lingyuwang
 *
 */
@RestController
@Slf4j
public class UserController {
	
	@RequestMapping("/user")
	public Object user() {
		log.info("获取用户信息：{}", SecurityContextHolder.getContext().getAuthentication().getPrincipal());

		return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

}
