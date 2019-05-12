package com.wise.demo.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import com.wise.demo.dto.User;

import lombok.extern.slf4j.Slf4j;

/**
 * 用户控制器
 * @author lingyuwang
 *
 */
@RestController
@Slf4j
public class UserController {

	@Autowired
	private ProviderSignInUtils providerSignInUtils;
	
	@PostMapping("/regist")
	public void regist(@Valid @RequestBody User user, HttpServletRequest request) {
		// 不管是注册用户还是绑定用户，都会拿到一个用户唯一标识。
		String userId = user.getUsername();
		providerSignInUtils.doPostSignUp(userId, new ServletWebRequest(request));
	}
	
	@RequestMapping("/user")
	public Object user() {
		log.info("获取用户信息：{}", SecurityContextHolder.getContext().getAuthentication().getPrincipal());

		return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

}
