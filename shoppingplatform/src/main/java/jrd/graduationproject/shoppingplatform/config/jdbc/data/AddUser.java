package jrd.graduationproject.shoppingplatform.config.jdbc.data;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import jrd.graduationproject.shoppingplatform.dao.jpa.mysql.UserJpa;
import jrd.graduationproject.shoppingplatform.pojo.User;
import jrd.graduationproject.shoppingplatform.pojo.enumfield.SexEnum;

@Component
@Order(value=2)
public class AddUser implements CommandLineRunner {


	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private UserJpa userJpa;
	
	@Override
	@Transactional
	public void run(String... arg0) throws Exception {
		User user=new User();
		user.setName("testUser");
		user.setPassword("123");
		user.setSex(SexEnum.MAN);
		user.setEmail("1477450172@qq.com");
		
		entityManager.joinTransaction();
		userJpa.save(user);
	}

}
