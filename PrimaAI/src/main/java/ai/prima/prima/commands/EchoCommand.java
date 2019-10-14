package ai.prima.prima.commands;

import java.util.Arrays;
import java.util.Map;

import ai.prima.prima.util.Log;

public class EchoCommand extends Command{

	private static final Parameter messageParameter = new Parameter("message", Parameter.Requirement.REQUIRED);
	
	public EchoCommand() {
		super("echo", "Prints a message in the console", Arrays.asList(messageParameter), Arrays.asList());
	}

	@Override
	public void execute(Map<Parameter, String> values) {
		Log.info(values.get(messageParameter));
	}
}
