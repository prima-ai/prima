package ai.prima.prima.commands;

import java.util.Arrays;
import java.util.Map;

import ai.prima.prima.PrimaAI;

public class ExitCommand extends Command {

	private final PrimaAI ai;
	
	public ExitCommand(PrimaAI ai) {
		super("exit", "Exits PrimaAI",
				Arrays.asList(),
				Arrays.asList(),
				Arrays.asList());
		this.ai = ai;
	}

	@Override
	public void execute(Map<String, String> args) {
		ai.exit();
	}

}
