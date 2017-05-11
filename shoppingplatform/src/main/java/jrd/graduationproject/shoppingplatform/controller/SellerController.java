package jrd.graduationproject.shoppingplatform.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jrd.graduationproject.shoppingplatform.pojo.enumfield.CategoryEnum;
import jrd.graduationproject.shoppingplatform.pojo.enumfield.TypeEnum;
import jrd.graduationproject.shoppingplatform.pojo.po.Commodity;
import jrd.graduationproject.shoppingplatform.pojo.po.Order;
import jrd.graduationproject.shoppingplatform.pojo.po.Seller;
import jrd.graduationproject.shoppingplatform.pojo.po.User;
import jrd.graduationproject.shoppingplatform.pojo.po.Ware;
import jrd.graduationproject.shoppingplatform.pojo.vo.OrderQuery;
import jrd.graduationproject.shoppingplatform.pojo.vo.PageParam;
import jrd.graduationproject.shoppingplatform.pojo.vo.WareQuery;
import jrd.graduationproject.shoppingplatform.service.IOrderService;
import jrd.graduationproject.shoppingplatform.service.IWareService;
import jrd.graduationproject.shoppingplatform.util.GlobalUtil;

@Controller
@RequestMapping("/shop")
public class SellerController {

	@Autowired
	private IWareService wareService;
	@Autowired
	private IOrderService orderService;

	@RequestMapping(value = "/ware/add.controller", method = RequestMethod.POST)
	public String addWare(Model model, Ware ware, HttpSession session, MultipartFile file) throws IOException {
		User user = (User) session.getAttribute("User");
		String id = user.getId();
		ware.setSeller(id);
		ware.setPhoto(GlobalUtil.savePhoto(file));
		model.addAttribute("add", wareService.addWare(ware));
		return showWare(id, new PageParam(), new WareQuery(), model);
	}

	@RequestMapping(value = "/ware/alter.controller", method = RequestMethod.POST)
	public String alterWare(Model model, Ware ware, HttpSession session, MultipartFile file) throws IOException {
		User user = (User) session.getAttribute("User");
		String id = user.getId();
		ware.setSeller(id);
		if (!file.isEmpty())
			ware.setPhoto(GlobalUtil.savePhoto(file));
		wareService.alterWare(ware);
		return showWare(id, new PageParam(), new WareQuery(), model);
	}

	@RequestMapping(value = "/ware/show.action")
	public String showWare(@RequestParam("sessionUserId") String id, PageParam page, WareQuery query, Model model) {
		query.getOrderby().put("updatedate", "DESC");
		query.getOrderby().put("", "CASE status WHEN 0 THEN 1 WHEN 1 THEN 1 WHEN 2 THEN 2 ELSE 3 END");
		query.setSeller(id);
		query.setStatus(-1);
		page.setPagesize(25);
		Slice<Ware> wares = wareService.getWares(page, query);
		model.addAttribute("wares", splitWare(wares.getContent()));
		model.addAttribute("page", wares);
		Map<TypeEnum, List<Commodity>> map = new LinkedHashMap<>();
		for (CategoryEnum categoryEnum : CategoryEnum.values()) {
			for (TypeEnum typeEnum : TypeEnum.getTypesByCategory(categoryEnum))
				map.put(typeEnum, wareService.getCommoditysByType(typeEnum));
		}
		model.addAttribute("select", map);
		Seller seller = wareService.getSeller(id);
		model.addAttribute("seller", seller);

		return "shop/ware";
	}

	private List<List<Ware>> splitWare(List<Ware> content) {
		List<List<Ware>> wares = new ArrayList<>();
		int fromIndex = 0;
		int step = 5;
		while (fromIndex < content.size()) {
			int toIndex = fromIndex + step;
			if (toIndex > content.size())
				toIndex = content.size();
			wares.add(content.subList(fromIndex, toIndex));
			fromIndex = toIndex;
		}

		return wares;
	}

	@RequestMapping(value = "/order/show.action")
	public String showOrder(@RequestParam("sessionUserId") String id, Order order, PageParam page, Model model) {

		Slice<Order> orders = orderService.getOrdersBySeller(order, id, page);
		model.addAttribute("orders", orders.getContent());
		model.addAttribute("page", orders);
		Seller seller = wareService.getSeller(id);
		model.addAttribute("seller", seller);
		return "shop/order";
	}

	@RequestMapping(value = "/order/query.action")
	public String queryOrder(@RequestParam("sessionUserId") String id, OrderQuery order, PageParam page, Model model) {

		Slice<Order> orders = orderService.getOrdersBySeller(order, id, page);
		model.addAttribute("orders", orders.getContent());
		model.addAttribute("page", orders);
		Seller seller = wareService.getSeller(id);
		model.addAttribute("seller", seller);
		model.addAttribute("querytype", order.getQuerytype());
		model.addAttribute("orderid", order.getId());
		model.addAttribute("startdate", order.getStartdate());
		model.addAttribute("enddate", order.getEnddate());
		return "shop/order";
	}
	@RequestMapping(value = "/order/songda.action")
	public String songda(@RequestParam("orderid") String orderid, 
			@RequestParam("sessionUserId") String id, Order order, PageParam page, Model model) {
		
		orderService.songda(orderid);
		return showOrder(id, order, page, model);
	}

	@RequestMapping("/{path}.html")
	public String publicHtml(@PathVariable(value = "path", required = true) String path) {
		return "shop/" + path;
	}

}
