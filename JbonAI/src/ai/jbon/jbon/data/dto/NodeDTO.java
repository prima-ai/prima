package ai.jbon.jbon.data.dto;

import java.util.List;

public class NodeDTO {

	private final int id;
	private final String name;
	private final String function;
	private final List<Integer> connections;
	
	public NodeDTO(final int id, final String name, final String function, final List<Integer> connections) {
		this.id = id;
		this.name = name;
		this.function = function;
		this.connections = connections;
	}
	
	public int getId() { return id;	}

	public String getName() { return name; }
	
	public String getFunction() { return function; }
	
	public List<Integer> getConnections(){ return connections; }
}
