package jrd.graduationproject.shoppingplatform.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jrd.graduationproject.shoppingplatform.dao.jpa.OrderJpa;
import jrd.graduationproject.shoppingplatform.dao.jpa.OrderSellerJpa;
import jrd.graduationproject.shoppingplatform.dao.jpa.UserJpa;
import jrd.graduationproject.shoppingplatform.exception.UserOptionErrorException;
import jrd.graduationproject.shoppingplatform.pojo.enumfield.OrderStatusEnum;
import jrd.graduationproject.shoppingplatform.pojo.po.Order;
import jrd.graduationproject.shoppingplatform.pojo.po.OrderOfSeller;
import jrd.graduationproject.shoppingplatform.pojo.po.User;
import jrd.graduationproject.shoppingplatform.pojo.vo.PageParam;
import jrd.graduationproject.shoppingplatform.service.IOrderService;
import jrd.graduationproject.shoppingplatform.util.GlobalUtil;

@Service
public class OrderServiceImpl implements IOrderService {

	@Autowired
	private OrderJpa orderJpa;
	@Autowired
	private OrderSellerJpa orderSellerJpa;
	
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

	@Override
	public Slice<Order> getOrdersBySeller(Order order, String id, PageParam page) {
		Sort sort = new Sort(Sort.Direction.DESC, "update");
		Pageable pageable = new PageRequest(page.getPagenum() - 1, page.getPagesize(), sort);
		OrderOfSeller os = new OrderOfSeller();
		os.setOrderid(order.getId());
		os.setSellerid(id);
		Example<OrderOfSeller> example = Example.of(os); 
		Page<OrderOfSeller> idpages=orderSellerJpa.findAll(example, pageable);
		List<OrderOfSeller> orderOfSellers=idpages.getContent();
		List<String> ids=new ArrayList<>();
		for(OrderOfSeller orderOfSeller:orderOfSellers)
			ids.add(orderOfSeller.getOrderid());
		return new PageImpl<Order>(orderJpa.findAll(ids),pageable,idpages.getTotalElements());
	}


	
	
}
