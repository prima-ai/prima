package ai.jbon.jbon;

import java.util.Properties;

public class ThreadConfig {
	
	private static final String PRIORITY = "priority";
	
	public int priority = 2;
	
	public ThreadConfig load(final Properties properties) {
		this.priority = Integer.parseInt(properties.getProperty(PRIORITY)) + 2;
		return this;
	}
	
	public Properties toProperties() {
		Properties properties = new Properties();
		properties.put(PRIORITY, priority);
		return properties;
	}
}
