package jrd.graduationproject.shoppingplatform.filter.paramListener;

public abstract class IParamValuesListener implements IParamListener {

	@Override
	public String executeName(String name) {
		return name;
	}
	@Override
	public boolean register() {
		return true;
	}
	
}
