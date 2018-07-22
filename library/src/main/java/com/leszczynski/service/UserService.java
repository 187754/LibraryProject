package com.leszczynski.service;

import com.leszczynski.dao.UserDao;
import com.leszczynski.dto.User;
import com.leszczynski.entity.BookEntity;
import com.leszczynski.entity.TestEntity;
import com.leszczynski.entity.UserEntity;
import com.leszczynski.mail.Mail;
import com.leszczynski.mail.MailSenderService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.xml.bind.ValidationException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {

    UserDao userDao = new UserDao();

    @Autowired
    private MailSenderService emailService;

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static final Pattern VALID_PASSWORD_REGEX =
            Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", Pattern.CASE_INSENSITIVE);

    public static final Pattern VALID_NAME_REGEX =
            Pattern.compile("(?i)(^[a-zàâäôéèëêïîçùûüÿæœäöüßąćęłńóśźżàèéìíîòóùúáéíñóúü])((?![ .,'-]$)[a-zàâäôéèëêïîçùûüÿæœäöüßąćęłńóśźżàèéìíîòóùúáéíñóúü .,'-]){0,24}$", Pattern.CASE_INSENSITIVE);

    public static final String BASE_URL = "http://localhost:8083/";

    //TODO remove this method
	public void testApp() {
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		UserEntity usr = new UserEntity("name");
		TestEntity test = new TestEntity("cos");
		BookEntity book = new BookEntity("hahaha");
		session.save(test);
		session.save(usr);
		session.getTransaction().commit();
		session.close();
	}


    private void validateNewUser(User user) throws ValidationException {
        if (!user.getPassword().equals(user.getPasswordConfirmation())){
            throw new ValidationException("{\"body\":\"Podane hasła nie są jednakowe\"}");
        }
        Matcher emailMatcher = VALID_EMAIL_ADDRESS_REGEX.matcher(user.getEmail());
        if (!emailMatcher.find()){
            throw new ValidationException("{\"body\":\"Podany email jest nieprawidłowy\"}");
        }
        Matcher passwordMatcher = VALID_PASSWORD_REGEX.matcher(user.getPassword());
        if (!passwordMatcher.find()){
            throw new ValidationException("{\"body\":\"Za słabe haslo\"}");
        }
        Matcher nameMatcher = VALID_NAME_REGEX.matcher(user.getName());
        if (!nameMatcher.find()){
            throw new ValidationException("{\"body\":\"Podane imię jest niepoprawne\"}");
        }
        Matcher surnameMatcher = VALID_NAME_REGEX.matcher(user.getSurname());
        if (!surnameMatcher.find()){
            throw new ValidationException("{\"body\":\"Podane nazwisko jest niepoprawne\"}");
        }
    }

	public void registerUser(Map<String, String> userData) throws ValidationException {
        User user = createUserFromData(userData);
        try {
            validateNewUser(user);
            user = userDao.saveUser(user);
            String activationLink = generateActivationLink();
            user.setActivationLink(activationLink);
            userDao.updateUser(user);
            Mail mail = new Mail();
            mail.setFrom("your.library.notification@gmail.com");
            mail.setTo(user.getEmail());
            mail.setSubject("Potwierdzenie rejestracji w Library Application");

            Map model = new HashMap();
            model.put("name", user.getName() + user.getSurname());
            model.put("location", "Poland");
            model.put("signature", "Library Application");
            model.put("activationLink", BASE_URL + "activate/" + activationLink + "/" + user.getId());
            mail.setModel(model);

            try {
                emailService.sendSimpleMessage(mail);
            } catch (MessagingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (ValidationException e) {
            throw new ValidationException(e);
        }
    }

    private String generateActivationLink(){
	    String random = generateString();
	    while (random.contains("/")){
            random = generateString();
        }
	    String link = generateString();
	    return link;
    }

    public static String generateString() {
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }

    private User createUserFromData(Map<String, String> userData) {
        User user = new User();
        user.setEmail(userData.get("email"));
        user.setLogin(userData.get("login"));
        user.setName(userData.get("name"));
        user.setSurname(userData.get("surname"));
        user.setPassword(userData.get("password"));
        user.setPasswordConfirmation(userData.get("password2"));
        return user;
    }

    private String generateToken() {
		return UUID.randomUUID().toString();
	}

    public void activateUser(String activationUrl, Long id) {
        User user = userDao.getUser(id);
        if (user.getActivationLink().equals(activationUrl)){
            user.setActivationLink(null);
            user.setActive(true);
            userDao.updateUser(user);
        }
    }
}
