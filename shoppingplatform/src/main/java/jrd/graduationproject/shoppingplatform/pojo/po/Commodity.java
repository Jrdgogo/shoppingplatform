package jrd.graduationproject.shoppingplatform.pojo.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import jrd.graduationproject.shoppingplatform.pojo.enumfield.TypeEnum;

@Entity
@Table(name = "t_commodity")
public class Commodity {
	@Id
	@Column(length=32)
	private String id;
	
	@Column(length=20,nullable=false)
	private String name;
	
	@Column(length=1000)
	private String searchkey;
	
	@Enumerated(EnumType.STRING)
	private TypeEnum typeenum;

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

	public String getSearchkey() {
		return searchkey;
	}

	public void setSearchkey(String searchkey) {
		this.searchkey = searchkey;
	}

	public TypeEnum getTypeenum() {
		return typeenum;
	}

	public void setTypeenum(TypeEnum typeenum) {
		this.typeenum = typeenum;
	}

	

}
