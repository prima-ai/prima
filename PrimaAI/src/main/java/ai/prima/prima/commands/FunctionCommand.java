package ai.prima.prima.commands;

import java.util.Map;

import ai.prima.prima.data.Registry;
import ai.prima.prima.util.Log;

public class FunctionCommand extends Command {

	public FunctionCommand() {
		super("function", "Lists all registered activation functions.");
	}

	@Override
	public void execute(Map<Parameter, String> values) {
		Registry.getFunctionRegistry().entrySet().forEach(entry -> {
			Log.info(entry.getValue().getClass().getName() + ":" + entry.getKey());
		});
	}
}
