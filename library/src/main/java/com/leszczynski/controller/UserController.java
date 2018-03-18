package com.leszczynski.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.leszczynski.mail.Mail;
import com.leszczynski.mail.MailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leszczynski.service.UserService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;


@RestController
public class UserController {

	UserService us = new UserService();

	@Autowired
	private MailSenderService emailService;

	@RequestMapping("/resource")
	public Map<String, Object> home() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("id", UUID.randomUUID().toString());
		model.put("content", "Hello!");

		us.testApp();
		return model;
	}

	@RequestMapping("/register")
	public Map<String, Object> registerUser() {
		Map<String, Object> modelRequest = new HashMap<String, Object>();
		modelRequest.put("id", UUID.randomUUID().toString());
		modelRequest.put("content", "Hello!");

		us.testApp();
		//wysylanie maila - test
		Mail mail = new Mail();
		mail.setFrom("your.library.notification@gmail.com");
		mail.setTo("juggler.0894@gmail.com");
		mail.setSubject("test mail from app");

		Map model = new HashMap();
		model.put("name", "Ania");
		model.put("location", "Poland");
		model.put("signature", "Library Application");
		mail.setModel(model);

		try {
			emailService.sendSimpleMessage(mail);
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return modelRequest;
	}


}
