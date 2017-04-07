package jrd.graduationproject.shoppingplatform.pojo.enumfield;

import jrd.graduationproject.shoppingplatform.exception.category.NotFindEnumException;

public enum AdminEnum {
	ADMIN(4, "管理员", 1 + 4), SHOPKEEPER(2, "店主", 1 + 2), USER(1, "用户", 1), VISITOR(0, "游客", 0);
	private Integer index;
	private String desc;
	private Integer power;

	private AdminEnum(Integer index, String desc, Integer power) {
		this.index = index;
		this.desc = desc;
		this.power = power;
	}

	public Integer getIndex() {
		return index;
	}

	public String getDesc() {
		return desc;
	}

	public Integer getPower() {
		return power;
	}

	public static AdminEnum getAdminByIndex(Integer index) {
		for (AdminEnum adminEnum : AdminEnum.values()) {
			if (adminEnum.getIndex() == index)
				return adminEnum;
		}
		throw new NotFindEnumException("没有发现该枚举类型");
	}

	public static AdminEnum getAdminByDesc(String desc) {
		for (AdminEnum adminEnum : AdminEnum.values()) {
			if (adminEnum.getDesc().equals(desc))
				return adminEnum;
		}
		throw new NotFindEnumException("没有发现该枚举类型");
	}

	public static Integer getModulePowerByRolePower(Integer power) {
		Integer totalPower = 0;
		for (AdminEnum adminEnum : AdminEnum.values()) {
			if ((adminEnum.getIndex() & power) == adminEnum.getIndex())
				totalPower = totalPower | adminEnum.getPower();
		}
		return totalPower;
	}

}
