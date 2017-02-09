package jrd.graduationproject.shoppingplatform.service;

import java.util.List;

import jrd.graduationproject.shoppingplatform.pojo.Role;
import jrd.graduationproject.shoppingplatform.pojo.User;

public interface UserService {

	List<User> getAllUser();

	List<Role> getAllRole();
	
	User save(User user);
}
