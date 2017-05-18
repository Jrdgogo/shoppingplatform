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

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import jrd.graduationproject.shoppingplatform.dao.jpa.CommentJpa;
import jrd.graduationproject.shoppingplatform.dao.jpa.OrderJpa;
import jrd.graduationproject.shoppingplatform.dao.jpa.OrderSellerJpa;
import jrd.graduationproject.shoppingplatform.dao.jpa.ShopCarJpa;
import jrd.graduationproject.shoppingplatform.dao.jpa.UserJpa;
import jrd.graduationproject.shoppingplatform.dao.jpa.UserWareAddrJpa;
import jrd.graduationproject.shoppingplatform.dao.jpa.WareJpa;
import jrd.graduationproject.shoppingplatform.dao.mybatis.OrderMapper;
import jrd.graduationproject.shoppingplatform.dao.mybatis.UserMapper;
import jrd.graduationproject.shoppingplatform.exception.UserOptionErrorException;
import jrd.graduationproject.shoppingplatform.pojo.enumfield.OrderStatusEnum;
import jrd.graduationproject.shoppingplatform.pojo.po.Comment;
import jrd.graduationproject.shoppingplatform.pojo.po.Order;
import jrd.graduationproject.shoppingplatform.pojo.po.OrderDetail;
import jrd.graduationproject.shoppingplatform.pojo.po.OrderExample;
import jrd.graduationproject.shoppingplatform.pojo.po.OrderExample.Criteria;
import jrd.graduationproject.shoppingplatform.pojo.po.OrderOfSeller;
import jrd.graduationproject.shoppingplatform.pojo.po.ShopCar;
import jrd.graduationproject.shoppingplatform.pojo.po.User;
import jrd.graduationproject.shoppingplatform.pojo.po.Ware;
import jrd.graduationproject.shoppingplatform.pojo.vo.OrderQuery;
import jrd.graduationproject.shoppingplatform.pojo.vo.PageParam;
import jrd.graduationproject.shoppingplatform.service.IOrderService;
import jrd.graduationproject.shoppingplatform.util.GlobalUtil;

@Service
public class OrderServiceImpl implements IOrderService {

	@Autowired
	private OrderJpa orderJpa;
	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private OrderSellerJpa orderSellerJpa;
	@Autowired
	private WareJpa wareJpa;
	@Autowired
	private ShopCarJpa shopCarJpa;
	@Autowired
	private UserJpa userJpa;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UserWareAddrJpa userWareAddrJpa;
	@Autowired
	private CommentJpa commentJpa;
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
		order.setType("1");
		userJpa.saveAndFlush(user);
		Set<OrderDetail> orderDetails=order.getOrderdetails();
		for(OrderDetail detail:orderDetails){
			Ware ware=detail.getWare();
			ware.setSales(ware.getSales()+1);
			wareJpa.saveAndFlush(ware);
		}
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
		order.setType("4");
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
			orw.add(o);
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

	@Override
	public Long queryCountByStatus(String id,OrderStatusEnum status) {
		Order order = new Order();
		order.setStatus(status);
		order.setUser(userJpa.findOne(id));
		Example<Order> example = Example.of(order);
		return orderJpa.count(example);
	}

	@Override
	public Long queryCountByType(String id,Integer type) {
		Order order = new Order();
		order.setUser(userJpa.findOne(id));
		order.setType("" + type);
		Example<Order> example = Example.of(order);
		return orderJpa.count(example);
	}

	@Override
	public Slice<Order> getOrdersbyUserId(String id, PageParam page) {
		Sort sort = new Sort(Sort.Direction.DESC, "createdate");
		Pageable pageable = new PageRequest(page.getPagenum() - 1, page.getPagesize(), sort);

		Order probe = new Order();
		probe.setUser(userJpa.findOne(id));
		Example<Order> example = Example.of(probe);

		return orderJpa.findAll(example, pageable);
	}

//	@Override
//	public Slice<Order> getOrdersbyUserId(String id, PageParam page, String type, Date date) {
//		Sort sort = new Sort(Sort.Direction.DESC, "createdate");
//		Pageable pageable = new PageRequest(page.getPagenum() - 1, page.getPagesize(), sort);
//
//		Order probe = new Order();
//		probe.setUser(userJpa.findOne(id));
//		if (!"0".equals(type)) {
//			if ("1".equals(type)) {//等待付款
//				probe.setStatus(OrderStatusEnum.UNPAID);
//			} else if ("2".equals(type)) {//等待收货
//				probe.setType("1");
//			}else if ("3".equals(type)) {//等待评论
//				probe.setType("2");
//			} else if ("4".equals(type)) {//已完成
//				probe.setStatus(OrderStatusEnum.OK);
//			} else if ("5".equals(type)) {//已取消
//				probe.setStatus(OrderStatusEnum.CANCEL);
//			}
//		}
//
//		Example<Order> example = Example.of(probe);
//
//		Page<Order> pages = orderJpa.findAll(example, pageable);
//		List<Order> content = new ArrayList<>();
//		for (Order order : pages.getContent()) {
//			if (order.getCreatedate().after(date))
//				content.add(order);
//		}
//		return new PageImpl<>(content, pageable, content.size());
//	}
	
