package jrd.graduationproject.shoppingplatform.util;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jrd.graduationproject.shoppingplatform.config.jdbc.DataSourceConfig;

public class PropertiesUtil {

	private static Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);

	public static void setMethodOfProperties(Object object, Properties properties) {
		Class<? extends Object> cls = object.getClass();
		Map<String, Method> methodMap = new HashMap<String, Method>();
		Method[] methods = cls.getMethods();
		for (Method method : methods) {
			String name = method.getName();
			if (!name.startsWith("set"))
				continue;
			name = name.substring(3);
			String key = name.substring(0, 1).toLowerCase() + name.substring(1);
			methodMap.put(key, method);
		}
		for (String nameKey : properties.stringPropertyNames()) {
			try {
				Method method = methodMap.get(nameKey);
				Class<?> pcls = method.getParameterTypes()[0];
				method.invoke(object, new Object[] { convertType(pcls, properties.getProperty(nameKey)) });
			} catch (Exception e) {
				logger.error("参数" + nameKey + "配置失败");
				continue;
			}

		}
	}

	private static Object convertType(Class<?> pcls, String property) {
		if (pcls.equals(String.class))
			return property;
		else if (pcls.equals(Integer.class) || pcls.equals(int.class))
			return Integer.parseInt(property);
		else if (pcls.equals(Boolean.class) || pcls.equals(boolean.class))
			return Boolean.parseBoolean(property);
		else if (pcls.equals(Long.class) || pcls.equals(long.class))
			return Long.parseLong(property);
		else if (pcls.equals(Byte.class) || pcls.equals(byte.class))
			return Byte.parseByte(property);
		else if (pcls.equals(Short.class) || pcls.equals(short.class))
			return Short.parseShort(property);
		else if (pcls.equals(CharSequence.class) || pcls.equals(char.class))
			return property.charAt(0);
		else if (pcls.equals(Double.class) || pcls.equals(double.class))
			return Double.parseDouble(property);
		else if (pcls.equals(Float.class) || pcls.equals(float.class))
			return Float.parseFloat(property);
		return property;
	}

	public static Properties propertiesOfFile(String fileName, String propertiesStartsWith) throws IOException {
		Properties properties = new Properties();

		Properties propertiesAll = new Properties();
		propertiesAll.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName));

		for (String nameKey : propertiesAll.stringPropertyNames()) {
			if (nameKey.startsWith(propertiesStartsWith))
				properties.setProperty(nameKey.substring(propertiesStartsWith.length() + 1),
						propertiesAll.getProperty(nameKey));
		}

		return properties;
	}

}
