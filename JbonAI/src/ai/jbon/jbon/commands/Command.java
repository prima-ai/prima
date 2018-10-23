package ai.jbon.jbon.commands;

import java.util.List;

import ai.jbon.jbon.JbonAI;

public abstract class Command {
	
	private final String usage;
	private final String description;
	
	public Command(final String usage, final String description) {
		this.usage = usage;
		this.description = description;
	}
	
	public abstract void execute(List<String> args);
	
	public String getUsage() {
		return this.usage;
	}
	
	public String getDescription() {
		return this.description;
	}
}
