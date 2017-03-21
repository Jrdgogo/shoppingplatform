package jrd.graduationproject.shoppingplatform.pojo.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
@Table(name="t_user_extr")
public class UserExtr {

	@Id
	private Integer id;
	@Column(nullable=false)
	private String activecode;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable = false)  
	@org.hibernate.annotations.CreationTimestamp 
	private Date createdate;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getActivecode() {
		return activecode;
	}

	public void setActivecode(String activecode) {
		this.activecode = activecode == null ? null : activecode.trim();
	}

	public Date getCreatedate() {
		return createdate;
	}
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
}