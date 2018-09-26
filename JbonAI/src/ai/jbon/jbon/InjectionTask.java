package ai.jbon.jbon;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
				System.out.println("YESS");
				inject();
			}
			
			// TODO clean this shit -----------
			try {
				sleep(0, 1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// --------------------------------
		}
	}

	private void inject() {

		List<Connection> connections = new ArrayList<Connection>();
		// Node to Connection to next Node
		queue.forEach(node -> {
			if (!(node.pushOutput() == null)) {
				connections.addAll(node.pushOutput());
			}
		});
		// Calculate target Node values
		List<Node> targets = gatherTargetNodes(connections);
		targets.forEach(target -> {
			target.calcOutput();
		});
		queue.clear();
		queue.addAll(targets);
	}

	private List<Node> gatherTargetNodes(final List<Connection> connections) {
		List<Node> targets = new ArrayList<Node>();
		connections.forEach(connection -> {
			targets.add(connection.getTarget());
		});
		return targets;
	}
}
