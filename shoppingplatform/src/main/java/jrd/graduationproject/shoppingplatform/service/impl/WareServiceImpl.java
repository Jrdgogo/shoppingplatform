package jrd.graduationproject.shoppingplatform.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jrd.graduationproject.shoppingplatform.dao.jpa.CommodityJpa;
import jrd.graduationproject.shoppingplatform.dao.jpa.SellerJpa;
import jrd.graduationproject.shoppingplatform.dao.jpa.WareJpa;
import jrd.graduationproject.shoppingplatform.pojo.enumfield.TypeEnum;
import jrd.graduationproject.shoppingplatform.pojo.po.Commodity;
import jrd.graduationproject.shoppingplatform.pojo.po.Ware;
import jrd.graduationproject.shoppingplatform.pojo.vo.PageParam;
import jrd.graduationproject.shoppingplatform.service.IWareService;

@Service
public class WareServiceImpl implements IWareService {

	@Autowired
	private SellerJpa sellerJpa;
	@Autowired
	private CommodityJpa commodityJpa;
	@Autowired
	private WareJpa wareJpa;

	@Override
	public List<Commodity> getCommoditysByType(TypeEnum typeEnum) {
		Commodity probe = new Commodity();
		probe.setTypeenum(typeEnum);
		Example<Commodity> example = Example.of(probe);

		return commodityJpa.findAll(example);
	}

	@Override
	public Page<Commodity> getCommoditys(PageParam page) {
		Pageable pageable = new PageRequest(page.getPagenum() - 1, page.getPagesize());

		return commodityJpa.findAll(pageable);

	}

	@Override
	public Page<Ware> getWares(Commodity commodity, PageParam page) {
		Pageable pageable = new PageRequest(page.getPagenum() - 1, page.getPagesize());
		Ware probe = new Ware();
		probe.setCommodity(commodity.getId());
		Example<Ware> example = Example.of(probe);
		return wareJpa.findAll(example, pageable);
	}

}
