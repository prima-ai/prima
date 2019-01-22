package ai.jbon.jbon.commands;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import ai.jbon.jbon.JbonAI;
import ai.jbon.jbon.data.Registry;
import ai.jbon.jbon.util.Log;

public class FunctionCommand extends Command {

	public FunctionCommand() {
		super("function", "lists loaded activation functions",
				Arrays.asList(),
				Arrays.asList(),
				Arrays.asList());
	}

	@Override
	public void execute(Map<String, String> args) {
		Registry.getFunctions().entrySet().forEach(entry -> {
			Log.info(entry.getValue().getTag() + ":" + entry.getKey());
		});
	}

}
