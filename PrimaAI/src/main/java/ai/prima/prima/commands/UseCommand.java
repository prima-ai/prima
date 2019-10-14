package ai.prima.prima.commands;

import java.util.Arrays;
import java.util.Map;

import ai.prima.prima.PrimaAI;
import ai.prima.prima.Network;
import ai.prima.prima.NetworkImage;

public class UseCommand extends Command {

	private static final Parameter TYPE_PARAMETER = new Parameter("type", Parameter.Requirement.OPTIONAL);
	private static final Parameter ITEM_PARAMETER = new Parameter("item", Parameter.Requirement.OPTIONAL);

	private final PrimaAI ai;
	
	public UseCommand(PrimaAI ai) {
		super("use", "Selects a network or an image in order to edit it faster. Leave item empty to unselect", Arrays.asList(TYPE_PARAMETER, ITEM_PARAMETER));
		this.ai = ai;
	}

	@Override
	public void execute(Map<Parameter, String> values) {
		String item = values.get(ITEM_PARAMETER);
		if(values.containsKey(TYPE_PARAMETER)) {
			selectDefinedType(item, values.get(TYPE_PARAMETER));
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
