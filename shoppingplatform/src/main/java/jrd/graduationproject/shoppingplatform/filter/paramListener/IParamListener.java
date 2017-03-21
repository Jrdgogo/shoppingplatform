package jrd.graduationproject.shoppingplatform.filter.paramListener;

public interface IParamListener {

	String executeName(String name);
	String[] executeValue(String name,String[] values);
	boolean	 register();
}
