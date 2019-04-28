package ai.prima.prima.commands.create;

import java.util.Arrays;
import java.util.Map;

import ai.prima.prima.PrimaAI;
import ai.prima.prima.commands.Command;
import ai.prima.prima.nodes.Connection;
import ai.prima.prima.nodes.Node;

public class CreateConnectionCommand extends Command {

	private static final String FROM = "from";
	private static final String TO = "to";
	private static final String WEIGHT = "weight";
	
	private final PrimaAI ai;
	
	public CreateConnectionCommand(PrimaAI ai) {
		super("connection", "Creates a connection between two nodes",
				Arrays.asList(FROM, TO),
				Arrays.asList(WEIGHT),
				Arrays.asList());
		this.ai = ai;
	}

	@Override
	public void execute(Map<String, String> args) {
		int toId = Integer.parseInt(args.get(TO));
		Node target = ai.getSelectedNetwork().getNodes().get(toId);
		int fromId = Integer.parseInt(args.get(FROM));
		Node source = ai.getSelectedNetwork().getNodes().get(fromId);
		Connection conn = new Connection(target, readWeight(args));
		source.getConnections().add(conn);
	}
	
	private float readWeight(Map<String, String> args) {
		if(args.containsKey(WEIGHT)) {
			return Float.parseFloat(args.get(WEIGHT));
		}
		return 1.0F;
	}
}
