package ai.jbon.jbon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import ai.jbon.jbon.commands.Command;
import ai.jbon.jbon.data.Registry;
import ai.jbon.jbon.exceptions.CommandSyntaxException;
import ai.jbon.jbon.exceptions.NoRegistryEntryException;
import ai.jbon.jbon.util.Log;

public class Prompt {

	private final Registry registry;
	private final Log log = new Log(getClass());

	public Prompt(Registry registry) {
		this.registry = registry;
	}

	private void execute(Command command, List<String> args) {
		try {
			Map<String, String> params = buildParams(command, args);
			command.execute(params);
		} catch (CommandSyntaxException e) {
			log.error("Ivalid syntax. Syntax: " + command.getUsage());
		}
	}

	public void runCmd(String cmd) {
		try {
			List<String> args = parseArgs(cmd);
			Command command = registry.getCommand(args.get(0));
			args.remove(0);
			execute(command, args);
		} catch (NoRegistryEntryException e) {
			log.error("Unknown Command");
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
		} catch(Exception e) {
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
}
