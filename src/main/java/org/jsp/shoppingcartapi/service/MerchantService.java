package org.jsp.shoppingcartapi.service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Optional;

import org.jsp.shoppingcartapi.dao.MerchantDao;
import org.jsp.shoppingcartapi.dto.EmailConfiguration;
import org.jsp.shoppingcartapi.dto.MerchantDto;
import org.jsp.shoppingcartapi.dto.ResponseStructure;
import org.jsp.shoppingcartapi.exception.InvalidCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class MerchantService {
	@Autowired
	private MerchantDao dao;
	@Autowired
	private EmailConfiguration configuration;
	@Autowired
	private ShoppingCartMailService mailService;
	@Autowired
	private GenerateLinkService service;

	public ResponseEntity<ResponseStructure<MerchantDto>> saveMerchant(MerchantDto merchant,
			HttpServletRequest request) {
		ResponseStructure<MerchantDto> response = new ResponseStructure<>();
		response.setMessage("Merchant  register successfully");
		response.setData(dao.saveMerchant(merchant));
		response.setStatusCode(HttpStatus.CREATED.value());
		configuration.setSubject("Registration succesful");
		HashMap<String, String> map = new LinkedHashMap<>();
		map.put("email", merchant.getEmail());
		map.put("name", merchant.getName());
		configuration.setText("Hello Mr." + merchant.getName()
				+ " You have succesfully initiated the  registration for Our shopping-cart "
				+ "please click on the link " + service.getVerificationLink(request, merchant));
		configuration.setUser(map);
		mailService.sendMail(configuration);
		return new ResponseEntity<ResponseStructure<MerchantDto>>(response, HttpStatus.CREATED);
	}

	public ResponseEntity<ResponseStructure<String>> verifyMerchant(String token) {
		ResponseStructure<String> structure = new ResponseStructure<>();
		MerchantDto merchant = dao.verifyMerchant(token);
		if (merchant != null) {
			merchant.setToken(null);
			merchant.setStatus("Active");
			dao.updateMerchant(merchant);
			structure.setData("Your Account is Activated");
			structure.setStatusCode(HttpStatus.OK.value());
			structure.setMessage("Merchant is Verified");
			return new ResponseEntity<ResponseStructure<String>>(structure, HttpStatus.OK);
		}
		throw new InvalidCredentialsException();

	}

	public ResponseEntity<ResponseStructure<String>> sendResetPasswordLink(String email, HttpServletRequest request) {
		ResponseStructure<String> structure = new ResponseStructure<>();
		MerchantDto merchant = dao.findMerchantByEmail(email);
		if (merchant != null) {
			HashMap<String, String> map = new LinkedHashMap<>();
			map.put("email", merchant.getEmail());
			map.put("name", merchant.getName());

			configuration.setSubject("Reset Password");
			configuration.setUser(map);
			configuration.setText("Hello Mr." + merchant.getName()
					+ " You have requested for password change please click on the following link "
					+ service.getResetPasswordLink(request, merchant));
			mailService.sendMail(configuration);
			structure.setData("Reset password link send to email");
			structure.setStatusCode(HttpStatus.ACCEPTED.value());
			structure.setMessage("Mail send to merchant");
			return new ResponseEntity<ResponseStructure<String>>(structure, HttpStatus.ACCEPTED);
		}
		throw new InvalidCredentialsException();
	}

	public ResponseEntity<ResponseStructure<MerchantDto>> loginVerifyByMerchant(String email, String password) {
		ResponseStructure<MerchantDto> structure = new ResponseStructure<>();
		Optional<MerchantDto> recMerchant = dao.loginVerifyMerchant(email, password);
		if (recMerchant.isPresent()) {
			structure.setData(recMerchant.get());
			structure.setMessage("User login");
			structure.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<MerchantDto>>(structure, HttpStatus.OK);
		}
		throw new InvalidCredentialsException();
	}

}
