package ai.prima.prima.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {

	private static final SimpleDateFormat DATEFORMAT = new SimpleDateFormat("hh:mm:ss:SSSS");
	
	public static void writeLine(final String message) {
		System.out.println(message);
	}
	
	public static void write(final String message) {
		System.out.print(message);
	}
	
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
