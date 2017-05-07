package jrd.graduationproject.shoppingplatform.pojo.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "t_comment")
public class Comment {
	@Id
	@Column(length=32)
	private String id;

	@Column(length=1000)
	private String cdesc;
	
	@Temporal(TemporalType.TIMESTAMP)
	@org.hibernate.annotations.CreationTimestamp
	@Column(columnDefinition = "timestamp DEFAULT CURRENT_TIMESTAMP")
	private Date createdate;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="wareid")
	private Ware ware;
	
	@ManyToOne
	@JoinColumn(name="userid")
	private User user;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCdesc() {
		return cdesc;
	}

	public void setCdesc(String cdesc) {
		this.cdesc = cdesc;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Ware getWare() {
		return ware;
	}

	public void setWare(Ware ware) {
		this.ware = ware;
	}

	
}
