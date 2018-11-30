package ai.jbon.jbon.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import ai.jbon.jbon.JbonAI;
import ai.jbon.jbon.util.Log;

public class EchoCommand extends Command{

	private static final String MESSAGE = "message";
	
	private final Log log = new Log(getClass());
	
	public EchoCommand() {
		super("echo", "Prints a message in the console", Arrays.asList(MESSAGE), new ArrayList<String>());
	}

	@Override
	public void execute(Map<String, String> args) {
		String message = args.get(MESSAGE);
		log.info(message);
	}

}
