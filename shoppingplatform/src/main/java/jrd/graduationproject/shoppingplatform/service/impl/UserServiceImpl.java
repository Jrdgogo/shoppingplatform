package jrd.graduationproject.shoppingplatform.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jrd.graduationproject.shoppingplatform.config.mail.MailPublish;
import jrd.graduationproject.shoppingplatform.config.redis.JedisDataSource;
import jrd.graduationproject.shoppingplatform.dao.jpa.SellerJpa;
import jrd.graduationproject.shoppingplatform.dao.jpa.ShopCarJpa;
import jrd.graduationproject.shoppingplatform.dao.jpa.UserJpa;
import jrd.graduationproject.shoppingplatform.dao.jpa.UserWareAddrJpa;
import jrd.graduationproject.shoppingplatform.dao.jpa.WareJpa;
import jrd.graduationproject.shoppingplatform.dao.mybatis.ShopCarMapper;
import jrd.graduationproject.shoppingplatform.dao.mybatis.UserMapper;
import jrd.graduationproject.shoppingplatform.dao.mybatis.UserWareAddrMapper;
import jrd.graduationproject.shoppingplatform.exception.UserOptionErrorException;
import jrd.graduationproject.shoppingplatform.exception.category.NotSaveException;
import jrd.graduationproject.shoppingplatform.pojo.enumfield.StatusEnum;
import jrd.graduationproject.shoppingplatform.pojo.po.Seller;
import jrd.graduationproject.shoppingplatform.pojo.po.ShopCar;
import jrd.graduationproject.shoppingplatform.pojo.po.User;
import jrd.graduationproject.shoppingplatform.pojo.po.UserWareAddr;
import jrd.graduationproject.shoppingplatform.pojo.po.Ware;
import jrd.graduationproject.shoppingplatform.service.IUserService;
import jrd.graduationproject.shoppingplatform.util.GlobalUtil;
import redis.clients.jedis.Jedis;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserJpa userJpa;
	@Autowired
	private ShopCarJpa shopcarJpa;
	@Autowired
	private ShopCarMapper shopcarMapper;
	@Autowired
	private SellerJpa sellerJpa;
	@Autowired
	private UserWareAddrJpa userWareAddrJpa;
	@Autowired
	private UserWareAddrMapper userWareAddrMapper;
	@Autowired
	private WareJpa wareJpa;
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
			userMapper.insertSelective(user);
			return userMapper.selectByPrimaryKey(user.getId());
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

	@Override
	public List<ShopCar> getUserShopCar(String id) {
		return shopcarJpa.findAll(new Sort(Sort.Direction.DESC, "createdate"));
	}

	@Override
	@Transactional
	public ShopCar getaddShopCar(String userid, Ware ware, Integer warenum) {
		ShopCar shopCar = new ShopCar();
		shopCar.setUser(userJpa.findOne(userid));

		Long num = shopcarJpa.count(Example.of(shopCar));
		if (num > 20) {
			return null;
		}
		shopCar.setId(GlobalUtil.getModelID(ShopCar.class));
		shopCar.setWare(wareJpa.findOne(ware.getId()));
		shopCar.setWarenum(warenum);
		shopCar.setCreatedate(new Date());
		return shopcarJpa.save(shopCar);
	}

	@Override
	@Transactional
	public void removeShopCar(List<String> shopcars) {
		for(String id:shopcars)
		   shopcarMapper.deleteByPrimaryKey(id);
	}

	@Override
	@Transactional
	public UserWareAddr addAddr(String id, UserWareAddr addr) {
		addr.setId(GlobalUtil.getModelID(UserWareAddr.class));
		User user = userJpa.findOne(id);
		addr.setUser(user);
		return userWareAddrJpa.saveAndFlush(addr);
	}

	@Override
	@Transactional
	public UserWareAddr alterAddr(UserWareAddr addr) {
		userWareAddrMapper.updateByPrimaryKey(addr);
		return userWareAddrMapper.selectByPrimaryKey(addr.getId());
	}

	@Override
	public Set<UserWareAddr> getAllAddr(String id) {

		return userJpa.findOne(id).getWareAddrs();
	}

	@Override
	public List<ShopCar> getUserShopCar(List<String> shopcars) {
		return shopcarJpa.findAll(shopcars);
	}

	@Override
	@Transactional
	public void applySeller(Seller seller, String id) {
		seller.setId(id);
		seller.setStatus(false);

		seller.setUser(userJpa.findOne(id));

		sellerJpa.saveAndFlush(seller);

	}

	@Override
	public User getUserByName_cookiePwd(User user) {
		return userJpa.findByUsernameAndPassword(user.getUsername(), user.getPassword());
	}

}
