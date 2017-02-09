package jrd.graduationproject.shoppingplatform.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import jrd.graduationproject.shoppingplatform.pojo.enumfield.AdminEnum;

@Entity
@Table(name="role")
public class Role {
	@Id 
	@Enumerated(EnumType.ORDINAL)
	private AdminEnum admin;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable = false)  
	@org.hibernate.annotations.CreationTimestamp
	private Date createdate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@org.hibernate.annotations.UpdateTimestamp
	private Date updatedate;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((admin == null) ? 0 : admin.hashCode());
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
		Role other = (Role) obj;
		if (admin != other.admin)
			return false;
		return true;
	}
	public AdminEnum getAdmin() {
		return admin;
	}
	public void setAdmin(AdminEnum admin) {
		this.admin = admin;
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
	

}
