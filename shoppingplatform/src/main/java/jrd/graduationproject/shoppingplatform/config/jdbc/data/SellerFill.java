package jrd.graduationproject.shoppingplatform.config.jdbc.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import jrd.graduationproject.shoppingplatform.dao.jpa.SellerJpa;
import jrd.graduationproject.shoppingplatform.dao.jpa.UserJpa;
import jrd.graduationproject.shoppingplatform.pojo.po.Seller;

@Component
@Order(value = 2)
public class SellerFill implements CommandLineRunner {

	@Autowired
	private SellerJpa sellerJpa;
	@Autowired
	private UserJpa userJpa;

	@Override
	@Transactional
	public void run(String... arg0) throws Exception {
		String id = "342623J19950718R0302D";
		if (sellerJpa.findOne(id) != null)
			return;

		Seller seller = new Seller();
		seller.setId(id);
		seller.setUser(userJpa.findOne(id));
		seller.setName("自营");
		seller.setStatus(true);
		seller.setLogo("/images/photo/01/ab/self.png");
		sellerJpa.saveAndFlush(seller);
	}

}
