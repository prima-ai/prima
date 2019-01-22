package ai.jbon.jbon.injector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ai.jbon.jbon.nodes.Connection;
import ai.jbon.jbon.nodes.Node;

public class InjectionTask extends Thread {

	private final List<Node> queue;

	private boolean running;

	public InjectionTask(final List<Node> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		running = true;
		while (running) {
			if (!queue.isEmpty()) {
				inject();
			}
		}
	}

	private void inject() {
		try {
			List<Connection> connections = new ArrayList<Connection>();
			queue.forEach(node -> {
				List<Connection> outputs = node.pushOutput();
				if (outputs != null) {
					connections.addAll(outputs);
				}
			});
			List<Node> targets = gatherTargetNodes(connections);
			calcOutputs(targets);
			resetQueue(targets);	
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	private void resetQueue(List<Node> targets) {
		queue.clear();
		queue.addAll(targets);
	}
	
	private void calcOutputs(List<Node> targets) {
		targets.forEach(target -> {
			target.calcOutput();
		});
	}
	
	private List<Node> gatherTargetNodes(final List<Connection> connections) {
		List<Node> targets = new ArrayList<Node>();
		connections.forEach(connection -> {
			targets.add(connection.getTarget());
		});
		return targets;
	}
}
