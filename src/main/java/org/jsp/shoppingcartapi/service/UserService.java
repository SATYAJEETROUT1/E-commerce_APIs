package org.jsp.shoppingcartapi.service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Optional;

import org.jsp.shoppingcartapi.dao.UserDao;
import org.jsp.shoppingcartapi.dto.EmailConfiguration;
import org.jsp.shoppingcartapi.dto.Product;
import org.jsp.shoppingcartapi.dto.ResponseStructure;
import org.jsp.shoppingcartapi.dto.User;
import org.jsp.shoppingcartapi.exception.IdNotFoundException;
import org.jsp.shoppingcartapi.exception.InvalidCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserService {
	@Autowired
	private UserDao dao;
	@Autowired
	private EmailConfiguration configuration;
	@Autowired
	private ShoppingCartMailService mailService;
	@Autowired
	private GenerateLinkService service;

	public ResponseEntity<ResponseStructure<User>> saveUser(User user, HttpServletRequest request) {
		ResponseStructure<User> response = new ResponseStructure<>();
		response.setMessage("User register successfully");
		response.setData(dao.saveUser(user));
		response.setStatusCode(HttpStatus.CREATED.value());
		configuration.setSubject("Registration succesful");
		HashMap<String, String> map = new LinkedHashMap<>();
		map.put("email", user.getEmail());
		map.put("name", user.getName());
		configuration.setText("Hello Mr." + user.getName()
				+ " You have succesfully initiated the  registration for Our shopping-cart "
				+ "please click on the link " + service.getVerificationLink(request, user));
		configuration.setUser(map);
		mailService.sendMail(configuration);
		return new ResponseEntity<ResponseStructure<User>>(response, HttpStatus.CREATED);
	}

	public ResponseEntity<ResponseStructure<String>> verifyUser(String token) {
		ResponseStructure<String> structure = new ResponseStructure<>();
		User user = dao.verifyUser(token);
		if (user != null) {
			user.setToken(null);
			user.setStatus("Active");
			dao.updateUser(user);
			structure.setData("Your Account is Activated");
			structure.setStatusCode(HttpStatus.OK.value());
			structure.setMessage("User is Verified");
			return new ResponseEntity<ResponseStructure<String>>(structure, HttpStatus.OK);
		}
		throw new InvalidCredentialsException();

	}

	public ResponseEntity<ResponseStructure<String>> sendResetPasswordLink(String email, HttpServletRequest request) {
		ResponseStructure<String> structure = new ResponseStructure<>();
		User user = dao.findUserByEmail(email);
		if (user != null) {
			HashMap<String, String> map = new LinkedHashMap<>();
			map.put("email", user.getEmail());
			map.put("name", user.getName());

			configuration.setSubject("Reset Password");
			configuration.setUser(map);
			configuration.setText("Hello Mr." + user.getName()
					+ " You have requested for password change please click on the following link "
					+ service.getResetPasswordLink(request, user));
			mailService.sendMail(configuration);
			structure.setData("Reset password link send to email");
			structure.setStatusCode(HttpStatus.ACCEPTED.value());
			structure.setMessage("Mail send to user");
			return new ResponseEntity<ResponseStructure<String>>(structure, HttpStatus.ACCEPTED);
		}
		throw new InvalidCredentialsException();
	}

	public ResponseEntity<ResponseStructure<User>> loginVerifyByUser(String email, String password) {
		Optional<User> recUser = dao.loginVerifyUser(email, password);
		ResponseStructure<User> structure = new ResponseStructure<>();
		if (recUser.isPresent()) {
			structure.setData(recUser.get());
			structure.setMessage("User login successfully");
			structure.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<User>>(structure, HttpStatus.OK);
		}
		throw new InvalidCredentialsException();
	}

	public ResponseEntity<ResponseStructure<User>> findById(int id) {
		Optional<User> recUser = dao.findById(id);
		ResponseStructure<User> structure = new ResponseStructure<>();
		if (recUser.isPresent()) {
			structure.setData(recUser.get());
			structure.setMessage("Product Found");
			structure.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<User>>(structure, HttpStatus.OK);
		}
		throw new IdNotFoundException();
	}

}
