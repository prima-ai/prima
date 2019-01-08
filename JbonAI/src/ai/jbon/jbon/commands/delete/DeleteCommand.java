package ai.jbon.jbon.commands.delete;

import java.util.Arrays;
import java.util.Map;

import ai.jbon.jbon.JbonAI;
import ai.jbon.jbon.commands.Command;

public class DeleteCommand extends Command {

	private final JbonAI ai;
	
	public DeleteCommand(JbonAI ai) {
		super("delete", "Deletes a JbonAI item",
				Arrays.asList(),
				Arrays.asList(),
				Arrays.asList(new DeleteNetworkCommand(ai), new DeleteNodeCommand(ai)));
		this.ai = ai;
	}

	@Override
	public void execute(Map<String, String> args) {

	}

}
