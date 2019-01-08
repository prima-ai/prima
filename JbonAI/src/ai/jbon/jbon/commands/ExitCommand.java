package ai.jbon.jbon.commands;

import java.util.Arrays;
import java.util.Map;

import ai.jbon.jbon.JbonAI;

public class ExitCommand extends Command {

	private final JbonAI ai;
	
	public ExitCommand(JbonAI ai) {
		super("exit", "Exits JbonAI",
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
