package jrd.graduationproject.shoppingplatform.pojo.enumfield;

import jrd.graduationproject.shoppingplatform.exception.category.NotFindEnumException;

public enum OrderStatusEnum {
	UNPAID(0,"未支付"),PAYMENT(1,"已支付"),CANCEL(2,"取消"),BACK(3,"退单");
	private Integer index;
	private String desc;
    private OrderStatusEnum(Integer index,String desc){
    	 this.index=index;
    	 this.desc=desc;
     }
	public Integer getIndex() {
		return index;
	}
	public String getDesc() {
		return desc;
	}
     public static OrderStatusEnum getStatusByIndex(Integer index){
    	 for(OrderStatusEnum statusEnum:OrderStatusEnum.values()){
    		 if(statusEnum.getIndex()==index)
    			 return statusEnum;
    	 }
    	 throw new NotFindEnumException("没有发现该枚举类型");
     }
     public static OrderStatusEnum getStatusByDesc(String desc){
    	 for(OrderStatusEnum statusEnum:OrderStatusEnum.values()){
    		 if(statusEnum.getDesc().equals(desc))
    			 return statusEnum;
    	 }
    	 throw new NotFindEnumException("没有发现该枚举类型");
     }
}
