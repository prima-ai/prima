package ai.jbon.jbon;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ai.jbon.jbon.injector.Injector;
import ai.jbon.jbon.nodes.InputNode;
import ai.jbon.jbon.nodes.Node;
import ai.jbon.jbon.nodes.OutputNode;
import ai.jbon.jbon.util.Log;

public class Network {
	
	private final Log log = new Log(getClass());
	
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
		log.info("Starting up network");
		setupInputNodes();
		log.info("Set up Inputnodes");
		injector.run();
		log.info("Started Injector");
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
