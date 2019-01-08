package ai.jbon.jbon.commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import ai.jbon.jbon.JbonAI;
import ai.jbon.jbon.Network;
import ai.jbon.jbon.util.Log;

public class SaveCommand extends Command{

	private static final String NETWORK = "network";
	
	private final JbonAI ai;
	
	public SaveCommand(JbonAI ai) {
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
			Log.info("Saved network at " + network.getFile().getPath());
		} catch (FileNotFoundException e) {
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
