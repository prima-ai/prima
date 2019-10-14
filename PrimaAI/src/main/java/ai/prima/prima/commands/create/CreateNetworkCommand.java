package ai.prima.prima.commands.create;

import java.util.Arrays;
import java.util.Map;

import ai.prima.prima.PrimaAI;
import ai.prima.prima.Network;
import ai.prima.prima.commands.Command;
import ai.prima.prima.commands.Parameter;
import ai.prima.prima.util.Log;

public class CreateNetworkCommand extends Command{

	private static final Parameter NAME_PARAMETER = new Parameter("name", Parameter.Requirement.REQUIRED);
		
	private final PrimaAI ai;
	
	public CreateNetworkCommand(PrimaAI ai) {
		super("network", "Creates a new Network", Arrays.asList(NAME_PARAMETER));
		this.ai = ai;
	}

	@Override
	public void execute(Map<Parameter, String> values) {
		String name = values.get(NAME_PARAMETER);
		if(name.contains(" ")) {
			Log.warning("Name contains invalid characters");
		} else {
			ai.getNetworks().add(new Network(name));
			Log.info("Created network with name " + name);
		}
	}

}
