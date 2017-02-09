package jrd.graduationproject.shoppingplatform.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jrd.graduationproject.shoppingplatform.dao.jpa.mysql.RoleJpa;
import jrd.graduationproject.shoppingplatform.dao.jpa.mysql.UserJpa;
import jrd.graduationproject.shoppingplatform.pojo.Role;
import jrd.graduationproject.shoppingplatform.pojo.User;
import jrd.graduationproject.shoppingplatform.service.UserService;
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserJpa userJpa;
	@Autowired
	private RoleJpa roleJpa;
	
	@Override
	public List<User> getAllUser() {
		return userJpa.findAll();
	}

	@Override
	public List<Role> getAllRole() {
		return roleJpa.findAll();
	}

	@Override
	@Transactional
	public User save(User user) {
		return userJpa.save(user);
	}

}
