package ai.prima.prima;

import java.util.List;
import java.util.stream.Collectors;

import ai.prima.prima.injector.Injector;
import ai.prima.prima.nodes.InputNode;

public class NetworkImage extends Thread{

	private final Injector injector = new Injector();
	
	private final Network network; 
	
	public NetworkImage(final String name, final Network network) {
		this.setName(name);
		injector.setName(name + "-Injector");
		this.network = network;
	}
	
	private List<InputNode> findInputNodes() {
		return network.getNodes().stream()
				.filter(node -> node instanceof InputNode)
				.map(node -> (InputNode) node).collect(Collectors.toList());
	}
	
	public void terminate() {
		injector.terminate();
	}
	
	@Override
	public void run() {
		injector.start();
		findInputNodes().forEach(node -> {
			node.setup(injector);
		});
	}
	
	public Network getNetwork() {
		return this.network;
	}	
}