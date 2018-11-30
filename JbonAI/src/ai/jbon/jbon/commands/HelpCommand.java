package ai.jbon.jbon.commands;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import ai.jbon.jbon.data.Registry;
import ai.jbon.jbon.exceptions.NoRegistryEntryException;
import ai.jbon.jbon.util.Log;

public class HelpCommand extends Command{

	private static final String COMMAND = "command";
	
	private final Log log = new Log(getClass());
	private final Registry registry;
	
	public HelpCommand(Registry registry) {
		super("help", "See available commands and their syntax", Arrays.asList(), Arrays.asList(COMMAND));
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
			log.write(command.getCmd() + ": \n"
					+ "Description: " + command.getDescription() + "\n"
					+ "Syntay: " + command.getUsage());
		} catch (NoRegistryEntryException e) {
			log.error("Unknown Command");
		}
	}
	
	private void printCommands() {
		registry.getCommands().values().forEach(command -> {
			log.write(command.getCmd() + " - " + command.getDescription());
		});
		log.write("[required parameter] <optional parameter>");
		log.write("use -parameter to specifically set a parameter");
		log.write("Type 'help -command \"command\"' do see detail information");
	}
}
