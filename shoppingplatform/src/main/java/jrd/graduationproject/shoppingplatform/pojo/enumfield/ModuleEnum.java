package jrd.graduationproject.shoppingplatform.pojo.enumfield;

import jrd.graduationproject.shoppingplatform.exception.NotFindEnumException;

public enum ModuleEnum {
	PUBLIC(0,"开放区","/public"),USER(1,"用户区","/user"),
	SHOP(2,"商店区","/shop"),SYSTEM(4,"系统区","/system");
	private Integer index;
	private String desc;
	private String url;
    private ModuleEnum(Integer index,String desc,String url){
    	 this.index=index;
    	 this.desc=desc;
    	 this.url=url;
     }
	public Integer getIndex() {
		return index;
	}
	public String getDesc() {
		return desc;
	}
     public String getUrl() {
		return url;
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
     public static ModuleEnum getAdminByUrl(String url){
    	 for(ModuleEnum adminEnum:ModuleEnum.values()){
    		 if(url.contains(adminEnum.getUrl()))
    			 return adminEnum;
    	 }
    	 throw new NotFindEnumException("没有发现该枚举类型");
     }
}
