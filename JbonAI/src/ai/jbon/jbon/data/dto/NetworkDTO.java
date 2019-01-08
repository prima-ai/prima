package ai.jbon.jbon.data.dto;

import java.util.List;

public class NetworkDTO {

	private final String name;
	private final List<NodeDTO> nodes;
	private final List<ConnectionDTO> connections;
	
	public NetworkDTO(String name, List<NodeDTO> nodes, List<ConnectionDTO> connections) {
		this.name = name;
		this.nodes = nodes;
		this.connections = connections;
	}
	
	public String getName() {
		return this.name;
	}
	
	public List<NodeDTO> getNodes(){
		return this.nodes;
	}
	
	public List<ConnectionDTO> getConnections(){
		return this.connections;
	}
}
