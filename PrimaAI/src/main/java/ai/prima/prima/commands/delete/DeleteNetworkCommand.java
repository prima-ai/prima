package ai.prima.prima.commands.delete;

import java.io.File;
import java.util.Arrays;
import java.util.Map;

import ai.prima.prima.PrimaAI;
import ai.prima.prima.Network;
import ai.prima.prima.commands.Command;
import ai.prima.prima.commands.Parameter;
import ai.prima.prima.util.Log;

public class DeleteNetworkCommand extends Command {

	private static final Parameter NETWORK_PARAMETER = new Parameter("network", Parameter.Requirement.REQUIRED);
	
	private final PrimaAI ai;
	
	public DeleteNetworkCommand(PrimaAI ai) {
		super("network", "Deletes a network and its file", Arrays.asList(NETWORK_PARAMETER));
		this.ai = ai;
	}

	@Override
	public void execute(Map<Parameter, String> values) {
		String name = values.get(NETWORK_PARAMETER);
		if(ai.getNetworks().stream().anyMatch(n -> n.getName().equals(name))) {
			Network network = ai.getNetworks().stream().filter(n -> n.getName().equals(name)).findAny().get();
			deleteFile(network);
			ai.getNetworks().remove(network);
		} else {
			Log.info("No such network " + name);
		}
	}
	
	private void deleteFile(Network network) {
		File file = new File(PrimaAI.NETWORK_DIR.getPath() + "\\" + network.getName() + "." + PrimaAI.NETWORK_EXTENSION);
		if(file.exists()) {
			file.delete();
		} else {
			Log.warning("Could not find file " + file.getPath());
		}
	}

}
