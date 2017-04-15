package jrd.graduationproject.shoppingplatform.pojo.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "t_message")
public class Message {

	@Id
	@Column(length = 32)
	private String id;

	@Column(length = 128, nullable = false)
	private String msg;

	@Column(nullable = false,columnDefinition="INT default 1")
	private Integer type;
	
	@Column(columnDefinition="BIT default 0")
	private Boolean status;
	
	@Column(length=32,nullable=false)
	private String typeid;

	@Temporal(TemporalType.TIMESTAMP)
	@org.hibernate.annotations.CreationTimestamp
	@Column(columnDefinition = "timestamp DEFAULT CURRENT_TIMESTAMP")
	private Date createdate;

	@Temporal(TemporalType.TIMESTAMP)
	@org.hibernate.annotations.UpdateTimestamp
	// @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	// @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(columnDefinition = "timestamp ON UPDATE CURRENT_TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date updatedate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
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

	public String getTypeid() {
		return typeid;
	}

	public void setTypeid(String typeid) {
		this.typeid = typeid;
	}	

}
