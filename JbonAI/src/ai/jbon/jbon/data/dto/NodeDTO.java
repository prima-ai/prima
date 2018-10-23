package ai.jbon.jbon.data.dto;

import java.util.List;

public class NodeDTO {

	private final int id;
	private final String name;
	private final List<Integer> connections;
	
	public NodeDTO(final int id, final String name, final List<Integer> connections) {
		this.id = id;
		this.name = name;
		this.connections = connections;
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public List<Integer> getConnections(){
		return this.connections;
	}
}
