package org.jsp.shoppingcartapi.repository;

import java.util.Optional;

import org.jsp.shoppingcartapi.dto.MerchantDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MerchantRepo extends JpaRepository<MerchantDto, Integer> {
	@Query("select m from MerchantDto m where m.token=?1")
	MerchantDto findMerchantByToken(String token);

	@Query("select m from MerchantDto m where m.email=?1")
	MerchantDto findMerchantByEmail(String email);
	
	@Query("select m from MerchantDto m where m.email=?1 and m.password=?2")
	 public Optional<MerchantDto> verifyMerchant(String email, String password);
}
