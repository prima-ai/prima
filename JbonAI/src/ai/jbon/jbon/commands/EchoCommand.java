package ai.jbon.jbon.commands;

import java.util.Arrays;
import java.util.Map;

import ai.jbon.jbon.util.Log;

public class EchoCommand extends Command{

	private static final String MESSAGE = "message";
	
	public EchoCommand() {
		super("echo", "Prints a message in the console",
				Arrays.asList(MESSAGE),
				Arrays.asList(),
				Arrays.asList());
	}

	@Override
	public void execute(Map<String, String> args) {
		String message = args.get(MESSAGE);
		Log.info(message);
	}

}
