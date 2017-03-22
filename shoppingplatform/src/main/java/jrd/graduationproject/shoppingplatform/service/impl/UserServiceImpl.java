package jrd.graduationproject.shoppingplatform.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jrd.graduationproject.shoppingplatform.config.mail.SpringMail;
import jrd.graduationproject.shoppingplatform.dao.jpa.UserJpa;
import jrd.graduationproject.shoppingplatform.dao.mybatis.UserExtrMapper;
import jrd.graduationproject.shoppingplatform.dao.mybatis.UserMapper;
import jrd.graduationproject.shoppingplatform.pojo.enumfield.StatusEnum;
import jrd.graduationproject.shoppingplatform.pojo.po.User;
import jrd.graduationproject.shoppingplatform.pojo.po.UserExample;
import jrd.graduationproject.shoppingplatform.pojo.po.UserExtr;
import jrd.graduationproject.shoppingplatform.service.IUserService;
import jrd.graduationproject.shoppingplatform.util.GlobalUtil;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserJpa userJpa;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UserExtrMapper userExtrMapper;
	@Autowired
	private SpringMail springMail;

	@Override
	public List<User> getAllUser() {
		return userJpa.findAll();
	}

	@Override
	public User getUserByName_Pwd(User user) {
		UserExample userExample = new UserExample();
		userExample.createCriteria().andUsernameEqualTo(user.getUsername())
				.andPasswordEqualTo(GlobalUtil.md5(user.getPassword()));
		List<User> users = userMapper.selectByExample(userExample);
		if (users != null && !users.isEmpty())
			return users.get(0);
		return null;
	}

	@Override
	public Boolean getUserByName(String username) {
		UserExample userExample = new UserExample();
		userExample.createCriteria().andUsernameEqualTo(username);
		int count = userMapper.countByExample(userExample);
		if (count > 0)
			return true;
		return false;
	}

	@Override
	@Transactional
	public Boolean ActivationUser(String id, String activeCode) {
		UserExtr extr = userExtrMapper.selectByPrimaryKey(id);
		if (extr == null)
			throw new RuntimeException("激活链接已失效");
		String code = extr.getActivecode();
		if (code.equals(activeCode)) {
			User record = userJpa.findOne(id);
			record.setStatus(StatusEnum.ACTIVE);
			if (userJpa.save(record)!=null)
				throw new RuntimeException("激活失败");
			userExtrMapper.deleteByPrimaryKey(id);
			return true;
		}
		return false;
	}

	@Override
	@Transactional
	public Boolean RegisterUser(User user) {
		user.setId(GlobalUtil.getModelID(User.class));
		user.setPassword(GlobalUtil.md5(user.getPassword()));
		if ((user = userJpa.save(user)) != null) {
			UserExtr record = new UserExtr();
			record.setId(user.getId());
			record.setActivecode(GlobalUtil.get32bitString());
			if (userExtrMapper.insertSelective(record) <= 0)
				throw new RuntimeException("激活码保存失败");

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("userName", user.getUsername());
			params.put("url",
					"/home/activationUser.action?id=" + user.getId() + "&activeCode=" + record.getActivecode());
			if (!springMail.doSend("用户激活", "activeion_User.ftl", params, user.getEmail()))
				throw new RuntimeException("邮箱发送失败");
			return true;
		}
		return false;
	}

}
