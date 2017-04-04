package jrd.graduationproject.shoppingplatform.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import jrd.graduationproject.shoppingplatform.config.mvc.paramListener.IParamListenerFactory;
import jrd.graduationproject.shoppingplatform.filter.paramListener.IParamListener;
import jrd.graduationproject.shoppingplatform.pojo.po.User;

public class ParamFilter implements Filter {
	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// 放行（代理request对象）
		paramRequest req = new paramRequest((HttpServletRequest) request);
		req.pushValues();
		req.workValues();
		chain.doFilter(req, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

	class paramRequest extends HttpServletRequestWrapper {

		public paramRequest(HttpServletRequest request) {
			super(request);
		}

		@Override
		public String getParameter(String name) {
			return pop(paramType.VALUE, name);
		}

		@Override
		public Map<String, String[]> getParameterMap() {
			return pop(paramType.MAP, null);
		}

		@Override
		public Enumeration<String> getParameterNames() {
			return pop(paramType.NAMES, null);
		}

		public String[] getParameterValues(String name) {
			return pop(paramType.VALUES, name);
		}

		private Map<String, String[]> paramValues = new HashMap<String, String[]>();

		@SuppressWarnings("unchecked")
		private <T> T pop(paramType type, String name) {
			if (paramValues.isEmpty())
				return null;
			Object value = null;
			switch (type) {
			case VALUE:
				String[] values = paramValues.get(name);
				if (values == null || values.length <= 0)
					return null;
				value = values[0];
				break;
			case VALUES:
				value = paramValues.get(name);
				break;
			case NAMES:
				List<String> names = new ArrayList<String>(paramValues.keySet());
				value = new Enumeration<String>() {
					@Override
					public boolean hasMoreElements() {
						return !names.isEmpty();
					}

					@Override
					public String nextElement() {
						if (names.isEmpty())
							return null;
						return names.remove(0);
					}
				};
				break;
			case MAP:
				value = paramValues;
				break;
			default:
				return null;
			}
			return (T) value;
		}

		private void pushValues() {
			// Set<String> paramNames = getParamNames();
			// if(paramNames==null)
			// return;
			// for (String name : paramNames) {
			// String[] value=gerParamValueByName(name);
			// if(value!=null)
			// paramValues.put(name, value);
			// }

			Enumeration<String> enumeration = super.getParameterNames();
			while (enumeration.hasMoreElements()) {
				String name = enumeration.nextElement();
				paramValues.put(name, super.getParameterValues(name));
			}
			User user = (User) (super.getSession().getAttribute("User"));
			if (user != null) {
				paramValues.put("sessionUserId", new String[] { user.getId() });
			}
		}

		public void workValues() {
			List<IParamListener> listeners = IParamListenerFactory.getListeners();
			for (IParamListener listener : listeners) {

				Iterator<Entry<String, String[]>> it = paramValues.entrySet().iterator();

				Map<String, String[]> params = new HashMap<String, String[]>();
				while (it.hasNext()) {
					Entry<String, String[]> entry = it.next();
					String name = entry.getKey();
					String[] values = entry.getValue();
					params.put(name, values);
				}
				it = params.entrySet().iterator();
				while (it.hasNext()) {
					Entry<String, String[]> entry = it.next();
					String name = entry.getKey();
					String[] values = entry.getValue();
					paramValues.remove(name, values);
					name = listener.executeName(name);
					values = listener.executeValue(name, values);
					paramValues.put(name, values);
				}
			}
		}
		// private Set<String> getParamNames() {
		// Set<String> paramNameSet = new HashSet<String>();
		// Enumeration<String> paramNames = super.getParameterNames();
		//
		// while (paramNames.hasMoreElements()) {
		// String paramName = paramNames.nextElement();
		// if (gerParamValueByName(paramName) != null)
		// paramNameSet.add(paramName);
		// }
		// if (paramNameSet.size() <= 0)
		// return null;
		//
		// names.addAll(paramNameSet);
		//
		// return paramNameSet;
		// }

		// private String[] gerParamValueByName(String name) {
		// String[] params = super.getParameterValues(name);
		// if (params == null)
		// return null;
		//
		// params = removeNULLAndALikeParam(params);
		// if (params == null || params.length <= 0)
		// return null;
		//
		// return params;
		// }

	}
}

enum paramType {
	VALUE, VALUES, NAMES, MAP
}
