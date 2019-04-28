package ai.prima.prima;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
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
			String cmd = readConsole();
			runCmd(cmd);
		}
	}

	private void execute(Command command, List<String> args) {
		try {
			Command target = redirectCommand(command, args);
			Map<String, String> params = buildParams(target, args);
			target.run(params);
		} catch (CommandSyntaxException e) {
			Log.error("Ivalid syntax. Syntax: " + command.getUsage());
		}
	}

	private Command redirectCommand(Command command, List<String> args) {
		if(args.size() > 0) {
			List<Command> subcommands = command.getSubcommands();
			if (subcommands.stream().anyMatch(c -> c.getCmd().equals(args.get(0)))) {
				Command target = subcommands.stream().filter(c -> c.getCmd().equals(args.get(0))).findAny().get();
				args.remove(0);
				return redirectCommand(target, args);
			}
		}
		return command;
	}
	
	public void runCmd(String cmd) {
		List<String> args = parseArgs(cmd);
		try {
			Command command = Registry.getCommand(args.get(0));
			args.remove(0);
			execute(command, args);
		} catch (NoRegistryEntryException e) {
			Log.writeLine("Unknown command");
		}
	}

	private Map<String, String> buildParams(Command command, List<String> args) throws CommandSyntaxException {
		Map<String, String> params = new HashMap<>();
		try {
			IntStream.range(0, args.size()).forEach(index -> {
				if (args.size() > index) {
					processParam(command, args, index, params);
				}
			});
		} catch (Exception e) {
			throw new CommandSyntaxException(command);
		}
		return params;
	}

	private void processParam(Command command, List<String> args, int index, Map<String, String> params) {
		String arg = args.get(index);
		if (arg.startsWith("-")) {
			params.put(arg.substring(1, arg.length()), args.get(index + 1).replaceAll("\"", ""));
			args.remove(arg);
		} else {
			if (params.size() >= command.getParams().size()) {
				params.put(command.getOptionals().get(params.size() - command.getParams().size()),
					arg.replaceAll("\"", ""));
			} else {
				params.put(command.getParams().get(index), arg.replaceAll("\"", ""));
			}
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
