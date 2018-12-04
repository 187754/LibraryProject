package com.leszczynski.controller;

import com.leszczynski.mail.Mail;
import com.leszczynski.mail.MailSenderService;
import com.leszczynski.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.ValidationException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@RestController
public class UserController {

    @Autowired
	UserService userService;

	@Autowired
	private MailSenderService emailService;

	@RequestMapping("/login")
	public Map<String, Object> login(){
		Map<String, Object> modelRequest = new HashMap<String, Object>();
		modelRequest.put("name", "Czesiek");

		return modelRequest;
	}

	@RequestMapping("/resource")
	public Map<String, Object> home() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("id", UUID.randomUUID().toString());
		model.put("content", "Hello!");

		userService.testApp();
		return model;
	}


	@RequestMapping(value="/user/new", method= RequestMethod.POST)
    @ResponseBody
    public ResponseEntity addUser(@RequestBody Map<String,String> request ){
        try {
            userService.registerUser(request);
        } catch (ValidationException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_ACCEPTABLE).ok().contentType(MediaType.APPLICATION_JSON).build();
        }
        String response = "{\"body\":\"Success\"}";
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @RequestMapping(value="/user/{nick}", method=RequestMethod.GET)
    public void checkIsNickUsed(@PathVariable("nick") String nick, HttpServletResponse response) throws IOException {
        if (userService.checkNick(nick)){
            response.sendError(HttpServletResponse.SC_CONFLICT, "This login is already use.");
            return;
        }

    }

    @RequestMapping(value="/user/{mail}", method=RequestMethod.GET)
    public void checkIsMailUsed(@PathVariable("mail") String mail, HttpServletResponse response) throws IOException {
        if (userService.checkMail(mail)){
            response.sendError(HttpServletResponse.SC_CONFLICT, "This login is already use.");
            return;
        }

    }

    //TODO check what this method do (send email?)
	@RequestMapping("/register")
	public Map<String, Object> registerUser() {
		Map<String, Object> modelRequest = new HashMap<String, Object>();
		modelRequest.put("id", UUID.randomUUID().toString());
		modelRequest.put("content", "Hello!");

		userService.testApp();
		//wysylanie maila - test
		Mail mail = new Mail();
		mail.setFrom("your.library.notification@gmail.com");
		mail.setTo("juggler.0894@gmail.com");
		mail.setSubject("test mail from app");

		Map model = new HashMap();
		model.put("name", "Ania");
		model.put("location", "Poland");
		model.put("signature", "Library Application");
//		mail.setModel(model);

		try {
			emailService.sendSimpleMessage(mail);
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return modelRequest;
	}

	@RequestMapping("/activate/{url}/{id}")
    public void activateUser(@PathVariable("url") String activationUrl, @PathVariable("id") Long id, HttpServletResponse response) throws IOException {
        userService.activateUser(activationUrl, id);
        System.out.println("activationUrl = " + activationUrl);
        response.sendRedirect("http://localhost:8083");

    }



}
