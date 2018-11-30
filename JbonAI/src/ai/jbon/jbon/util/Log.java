package ai.jbon.jbon.util;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {

	private final SimpleDateFormat DATEFORMAT = new SimpleDateFormat("hh:mm:ss:SSSS");
	private final String initiator;
	
	public Log(Class<?> c) {
		initiator = c.getSimpleName();
	}
	
	public void write(final String message) {
		System.out.println(message);
	}
	
	public void info(final String message) {
		System.out.println(now() + " [INFO] " + initiator + ": " + message);
	}
	
	public void error(final String message) {
		System.out.println(now() + " [ERROR] " + initiator + ": " + message);
	}
	
	public void warning(final String message) {
		System.out.println(now() + " [WARNING] " + initiator + ": " + message);
	}
	
	private String now() {
		return DATEFORMAT.format(new Date());
	}
}
