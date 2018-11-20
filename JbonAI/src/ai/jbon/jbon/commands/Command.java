package ai.jbon.jbon.commands;

import java.util.List;

import ai.jbon.jbon.JbonAI;

public abstract class Command {
	
	private final String cmd;
	private final String usage;
	private final String description;
	
	public Command(final String cmd, final String usage, final String description) {
		this.cmd = cmd;
		this.usage = usage;
		this.description = description;
	}
	
	public abstract void execute(List<String> args);
	
	public String getCmd() {
		return this.cmd;
	}
	
	public String getUsage() {
		return this.usage;
	}
	
	public String getDescription() {
		return this.description;
	}
}
