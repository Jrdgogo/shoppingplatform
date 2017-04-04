package jrd.graduationproject.shoppingplatform.pojo.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "t_order_detail")
public class OrderDetail {
	@Id
	@Column(length=32)
	private String id;
	
	@Column(nullable=false,columnDefinition="INT default 1")
	private Integer warenum;
	
	//级联
	@OneToOne(optional=false)
	@JoinColumn(name="wareid")
	private Ware ware;
	
	@ManyToOne
	@JoinColumn(name="orderid")
	private Order order;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getWarenum() {
		return warenum;
	}

	public void setWarenum(Integer warenum) {
		this.warenum = warenum;
	}

	public Ware getWare() {
		return ware;
	}

	public void setWare(Ware ware) {
		this.ware = ware;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	

}
