package jrd.graduationproject.shoppingplatform.pojo.vo;

import java.util.HashMap;
import java.util.Map;

import jrd.graduationproject.shoppingplatform.pojo.po.Ware;

public class WareQuery extends Ware {
	
	private Map<String, String> orderby=new HashMap<>();
	
	private Map<String, Between> pricebetween=new HashMap<>();
	
	private String selecttype;

	public Map<String, String> getOrderby() {
		return orderby;
	}

	public void setOrderby(Map<String, String> orderby) {
		this.orderby = orderby;
	}

	public Map<String, Between> getPricebetween() {
		return pricebetween;
	}

	public void setPricebetween(Map<String, Between> pricebetween) {
		this.pricebetween = pricebetween;
	}

	public String getSelecttype() {
		return selecttype;
	}

	public void setSelecttype(String selecttype) {
		this.selecttype = selecttype;
	}
	
}
