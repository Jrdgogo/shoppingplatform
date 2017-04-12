package jrd.graduationproject.shoppingplatform.service;

import java.util.List;

import org.springframework.data.domain.Slice;

import jrd.graduationproject.shoppingplatform.pojo.po.Order;
import jrd.graduationproject.shoppingplatform.pojo.vo.PageParam;

public interface IOrderService {

	List<Order> getOrdersbyUserId(String id);
	
	Order defrayOrder(String orderid);
	
	Order cancalOrder(String orderid);
	
	Order backOrder(String orderid);

	Slice<Order> getOrdersBySeller(Order order, String id, PageParam page);

	Order createOrder(String id, String addr, List<String> wares);

}
