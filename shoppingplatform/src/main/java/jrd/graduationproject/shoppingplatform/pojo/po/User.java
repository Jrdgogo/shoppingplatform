package jrd.graduationproject.shoppingplatform.pojo.po;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import jrd.graduationproject.shoppingplatform.pojo.enumfield.AdminEnum;
import jrd.graduationproject.shoppingplatform.pojo.enumfield.SexEnum;
import jrd.graduationproject.shoppingplatform.pojo.enumfield.StatusEnum;

@Entity
@Table(name = "t_user")
public class User {
	@Id
	@Column(length = 32)
	private String id;

	@Column(length = 40, nullable = false, unique = true)
	private String username;

	@Column(length = 20)
	private String realname;

	@Column(length = 32, nullable = false)
	private String password;

	@Column(columnDefinition = "NUMERIC(11,2) DEFAULT 0.00")
	private Double account;

	@Column(length = 32, nullable = false)
	private String paymentpwd;

	private Integer age;

	@Enumerated(EnumType.STRING)
	private SexEnum sex;

	@Column(columnDefinition = "INT DEFAULT 0")
	@Enumerated(EnumType.ORDINAL)
	private StatusEnum status;

	@Column(nullable = false)
	private String email;

	@Column(length = 20)
	private String phone;

	@Temporal(TemporalType.DATE)
	private Date birth;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	private Byte[] photo;

	@Column(columnDefinition = "INT default 1")
	private Integer power;

	@Column(columnDefinition = "varchar(32) default 'USER'")
	@Enumerated(EnumType.STRING)
	private AdminEnum card;

	@Temporal(TemporalType.TIMESTAMP)
	@org.hibernate.annotations.CreationTimestamp
	private Date createdate;

	@Temporal(TemporalType.TIMESTAMP)
	@org.hibernate.annotations.UpdateTimestamp
	// @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	// @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date updatedate;

	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL }, mappedBy = "user")
	private Set<UserWareAddr> wareAddrs = new HashSet<>();

	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL }, mappedBy = "user")
	private Set<Order> orders = new HashSet<>();

	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL }, mappedBy = "user")
	private Set<ShopCar> shopcars = new HashSet<>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Double getAccount() {
		return account;
	}

	public void setAccount(Double account) {
		this.account = account;
	}

	public String getPaymentpwd() {
		return paymentpwd;
	}

	public void setPaymentpwd(String paymentpwd) {
		this.paymentpwd = paymentpwd;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public SexEnum getSex() {
		return sex;
	}

	public void setSex(SexEnum sex) {
		this.sex = sex;
	}

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(Byte[] photo) {
		this.photo = photo;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public Date getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public Integer getPower() {
		return power;
	}

	public void setPower(Integer power) {
		this.power = power;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Set<UserWareAddr> getWareAddrs() {
		return wareAddrs;
	}

	public void setWareAddrs(Set<UserWareAddr> wareAddrs) {
		this.wareAddrs = wareAddrs;
	}

	public Set<Order> getOrders() {
		return orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

	public Set<ShopCar> getShopcars() {
		return shopcars;
	}

	public void setShopcars(Set<ShopCar> shopcars) {
		this.shopcars = shopcars;
	}

	public AdminEnum getCard() {
		return card;
	}

	public void setCard(AdminEnum card) {
		this.card = card;
	}

}
