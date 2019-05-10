package com.wise.demo.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * 订单控制器
 * @author lingyuwang
 *
 */
@RestController
@Slf4j
public class OrderController {
	
	@RequestMapping("/order")
	public String order() {
		log.info("获取订单");

		return "1";
	}

}
