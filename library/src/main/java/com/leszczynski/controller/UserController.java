package com.leszczynski.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leszczynski.service.UserService;

@RestController
public class UserController {
	
	
	
	@RequestMapping("/resource")
	public Map<String,Object> home(){
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("id", UUID.randomUUID().toString());
		model.put("content", "Hello!");
		UserService us = new UserService();
		us.testApp();
		return model;
	}
}
