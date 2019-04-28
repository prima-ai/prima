package ai.prima.prima.commands;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import ai.prima.prima.PrimaAI;
import ai.prima.prima.nodes.Node;
import ai.prima.prima.util.Log;

public class NetworkCommand extends Command{

	private static final String NETWORK = "network";
	
	private final PrimaAI ai;
	
	public NetworkCommand(PrimaAI ai) {
		super("network", "Lists all loaded networks. or shows details about a specific network", 
				Arrays.asList(),
				Arrays.asList(NETWORK),
				Arrays.asList());
		this.ai = ai;
	}

	@Override
	public void execute(Map<String, String> args) {
		if(args.containsKey(NETWORK)) {
			
		} else {
			ai.getNetworks().forEach(network -> {
				Log.writeLine(" -" + network.getName());
			});
		}
	}
	
	private void printNodeInfo(List<Node> nodes, Node node) {
		Log.writeLine(node.getClass().getSimpleName() + ":" + nodes.indexOf(node));
		node.getConnections().forEach(connection -> {
			printNodeInfo(nodes, connection.getTarget(), 1);
		});
	}
	
	private void printNodeInfo(List<Node> nodes, Node node, int depth) {
		Log.writeLine(buildIndent(depth) + node.getClass().getSimpleName() + ":" + nodes.indexOf(node));
		node.getConnections().forEach(connection -> {
			printNodeInfo(nodes, connection.getTarget(), depth + 1);
		});
	}
	
	private String buildIndent(int depth) {
		StringBuilder indent = new StringBuilder();
		IntStream.of(0, depth).forEach(i -> {
			indent.append(" ");
		});
		return indent.toString();
	}
}
