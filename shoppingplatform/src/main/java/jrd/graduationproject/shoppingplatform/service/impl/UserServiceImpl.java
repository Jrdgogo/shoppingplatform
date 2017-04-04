package jrd.graduationproject.shoppingplatform.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jrd.graduationproject.shoppingplatform.config.mail.MailPublish;
import jrd.graduationproject.shoppingplatform.config.redis.JedisDataSource;
import jrd.graduationproject.shoppingplatform.dao.jpa.UserJpa;
import jrd.graduationproject.shoppingplatform.dao.mybatis.UserMapper;
import jrd.graduationproject.shoppingplatform.exception.UserOptionErrorException;
import jrd.graduationproject.shoppingplatform.exception.category.NotSaveException;
import jrd.graduationproject.shoppingplatform.pojo.enumfield.StatusEnum;
import jrd.graduationproject.shoppingplatform.pojo.po.User;
import jrd.graduationproject.shoppingplatform.service.IUserService;
import jrd.graduationproject.shoppingplatform.util.GlobalUtil;
import redis.clients.jedis.Jedis;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserJpa userJpa;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private JedisDataSource jedisDataSource;
	@Autowired
	private MailPublish mailPublish;

	@Override
	public User getUserByName_Pwd(User user) {
		return userJpa.findByUsernameAndPassword(user.getUsername(), GlobalUtil.md5(user.getPassword()));
	}

	@Override
	public User getUserByName(String username) {
		return userJpa.findByUsername(username);
	}

	@Override
	@Transactional
	public User ActivationUser(String id, String activeCode) {
		User user = userJpa.findOne(id);
		if (user == null)
			throw new UserOptionErrorException("用户不存在！！！");
		if (user.getStatus().equals(StatusEnum.ACTIVE))
			throw new UserOptionErrorException("用户已经激活！！！");
		Jedis jedis = jedisDataSource.getJedis();
		try {
			String value = jedis.get(id);
			if (value == null) {
				ActiveCode(user, jedis);
				throw new UserOptionErrorException("激活码已失效！！！,已重新发送激活邮件，注意查收");
			}
			if (!value.equals(activeCode))
				throw new UserOptionErrorException("激活码错误！请点击邮件链接进行激活");

			jedis.del(id);

			user.setStatus(StatusEnum.ACTIVE);

			return userJpa.saveAndFlush(user);
		} catch (RuntimeException e) {
			throw e;
		} finally {
			if (jedis != null)
				jedis.close();
		}
	}

	@Override
	@Transactional
	public User RegisterUser(User user) {

		user.setId(GlobalUtil.getModelID(User.class));
		user.setPassword(GlobalUtil.md5(user.getPassword()));
		user.setPaymentpwd(user.getPassword());
		user.setStatus(StatusEnum.NOTACTIVE);

		Jedis jedis = jedisDataSource.getJedis();
		try {
			ActiveCode(user, jedis);
			return userJpa.saveAndFlush(user);
		} catch (RuntimeException e) {
			throw e;
		} finally {
			if (jedis != null)
				jedis.close();
		}
	}

	private void ActiveCode(User user, Jedis jedis) {
		String activeCode = GlobalUtil.get32bitString();
		String reply = jedis.set(user.getId(), activeCode, "NX", "EX", 60 * 30);
		if (!"OK".equals(reply)) {
			throw new NotSaveException("用户激活码保存失败！");
		}
		String logmsg = "用戶:" + user.getId() + ";激活码:" + activeCode;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userName", user.getUsername());
		params.put("url",
				"http://127.0.0.1/home/activationUser.action?id=" + user.getId() + "&activeCode=" + activeCode);
		String msg = GlobalUtil.toJsonString(logmsg, params, user.getEmail());
		mailPublish.publish(msg);
	}

	@Override
	@Transactional
	public User alterUserInfo(User user) {
		userMapper.updateByPrimaryKeySelective(user);
		return userMapper.selectByPrimaryKey(user.getId());
	}

	@Override
	public User getUserInfo(String id) {
		return userJpa.findOne(id);
	}

}
