package ai.prima.prima.commands;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import ai.prima.prima.PrimaAI;
import ai.prima.prima.Network;
import ai.prima.prima.util.Log;

public class SaveCommand extends Command{

	private static final String NETWORK = "network";
	
	private final PrimaAI ai;
	
	public SaveCommand(PrimaAI ai) {
		super("save", "Saves the given or the selected network",
				Arrays.asList(),
				Arrays.asList(NETWORK),
				Arrays.asList());
		this.ai = ai;
	}

	@Override
	public void execute(Map<String, String> args) {
		Network network = findNetwork(args);
		try {
			ai.getResourceLoader().storeNetwork(network);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private Network findNetwork(Map<String, String> args) {
		if(args.containsKey(NETWORK)) {
			String term = args.get(NETWORK);
			return ai.getNetworks().stream()
					.filter(network -> network.getName().equals(term)).findAny().get();
		}else {
			return ai.getSelectedNetwork();
		}
	}
}
