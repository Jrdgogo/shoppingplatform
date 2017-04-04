package jrd.graduationproject.shoppingplatform.pojo.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_ware")
public class Ware {

	@Id
	@Column(length=32)
	private String id;
	// 商品描述
	@Column(length=80,nullable = false)
	private String ware;
	// 商品详情表
	@Column(length=20,nullable = false)
	private String waretable;

	@Column(nullable = false)
	private Double price;
	
	@Column(nullable = false)
	private String photo;

	//级联
	@Column(length=32,nullable = false)
	private String commodity;

	@Column(nullable = false,columnDefinition="VARCHAR(32) default '342623J19950718R0302D'")
	private String seller;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWare() {
		return ware;
	}

	public void setWare(String ware) {
		this.ware = ware;
	}

	public String getWaretable() {
		return waretable;
	}

	public void setWaretable(String waretable) {
		this.waretable = waretable;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getCommodity() {
		return commodity;
	}

	public void setCommodity(String commodity) {
		this.commodity = commodity;
	}

	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}
	
}
