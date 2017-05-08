package jrd.graduationproject.shoppingplatform.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
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
	// @Autowired
	// private WareJpa wareJpa;
	@Autowired
	private ShopCarJpa shopCarJpa;
	@Autowired
	private UserJpa userJpa;
	@Autowired
	private UserWareAddrJpa userWareAddrJpa;
	// @Autowired
	// private SellerJpa sellerJpa;

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

		List<Order> orw = new ArrayList<>();

		OrderOfSeller os = new OrderOfSeller();
		os.setOrderid(order.getId());
		os.setSellerid(id);
		Example<OrderOfSeller> example = Example.of(os);
		List<OrderOfSeller> orderOfSellers = orderSellerJpa.findAll(example);

		List<String> ids = new ArrayList<>();
		for (OrderOfSeller orderOfSeller : orderOfSellers)
			ids.add(orderOfSeller.getOrderid());
		List<Order> orders = orderJpa.findAll(ids);

		for (Order o : orders) {

			if (order.getCreatedate() != null) {
				if (!between(o.getCreatedate(), order.getCreatedate()))
					continue;
			}
			Set<OrderDetail> details = o.getOrderdetails();
			Set<OrderDetail> orderdetails = new HashSet<>();
			for (OrderDetail detail : details) {
				String seller = detail.getWare().getSeller();
				if (id.equals(seller))
					orderdetails.add(detail);
			}
			o.setOrderdetails(orderdetails);
			orw.add(order);
		}
		if (!orw.isEmpty()) {
			orw.sort((o1, o2) -> (int) (o1.getCreatedate().getTime() - o2.getCreatedate().getTime()));
			int fi = (page.getPagenum() - 1) * page.getPagesize();
			int ei = page.getPagenum() * page.getPagesize();
			if (ei <= orw.size())
				return new PageImpl<Order>(orw.subList(fi, ei), pageable, orw.size());
			else if (ei > orw.size() && fi < orw.size())
				return new PageImpl<Order>(orw.subList(fi, orw.size()), pageable, orw.size());
			else if (fi >= orw.size())
				return new PageImpl<Order>(new ArrayList<>(), pageable, orw.size());
		}
		return new PageImpl<Order>(orw, pageable, orw.size());
	}

	private boolean between(Date createdate, Date createdate2) {
		Date before = GlobalUtil.formatDate(GlobalUtil.dateFormat(createdate2));
		Date after = new Date(before.getTime() + 1000 * 60 * 60 * 24);

		return before.getTime() <= createdate.getTime() && after.getTime() >= createdate.getTime();
	}

	@Override
	@Transactional
	public Order createOrder(String id, String addr, List<String> shopcars) {
		Order order = new Order();
		order.setId(GlobalUtil.getModelID(Order.class));
		order.setUser(userJpa.findOne(id));
		order.setWareaddr(userWareAddrJpa.findOne(addr));
		order.setType("0");
		order.setCreatedate(new Date());
		order.setStatus(OrderStatusEnum.UNPAID);
		// order.setType(type);
		Double money = 0.0;
		List<ShopCar> shopCars = shopCarJpa.findAll(shopcars);

		for (ShopCar shopCar : shopCars) {
			Ware ware = shopCar.getWare();
			money += ware.getPrice() * shopCar.getWarenum();
			String seller = ware.getSeller();
			OrderOfSeller entity = new OrderOfSeller();
			entity.setId(GlobalUtil.getModelID(OrderOfSeller.class));
			entity.setSellerid(seller);
			entity.setOrderid(order.getId());
			orderSellerJpa.save(entity);

			OrderDetail detail = new OrderDetail();
			detail.setId(GlobalUtil.getModelID(OrderDetail.class));
			detail.setWare(ware);
			detail.setWarenum(shopCar.getWarenum());
			detail.setOrder(order);
			
			order.getOrderdetails().add(detail);
		}
		shopCarJpa.delete(shopCars);
		order.setPrice(money);

		return orderJpa.save(order);
	}

}
