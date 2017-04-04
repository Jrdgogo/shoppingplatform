package jrd.graduationproject.shoppingplatform.pojo.po;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import jrd.graduationproject.shoppingplatform.pojo.enumfield.OrderStatusEnum;

@Entity
@Table(name = "t_order")
public class Order {

	@Id
	@Column(length=32)
	private String id;

	@Column(nullable = false)
	private Double price;

	@Column(columnDefinition="INT DEFAULT 0")
	@Enumerated(EnumType.ORDINAL)
	private OrderStatusEnum status;
	
	//级联
	@OneToOne
	@JoinColumn(name="wareaddr")
	private UserWareAddr wareaddr;
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="userid")
	private User user;
	
	@OneToMany(cascade={CascadeType.PERSIST,CascadeType.REMOVE},
			mappedBy="order")
	private Set<OrderDetail> orderdetails;
	

	@Temporal(TemporalType.TIMESTAMP)
	@org.hibernate.annotations.CreationTimestamp
	private Date createdate;


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public Double getPrice() {
		return price;
	}


	public void setPrice(Double price) {
		this.price = price;
	}


	public OrderStatusEnum getStatus() {
		return status;
	}


	public void setStatus(OrderStatusEnum status) {
		this.status = status;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public UserWareAddr getWareaddr() {
		return wareaddr;
	}


	public void setWareaddr(UserWareAddr wareaddr) {
		this.wareaddr = wareaddr;
	}


	public Set<OrderDetail> getOrderdetails() {
		return orderdetails;
	}


	public void setOrderdetails(Set<OrderDetail> orderdetails) {
		this.orderdetails = orderdetails;
	}


	public Date getCreatedate() {
		return createdate;
	}


	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	
}