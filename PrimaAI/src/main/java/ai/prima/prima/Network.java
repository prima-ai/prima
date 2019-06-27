package ai.prima.prima;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ai.prima.prima.nodes.Connection;
import ai.prima.prima.nodes.Node;

public class Network {
	
	protected String name;
	
	protected List<Node> nodes;

	public Network(){
		this.name = "unnamed";
	}

	public Network(File file, String name, List<Node> nodes) {
		this.name = name;
		this.nodes = nodes;
	}
	
	public Network(String name, List<Node> nodes) {
		this.name = name;
		this.nodes = nodes;
	}
	
	public Network(String name) {
		this.name = name;
		nodes = new ArrayList<>();
	}

	public Network clone(){
		Map<Node, Node> clones = cloneIsolated(nodes);
		clones.keySet().forEach( node -> {
			node.getConnections().forEach( connection -> {
				clones.get(node).getConnections().add(
						new Connection(clones.get(connection.getTarget()), connection.getWeight()));
			});
		});
		return new Network(name, new ArrayList<>(clones.values()));
	}

	private Map<Node, Node> cloneIsolated(List<Node> nodes){
		Map<Node, Node> clones = new HashMap<>();
		nodes.forEach(node -> {
			Node clone = node.clone();
			clone.getConnections().clear();
			clones.put(node, clone);
		});
		return clones;
	}

	public void addNode(Node node) { 
		nodes.add(node);
	}
	
	public String getName() { return this.name; }

	public void setName(String name) { this.name = name; }
	
	public List<Node> getNodes(){ return this.nodes; }

}
