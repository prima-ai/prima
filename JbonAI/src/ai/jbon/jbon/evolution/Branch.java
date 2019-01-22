package ai.jbon.jbon.evolution;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.sun.corba.se.impl.encoding.OSFCodeSetRegistry.Entry;

import ai.jbon.jbon.Network;
import ai.jbon.jbon.data.Registry;
import ai.jbon.jbon.functions.Function;
import ai.jbon.jbon.nodes.Connection;
import ai.jbon.jbon.nodes.Node;

public class Branch {
	
	private String name;
	private int generation = 0;
	private Network network;
	private Map<Integer, Network> generations;
	private Random random;
	private Function defaultFunction = Registry.getFunction("identity");
	
	public Branch(String name) {
		this.name = name;
		generations = new HashMap<>();
		initCurrentGeneration();
	}
	
	public Branch(String name, Map<Integer, Network> generations) {
		this.generations = generations;
		initCurrentGeneration();
	}
	
	private void initCurrentGeneration() {
		if(generations.isEmpty()) {
			evolve();
		} else {
			generation = Collections.max(generations.keySet());
			network = generations.get(generation);
		}
	}
	
	private void evolve() {
		if(network == null || generation <= 0) {
			initStartpoint();
		} else {
			//mutate();
			generation ++;
		}
		generations.put(generation, network);
	}
	
	private void mutate(float error) {
		
	}
	
	private void grow(float error) {
		List<Node> nodes = network.getNodes();
		Node node = nodes.get(random.nextInt(nodes.size()));
		Node target = new Node(defaultFunction);
	}
	
	private void adjust(float error) {
		List<Node> nodes = network.getNodes();
		Node node = nodes.get(random.nextInt(nodes.size()));
		List<Connection> connections = node.getConnections();
		Connection connection = connections.get(random.nextInt(connections.size()));
		connection.adjustWeight(random.nextFloat() / 2 * error);
	}
	
	private void shrink() {
		List<Node> nodes = network.getNodes().stream()
				.filter(node -> !node.getConnections().isEmpty())
				.collect(Collectors.toList());
		List<Connection> connections = nodes.get(random.nextInt(nodes.size())).getConnections();
		connections.remove(connections.get(random.nextInt(connections.size())));
	}
	
	private void initStartpoint() {
		generation = 1;
		network = new Network("Generation1");
	}
	
	public Map<Integer, Network> getGenerations() {
		return generations;
	}
}
