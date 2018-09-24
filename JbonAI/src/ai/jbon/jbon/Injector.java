package ai.jbon.jbon;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ai.jbon.jbon.nodes.Node;

public class Injector {
	
	private List<Node> queue = new ArrayList<Node>();
	
	private InjectionThread thread;
	
	public Injector() {
		thread = new InjectionThread(queue);
	}
	
	public void run() {
		thread.start();
	}
	
	public void register(Node node) {
		queue.add(node);
	}
	
	public void register(Collection<Node> nodes) {
		queue.addAll(nodes);
	}
}
