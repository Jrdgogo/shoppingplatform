package jrd.graduationproject.shoppingplatform.service;

import java.util.List;

import jrd.graduationproject.shoppingplatform.pojo.po.Order;

public interface IOrderService {

	List<Order> getOrdersbyUserId(String id);
	
	Order defrayOrder(String orderid);
	
	Order cancalOrder(String orderid);
	
	Order backOrder(String orderid);
	
	Order createOrder(Order order);

}
