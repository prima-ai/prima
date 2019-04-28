package ai.prima.prima.commands;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import ai.prima.prima.PrimaAI;
import ai.prima.prima.Network;
import ai.prima.prima.NetworkImage;
import ai.prima.prima.util.Log;

public class BuildCommand extends Command {

	private static final String NAME = "name";
	private static final String NETWORK = "network";
		
	private final PrimaAI ai;
	
	public BuildCommand(PrimaAI ai) {
		super("build", "Builds a new image with a network reference",
				Arrays.asList(NAME, NETWORK),
				Arrays.asList(),
				Arrays.asList());
		this.ai = ai;
	}

	@Override
	public void execute(Map<String, String> args) {
		String name = args.get(NAME);
		Network network = findNetwork(args.get(NETWORK));
		if(network != null) {
			NetworkImage image = NetworkImage.build(name, network);
			ai.getNetworkImages().add(image);
			Log.info("Created Image " + name);
		} else {
			Log.info("No such network.");
		}
	}
	
	private Network findNetwork(String term) {
		Optional<Network> network = ai.getNetworks().stream()
				.filter(n -> n.getName().equals(term)).findAny();
		if(network.isPresent()) {
			return network.get();
		}
		return null;
	}

}
