package ai.prima.prima.commands;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import ai.prima.prima.PrimaAI;
import ai.prima.prima.Network;
import ai.prima.prima.util.Log;

import javax.print.attribute.standard.PageRanges;

public class SaveCommand extends Command{

	private static final Parameter NETWORK_PARAMETER = new Parameter("network", Parameter.Requirement.OPTIONAL);
	
	private final PrimaAI ai;
	
	public SaveCommand(PrimaAI ai) {
		super("save", "Saves the given or the selected network", Arrays.asList(NETWORK_PARAMETER));
		this.ai = ai;
	}

	@Override
	public void execute(Map<Parameter, String> values) {
		Network network = findNetwork(values);
		try {
			ai.getResourceLoader().storeNetwork(network);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private Network findNetwork(Map<Parameter, String> values) {
		if(values.containsKey(NETWORK_PARAMETER)) {
			String term = values.get(NETWORK_PARAMETER);
			return ai.getNetworks().stream()
					.filter(network -> network.getName().equals(term)).findAny().get();
		}else {
			return ai.getSelectedNetwork();
		}
	}
}
