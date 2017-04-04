package jrd.graduationproject.shoppingplatform.pojo.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "t_seller")
public class Seller {
	
	@Id
	@Column(length=32)
	private String id;
	
	@Column(length=40,nullable=false)
	private String name;
	
	@Lob
	private Byte[] logo;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Byte[] getLogo() {
		return logo;
	}

	public void setLogo(Byte[] logo) {
		this.logo = logo;
	}

}
