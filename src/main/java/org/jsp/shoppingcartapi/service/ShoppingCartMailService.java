package org.jsp.shoppingcartapi.service;

import org.jsp.shoppingcartapi.dto.EmailConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
public class ShoppingCartMailService {
	@Autowired
	private JavaMailSender javaMailSender;

	public String sendMail(EmailConfiguration configuration) {
		MimeMessage massage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(massage);
		try {
			helper.setTo(configuration.getUser().get("email"));
			helper.setSubject(configuration.getSubject());
			helper.setText(configuration.getText());
			javaMailSender.send(massage);
			return "Mail sent succesfully";
		} catch (Exception e) {
			return "Unable to send email";
		}
	}
}
