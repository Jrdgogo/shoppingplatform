package jrd.graduationproject.shoppingplatform.service;

import jrd.graduationproject.shoppingplatform.pojo.po.User;

public interface IUserService {

	User getUserByName_Pwd(User user);

	User getUserByName(String username);

	User ActivationUser(String id, String activeCode);

	User RegisterUser(User user);

	User alterUserInfo(User user);

	User getUserInfo(String id);
}
