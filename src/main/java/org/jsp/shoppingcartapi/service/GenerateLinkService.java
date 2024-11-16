package org.jsp.shoppingcartapi.service;

import org.jsp.shoppingcartapi.dao.MerchantDao;
import org.jsp.shoppingcartapi.dao.UserDao;
import org.jsp.shoppingcartapi.dto.MerchantDto;
import org.jsp.shoppingcartapi.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import net.bytebuddy.utility.RandomString;

@Service
public class GenerateLinkService {
	@Autowired
	private MerchantDao dao;
	@Autowired
	private UserDao daoo;

	public String getVerificationLink(HttpServletRequest request, MerchantDto merchantDto) {
		String token = RandomString.make(45);
		merchantDto.setToken(token);
		merchantDto.setStatus("InActive");
		dao.updateMerchant(merchantDto);
		String siteurl = request.getRequestURL().toString();
		String url = siteurl.replace(request.getServletPath(), "");
		String verfiy_link = url + "/merchants/verify?token=" + token;
		System.out.println(verfiy_link);
		return verfiy_link;
	}

	public String getResetPasswordLink(HttpServletRequest request, MerchantDto merchantDto) {
		String token = RandomString.make(45);
		merchantDto.setToken(token);
		dao.updateMerchant(merchantDto);
		String siteurl = request.getRequestURL().toString();
		String url = siteurl.replace(request.getServletPath(), "");
		String reset_link = url + "/merchants/reset-passeord?token=" + token;
		return reset_link;
	}
	
	public String getVerificationLink(HttpServletRequest request, User user) {
		String token = RandomString.make(45);
		user.setToken(token);
		user.setStatus("InActive");
		daoo.updateUser(user);
		String siteurl = request.getRequestURL().toString();
		String url = siteurl.replace(request.getServletPath(), "");
		String verfiy_link = url + "/users/verify?token=" + token;
		System.out.println(verfiy_link);
		return verfiy_link;
	}

	public String getResetPasswordLink(HttpServletRequest request, User user) {
		String token = RandomString.make(45);
		user.setToken(token);
		daoo.updateUser(user);
		String siteurl = request.getRequestURL().toString();
		String url = siteurl.replace(request.getServletPath(), "");
		String reset_link = url + "/users/reset-passeord?token=" + token;
		return reset_link;
	}
}
