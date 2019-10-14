package ai.prima.prima.commands;

import java.util.Arrays;
import java.util.Map;

import ai.prima.prima.PrimaAI;
import ai.prima.prima.Network;

public class DuplicateCommand extends Command{

	private static final Parameter NETWORK_PARAMETER = new Parameter("network", Parameter.Requirement.REQUIRED);
	private static final Parameter NAME_PARAMETER = new Parameter("name", Parameter.Requirement.OPTIONAL);
	
	private final PrimaAI ai;
	
	public DuplicateCommand(PrimaAI ai) {
		super("duplicate", "Duplicates an existing network.", Arrays.asList(NETWORK_PARAMETER, NAME_PARAMETER));
		this.ai = ai;
	}

	@Override
	public void execute(Map<Parameter, String> values) {
		String networkName = values.get(NETWORK_PARAMETER);
		Network network = ai.getNetworks().stream().filter(n -> n.getName().equals(networkName)).findAny().get();
		String name = network.getName() + "2";
		if(values.containsKey(NAME_PARAMETER)) {
			name = values.get(NAME_PARAMETER);
		}
		ai.getNetworks().add(new Network(name, network.getNodes()));
	}
}
