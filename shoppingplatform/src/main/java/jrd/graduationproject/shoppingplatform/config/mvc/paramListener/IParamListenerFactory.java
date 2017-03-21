package jrd.graduationproject.shoppingplatform.config.mvc.paramListener;

import java.util.ArrayList;
import java.util.List;

import jrd.graduationproject.shoppingplatform.filter.paramListener.IParamListener;
import jrd.graduationproject.shoppingplatform.util.ErgodicClass;

public class IParamListenerFactory {

	private static List<IParamListener> listeners = new ArrayList<IParamListener>();

	static {
		List<IParamListener> iParamListeners = ErgodicClass.getAllBean(IParamListener.class);
		for (IParamListener listener : iParamListeners) {
			if (listener.register())
				listeners.add(listener);
		}
	}

	public static List<IParamListener> getListeners() {
		List<IParamListener> iParamListeners = new ArrayList<IParamListener>(listeners);
		return iParamListeners;
	}

}
