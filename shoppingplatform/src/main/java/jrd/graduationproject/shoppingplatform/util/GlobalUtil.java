package jrd.graduationproject.shoppingplatform.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GlobalUtil {
	
	public static String dateFormat(Date date,String pattern){
		DateFormat dateFormat=new SimpleDateFormat(pattern);
		return dateFormat.format(date);
	}
	public static String dateFormat(Long date,String pattern){
		return dateFormat(new Date(date),pattern);
	}
	public static String dateFormat(Date date){
		return dateFormat(date,"yyyy-MM-dd HH:mm:ss");
	}
	public static String dateFormat(Long date){
		return dateFormat(new Date(date));
	}

}
