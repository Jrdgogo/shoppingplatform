package jrd.graduationproject.shoppingplatform.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jrd.graduationproject.shoppingplatform.dao.jpa.MessageJpa;
import jrd.graduationproject.shoppingplatform.dao.jpa.UserJpa;
import jrd.graduationproject.shoppingplatform.dao.mybatis.UserMapper;
import jrd.graduationproject.shoppingplatform.pojo.enumfield.AdminEnum;
import jrd.graduationproject.shoppingplatform.pojo.enumfield.StatusEnum;
import jrd.graduationproject.shoppingplatform.pojo.po.Message;
import jrd.graduationproject.shoppingplatform.pojo.po.User;
import jrd.graduationproject.shoppingplatform.pojo.vo.PageParam;
import jrd.graduationproject.shoppingplatform.service.ISystemService;

@Service
public class SystemServiceImpl implements ISystemService {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UserJpa userJpa;
	@Autowired
	private MessageJpa messageJpa;

	@Override
	@Transactional
	public User grantAdmin(User admin, User user) {

		if (!validateSuperAdmin(admin))
			return null;

		if ((user.getPower() & AdminEnum.ADMIN.getIndex()) == AdminEnum.ADMIN.getIndex())
			return null;

		user.setPower(user.getPower() | AdminEnum.ADMIN.getIndex());
		user.setCard(AdminEnum.getAdminByPower(user.getPower()));
		userMapper.updateByPrimaryKeySelective(user);
		return userMapper.selectByPrimaryKey(user.getId());
	}

	@Override
	@Transactional
	public User cancelAdmin(User admin, User user) {
		if (!validateSuperAdmin(admin))
			return null;

		if (admin.getId().equals(user.getId()))
			return null;

		if ((user.getPower() & AdminEnum.ADMIN.getIndex()) != AdminEnum.ADMIN.getIndex())
			return null;

		user.setPower(user.getPower() - AdminEnum.ADMIN.getIndex());
		user.setCard(AdminEnum.getAdminByPower(user.getPower()));
		userMapper.updateByPrimaryKeySelective(user);
		return userMapper.selectByPrimaryKey(user.getId());
	}

	@Override
	@Transactional
	public User grantSeller(User user) {
		if ((user.getPower() & AdminEnum.SHOPKEEPER.getIndex()) == AdminEnum.SHOPKEEPER.getIndex())
			return null;

		user.setPower(user.getPower() | AdminEnum.ADMIN.getIndex());
		user.setCard(AdminEnum.getAdminByPower(user.getPower()));
		userMapper.updateByPrimaryKeySelective(user);
		return userMapper.selectByPrimaryKey(user.getId());
	}

	@Override
	@Transactional
	public User cancelSeller(User user) {
		if ((user.getPower() & AdminEnum.SHOPKEEPER.getIndex()) != AdminEnum.SHOPKEEPER.getIndex())
			return null;

		user.setPower(user.getPower() - AdminEnum.ADMIN.getIndex());
		user.setCard(AdminEnum.getAdminByPower(user.getPower()));
		userMapper.updateByPrimaryKeySelective(user);
		return userMapper.selectByPrimaryKey(user.getId());
	}

	@Override
	@Transactional
	public User freezeUser(User user) {
		user.setStatus(StatusEnum.CANCEL);
		user.setPower(0);
		// user.setCard(AdminEnum.getAdminByPower(user.getPower()));
		userMapper.updateByPrimaryKeySelective(user);
		return userMapper.selectByPrimaryKey(user.getId());
	}

	private boolean validateSuperAdmin(User user) {
		if ("342623J19950718R0302D".equals(user.getId()))
			return true;
		return false;
	}

	@Override
	public Page<Message> selectMessage(PageParam page, String id) {
		Sort sort = new Sort(Sort.Direction.ASC, "status").and(new Sort(Sort.Direction.DESC, "update"));
		User admin = new User();
		admin.setId(id);
		Message message = new Message();

		if (!validateSuperAdmin(admin))
			message.setType(1);
		Example<Message> example = Example.of(message);
		Pageable pageable = new PageRequest(page.getPagenum() - 1, page.getPagesize(), sort);
		return messageJpa.findAll(example, pageable);
	}

	@Override
	public Page<User> SelectUserByType(AdminEnum adminEnum, PageParam page) {
		Sort sort = new Sort(Sort.Direction.DESC, "update");
		Pageable pageable = new PageRequest(page.getPagenum() - 1, page.getPagesize(), sort);
		User user = new User();
		user.setCard(adminEnum);
		Example<User> example = Example.of(user);
		return userJpa.findAll(example, pageable);
	}

	@Override
	@Transactional
	public User activeUser(User user) {
		user.setStatus(StatusEnum.ACTIVE);
		user.setPower(AdminEnum.USER.getPower());
		user.setCard(AdminEnum.USER);
		userMapper.updateByPrimaryKeySelective(user);
		return userMapper.selectByPrimaryKey(user.getId());
	}

	@Override
	@Transactional
	public Message handleMessage(String id) {
		Message message = messageJpa.findOne(id);
		message.setStatus(true);
		return messageJpa.save(message);
	}

}
