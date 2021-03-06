package jrd.graduationproject.shoppingplatform.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

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
import jrd.graduationproject.shoppingplatform.dao.jpa.CommodityJpa;
import jrd.graduationproject.shoppingplatform.dao.jpa.MessageJpa;
import jrd.graduationproject.shoppingplatform.dao.jpa.SellerJpa;
import jrd.graduationproject.shoppingplatform.dao.jpa.ShopCarJpa;
import jrd.graduationproject.shoppingplatform.dao.jpa.WareJpa;
import jrd.graduationproject.shoppingplatform.dao.mybatis.WareMapper;
import jrd.graduationproject.shoppingplatform.pojo.enumfield.CategoryEnum;
import jrd.graduationproject.shoppingplatform.pojo.enumfield.TypeEnum;
import jrd.graduationproject.shoppingplatform.pojo.po.Comment;
import jrd.graduationproject.shoppingplatform.pojo.po.Commodity;
import jrd.graduationproject.shoppingplatform.pojo.po.Message;
import jrd.graduationproject.shoppingplatform.pojo.po.Seller;
import jrd.graduationproject.shoppingplatform.pojo.po.ShopCar;
import jrd.graduationproject.shoppingplatform.pojo.po.User;
import jrd.graduationproject.shoppingplatform.pojo.po.Ware;
import jrd.graduationproject.shoppingplatform.pojo.po.WareExample;
import jrd.graduationproject.shoppingplatform.pojo.vo.Between;
import jrd.graduationproject.shoppingplatform.pojo.vo.PageParam;
import jrd.graduationproject.shoppingplatform.pojo.vo.WareQuery;
import jrd.graduationproject.shoppingplatform.service.IWareService;
import jrd.graduationproject.shoppingplatform.util.GlobalUtil;

@Service
public class WareServiceImpl implements IWareService {

	@Autowired
	private ShopCarJpa shopCarJpa;
	@Autowired
	private CommodityJpa commodityJpa;
	@Autowired
	private WareJpa wareJpa;
	@Autowired
	private SellerJpa sellerJpa;
	@Autowired
	private WareMapper wareMapper;
	@Autowired
	private MessageJpa messageJpa;
	@Autowired
	private CommentJpa commentJpa;

	@Override
	public List<Commodity> getCommoditysByType(TypeEnum typeEnum) {
		Commodity probe = new Commodity();
		probe.setTypeenum(typeEnum);
		Example<Commodity> example = Example.of(probe);

		return commodityJpa.findAll(example);
	}

	@Override
	public Slice<Commodity> getCommoditys(PageParam page) {
		Pageable pageable = new PageRequest(page.getPagenum() - 1, page.getPagesize());

		return commodityJpa.findAll(pageable);

	}

	@Override
	@Transactional
	public Commodity addCommodity(Commodity commodity) {
		commodity.setId(GlobalUtil.getModelID(Commodity.class));
		return commodityJpa.saveAndFlush(commodity);
	}

	@Override
	@Transactional
	public String addWare(Ware ware) {
		ware.setId(GlobalUtil.getModelID(Ware.class));
		Commodity commodity = commodityJpa.findOne(ware.getCommodity());
		TypeEnum type = commodity.getTypeenum();
		ware.setType(type);
		ware.setCategory(type.getCategoryEnum());

		wareMapper.insertSelective(ware);

		Message entity = new Message();
		Seller seller = sellerJpa.findOne(ware.getSeller());
		entity.setTypeid(ware.getId());
		entity.setId(GlobalUtil.getModelID(Message.class));
		entity.setCreatedate(new Date());
		entity.setUpdatedate(new Date());
		entity.setType(3);
		entity.setStatus(false);
		entity.setMsg("商家：" + seller.getName() + "申请商家商品：" + ware.getName() + "^-^");

		messageJpa.saveAndFlush(entity);
		return ware.getId();
	}

