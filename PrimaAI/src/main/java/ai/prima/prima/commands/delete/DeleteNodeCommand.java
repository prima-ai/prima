package ai.prima.prima.commands.delete;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import ai.prima.prima.PrimaAI;
import ai.prima.prima.Network;
import ai.prima.prima.commands.Command;
import ai.prima.prima.nodes.Connection;
import ai.prima.prima.nodes.Node;

public class DeleteNodeCommand extends Command{

	private static final String NODE_ID = "node-id";
	private static final String NETWORK = "network";
	private static final String CASCADE = "cascade";
	
	private final PrimaAI ai;
	
	public DeleteNodeCommand(PrimaAI ai) {
		super("node", "Deletes a node",
				Arrays.asList(NODE_ID),
				Arrays.asList(NETWORK, CASCADE),
				Arrays.asList());
		this.ai = ai;
	}

	@Override
	public void execute(Map<String, String> args) {
		int nodeId = Integer.parseInt(args.get(NODE_ID));
		Network network = findNetwork(args);
		Node node = network.getNodes().get(nodeId);
		boolean cascade = cascades(args);
		deleteNode(network, node, cascade);
	}
	
	private Network findNetwork(Map<String, String> args) {
		if(args.containsKey(NETWORK)) {
			return ai.getNetworks().stream()
					.filter(network -> network.getName().equals(args.get(NETWORK))).findAny().get();
		} else {
			return ai.getSelectedNetwork();
		}
	}
	
	private boolean cascades(Map<String, String> args) {
		if(args.containsKey(CASCADE)) {
			return Boolean.parseBoolean(args.get(CASCADE));
		}
		return false;
	}
	
	private void deleteNode(Network network, Node node, boolean cascade) {
		List<Connection> connections = node.getConnections();
		network.getNodes().remove(node);
		if(cascade) {
			connections.forEach(conn -> {
				deleteNode(network, conn.getTarget(), cascade);
			});
		}
	}

}
