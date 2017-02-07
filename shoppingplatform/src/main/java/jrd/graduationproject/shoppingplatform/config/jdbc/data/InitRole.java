package jrd.graduationproject.shoppingplatform.config.jdbc.data;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import jrd.graduationproject.shoppingplatform.pojo.Role;
import jrd.graduationproject.shoppingplatform.pojo.enumfield.AdminEnum;

@Component
@Order(value=1)
public class InitRole implements CommandLineRunner {

	@Autowired
	private EntityManager entityManager;
	
	@Override
	public void run(String... arg0) throws Exception {
		for(AdminEnum adminEnum:AdminEnum.values()){
			Role role=new Role();
			role.setAdmin(adminEnum);
			EntityTransaction entityTransaction=entityManager.getTransaction();
			entityTransaction.begin();
			entityManager.persist(role);
			entityTransaction.commit();
		}

	}

}