	@Override
	public List<Ware> getExWares(CategoryEnum categoryEnum) {
		List<Ware> wares = new ArrayList<>();
		List<TypeEnum> typeEnums = TypeEnum.getTypesByCategory(categoryEnum);
		Integer needNum = 8;
		for (TypeEnum typeEnum : typeEnums) {
			List<Commodity> commodities = getCommoditysByType(typeEnum);
			for (Commodity commodity : commodities) {

				Ware probe = new Ware();
				probe.setStatus(1);
				probe.setCommodity(commodity.getId());
				Example<Ware> example = Example.of(probe);
				Pageable pageable = new PageRequest(1, needNum);
				Slice<Ware> page = wareJpa.findAll(example, pageable);
				List<Ware> singleWares = page.getContent();
				if (singleWares.isEmpty())
					continue;
				needNum = exfull(wares, singleWares, needNum);
				// 渴望度
				if (needNum <= 0)
					return wares;
			}
		}
		return wares;
	}

	private Integer exfull(List<Ware> wares, List<Ware> singleWares, Integer needNum) {
		Integer full = 8 - wares.size();
		if (full != 0) {
			if (singleWares.size() > full)
				wares.addAll(singleWares.subList(0, full));
			else
				wares.addAll(singleWares);
			full = 8 - wares.size();
			if (full > 0)
				return full;
			else
				needNum = 4;
		} else {
			if (singleWares.size() > needNum) {
				singleWares = singleWares.subList(0, needNum);
			}
			for (int i = 0; i < singleWares.size(); i++) {
				wares.remove(0);
			}
			wares.addAll(singleWares);
			needNum /= 2;
		}
		return needNum;
	}

	@Override
	public Slice<Ware> getWares(List<CategoryEnum> categoryEnums, PageParam page) {
		WareExample wareExample = new WareExample();
		wareExample.createCriteria().andStatusEqualTo(1);
		for (CategoryEnum categoryEnum : categoryEnums) {
			wareExample.or().andCategoryEqualTo(categoryEnum);
		}
		Page<Ware> pagehelperPage = PageHelper.startPage(page.getPagenum(), page.getPagesize());
		List<Ware> wares = wareMapper.selectByExample(wareExample);
		Pageable pageable = new PageRequest(page.getPagenum() - 1, page.getPagesize());
		Slice<Ware> slice = new PageImpl<>(wares, pageable, pagehelperPage.getTotal());
		return slice;
	}

	@Override
	public Slice<Ware> getWares(PageParam page, Ware probe) {
		Sort sort = new Sort(Sort.Direction.DESC, "sales");
		Pageable pageable = new PageRequest(page.getPagenum() - 1, page.getPagesize(), sort);

		probe.setStatus(1);
		Example<Ware> example = Example.of(probe);
		return wareJpa.findAll(example, pageable);
	}

	@Override
	public Commodity getCommodityById(String id) {
		return commodityJpa.findOne(id);
	}

	@Override
	public Slice<Ware> getWares(PageParam page, WareQuery query) {
		WareExample wareExample = new WareExample();//商品查询的Example对象

		fullSelect(wareExample, query);//向Example对象中加入查询条件

		OrderSelect(wareExample, query);//向Example对象中加入排序条件

		//分页对象
		Page<Ware> pagehelperPage = PageHelper.startPage(page.getPagenum(), page.getPagesize());
		//获取商品信息
		List<Ware> wares = wareMapper.selectByExample(wareExample);
		//构造jpa的分页对象，用于前端分页
		Pageable pageable = new PageRequest(page.getPagenum() - 1, page.getPagesize());
		Slice<Ware> slice = new PageImpl<>(wares, pageable, pagehelperPage.getTotal());
		return slice;
	}

	private void OrderSelect(WareExample wareExample, WareQuery query) {
		//获取要排序字段的集合
		Map<String, String> orderMap = query.getOrderby();
		if (orderMap == null || orderMap.isEmpty())
			return;
		StringBuffer sb = new StringBuffer();
		Iterator<Entry<String, String>> it = orderMap.entrySet().iterator();
		Integer index = 0;
		//遍历排序字段，组装sql中 order by语句
		while (it.hasNext()) {
			if (index != 0)
				sb.append(",");
			else
				sb.append(" ");
			Entry<String, String> entry = it.next();
			sb.append(entry.getKey() + " " + entry.getValue());
			index++;
		}
		sb.append(" ");
		//设置排序
		wareExample.setOrderByClause(sb.toString());
	}

