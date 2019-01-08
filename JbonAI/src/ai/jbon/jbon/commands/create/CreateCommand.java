package ai.jbon.jbon.commands.create;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import ai.jbon.jbon.JbonAI;
import ai.jbon.jbon.commands.Command;

public class CreateCommand extends Command{

	public CreateCommand(JbonAI ai) {
		super("create", "Creates a new JbonAI Element",
				Arrays.asList(),
				Arrays.asList(),
				Arrays.asList(new CreateNetworkCommand(ai), new CreateNodeCommand(ai), new CreateConnectionCommand(ai)));
	}

	@Override
	public void execute(Map<String, String> args) {}
}
