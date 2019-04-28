package ai.prima.prima.commands;

import java.util.Arrays;
import java.util.Map;

import ai.prima.prima.data.Registry;
import ai.prima.prima.util.Log;

public class FunctionCommand extends Command {

	public FunctionCommand() {
		super("function", "lists loaded activation functions",
				Arrays.asList(),
				Arrays.asList(),
				Arrays.asList());
	}

	@Override
	public void execute(Map<String, String> args) {
		Registry.getFunctionRegistry().entrySet().forEach(entry -> {
			Log.info(entry.getValue().getTag() + ":" + entry.getKey());
		});
	}

}