	private void fullSelect(WareExample wareExample, WareQuery ware) {
		WareExample.Criteria criteria = wareExample.or();//制造criteria对象

		//若状态为空，则sql语句添加 AND status = 1为默认查询
		if (ware.getStatus() == null)
			criteria.andStatusEqualTo(1);
		//若状态不为空，则sql语句添加 AND status = ?
		else if (ware.getStatus() > -1)
			criteria.andStatusEqualTo(ware.getStatus());
		//若id不为空，则sql语句添加 AND id = ?
		if (ware.getId() != null)
			criteria.andIdEqualTo(ware.getId());
		//若商品名不为空，则sql语句添加 AND name = ?
		if (ware.getName() != null)
			criteria.andNameEqualTo(ware.getName());
		//若门类不为空，则sql语句添加 AND category = ?
		if (ware.getCategory() != null)
			criteria.andCategoryEqualTo(ware.getCategory());
		//若大类不为空，则sql语句添加 AND type = ?
		if (ware.getType() != null)
			criteria.andTypeEqualTo(ware.getType());
		//若分类不为空，则sql语句添加 AND commodity = ?
		if (ware.getCommodity() != null)
			criteria.andCommodityEqualTo(ware.getCommodity());
		//若商家不为空，则sql语句添加 AND seller = 1
		if (ware.getSeller() != null)
			criteria.andSellerEqualTo(ware.getSeller());
		//价格范围查询处理
		Map<String, Between> between = ware.getPricebetween();
		//价格为空，查询全部价格商品
		if (between == null || between.isEmpty())
			return;
		//价格不为空，则sql语句添加 AND price > ? AND price < ?
		Iterator<Entry<String, Between>> it = between.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, Between> entry = it.next();
			String path = entry.getKey();
			Between values = entry.getValue();
			if ("price".equals(path)) {
				//价格上下限约束
				if (values.getLessthen() != null && values.getGreaterthen() != null)
					criteria.andPriceBetween(Double.valueOf(values.getGreaterthen()),
							Double.valueOf(values.getLessthen()));
				//价格上限约束
				else if (values.getLessthen() != null)
					criteria.andPriceLessThan(Double.valueOf(values.getLessthen()));
				//价格下限约束
				else if (values.getGreaterthen() != null)
					criteria.andPriceGreaterThan(Double.valueOf(values.getGreaterthen()));
			}
		}

	}

	@Override
	public Long getUserShopCar(User user) {
		ShopCar probe = new ShopCar();
		probe.setUser(user);
		Example<ShopCar> example = Example.of(probe);
		return shopCarJpa.count(example);
	}

	@Override
	public void alterWare(Ware ware) {
		wareMapper.updateByPrimaryKeySelective(ware);
	}

	@Override
	public List<Ware> getWares(List<String> wares) {

		return wareJpa.findAll(wares);
	}

	@Override
	public Ware allorWare(String id) {
		Ware ware = new Ware();
		ware.setId(id);
		ware.setStatus(1);
		wareMapper.updateByPrimaryKeySelective(ware);
		return ware;
	}

	@Override
	public Seller getSeller(String id) {

		return sellerJpa.findOne(id);
	}

	@Override
	public Ware findWarebyId(String id) {

		return wareJpa.findOne(id);
	}

	@Override
	public Slice<Comment> getCommentbyWare(PageParam page, Ware ware) {
		Sort sort = new Sort(Sort.Direction.DESC, "createdate");
		Pageable pageable = new PageRequest(page.getPagenum() - 1, page.getPagesize(), sort);

		Comment probe = new Comment();
		probe.setWare(ware);
		Example<Comment> example = Example.of(probe);
		return commentJpa.findAll(example, pageable);
	}

	@Override
	public Commodity getCommodityByKeyWord(String keyword){
		List<Commodity> commodities=commodityJpa.findBySearchkeyLike(keyword);
		if(commodities==null||commodities.isEmpty())
			return null;
		int i=new Random().nextInt(commodities.size());
		return commodities.get(i);
	}

}
