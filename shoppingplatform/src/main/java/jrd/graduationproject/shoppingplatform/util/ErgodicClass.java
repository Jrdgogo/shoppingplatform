package jrd.graduationproject.shoppingplatform.util;

import java.io.File;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class ErgodicClass {

	private static final Logger log = Logger.getLogger(ErgodicClass.class);

	@SuppressWarnings("unchecked")
	public static <T> List<Class<T>> getAllBeanClass(Class<T> superClass) {
		List<Class<T>> clss = new ArrayList<Class<T>>(0);
		for (Class<?> cls : getAllClass()) {
			if (superClass.isAssignableFrom(cls) && !Modifier.isInterface(cls.getModifiers())
					&& !Modifier.isAbstract(cls.getModifiers())) {
				clss.add((Class<T>) cls);
			}
		}
		return clss;
	}

	public static <T> List<T> getAllBean(Class<T> superClass) {
		List<Class<T>> clss = getAllBeanClass(superClass);
		List<T> instances = new ArrayList<T>();
		for (Class<T> cls : clss) {
			try {
				instances.add(cls.newInstance());
			} catch (InstantiationException | IllegalAccessException e) {
				log.error(cls.getName() + "无法创建实例", e);
			}
		}
		return instances;
	}

	public static List<Class<?>> getAllClass() {
		List<Class<?>> clss = new ArrayList<Class<?>>(0);
		URL url = Thread.currentThread().getContextClassLoader().getResource("/");
		try {
			String path = url.getPath();
			if (path.contains("/bin/"))
				path = path.substring(path.indexOf("/bin/") + "/bin/".length());
			else if (path.contains("/classes/"))
				path = path.substring(path.indexOf("/classes/") + "/classes/".length());
			if (path.endsWith("/"))
				path = path.substring(0, path.length() - 1);
			clss.addAll(findClass(new File(url.getFile()), path.replace("/", ".")));
		} catch (Exception e) {
			log.error("遍历bean失败", e);
		}

		return clss;
	}

	private static List<Class<?>> findClass(File file, String dirName) throws Exception {
		List<Class<?>> clss = new ArrayList<Class<?>>(0);
		if (!file.exists())
			return clss;
		File[] files = file.listFiles();
		for (File f : files) {
			if (f.isDirectory()) {
				if (dirName.equals(""))
					clss.addAll(findClass(f, f.getName()));
				else
					clss.addAll(findClass(f, dirName + "." + f.getName()));
			} else {
				if (f.getName().endsWith(".class")) {
					try {
						clss.add(Class
								.forName(dirName + "." + f.getName().substring(0, f.getName().lastIndexOf(".class"))));
					} catch (ClassNotFoundException e) {
						log.error(dirName + "." + f.getName().substring(0, f.getName().lastIndexOf(".class")));
					}
				}
			}
		}

		return clss;
	}

}
