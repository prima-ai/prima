package ai.prima.prima.commands;

import java.util.List;
import java.util.Map;

public abstract class Command {
	
	private String shortcut;
	private List<Parameter> parameters;
	private List<Command> subCommands;
}
