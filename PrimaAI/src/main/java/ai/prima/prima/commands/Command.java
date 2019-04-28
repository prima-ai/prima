package ai.prima.prima.commands;

import java.util.List;
import java.util.Map;

public abstract class Command {
	
	private final String cmd;
	private final String description;
	private List<String> params;
	private final List<String> optionals;
	private final List<Command> subcommands;
	private final String usage;
	
	public Command(final String cmd, final String description, List<String> params, List<String> optionals, List<Command> subcommands) {
		this.cmd = cmd;
		this.description = description;
		this.params = params;
		this.optionals = optionals;
		this.subcommands = subcommands;
		usage = buildUsage();
	}
	
	
	public void run(Map<String, String> args) {
		execute(args);
	}
	
	public abstract void execute(Map<String, String> args);
	
	public String buildUsage() {
		StringBuilder builder = new StringBuilder(buildCommandUsage());
		subcommands.forEach(command -> {
			builder.append("\n            " + cmd + " " + command.buildUsage());
		});
		return builder.toString();
	}
	
	private String buildCommandUsage() {
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
	
	public List<Command> getSubcommands(){
		return this.subcommands;
	}
	
	public String getUsage() {
		return this.usage;
	}
}
