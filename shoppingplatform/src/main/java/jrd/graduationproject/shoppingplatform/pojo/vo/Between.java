package jrd.graduationproject.shoppingplatform.pojo.vo;

public class Between {
	
	private Integer lessthen;
	
	private Integer greaterthen;

	public Integer getLessthen() {
		return lessthen;
	}

	public void setLessthen(Integer lessthen) {
		this.lessthen = lessthen;
	}

	public Integer getGreaterthen() {
		return greaterthen;
	}

	public void setGreaterthen(Integer greaterthen) {
		this.greaterthen = greaterthen;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((greaterthen == null) ? 0 : greaterthen.hashCode());
		result = prime * result + ((lessthen == null) ? 0 : lessthen.hashCode());
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
		Between other = (Between) obj;
		if (greaterthen == null) {
			if (other.greaterthen != null)
				return false;
		} else if (!greaterthen.equals(other.greaterthen))
			return false;
		if (lessthen == null) {
			if (other.lessthen != null)
				return false;
		} else if (!lessthen.equals(other.lessthen))
			return false;
		return true;
	}
	
	
	

}
