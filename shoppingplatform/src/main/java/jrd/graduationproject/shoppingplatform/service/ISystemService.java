package jrd.graduationproject.shoppingplatform.service;

import jrd.graduationproject.shoppingplatform.pojo.po.User;

public interface ISystemService {
	
	User grantAdmin(User admin,User user);
	
	User cancelAdmin(User admin,User user);
	
	User grantSeller(User user);
	
	User cancelSeller(User user);
	
	User freezeUser(User user);
}
