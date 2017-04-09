package jrd.graduationproject.shoppingplatform.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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

import jrd.graduationproject.shoppingplatform.dao.jpa.CommodityJpa;
import jrd.graduationproject.shoppingplatform.dao.jpa.SellerJpa;
import jrd.graduationproject.shoppingplatform.dao.jpa.ShopCarJpa;
import jrd.graduationproject.shoppingplatform.dao.jpa.WareJpa;
import jrd.graduationproject.shoppingplatform.dao.mybatis.WareMapper;
import jrd.graduationproject.shoppingplatform.pojo.enumfield.CategoryEnum;
import jrd.graduationproject.shoppingplatform.pojo.enumfield.TypeEnum;
import jrd.graduationproject.shoppingplatform.pojo.po.Commodity;
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
	private SellerJpa sellerJpa;
	@Autowired
	private ShopCarJpa shopCarJpa;
	@Autowired
	private CommodityJpa commodityJpa;
	@Autowired
	private WareJpa wareJpa;
	@Autowired
	private WareMapper wareMapper;

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
	public Ware addWare(Ware ware) {
		ware.setId(GlobalUtil.getModelID(Ware.class));
		return wareJpa.saveAndFlush(ware);
	}

	@Override
	public List<Ware> getExWares(CategoryEnum categoryEnum) {
		List<Ware> wares=new ArrayList<>();
		List<TypeEnum> typeEnums=TypeEnum.getTypesByCategory(categoryEnum);
		Integer needNum=8;
		for(TypeEnum typeEnum:typeEnums){
			List<Commodity> commodities=getCommoditysByType(typeEnum);
			for(Commodity commodity:commodities){
				
				Ware probe = new Ware();
				probe.setCommodity(commodity.getId());
				Example<Ware> example = Example.of(probe);
				Pageable pageable =new PageRequest(1, needNum);
				Slice<Ware> page=wareJpa.findAll(example, pageable);
				List<Ware> singleWares=page.getContent();
				if(singleWares.isEmpty())
					continue;
				needNum=exfull(wares,singleWares,needNum);
				//渴望度
				if(needNum<=0)
					return wares;
			}
		}
		return wares;
	}

	private Integer exfull(List<Ware> wares, List<Ware> singleWares, Integer needNum) {
		Integer full=8-wares.size();
		if(full!=0){
			if(singleWares.size()>full)
				wares.addAll(singleWares.subList(0, full));
			else
				wares.addAll(singleWares);
			full=8-wares.size();
			if(full>0)
				return full;
			else
			    needNum=4;
		}else{
			if(singleWares.size()>needNum){
				singleWares=singleWares.subList(0, needNum);
			}
			for(int i=0;i<singleWares.size();i++){
			    wares.remove(0);
			}
			wares.addAll(singleWares);
			needNum/=2;
		}
		return needNum;
	}

	@Override
	public Slice<Ware> getWares(List<CategoryEnum> categoryEnums, PageParam page) {
		WareExample wareExample=new WareExample();
		for(CategoryEnum categoryEnum:categoryEnums){
			wareExample.or().andCategoryEqualTo(categoryEnum);
		}
		Page<Ware> pagehelperPage =PageHelper.startPage(page.getPagenum(), page.getPagesize());
		List<Ware> wares= wareMapper.selectByExample(wareExample);
		Pageable pageable = new PageRequest(page.getPagenum() - 1, page.getPagesize());
		Slice<Ware> slice=new PageImpl<>(wares,pageable,pagehelperPage.getTotal());
		return slice;
	}

	@Override
	public Slice<Ware> getWares(PageParam page,Ware probe) {
		Sort sort = new Sort(Sort.Direction.DESC, "sales");
		Pageable pageable = new PageRequest(page.getPagenum() - 1, page.getPagesize(),sort);
		
		Example<Ware> example = Example.of(probe);
		return wareJpa.findAll(example, pageable);
	}

	@Override
	public Commodity getCommodityById(String id) {
		return commodityJpa.findOne(id);
	}

	@Override
	public Slice<Ware> getWares(PageParam page, WareQuery query) {
		WareExample wareExample=new WareExample();
		
		fullSelect(wareExample,query);
		
		OrderSelect(wareExample, query);
		
		Page<Ware> pagehelperPage =PageHelper.startPage(page.getPagenum(), page.getPagesize());
		List<Ware> wares= wareMapper.selectByExample(wareExample);
		Pageable pageable = new PageRequest(page.getPagenum() - 1, page.getPagesize());
		Slice<Ware> slice=new PageImpl<>(wares,pageable,pagehelperPage.getTotal());
		return slice;
	}

	private void OrderSelect(WareExample wareExample,WareQuery query) {
		Map<String, String> orderMap=query.getOrderby();
		if(orderMap==null||orderMap.isEmpty())
			return;
		StringBuffer sb=new StringBuffer();
		Iterator<Entry<String, String>> it=orderMap.entrySet().iterator();
		Integer index=0;
		while(it.hasNext()){
			if(index!=0)
				sb.append(",");
			else
				sb.append(" ");
			Entry<String, String> entry=it.next();
			sb.append(entry.getKey()+" "+entry.getValue());
			index++;
		}
		sb.append(" ");
		wareExample.setOrderByClause(sb.toString());
	}
	
	private void fullSelect(WareExample wareExample,WareQuery ware){
		WareExample.Criteria criteria=wareExample.or();
		
		if(ware.getId()!=null)
			criteria.andIdEqualTo(ware.getId());
		if(ware.getName()!=null)
			criteria.andIdEqualTo(ware.getName());
		if(ware.getCategory()!=null)
			criteria.andCategoryEqualTo(ware.getCategory());
		if(ware.getType()!=null)
			criteria.andTypeEqualTo(ware.getType());
		if(ware.getCommodity()!=null)
			criteria.andCommodityEqualTo(ware.getCommodity());
		if(ware.getSales()!=null)
			criteria.andSellerEqualTo(ware.getSeller());
		Map<String, Between> between=ware.getPricebetween();
		if(between==null||between.isEmpty())
			return;
		Iterator<Entry<String, Between>> it=between.entrySet().iterator();
		while(it.hasNext()){
			Entry<String, Between> entry=it.next();
			String path=entry.getKey();
			Between values=entry.getValue();
			if("price".equals(path)){
				if(values.getLessthen()!=null&&values.getGreaterthen()!=null)
					criteria.andPriceBetween(Double.valueOf(values.getLessthen()),Double.valueOf(values.getGreaterthen()));
				else if(values.getLessthen()!=null)
					criteria.andPriceLessThan(Double.valueOf(values.getLessthen()));
				else if(values.getGreaterthen()!=null)
					criteria.andPriceGreaterThan(Double.valueOf(values.getGreaterthen()));
			}
		}
		
	}

	@Override
	public Long getUserShopCar(User user) {
		ShopCar probe=new ShopCar();
		probe.setId(user.getId());
		Example<ShopCar> example=Example.of(probe);
		return shopCarJpa.count(example);
	}

}
