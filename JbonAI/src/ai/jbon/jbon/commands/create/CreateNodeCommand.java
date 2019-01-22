package ai.jbon.jbon.commands.create;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import ai.jbon.jbon.JbonAI;
import ai.jbon.jbon.Network;
import ai.jbon.jbon.commands.Command;
import ai.jbon.jbon.data.Registry;
import ai.jbon.jbon.exceptions.NodeGenerationException;
import ai.jbon.jbon.nodes.Node;
import ai.jbon.jbon.util.Log;

public class CreateNodeCommand extends Command{

	private static final String TYPE = "type";
	
	private static final String FUNCTION = "function";
		
	private final JbonAI ai;
	
	public CreateNodeCommand(JbonAI ai) {
		super("node", "Creates a node to the currently selected images network", 
				Arrays.asList(TYPE, FUNCTION),
				Arrays.asList(),
				Arrays.asList());
		this.ai = ai;
	}

	@Override
	public void execute(Map<String, String> args) {
		try {
			String type = readType(args);
			String function = readFunction(args);
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
	
	private String readType(Map<String, String> args) throws NoSuchElementException {
		String term = args.get(TYPE);
		if(term.contains(".")) {
			return term;
		} else {
			return Registry.getNodes().keySet().stream()
				.filter(key -> key.toLowerCase().endsWith(term.toLowerCase())).findAny().get();
		}
	}
	
	private String readFunction(Map<String, String> args) throws NoSuchElementException {
		String term = args.get(FUNCTION);
		if(term.contains(".")) {
			return term;
		} else {
			return Registry.getFunctions().entrySet().stream()
					.filter(e -> e.getValue().getTag().equals(term)).findAny().get().getKey();
		}
	}
}
