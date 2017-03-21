package jrd.graduationproject.shoppingplatform.pojo.enumfield;

import jrd.graduationproject.shoppingplatform.exception.NotFindEnumException;

public enum ModuleEnum {
	PUBLIC(0,"开放区"),USER(1,"用户区"),
	SHOP(2,"商店区"),SYSTEM(4,"系统区");
	private Integer index;
	private String desc;
    private ModuleEnum(Integer index,String desc){
    	 this.index=index;
    	 this.desc=desc;
     }
	public Integer getIndex() {
		return index;
	}
	public String getDesc() {
		return desc;
	}
     public static ModuleEnum getAdminByIndex(Integer index){
    	 for(ModuleEnum adminEnum:ModuleEnum.values()){
    		 if(adminEnum.getIndex()==index)
    			 return adminEnum;
    	 }
    	 throw new NotFindEnumException("没有发现该枚举类型");
     }
     public static ModuleEnum getAdminByDesc(String desc){
    	 for(ModuleEnum adminEnum:ModuleEnum.values()){
    		 if(adminEnum.getDesc().equals(desc))
    			 return adminEnum;
    	 }
    	 throw new NotFindEnumException("没有发现该枚举类型");
     }
}
