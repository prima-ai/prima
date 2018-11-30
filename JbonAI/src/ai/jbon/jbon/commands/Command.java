package ai.jbon.jbon.commands;

import java.util.List;
import java.util.Map;

public abstract class Command {
	
	private final String cmd;
	private final String description;
	private final List<String> params;
	private final List<String> optionals;
	private final String usage;
	
	public Command(final String cmd, final String description, List<String> params, List<String> optionals) {
		this.cmd = cmd;
		this.description = description;
		this.params = params;
		this.optionals = optionals;
		usage = buildUsage();
	}
	
	public abstract void execute(Map<String, String> args);
	
	private String buildUsage() {
		StringBuilder builder = new StringBuilder(cmd);
		params.forEach(param -> {
			builder.append(" [" + param + "]");
		});
		optionals.forEach(optional -> {
			builder.append(" <" + optional + ">");
		});
		return builder.toString();
	}
	
	public String getCmd() {
		return this.cmd;
	}
	
	public String getDescription() {
		return this.description;
	}

	public List<String> getParams() {
		return params;
	}

	public List<String> getOptionals() {
		return optionals;
	}
	public String getUsage() {
		return this.usage;
	}
}
