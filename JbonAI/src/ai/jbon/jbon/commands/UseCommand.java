package ai.jbon.jbon.commands;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import ai.jbon.jbon.JbonAI;
import ai.jbon.jbon.Network;
import ai.jbon.jbon.NetworkImage;
import ai.jbon.jbon.util.Log;

public class UseCommand extends Command {

	private static final String TYPE = "type";
	private static final String ITEM = "item";

	private final JbonAI ai;
	
	public UseCommand(JbonAI ai) {
		super("use", "Selects a network or an image in order to edit it faster. Leave item empty to unselect", 
				Arrays.asList(),
				Arrays.asList(ITEM, TYPE),
				Arrays.asList());
		this.ai = ai;
	}

	@Override
	public void execute(Map<String, String> args) {
		String item = args.get(ITEM);
		if(args.containsKey(TYPE)) {
			selectDefinedType(item, args.get(TYPE));
		} else {
			searchItem(item);
		}
	}	
	
	private void selectDefinedType(String item, String type) {
		if(type.equals("network")) {
			selectNetwork(item);
		}
		if(type.equals("image")) {
			selectNetworkImage(item);
		}
	}
	
	private void searchItem(String item) {
		if(ai.getNetworkImages().stream().anyMatch(image -> image.getName().equals(item))) {
			selectNetworkImage(item);
		} else if(ai.getNetworks().stream().anyMatch(network -> network.getName().equals(item))) {
			selectNetwork(item);
		} else {
			unselect();
		}
	}
	
	private void unselect() {
		ai.unselect();
		ai.getPrompt().clearDirectory();
	}
	
	private void selectNetwork(String name) {
		Network network = findNetwork(name);
		ai.select(network);
		ai.getPrompt().setDirectory(network.getName());
	}
	
	private void selectNetworkImage(String name) {
		NetworkImage image = findNetworkImage(name);
		ai.select(image);
		ai.getPrompt().setDirectory(image.getName());
	}
	
	private Network findNetwork(String name) {
		return ai.getNetworks().stream()
		.filter(network -> network.getName().equals(name)).findAny().get();
	}
	
	private NetworkImage findNetworkImage(String name) {
		return ai.getNetworkImages().stream()
				.filter(image -> image.getName().equals(name)).findAny().get();
	}
}
