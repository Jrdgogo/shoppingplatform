package jrd.graduationproject.shoppingplatform.config.jdbc.data;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import jrd.graduationproject.shoppingplatform.dao.jpa.UserJpa;
import jrd.graduationproject.shoppingplatform.pojo.enumfield.SexEnum;
import jrd.graduationproject.shoppingplatform.pojo.enumfield.StatusEnum;
import jrd.graduationproject.shoppingplatform.pojo.po.User;
import jrd.graduationproject.shoppingplatform.util.GlobalUtil;

@Component
@Order(value = 2)
public class AddUser implements CommandLineRunner {

	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private UserJpa userJpa;

	@Override
	@Transactional
	public void run(String... arg0) throws Exception {
		User user = new User();

		user.setUsername("jrd");
		user.setPassword("shop_J");
		user.setSex(SexEnum.MAN);
		user.setEmail("1477450172@qq.com");
		user.setAge(22);
		user.setQq("1477450172");
		user.setStatus(StatusEnum.ACTIVE);
		user.setPhone("15576254691");
		user.setAddress("china");
		user.setBirth(GlobalUtil.formatDate("1995-07-18"));
		user.setRealname("季睿东");
		user.setSelfdesc("毕业生");
		entityManager.joinTransaction();
		userJpa.save(user);
	}

}
