package ai.jbon.jbon.data.dto;

import java.util.List;

public class NetworkDTO {

	private final List<NodeDTO> nodes;
	private final List<ConnectionDTO> connections;
	
	public NetworkDTO(List<NodeDTO> nodes, List<ConnectionDTO> connections) {
		this.nodes = nodes;
		this.connections = connections;
	}
	
	public List<NodeDTO> getNodes(){
		return this.nodes;
	}
	
	public List<ConnectionDTO> getConnections(){
		return this.connections;
	}
}
