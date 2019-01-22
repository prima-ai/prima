package ai.jbon.jbon.commands;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Optional;

import ai.jbon.jbon.JbonAI;
import ai.jbon.jbon.Network;
import ai.jbon.jbon.NetworkImage;
import ai.jbon.jbon.util.Log;

public class BuildCommand extends Command {

	private static final String NAME = "name";
	private static final String NETWORK = "network";
		
	private final JbonAI ai;
	
	public BuildCommand(JbonAI ai) {
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
			NetworkImage image = new NetworkImage(name, network);
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
