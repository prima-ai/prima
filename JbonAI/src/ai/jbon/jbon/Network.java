package ai.jbon.jbon;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ai.jbon.jbon.injector.Injector;
import ai.jbon.jbon.nodes.InputNode;
import ai.jbon.jbon.nodes.Node;
import ai.jbon.jbon.nodes.OutputNode;

public class Network {
	
	private List<Node> nodes;
	private List<InputNode> inputNodes;
	
	private Injector injector;
	
	public Network(List<Node> nodes) {
		this.nodes = nodes;
		List<InputNode> inputNodes = new ArrayList<InputNode>();
		nodes.forEach(node -> {
			if(node instanceof InputNode) {
				inputNodes.add((InputNode) node);
			}
		});
		injector = new Injector();
	}
	
	public void run() {
		Log.info("Starting up network");
		setupInputNodes();
		Log.info("Set up Inputnodes");
		injector.run();
		Log.info("Started Injector");
	}
	
	private void setupInputNodes() {
		inputNodes.forEach(node -> {
			node.setup(injector);
		});
	}
	
	public List<Node> getNodes(){
		return this.nodes;
	}
}
