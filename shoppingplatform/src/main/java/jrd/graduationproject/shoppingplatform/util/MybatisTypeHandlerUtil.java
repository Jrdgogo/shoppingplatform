package jrd.graduationproject.shoppingplatform.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.persistence.Id;

public class MybatisTypeHandlerUtil {

	public static Object getid(Object parameter) {
		try {
			Class<?> clz = parameter.getClass();
			Field[] fields = clz.getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				if (field.isAnnotationPresent(Id.class))
					return field.get(parameter);
			}

			Method[] methods = clz.getMethods();
			for (Method method : methods) {
				if (method.isAnnotationPresent(Id.class)) {
					String name = method.getName();
					if (name.startsWith("get"))
						method.invoke(parameter, new Object[] {});
					else if (name.startsWith("set")) {
						name = "g" + name.substring(1);
						clz.getMethod(name, new Class[] {}).invoke(parameter, new Object[] {});
					}
				}

			}
		} catch (Exception e) {

		}
		return null;
	}

	public static <E> E newInstance(Object id, Class<E> clz) {
		try {
			E parameter = clz.newInstance();
			Field[] fields = clz.getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				if (field.isAnnotationPresent(Id.class)) {
					field.set(parameter, id);
					return parameter;
				}
			}

			Method[] methods = clz.getMethods();
			for (Method method : methods) {
				if (method.isAnnotationPresent(Id.class)) {
					String name = method.getName();
					if (name.startsWith("set")){
						method.invoke(parameter, new Object[] { id });
						return parameter;
					}
					else if (name.startsWith("get")) {
						name = "s" + name.substring(1);
						clz.getMethod(name, new Class[] { method.getReturnType() }).invoke(parameter,
								new Object[] { id });
						return parameter;
					}
				}

			}

		} catch (Exception e) {
		}
		return null;
	}
}