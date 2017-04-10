package jrd.graduationproject.shoppingplatform.service;

import java.util.List;

import org.springframework.data.domain.Slice;

import jrd.graduationproject.shoppingplatform.pojo.enumfield.CategoryEnum;
import jrd.graduationproject.shoppingplatform.pojo.enumfield.TypeEnum;
import jrd.graduationproject.shoppingplatform.pojo.po.Commodity;
import jrd.graduationproject.shoppingplatform.pojo.po.User;
import jrd.graduationproject.shoppingplatform.pojo.po.Ware;
import jrd.graduationproject.shoppingplatform.pojo.vo.PageParam;
import jrd.graduationproject.shoppingplatform.pojo.vo.WareQuery;

public interface IWareService {

	List<Commodity> getCommoditysByType(TypeEnum typeEnum);

	Slice<Commodity> getCommoditys(PageParam page);

	Commodity addCommodity(Commodity commodity);

	Ware addWare(Ware ware);

	List<Ware> getExWares(CategoryEnum categoryEnum);

	Slice<Ware> getWares(List<CategoryEnum> categoryEnums, PageParam page);

	Slice<Ware> getWares(PageParam page, Ware ware);

	Commodity getCommodityById(String id);

	Slice<Ware> getWares(PageParam page, WareQuery query);

	Long getUserShopCar(User user);

	void alterWare(Ware ware);

}
