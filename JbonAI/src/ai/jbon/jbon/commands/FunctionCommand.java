package ai.jbon.jbon.commands;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import ai.jbon.jbon.JbonAI;
import ai.jbon.jbon.util.Log;

public class FunctionCommand extends Command {

	private final JbonAI ai;
	
	public FunctionCommand(JbonAI ai) {
		super("function", "lists loaded activation functions",
				Arrays.asList(),
				Arrays.asList(),
				Arrays.asList());
		this.ai = ai;
	}

	@Override
	public void execute(Map<String, String> args) {
		ai.getRegistry().getFunctions().entrySet().forEach(entry -> {
			Log.info(entry.getValue().getTag() + ":" + entry.getKey());
		});
	}

}
