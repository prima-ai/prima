package ai.prima.prima.commands;

import java.util.Arrays;
import java.util.Map;

import ai.prima.prima.PrimaAI;
import ai.prima.prima.Network;

public class DuplicateCommand extends Command{

	private static final String NETWORK = "network";
	private static final String NAME = "name";
	
	private final PrimaAI ai;
	
	public DuplicateCommand(PrimaAI ai) {
		super("duplicate", "Dublicates an existing network.",
				Arrays.asList(NETWORK),
				Arrays.asList(NAME),
				Arrays.asList());
		this.ai = ai;
	}

	@Override
	public void execute(Map<String, String> args) {
		String networkName = args.get(NETWORK);
		Network network = ai.getNetworks().stream().filter(n -> n.getName().equals(networkName)).findAny().get();
		String name = network.getName() + "2";
		if(args.containsKey(NAME)) {
			name = args.get(NAME);
		}
		ai.getNetworks().add(new Network(name, network.getNodes()));
	}

}
