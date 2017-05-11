package jrd.graduationproject.shoppingplatform.pojo.vo;

import java.util.Date;

import jrd.graduationproject.shoppingplatform.pojo.po.Order;

public class OrderQuery extends Order {

	private Date startdate;

	private Date enddate;

	private String querytype;

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public String getQuerytype() {
		return querytype;
	}

	public void setQuerytype(String querytype) {
		this.querytype = querytype;
	}

}
