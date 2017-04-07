package jrd.graduationproject.shoppingplatform.service;

import java.util.List;

import org.springframework.data.domain.Page;

import jrd.graduationproject.shoppingplatform.pojo.enumfield.TypeEnum;
import jrd.graduationproject.shoppingplatform.pojo.po.Commodity;
import jrd.graduationproject.shoppingplatform.pojo.po.Ware;
import jrd.graduationproject.shoppingplatform.pojo.vo.PageParam;

public interface IWareService {

	List<Commodity> getCommoditysByType(TypeEnum typeEnum);

	Page<Commodity> getCommoditys(PageParam page);

	Page<Ware> getWares(Commodity commodity, PageParam page);
	
	Commodity addCommodity(Commodity commodity);
	
	Ware addWare(Ware ware);

}
