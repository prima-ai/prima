package ai.jbon.jbon;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {

	private static final SimpleDateFormat DATEFORMAT = new SimpleDateFormat("hh:mm:ss:SSSS");
	
	public static void info(final String message) {
		System.out.println(now() + " [INFO]: " + message);
	}
	
	public static void error(final String message) {
		System.out.println(now() + " [ERROR]: " + message);
	}
	
	public static void warning(final String message) {
		System.out.println(now() + " [WARNING]: " + message);
	}
	
	private static String now() {
		return DATEFORMAT.format(new Date());
	}
}
