package ai.prima.prima.commands.delete;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import ai.prima.prima.PrimaAI;
import ai.prima.prima.Network;
import ai.prima.prima.commands.Command;
import ai.prima.prima.commands.Parameter;
import ai.prima.prima.nodes.Connection;
import ai.prima.prima.nodes.Node;

public class DeleteNodeCommand extends Command{

	private static final Parameter NODE_ID_PARAMETER = new Parameter("node-id", Parameter.Requirement.REQUIRED);
	private static final Parameter NETWORK_PARAMETER = new Parameter("network", Parameter.Requirement.OPTIONAL);
	private static final Parameter CASCADE_PARAMETER = new Parameter("cascade", Parameter.Requirement.OPTIONAL);
	
	private final PrimaAI ai;
	
	public DeleteNodeCommand(PrimaAI ai) {
		super("node", "Deletes a node", Arrays.asList(NODE_ID_PARAMETER, NETWORK_PARAMETER, CASCADE_PARAMETER));
		this.ai = ai;
	}

	@Override
	public void execute(Map<Parameter, String> values) {
		int nodeId = Integer.parseInt(values.get(NODE_ID_PARAMETER));
		Network network = findNetwork(values);
		Node node = network.getNodes().get(nodeId);
		boolean cascade = cascades(values);
		deleteNode(network, node, cascade);
	}
	
	private Network findNetwork(Map<Parameter, String> values) {
		if(values.containsKey(NETWORK_PARAMETER)) {
			return ai.getNetworks().stream()
					.filter(network -> network.getName().equals(values.get(NETWORK_PARAMETER))).findAny().get();
		} else {
			return ai.getSelectedNetwork();
		}
	}
	
	private boolean cascades(Map<Parameter, String> values) {
		if(values.containsKey(CASCADE_PARAMETER)) {
			return Boolean.parseBoolean(values.get(CASCADE_PARAMETER));
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
