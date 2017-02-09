package jrd.graduationproject.shoppingplatform.pojo.enumfield;

import jrd.graduationproject.shoppingplatform.exception.NotFindEnumException;

public enum StatusEnum {
     NOTACTIVE(0,"未激活"),ACTIVE(1,"活跃"),CANCEL(2,"注销");
	private Integer index;
	private String desc;
    private StatusEnum(Integer index,String desc){
    	 this.index=index;
    	 this.desc=desc;
     }
	public Integer getIndex() {
		return index;
	}
	public String getDesc() {
		return desc;
	}
     public static StatusEnum getStatusByIndex(Integer index){
    	 for(StatusEnum statusEnum:StatusEnum.values()){
    		 if(statusEnum.getIndex()==index)
    			 return statusEnum;
    	 }
    	 throw new NotFindEnumException("没有发现该枚举类型");
     }
     public static StatusEnum getStatusByDesc(String desc){
    	 for(StatusEnum statusEnum:StatusEnum.values()){
    		 if(statusEnum.getDesc().equals(desc))
    			 return statusEnum;
    	 }
    	 throw new NotFindEnumException("没有发现该枚举类型");
     }
}
