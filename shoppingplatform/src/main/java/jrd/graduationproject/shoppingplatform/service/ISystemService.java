package jrd.graduationproject.shoppingplatform.service;

import java.util.List;

import jrd.graduationproject.shoppingplatform.pojo.po.User;
import jrd.graduationproject.shoppingplatform.pojo.vo.PageParam;

public interface ISystemService {
	
	User grantAdmin(User admin,User user);
	
	User cancelAdmin(User admin,User user);
	
	User grantSeller(User user);
	
	User cancelSeller(User user);
	
	User freezeUser(User user);

	List<User> findAllAdmin(User user, PageParam page);
}
