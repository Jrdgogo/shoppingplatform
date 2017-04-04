package jrd.graduationproject.shoppingplatform.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jrd.graduationproject.shoppingplatform.dao.jpa.OrderJpa;
import jrd.graduationproject.shoppingplatform.dao.jpa.UserJpa;
import jrd.graduationproject.shoppingplatform.exception.UserOptionErrorException;
import jrd.graduationproject.shoppingplatform.pojo.enumfield.OrderStatusEnum;
import jrd.graduationproject.shoppingplatform.pojo.po.Order;
import jrd.graduationproject.shoppingplatform.pojo.po.User;
import jrd.graduationproject.shoppingplatform.service.IOrderService;
import jrd.graduationproject.shoppingplatform.util.GlobalUtil;

@Service
public class OrderServiceImpl implements IOrderService {

	@Autowired
	private OrderJpa orderJpa;
	
	@Autowired
	private UserJpa userJpa;
	
	@Override
	public List<Order> getOrdersbyUserId(String id) {
		Set<Order> set=userJpa.getOne(id).getOrders();
		return new ArrayList<>(set);
	}

	@Override
	@Transactional
	public Order defrayOrder(String orderid) {
		Order order=orderJpa.findOne(orderid);
		User user=order.getUser();
		Double orderMoney=order.getPrice();
		Double userMoney=user.getAccount();
		if(orderMoney>userMoney){
			throw new UserOptionErrorException("账户余额不足");
		}
		user.setAccount(userMoney-orderMoney);
		order.setStatus(OrderStatusEnum.PAYMENT);
		userJpa.saveAndFlush(user);
		return order;
	}

	@Override
	@Transactional
	public Order cancalOrder(String orderid) {
		Order order=orderJpa.findOne(orderid);
		order.setStatus(OrderStatusEnum.CANCEL);
		orderJpa.save(order);
		return order;
	}

	@Override
	@Transactional
	public Order backOrder(String orderid) {
		Order order=orderJpa.findOne(orderid);
		User user=order.getUser();
		Double orderMoney=order.getPrice();
		Double userMoney=user.getAccount();
		user.setAccount(userMoney+orderMoney);
		order.setStatus(OrderStatusEnum.BACK);
		userJpa.saveAndFlush(user);
		return order;
	}

	@Override
	public Order createOrder(Order order) {
		order.setId(GlobalUtil.getModelID(Order.class));
		//order.set
		return orderJpa.save(order);
	}


	
	
}