	@Override
	public Slice<Order> getOrdersbyUserId(String id, PageParam page, String type, Date date) {
		OrderExample example=new OrderExample();
		Criteria criteria=example.createCriteria();
		criteria.andUserEqualTo(userMapper.selectByPrimaryKey(id));
		
		if (!"0".equals(type)) {
			if ("1".equals(type)) {//等待付款
				criteria.andStatusEqualTo(OrderStatusEnum.UNPAID);
			} else if ("2".equals(type)) {//等待收货
				criteria.andTypeEqualTo(1);
			}else if ("3".equals(type)) {//等待评论
				criteria.andTypeEqualTo(2);
			} else if ("4".equals(type)) {//已完成
				criteria.andStatusEqualTo(OrderStatusEnum.OK);
			} else if ("5".equals(type)) {//已取消
				criteria.andStatusEqualTo(OrderStatusEnum.CANCEL);
			}
		}
		criteria.andCreatedateGreaterThanOrEqualTo(date);
		example.setOrderByClause(" createdate DESC ");
		Page<Order> pagehelperPage = PageHelper.startPage(page.getPagenum(), page.getPagesize());
		List<Order> orders=orderMapper.selectByExample(example);
		List<Order> ods=new ArrayList<>();
		for(Order order:orders){
			ods.add(orderJpa.findOne(order.getId()));
		}
		Sort sort = new Sort(Sort.Direction.DESC, "createdate");
		Pageable pageable = new PageRequest(page.getPagenum() - 1, page.getPagesize(), sort);
		return new PageImpl<>(ods, pageable, pagehelperPage.getTotal());
	}

	@Override
	@Transactional
	public String addComment(String orderId, String id, String txt) {
		Order order=orderJpa.findOne(orderId);
		
		User user=userJpa.findOne(id);
		for(OrderDetail orderDetail:  order.getOrderdetails()){
			Ware ware=orderDetail.getWare();
			Comment comment=new Comment();
			comment.setId(GlobalUtil.getModelID(Comment.class));
			comment.setUser(user);
			comment.setWare(ware);
			comment.setCreatedate(new Date());
			comment.setCdesc(txt);
			commentJpa.saveAndFlush(comment);
		}
		order.setStatus(OrderStatusEnum.OK);
		order.setType("3");
		orderJpa.saveAndFlush(order);
		return "评论发表成功";
	}

	@Override
	public Order queryById(String orderId) {
		return orderJpa.findOne(orderId);
	}

	@Override
	public Order defrayOrder(String orderId, String id, String pwd) {
		User user=userJpa.findOne(id);
		if(!user.getPaymentpwd().equals(GlobalUtil.md5(pwd))){
			return null;
		}
		Order order = orderJpa.findOne(orderId);
		Double orderMoney = order.getPrice();
		Double userMoney = user.getAccount();
		if (orderMoney > userMoney) {
			throw new UserOptionErrorException("账户余额不足");
		}
		user.setAccount(userMoney - orderMoney);
		order.setStatus(OrderStatusEnum.PAYMENT);
		order.setType("1");
		userJpa.saveAndFlush(user);
		return order;
	}

	@Override
	public Slice<Order> getOrdersBySeller(OrderQuery order, String id, PageParam page) {
		
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

			if (order.getStartdate() != null) {
				if (o.getCreatedate().before(order.getStartdate()))
					continue;
			}
			if (order.getEnddate() != null) {
				Date d=new Date(order.getEnddate().getTime()+1000*60*60*24);
				if (o.getCreatedate().after(d))
					continue;
			}
			String type=order.getQuerytype();
			if(type!=null){
				if("0".equals(type)&&!OrderStatusEnum.UNPAID.equals(o.getStatus()))
					continue;
				if("1".equals(type)&&!OrderStatusEnum.PAYMENT.equals(o.getStatus()))
					continue;
				if("2".equals(type)&&!("3".equals(o.getType())||OrderStatusEnum.OK.equals(o.getStatus())))
					continue;
				if("3".equals(type)&&!OrderStatusEnum.BACK.equals(o.getStatus()))
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
			orw.add(o);
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

	@Override
	public void songda(String orderid) {
		Order order=orderJpa.findOne(orderid);
		order.setType("2");
		orderJpa.saveAndFlush(order);
	}


}
