package jrd.graduationproject.shoppingplatform.config.jdbc.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import jrd.graduationproject.shoppingplatform.dao.jpa.UserJpa;
import jrd.graduationproject.shoppingplatform.pojo.enumfield.AdminEnum;
import jrd.graduationproject.shoppingplatform.pojo.enumfield.SexEnum;
import jrd.graduationproject.shoppingplatform.pojo.enumfield.StatusEnum;
import jrd.graduationproject.shoppingplatform.pojo.po.User;
import jrd.graduationproject.shoppingplatform.util.GlobalUtil;

@Component
@Order(value = 1)
public class UserFill implements CommandLineRunner {

	@Autowired
	private UserJpa userJpa;

	@Override
	@Transactional
	public void run(String... arg0) throws Exception {
		String id = "342623J19950718R0302D";
		if (userJpa.findOne(id) != null)
			return;

		User user = new User();
		user.setId(id);
		user.setUsername("jrd");
		String pwd = GlobalUtil.md5("shop_J");
		user.setPassword(pwd);
		user.setAccount(100000.0);
		user.setPaymentpwd(pwd);
		user.setSex(SexEnum.MAN);
		user.setEmail("1477450172@qq.com");
		user.setAge(22);
		user.setStatus(StatusEnum.ACTIVE);
		user.setPhone("15576254691");
		user.setBirth(GlobalUtil.formatDate("1995-07-18"));
		user.setRealname("季睿东");
		user.setPower(1 + 2 + 4);
		user.setCard(AdminEnum.ADMIN);
		userJpa.saveAndFlush(user);
	}

}
