package jrd.graduationproject.shoppingplatform.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Slice;

import jrd.graduationproject.shoppingplatform.pojo.enumfield.OrderStatusEnum;
import jrd.graduationproject.shoppingplatform.pojo.po.Order;
import jrd.graduationproject.shoppingplatform.pojo.vo.PageParam;

public interface IOrderService {

	List<Order> getOrdersbyUserId(String id);
	
	Order defrayOrder(String orderid);
	
	Order cancalOrder(String orderid);
	
	Order backOrder(String orderid);

	Slice<Order> getOrdersBySeller(Order order, String id, PageParam page);

	Order createOrder(String id, String addr, List<String> wares);

	Long queryCountByStatus(OrderStatusEnum status);

	Long queryCountByType(Integer type);

	Slice<Order> getOrdersbyUserId(String id, PageParam page);

	Slice<Order> getOrdersbyUserId(String id, PageParam page, String type, Date date);

	String addComment(String orderId, String id, String txt);

	Order queryById(String orderId);

	Order defrayOrder(String orderId, String id, String pwd);

}
