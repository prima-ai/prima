package ai.prima.prima.commands;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import ai.prima.prima.PrimaAI;
import ai.prima.prima.Network;
import ai.prima.prima.NetworkImage;
import ai.prima.prima.util.Log;

public class BuildCommand extends Command {

	private static final Parameter NETWORK_PARAMETER = new Parameter("network", Parameter.Requirement.REQUIRED);
	private static final Parameter NAME_PARAMETER = new Parameter("name", Parameter.Requirement.REQUIRED);
		
	private final PrimaAI ai;
	
	public BuildCommand(PrimaAI ai) {
		super("build", "Creates a runnable image from a network", Arrays.asList(NETWORK_PARAMETER, NAME_PARAMETER));
		this.ai = ai;
	}
	
	private Network findNetwork(String term) {
		Optional<Network> network = ai.getNetworks().stream()
				.filter(n -> n.getName().equals(term)).findAny();
		if(network.isPresent()) {
			return network.get();
		}
		return null;
	}

	@Override
	public void execute(Map<Parameter, String> values) {
		String name = values.get(NAME_PARAMETER);
		Network network = findNetwork(values.get(NETWORK_PARAMETER));
		if(network != null) {
			NetworkImage image = NetworkImage.build(name, network);
			ai.getNetworkImages().add(image);
			Log.info("Created Image " + name);
		} else {
			Log.info("No such network.");
		}
	}
}
