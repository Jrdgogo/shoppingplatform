package jrd.graduationproject.shoppingplatform.pojo.enumfield;

import jrd.graduationproject.shoppingplatform.exception.NotFindEnumException;

public enum AdminEnum {
	SUPERADMIN(0,"超级管理员"),ADMIN(1,"管理员"),SHOPKEEPER(2,"店主"),SHOPASSISTANT(3,"店员"),
	USER(4,"用户"),VISITOR(5,"游客");
	private Integer index;
	private String desc;
    private AdminEnum(Integer index,String desc){
    	 this.index=index;
    	 this.desc=desc;
     }
	public Integer getIndex() {
		return index;
	}
	public String getDesc() {
		return desc;
	}
     public static AdminEnum getAdminByIndex(Integer index){
    	 for(AdminEnum adminEnum:AdminEnum.values()){
    		 if(adminEnum.getIndex()==index)
    			 return adminEnum;
    	 }
    	 throw new NotFindEnumException("没有发现该枚举类型");
     }
     public static AdminEnum getAdminByDesc(String desc){
    	 for(AdminEnum adminEnum:AdminEnum.values()){
    		 if(adminEnum.getDesc().equals(desc))
    			 return adminEnum;
    	 }
    	 throw new NotFindEnumException("没有发现该枚举类型");
     }
}
