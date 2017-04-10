package jrd.graduationproject.shoppingplatform.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jrd.graduationproject.shoppingplatform.dao.mybatis.UserMapper;
import jrd.graduationproject.shoppingplatform.pojo.enumfield.AdminEnum;
import jrd.graduationproject.shoppingplatform.pojo.enumfield.StatusEnum;
import jrd.graduationproject.shoppingplatform.pojo.po.User;
import jrd.graduationproject.shoppingplatform.pojo.vo.PageParam;
import jrd.graduationproject.shoppingplatform.service.ISystemService;

@Service
public class SystemServiceImpl implements ISystemService {

	@Autowired
	private UserMapper userMapper;

	@Override
	@Transactional
	public User grantAdmin(User admin, User user) {

		if (!validateSuperAdmin(admin))
			return null;

		if ((user.getPower() & AdminEnum.ADMIN.getIndex()) == AdminEnum.ADMIN.getIndex())
			return null;

		user.setPower(user.getPower() | AdminEnum.ADMIN.getIndex());
		userMapper.updateByPrimaryKeySelective(user);
		return userMapper.selectByPrimaryKey(user.getId());
	}

	@Override
	@Transactional
	public User cancelAdmin(User admin, User user) {
		if (!validateSuperAdmin(admin))
			return null;

		if ((user.getPower() & AdminEnum.ADMIN.getIndex()) != AdminEnum.ADMIN.getIndex())
			return null;

		user.setPower(user.getPower() - AdminEnum.ADMIN.getIndex());
		userMapper.updateByPrimaryKeySelective(user);
		return userMapper.selectByPrimaryKey(user.getId());
	}

	@Override
	@Transactional
	public User grantSeller(User user) {
		if ((user.getPower() & AdminEnum.SHOPKEEPER.getIndex()) == AdminEnum.SHOPKEEPER.getIndex())
			return null;

		user.setPower(user.getPower() | AdminEnum.ADMIN.getIndex());
		userMapper.updateByPrimaryKeySelective(user);
		return userMapper.selectByPrimaryKey(user.getId());
	}

	@Override
	@Transactional
	public User cancelSeller(User user) {
		if ((user.getPower() & AdminEnum.SHOPKEEPER.getIndex()) != AdminEnum.SHOPKEEPER.getIndex())
			return null;

		user.setPower(user.getPower() - AdminEnum.ADMIN.getIndex());
		userMapper.updateByPrimaryKeySelective(user);
		return userMapper.selectByPrimaryKey(user.getId());
	}

	@Override
	@Transactional
	public User freezeUser(User user) {
		user.setStatus(StatusEnum.CANCEL);
		userMapper.updateByPrimaryKeySelective(user);
		return userMapper.selectByPrimaryKey(user.getId());
	}

	private boolean validateSuperAdmin(User user) {
		if ("342623J19950718R0302D".equals(user.getId()))
			return true;
		return false;
	}

	@Override
	public List<User> findAllAdmin(User user, PageParam page) {
		// TODO Auto-generated method stub
		return null;
	}

}
