package ai.prima.prima.commands.create;

import java.util.Arrays;
import java.util.Map;
import java.util.NoSuchElementException;

import ai.prima.prima.PrimaAI;
import ai.prima.prima.Network;
import ai.prima.prima.commands.Command;
import ai.prima.prima.commands.Parameter;
import ai.prima.prima.data.Registry;
import ai.prima.prima.exceptions.NodeGenerationException;
import ai.prima.prima.nodes.Node;
import ai.prima.prima.util.Log;

public class CreateNodeCommand extends Command{

	private static final Parameter TYPE_PARAMETER = new Parameter("type", Parameter.Requirement.REQUIRED);
	private static final Parameter FUNCTION_PARAMETER = new Parameter("function", Parameter.Requirement.REQUIRED);
		
	private final PrimaAI ai;
	
	public CreateNodeCommand(PrimaAI ai) {
		super("node", "Creates a node to the currently selected images network", Arrays.asList(TYPE_PARAMETER, FUNCTION_PARAMETER));
		this.ai = ai;
	}

	@Override
	public void execute(Map<Parameter, String> values) {
		try {
			String type = readType(values);
			String function = readFunction(values);
			Network network = ai.getSelectedNetwork();
			Node node = Registry.createNode(type, function);
			network.addNode(node);
			Log.info("Created node with id " + network.getNodes().indexOf(node));
		} catch (NodeGenerationException e) {
			e.printStackTrace();
		} catch (NoSuchElementException e) {
			e.printStackTrace();
		}
	}
	
	private String readType(Map<Parameter, String> values) throws NoSuchElementException {
		String term = values.get(TYPE_PARAMETER);
		if(term.contains(".")) {
			return term;
		} else {
			return Registry.getNodeRegistry().keySet().stream()
				.filter(key -> key.toLowerCase().endsWith(term.toLowerCase())).findAny().get();
		}
	}
	
	private String readFunction(Map<Parameter, String> args) throws NoSuchElementException {
		String term = args.get(FUNCTION_PARAMETER);
		if(term.contains(".")) {
			return term;
		} else {
			return Registry.getFunctionRegistry().entrySet().stream()
					.filter(e -> e.getValue().getClass().getName().equals(term)).findAny().get().getKey();
		}
	}
}
