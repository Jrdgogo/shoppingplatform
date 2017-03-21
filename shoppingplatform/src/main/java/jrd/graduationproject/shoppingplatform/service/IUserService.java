package jrd.graduationproject.shoppingplatform.service;

import java.util.List;

import jrd.graduationproject.shoppingplatform.pojo.po.Role;
import jrd.graduationproject.shoppingplatform.pojo.po.User;

public interface IUserService {

	List<User> getAllUser();

	List<Role> getAllRole();

	User getUserByName_Pwd(User user);

	Boolean getUserByName(String username);

	Boolean ActivationUser(Integer id, String activeCode);

	Boolean RegisterUser(User user);
}
