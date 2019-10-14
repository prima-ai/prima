package ai.prima.prima;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import ai.prima.prima.commands.Command;
import ai.prima.prima.data.Registry;
import ai.prima.prima.exceptions.CommandSyntaxException;
import ai.prima.prima.exceptions.NoRegistryEntryException;
import ai.prima.prima.util.Log;

public class Prompt {

	private static final String DEFAULT_DIRECTORY = "PrimaAI";

	private final Scanner scanner = new Scanner(System.in);

	private boolean running;

	private String directory = DEFAULT_DIRECTORY;

	public void run() {
		running = true;
		while (running) {
			String input = readConsole();
			runCmd(input);
		}
	}

	public void runCmd(String input) {
		List<String> arguments = parseArgs(input);
		Optional<Command> command = Registry.getCommandRegistry().stream().filter(command1 -> command1.getShortcut().equals(arguments.get(0))).findFirst();
		if(command.isPresent()) {
			arguments.remove(0);
			command.get().run(arguments);
		} else {
			Log.info("Unknown command");
		}
	}

	private List<String> parseArgs(String cmd) {
		List<String> args = new ArrayList<>();
		Pattern regex = Pattern.compile("[^\\s\"']+|\"([^\"]*)\"|'([^']*)'");
		Matcher regexMatcher = regex.matcher(cmd);
		while (regexMatcher.find()) {
			args.add(regexMatcher.group());
		}
		return args;
	}

	public void stop() {
		running = false;
	}

	private String readConsole() {
		Log.write(directory + " > ");
		return scanner.nextLine();
	}

	public void clearDirectory() {
		this.directory = DEFAULT_DIRECTORY;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}
}
