package ai.jbon.jbon;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ai.jbon.jbon.injector.Injector;
import ai.jbon.jbon.nodes.InputNode;
import ai.jbon.jbon.nodes.Node;
import ai.jbon.jbon.nodes.OutputNode;
import ai.jbon.jbon.util.Log;

public class Network {
	
	private String name;
	
	private List<Node> nodes;
	private List<InputNode> inputNodes;
	
	private Injector injector;
	
	private File file;
	
	public Network(File file, String name, List<Node> nodes) {
		this.file = file;
		this.name = name;
		this.nodes = nodes;
		inputNodes = new ArrayList<>();
		findInputNodes();
		injector = new Injector();
	}
	
	public Network(String name, List<Node> nodes) {
		this.file = new File(JbonAI.NETWORK_DIR.getPath() + "\\" + name + "." + JbonAI.NETWORK_EXTENSION);
		this.name = name;
		this.nodes = nodes;
		inputNodes = new ArrayList<>();
		findInputNodes();
		injector = new Injector();
	}
	
	public Network(String name) {
		this.name = name;
		this.file = new File(JbonAI.NETWORK_DIR.getPath() + "\\" + name + "." + JbonAI.NETWORK_EXTENSION);
		nodes = new ArrayList<>();
		inputNodes = new ArrayList<>();
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
	
	private void findInputNodes() {
		inputNodes.clear();
		nodes.forEach(node -> {
			if(node instanceof InputNode) {
				inputNodes.add((InputNode) node);
			}
		});
	}

	public void addNode(Node node) { 
		nodes.add(node);
		findInputNodes();
	}
	
	public String getName() { return this.name; }
	
	public List<Node> getNodes(){ return this.nodes; }
	
	public List<InputNode> getInputNodes(){ return this.inputNodes; }
	
	public File getFile() {
		return this.file;
	}
}
