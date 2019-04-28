package ai.prima.prima.commands.create;

import java.util.Arrays;
import java.util.Map;

import ai.prima.prima.PrimaAI;
import ai.prima.prima.commands.Command;

public class CreateCommand extends Command{

	public CreateCommand(PrimaAI ai) {
		super("create", "Creates a new PrimaAI Element",
				Arrays.asList(),
				Arrays.asList(),
				Arrays.asList(new CreateNetworkCommand(ai), new CreateNodeCommand(ai), new CreateConnectionCommand(ai)));
	}

	@Override
	public void execute(Map<String, String> args) {}
}
