package jrd.graduationproject.shoppingplatform.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jrd.graduationproject.shoppingplatform.dao.jpa.OrderJpa;
import jrd.graduationproject.shoppingplatform.dao.jpa.OrderSellerJpa;
import jrd.graduationproject.shoppingplatform.dao.jpa.ShopCarJpa;
import jrd.graduationproject.shoppingplatform.dao.jpa.UserJpa;
import jrd.graduationproject.shoppingplatform.dao.jpa.UserWareAddrJpa;
import jrd.graduationproject.shoppingplatform.exception.UserOptionErrorException;
import jrd.graduationproject.shoppingplatform.pojo.enumfield.OrderStatusEnum;
import jrd.graduationproject.shoppingplatform.pojo.po.Order;
import jrd.graduationproject.shoppingplatform.pojo.po.OrderDetail;
import jrd.graduationproject.shoppingplatform.pojo.po.OrderOfSeller;
import jrd.graduationproject.shoppingplatform.pojo.po.ShopCar;
import jrd.graduationproject.shoppingplatform.pojo.po.User;
import jrd.graduationproject.shoppingplatform.pojo.po.Ware;
import jrd.graduationproject.shoppingplatform.pojo.vo.PageParam;
import jrd.graduationproject.shoppingplatform.service.IOrderService;
import jrd.graduationproject.shoppingplatform.util.GlobalUtil;

@Service
public class OrderServiceImpl implements IOrderService {

	@Autowired
	private OrderJpa orderJpa;
	@Autowired
	private OrderSellerJpa orderSellerJpa;
//	@Autowired
//	private WareJpa wareJpa;
	@Autowired
	private ShopCarJpa shopCarJpa;
	@Autowired
	private UserJpa userJpa;
	@Autowired
	private UserWareAddrJpa userWareAddrJpa;
//	@Autowired
//	private SellerJpa sellerJpa;

	@Override
	public List<Order> getOrdersbyUserId(String id) {
		Set<Order> set = userJpa.getOne(id).getOrders();
		return new ArrayList<>(set);
	}

	@Override
	@Transactional
	public Order defrayOrder(String orderid) {
		Order order = orderJpa.findOne(orderid);
		User user = order.getUser();
		Double orderMoney = order.getPrice();
		Double userMoney = user.getAccount();
		if (orderMoney > userMoney) {
			throw new UserOptionErrorException("账户余额不足");
		}
		user.setAccount(userMoney - orderMoney);
		order.setStatus(OrderStatusEnum.PAYMENT);
		userJpa.saveAndFlush(user);
		return order;
	}

	@Override
	@Transactional
	public Order cancalOrder(String orderid) {
		Order order = orderJpa.findOne(orderid);
		order.setStatus(OrderStatusEnum.CANCEL);
		orderJpa.save(order);
		return order;
	}

	@Override
	@Transactional
	public Order backOrder(String orderid) {
		Order order = orderJpa.findOne(orderid);
		User user = order.getUser();
		Double orderMoney = order.getPrice();
		Double userMoney = user.getAccount();
		user.setAccount(userMoney + orderMoney);
		order.setStatus(OrderStatusEnum.BACK);
		userJpa.saveAndFlush(user);
		return order;
	}

	@Override
	public Slice<Order> getOrdersBySeller(Order order, String id, PageParam page) {
		Sort sort = new Sort(Sort.Direction.DESC, "createdate");
		Pageable pageable = new PageRequest(page.getPagenum() - 1, page.getPagesize(), sort);
		OrderOfSeller os = new OrderOfSeller();
		os.setOrderid(order.getId());
		os.setSellerid(id);
		Example<OrderOfSeller> example = Example.of(os);
		List<OrderOfSeller> orderOfSellers = orderSellerJpa.findAll(example);
		List<String> ids = new ArrayList<>();
		for (OrderOfSeller orderOfSeller : orderOfSellers)
			ids.add(orderOfSeller.getOrderid());
		return new PageImpl<Order>(orderJpa.findAll(ids), pageable, orderOfSellers.size());
	}

	@Override
	@Transactional
	public Order createOrder(String id, String addr, List<String> shopcars) {
		Order order = new Order();
		order.setId(GlobalUtil.getModelID(Order.class));
		order.setUser(userJpa.findOne(id));
		order.setWareaddr(userWareAddrJpa.findOne(addr));
		// order.setType(type);
		Double money = 0.0;
		List<ShopCar> shopCars = shopCarJpa.findAll(shopcars);

		for (ShopCar shopCar : shopCars) {
			Ware ware = shopCar.getWare();
			money += ware.getPrice()*shopCar.getWarenum();
			String seller = ware.getSeller();
			OrderOfSeller entity = new OrderOfSeller();
			entity.setId(GlobalUtil.getModelID(OrderOfSeller.class));
			entity.setSellerid(seller);
			entity.setSellerid(order.getId());
			orderSellerJpa.save(entity);

			OrderDetail detail = new OrderDetail();
			detail.setId(GlobalUtil.getModelID(OrderDetail.class));
			detail.setWare(ware);
			detail.setWarenum(shopCar.getWarenum());
			order.getOrderdetails().add(detail);
		}
		shopCarJpa.delete(shopCars);
		order.setPrice(money);

		return orderJpa.save(order);
	}

}
