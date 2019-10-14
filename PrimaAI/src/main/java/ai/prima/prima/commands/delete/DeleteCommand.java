package ai.prima.prima.commands.delete;

import java.util.Arrays;
import java.util.Map;

import ai.prima.prima.PrimaAI;
import ai.prima.prima.commands.Command;
import ai.prima.prima.commands.Parameter;

public class DeleteCommand extends Command {

	private final PrimaAI ai;
	
	public DeleteCommand(PrimaAI ai) {
		super("delete", "Deletes a PrimaAI item",
				Arrays.asList(),
				Arrays.asList(new DeleteNetworkCommand(ai), new DeleteNodeCommand(ai)));
		this.ai = ai;
	}


	@Override
	public void execute(Map<Parameter, String> values) {
		
	}
}
