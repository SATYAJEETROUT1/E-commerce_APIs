package org.jsp.shoppingcartapi.dao;

import java.util.Optional;

import org.jsp.shoppingcartapi.dto.MerchantDto;
import org.jsp.shoppingcartapi.repository.MerchantRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MerchantDao {
	@Autowired
	public MerchantRepo repo;

	public MerchantDto saveMerchant(MerchantDto merchant) {
		return repo.save(merchant);
	}

	public MerchantDto updateMerchant(MerchantDto merchant) {
		return repo.save(merchant);
	}

	public MerchantDto verifyMerchant(String token) {
		return repo.findMerchantByToken(token);
	}

	public MerchantDto findMerchantByEmail(String email) {
		return repo.findMerchantByEmail(email);
	}
	public Optional<MerchantDto> loginVerifyMerchant(String email,String password){
		return repo.verifyMerchant(email,password);
	}
	public Optional<MerchantDto> findById(int id){
		return repo.findById(id);
	}
}
