package jrd.graduationproject.shoppingplatform.pojo.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "t_seller")
public class Seller {

	@Id
	@Column(length = 32)
	private String id;

	@Column(length = 40, nullable = false)
	private String name;

	@Column(length = 40, nullable = false)
	private String logo;
	
	@Column(columnDefinition="BIT default 0")
	private Boolean status;

	@OneToOne
	@JoinColumn(name = "userid")
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}
	
	

}
