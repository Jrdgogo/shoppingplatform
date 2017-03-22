package jrd.graduationproject.shoppingplatform.service;

import java.util.List;

import jrd.graduationproject.shoppingplatform.pojo.po.User;

public interface IUserService {

	List<User> getAllUser();

	User getUserByName_Pwd(User user);

	Boolean getUserByName(String username);

	Boolean ActivationUser(String id, String activeCode);

	Boolean RegisterUser(User user);
}
