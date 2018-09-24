package ai.jbon.jbon;

import java.util.ArrayList;
import java.util.List;

import ai.jbon.jbon.nodes.Node;

public class InjectionThread extends Thread{
	
	private List<Node> queue;
	
	private boolean running;
	
	public InjectionThread(List<Node> queue) {
		this.queue = queue;
	}
	
	@Override
	public void run() {
		while(running) {
			if(!queue.isEmpty()) {
				inject();
			}
		}
	}
	
	private void inject() {
		
		List<Connection> connections = new ArrayList<Connection>();
		// Node to Connection to next Node
		queue.forEach(node -> {
			connections.addAll(node.pushOutput());
		});
		// Calculate target Node values
		List<Node> targets = gatherTargetNodes(connections);
		targets.forEach(target -> {
			target.calcOutput();
		});
		queue.clear();
		queue.addAll(targets);
	}
	
	private List<Node> gatherTargetNodes(final List<Connection> connections){
		List<Node> targets = new ArrayList<Node>();
		connections.forEach(connection -> {
			targets.add(connection.getTarget());
		});
		return targets;
	}
}
