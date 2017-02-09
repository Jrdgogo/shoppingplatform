package jrd.graduationproject.shoppingplatform.config.jdbc.data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import jrd.graduationproject.shoppingplatform.dao.jpa.mysql.RoleJpa;
import jrd.graduationproject.shoppingplatform.pojo.Role;
import jrd.graduationproject.shoppingplatform.pojo.enumfield.AdminEnum;

@Component
@Order(value=1)
public class InitRole implements CommandLineRunner {

	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private RoleJpa roleJpa;
	
	@Override
	@Transactional
	public void run(String... arg0) throws Exception {
		entityManager.joinTransaction();
		List<Role> roles=new ArrayList<Role>(0);
		for(AdminEnum adminEnum:AdminEnum.values()){
			Role role=new Role();
			role.setAdmin(adminEnum);
			roles.add(role);
		}
		roleJpa.save(roles);
	}

}
