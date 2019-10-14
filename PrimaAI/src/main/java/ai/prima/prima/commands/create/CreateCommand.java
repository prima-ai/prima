package ai.prima.prima.commands.create;

import java.util.Arrays;
import java.util.Map;

import ai.prima.prima.PrimaAI;
import ai.prima.prima.commands.Command;
import ai.prima.prima.commands.Parameter;

public class CreateCommand extends Command{

	public CreateCommand(PrimaAI ai) {
		super("create", Arrays.asList(new CreateNetworkCommand(ai), new CreateNodeCommand(ai), new CreateConnectionCommand(ai)), "Creates a new PrimaAI Element");
	}

	@Override
	public void execute(Map<Parameter, String> args) {}
}
