package jrd.graduationproject.shoppingplatform.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jrd.graduationproject.shoppingplatform.dao.jpa.MessageJpa;
import jrd.graduationproject.shoppingplatform.dao.jpa.SellerJpa;
import jrd.graduationproject.shoppingplatform.dao.jpa.UserJpa;
import jrd.graduationproject.shoppingplatform.dao.jpa.WareJpa;
import jrd.graduationproject.shoppingplatform.dao.mybatis.UserMapper;
import jrd.graduationproject.shoppingplatform.pojo.enumfield.AdminEnum;
import jrd.graduationproject.shoppingplatform.pojo.enumfield.StatusEnum;
import jrd.graduationproject.shoppingplatform.pojo.po.Message;
import jrd.graduationproject.shoppingplatform.pojo.po.Seller;
import jrd.graduationproject.shoppingplatform.pojo.po.User;
import jrd.graduationproject.shoppingplatform.pojo.vo.PageParam;
import jrd.graduationproject.shoppingplatform.service.ISystemService;
import jrd.graduationproject.shoppingplatform.util.GlobalUtil;

@Service
public class SystemServiceImpl implements ISystemService {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UserJpa userJpa;
	@Autowired
	private SellerJpa sellerJpa;
	@Autowired
	private MessageJpa messageJpa;
	@Autowired
	private WareJpa wareJpa;

	@Override
	@Transactional
	public User grantAdmin(User admin, User user) {

		if (!validateSuperAdmin(admin))
			return null;

		user = userJpa.getOne(user.getId());

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

		user = userJpa.getOne(user.getId());

		if ((user.getPower() & AdminEnum.ADMIN.getIndex()) != AdminEnum.ADMIN.getIndex())
			return null;

		user.setPower(user.getPower() - AdminEnum.ADMIN.getIndex());
		user.setCard(AdminEnum.getAdminByPower(user.getPower()));
		userMapper.updateByPrimaryKeySelective(user);
		return userMapper.selectByPrimaryKey(user.getId());
	}

	@Override
	@Transactional
	public Seller grantSeller(Seller seller) {

		User user = userJpa.getOne(seller.getId());

		if ((user.getPower() & AdminEnum.SHOPKEEPER.getIndex()) == AdminEnum.SHOPKEEPER.getIndex())
			return null;

		user.setPower(user.getPower() | AdminEnum.SHOPKEEPER.getIndex());
		user.setCard(AdminEnum.getAdminByPower(user.getPower()));
		userMapper.updateByPrimaryKeySelective(user);
		seller = sellerJpa.findOne(user.getId());
		seller.setStatus(true);
		return sellerJpa.saveAndFlush(seller);
	}

	@Override
	@Transactional
	public void cancelSeller(Seller seller) {

		User user = userJpa.getOne(seller.getId());

		if ((user.getPower() & AdminEnum.SHOPKEEPER.getIndex()) != AdminEnum.SHOPKEEPER.getIndex())
			return;

		user.setPower(user.getPower() - AdminEnum.SHOPKEEPER.getIndex());
		user.setCard(AdminEnum.getAdminByPower(user.getPower()));
		userMapper.updateByPrimaryKeySelective(user);

		wareJpa.createWareBySeller(seller.getId());
		
		sellerJpa.delete(seller.getId());
	}

	@Override
	@Transactional
	public User freezeUser(User user) {

		user = userJpa.getOne(user.getId());

		user.setStatus(StatusEnum.CANCEL);
		user.setPower(0);
		// user.setCard(AdminEnum.getAdminByPower(user.getPower()));
		userMapper.updateByPrimaryKeySelective(user);
		return userMapper.selectByPrimaryKey(user.getId());
	}

	private boolean validateSuperAdmin(User user) {
		return validateSuperAdmin(user.getId());
	}

	private boolean validateSuperAdmin(String adminid) {
		return "342623J19950718R0302D".equals(adminid);
	}

	@Override
	public Page<Message> selectMessage(PageParam page, String id) {
		Sort sort = new Sort(Sort.Direction.ASC, "status").and(new Sort(Sort.Direction.DESC, "updatedate"));

		Message message = new Message();

		if (!validateSuperAdmin(id))
			message.setType(1);
		Example<Message> example = Example.of(message);
		Pageable pageable = new PageRequest(page.getPagenum() - 1, page.getPagesize(), sort);
		return messageJpa.findAll(example, pageable);
	}

	@Override
	public Page<User> SelectUserByType(AdminEnum adminEnum, PageParam page, User user) {
		Sort sort = new Sort(Sort.Direction.DESC, "updatedate");
		Pageable pageable = new PageRequest(page.getPagenum() - 1, page.getPagesize(), sort);
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

	@Override
	public Long countMessage(String id) {
		Message probe = new Message();
		probe.setStatus(false);
		if (!validateSuperAdmin(id))
			probe.setType(1);
		Example<Message> example = Example.of(probe);
		return messageJpa.count(example);
	}

	@Override
	@Transactional
	public Boolean apply(Integer type, String id) {
		Message entity = new Message();
		entity.setTypeid(id);
		entity.setId(GlobalUtil.getModelID(Message.class));
		entity.setCreatedate(new Date());
		entity.setUpdatedate(new Date());
		entity.setType(type);
		entity.setStatus(false);
		User user = userJpa.findOne(id);
		if (type == 1)
			entity.setMsg(user.getUsername() + "申请商家入驻^-^");
		else
			entity.setMsg(user.getUsername() + "申请管理员权限！！！");

		return messageJpa.saveAndFlush(entity) != null;
	}

	@Override
	public Page<Seller> SelectSeller(PageParam page, Seller seller) {
		//Sort sort = new Sort(Sort.Direction.DESC, "status");
		Pageable pageable = new PageRequest(page.getPagenum() - 1, page.getPagesize());
		seller.setStatus(true);
		Example<Seller> example = Example.of(seller);
		return sellerJpa.findAll(example, pageable);
	}

}
