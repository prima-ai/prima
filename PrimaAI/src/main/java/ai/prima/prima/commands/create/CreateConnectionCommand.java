package ai.prima.prima.commands.create;

import java.util.Arrays;
import java.util.Map;

import ai.prima.prima.PrimaAI;
import ai.prima.prima.commands.Command;
import ai.prima.prima.commands.Parameter;
import ai.prima.prima.nodes.Connection;
import ai.prima.prima.nodes.Node;

public class CreateConnectionCommand extends Command {

	private static final Parameter FROM_PARAMETER = new Parameter("from", Parameter.Requirement.REQUIRED);
	private static final Parameter TO_PARAMETER = new Parameter("to", Parameter.Requirement.REQUIRED);
	private static final Parameter WEIGHT_PARAMETER = new Parameter("weight", Parameter.Requirement.OPTIONAL);
	
	private final PrimaAI ai;
	
	public CreateConnectionCommand(PrimaAI ai) {
		super("connection", "Creates a connection between two nodes", Arrays.asList(FROM_PARAMETER, TO_PARAMETER, WEIGHT_PARAMETER));
		this.ai = ai;
	}

	@Override
	public void execute(Map<Parameter, String> values) {
		int toId = Integer.parseInt(values.get(TO_PARAMETER));
		Node target = ai.getSelectedNetwork().getNodes().get(toId);
		int fromId = Integer.parseInt(values.get(FROM_PARAMETER));
		Node source = ai.getSelectedNetwork().getNodes().get(fromId);
		Connection conn = new Connection(target, readWeight(values));
		source.getConnections().add(conn);
	}
	
	private float readWeight(Map<Parameter, String> values) {
		if(values.containsKey(WEIGHT_PARAMETER)) {
			return Float.parseFloat(values.get(WEIGHT_PARAMETER));
		}
		return 1.0F;
	}
}
