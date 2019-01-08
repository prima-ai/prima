package ai.jbon.jbon.commands.delete;

import java.io.File;
import java.util.Arrays;
import java.util.Map;

import ai.jbon.jbon.JbonAI;
import ai.jbon.jbon.Network;
import ai.jbon.jbon.commands.Command;
import ai.jbon.jbon.util.Log;

public class DeleteNetworkCommand extends Command {

	private static final String NETWORK = "network";
	
	private final JbonAI ai;
	
	public DeleteNetworkCommand(JbonAI ai) {
		super("network", "Deletes a network and its file",
				Arrays.asList(NETWORK),
				Arrays.asList(),
				Arrays.asList());
		this.ai = ai;
	}

	@Override
	public void execute(Map<String, String> args) {
		String name = args.get(NETWORK);
		if(ai.getNetworks().stream().anyMatch(n -> n.getName().equals(name))) {
			Network network = ai.getNetworks().stream().filter(n -> n.getName().equals(name)).findAny().get();
			deleteFile(network);
			ai.getNetworks().remove(network);
		} else {
			Log.info("No such network " + name);
		}
	}
	
	private void deleteFile(Network network) {
		File file = new File(JbonAI.NETWORK_DIR.getPath() + "\\" + network.getName() + "." + JbonAI.NETWORK_EXTENSION);
		if(file.exists()) {
			file.delete();
		} else {
			Log.warning("Could not find file " + file.getPath());
		}
	}

}
