package jrd.graduationproject.shoppingplatform.pojo.po;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_order_seller")
public class OrderOfSeller {

	private String order;

	private String seller;

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

}
