package ai.jbon.jbon.data.dto;

import java.util.List;

public class NodeDTO {

	private long id;
	
	private List<Long> connections;
	
	public NodeDTO(long id, List<Long> connections) {
		this.id = id;
		this.connections = connections;
	}
	
	public long getId() {
		return this.id;
	}
	
	public List<Long> getConnections(){
		return this.connections;
	}
}
