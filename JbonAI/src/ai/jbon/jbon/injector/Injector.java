package ai.jbon.jbon.injector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ai.jbon.jbon.nodes.Node;

public class Injector {

	private final List<Node> queue;

	private Thread thread;

	public Injector() {
		queue = new ArrayList<Node>();
		thread = new Thread(new InjectionTask(queue));
		thread.setPriority(1);
	}

	public void run() {
		thread.start();
	}

	public synchronized void register(Node node) {
		queue.add(node);
	}

	public synchronized void register(Collection<Node> nodes) {
		queue.addAll(nodes);
	}
}
