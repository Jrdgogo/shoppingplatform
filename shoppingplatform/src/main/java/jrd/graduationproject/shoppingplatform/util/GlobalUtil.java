package jrd.graduationproject.shoppingplatform.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.WebUtils;

import com.alibaba.fastjson.JSONObject;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

import jrd.graduationproject.shoppingplatform.exception.category.NotParseException;

public class GlobalUtil {

	public static final DateFormat datedf = new SimpleDateFormat("yyyy-MM-dd");
	public static final DateFormat timedf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static String get32bitString() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	public static String getModelID(Class<?> model) {
		return md5(get32bitString(), model.getName());
	}

	public static String getCode(String name) {
		try {
			byte[] bytes = name.getBytes("utf-8");
			StringBuffer sb = new StringBuffer();
			int mod = 0;
			for (int i = 0; i < bytes.length && i < 27; i++) {
				if (i == 0 || i % 3 != 0) {
					mod += bytes[i];
					continue;
				}
				mod = (mod % 26 + 26) % 26;
				char c = (char) ('A' + mod);
				sb.append(c);

				mod = 0;
				mod += bytes[i];
			}
			if (sb.length() < 9)
				sb.append(get32bitString().substring(5, 9 - sb.length() + 5));
			return sb.toString();
		} catch (UnsupportedEncodingException e) {
			new RuntimeException("utf-8编码错误", e);
		}
		return name;
	}

	/**
	 * 参数1为原密码，参数2为盐值
	 */
	private static final Md5PasswordEncoder en = new Md5PasswordEncoder();

	public static String md5(String password) {
		return md5(password, password.toLowerCase());
	}

	private static String md5(String password, String solt) {
		return en.encodePassword(password, solt);
	}

	public static String margeCmd(String cmd, String... args) {
		int index = 0, lastindex = 0;
		int i = 0;
		while (true) {
			if (i == args.length) {
				return cmd;
			}
			index = cmd.indexOf("{");
			if (index == -1)
				return cmd;
			lastindex = cmd.indexOf("}");
			cmd = cmd.replace(cmd.substring(index, lastindex + 1), args[i++]);
		}
	}

	public static String dateFormat(Date date, String pattern) {
		DateFormat dateFormat = new SimpleDateFormat(pattern);
		return dateFormat.format(date);
	}

	public static String dateFormat(Long date, String pattern) {
		return dateFormat(new Date(date), pattern);
	}

	public static String timeFormat(Date date) {
		return timedf.format(date);
	}

	public static String dateFormat(Date date) {
		return datedf.format(date);
	}

	public static String timeFormat(Long date) {
		return timeFormat(new Date(date));
	}

	public static String dateFormat(Long date) {
		return dateFormat(new Date(date));
	}

	public static <T> T toJsonObject(Class<T> clazz, String jsonString) {
		return JSONObject.parseObject(jsonString, clazz);
	}

	public static Date formatDate(String date) {
		try {
			return datedf.parse(date);
		} catch (ParseException e) {
			throw new NotParseException("日期格式传入错误！请传入yyyy-MM-dd格式");
		}
	}

	public static Date formatTime(String date) {
		try {
			return timedf.parse(date);
		} catch (ParseException e) {
			throw new NotParseException("日期格式传入错误！请传入yyyy-MM-dd hh:mm:ss格式");
		}
	}

	public static String toJsonString(Object... agrs) {
		JSONObject jsonObject = new JSONObject();
		for (int i = 0; i < agrs.length; i++) {
			jsonObject.put("" + i, agrs[i]);
		}
		return jsonObject.toJSONString();
	}

	public static String savePhoto(MultipartFile file) throws IOException {
		String root = "/images/photo/";
		StringBuffer ware = new StringBuffer(root);
		byte[] bytes = file.getBytes();
		String dir = md5(file.getName(), get32bitString());
		URL url=Thread.currentThread().getContextClassLoader().getResource("");
		String parent = url.getFile()+"static"+root;
		String firstDir = dir.substring(0, 2);
		ware.append(firstDir + "/");
		File savediv = new File(parent, firstDir);

		String secondDir = dir.substring(2, 4);
		ware.append(secondDir + "/");
		savediv = new File(savediv, secondDir);

		if (!savediv.exists())
			savediv.mkdirs();
		String photoName = dir.substring(0, 8) + ".jpg";
		ware.append(photoName);
		File photo = new File(savediv, photoName);

		BufferedImage bufferedImage = ImageIO.read(new ByteInputStream(bytes, 0, bytes.length));
		ImageIO.write(bufferedImage, "JPEG", photo);
		return ware.toString();
	}

}
