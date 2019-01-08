package ai.jbon.jbon.commands;

import java.util.Arrays;
import java.util.Map;

import ai.jbon.jbon.data.Registry;
import ai.jbon.jbon.exceptions.NoRegistryEntryException;
import ai.jbon.jbon.util.Log;

public class HelpCommand extends Command{

	private static final String COMMAND = "command";
	
	private final Registry registry;
	
	public HelpCommand(Registry registry) {
		super("help", "See available commands and their syntax",
				Arrays.asList(),
				Arrays.asList(COMMAND),
				Arrays.asList());
		this.registry = registry;
	}

	@Override
	public void execute(Map<String, String> args) {
		if(args.containsKey(COMMAND)) {
			printUsage(args.get(COMMAND));
		} else {
			printCommands();
		}
	}
	
	private void printUsage(String cmd) {
		try {
			Command command = registry.getCommand(cmd);
			Log.writeLine(command.getCmd() + ": \n"
					+ "Description: " + command.getDescription() + "\n"
					+ "Syntax: " + command.getUsage());
		} catch (NoRegistryEntryException e) {
			Log.error("Unknown Command");
		}
	}
	
	private void printCommands() {
		registry.getCommands().values().forEach(command -> {
			Log.writeLine(command.getCmd() + " - " + command.getDescription());
		});
		Log.writeLine("\n[required parameter] <optional parameter>");
		Log.writeLine("\nuse -parameter to specifically set a parameter");
		Log.writeLine("\nType 'help <command>' do see detail information");
	}
}
