package ai.jbon.jbon.data;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ai.jbon.jbon.Connection;
import ai.jbon.jbon.Log;
import ai.jbon.jbon.Network;
import ai.jbon.jbon.data.dto.ConnectionDTO;
import ai.jbon.jbon.data.dto.NetworkDTO;
import ai.jbon.jbon.data.dto.NodeDTO;
import ai.jbon.jbon.functions.Function;
import ai.jbon.jbon.functions.IdentityFunction;
import ai.jbon.jbon.nodes.Node;

public class NetworkBuilder {

	public Network assemble(NetworkDTO dto) {
		Map<NodeDTO, Node> nodeRegistry = generateNodes(dto.getNodes());
		Map<Integer, Connection> connections = generateConnections(dto.getConnections(), nodeRegistry);
		List<Node> nodes = connectNodes(nodeRegistry, connections);
		return new Network(nodes);
	}

	public NetworkDTO disassemble(final Network network) {
		List<Node> nodes = network.getNodes();
		Map<Node, Integer> nodeRegistry = identifyNodes(nodes);
		Map<Connection, Integer> connectionRegistry = identifyConnections(nodes);
		List<ConnectionDTO> connectionDTOs = generateConnectionDTOs(nodeRegistry, connectionRegistry);
		List<NodeDTO> nodeDTOs = generateNodeDTOs(nodeRegistry, connectionRegistry);
		return new NetworkDTO(nodeDTOs, connectionDTOs);
	}

	public List<Node> connectNodes(final Map<NodeDTO, Node> nodeRegistry, final Map<Integer, Connection> connections){
		nodeRegistry.keySet().forEach(dto -> {
			dto.getConnections().forEach(id -> {
				nodeRegistry.get(dto).addConnection(connections.get(id));
			});
		});
		return new ArrayList<Node>(nodeRegistry.values());
	}

	private Node findConnectionTarget(ConnectionDTO dto, Map<NodeDTO, Node> nodeRegistry) {
		for(NodeDTO nodeDTO : nodeRegistry.keySet()) {
			if(nodeDTO.getId() == dto.getId()) {
				return nodeRegistry.get(nodeDTO);
			}
		}
		return null;
	}
	
	private Map<Integer, Connection> generateConnections(final List<ConnectionDTO> dtos,
			final Map<NodeDTO, Node> nodeRegistry) {
		Map<Integer, Connection> connections = new HashMap<Integer, Connection>();
		dtos.forEach(dto -> {
			Node target = findConnectionTarget(dto, nodeRegistry);
			Connection connection = new Connection(target, dto.getWeight());
			connections.put(dto.getId(), connection);
		});
		return connections;
	}

	private Map<NodeDTO, Node> generateNodes(final List<NodeDTO> dtos) {
		Map<NodeDTO, Node> nodes = new HashMap<NodeDTO, Node>();
		dtos.forEach(dto -> {
			Constructor<?> constructor;
			try {
				constructor = Class.forName(dto.getName()).getDeclaredConstructor(Function.class);
				Node node = (Node) constructor.newInstance(new IdentityFunction());
				nodes.put(dto, node);
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		});
		return nodes;
	}

	private List<ConnectionDTO> generateConnectionDTOs(final Map<Node, Integer> nodeRegistry,
			final Map<Connection, Integer> connectionRegistry) {
		List<ConnectionDTO> dtos = new ArrayList<ConnectionDTO>();
		connectionRegistry.keySet().forEach(connection -> {
			int id = connectionRegistry.get(connection);
			int targetId = nodeRegistry.get(connection.getTarget());
			ConnectionDTO dto = new ConnectionDTO(id, targetId, connection.getWeight());
			dtos.add(dto);
		});
		return dtos;
	}

	private List<NodeDTO> generateNodeDTOs(final Map<Node, Integer> nodeRegistry,
			final Map<Connection, Integer> connectionRegistry) {
		List<NodeDTO> dtos = new ArrayList<NodeDTO>();
		nodeRegistry.keySet().forEach(node -> {
			List<Integer> connectionIds = parseConnectionList(connectionRegistry, node.getConnections());
			int id = nodeRegistry.get(node);
			NodeDTO dto = new NodeDTO(id, node.getClass().getName(), connectionIds);
			dtos.add(dto);
		});
		return dtos;
	}

	private List<Integer> parseConnectionList(Map<Connection, Integer> connectionRegistry,
			List<Connection> connections) {
		List<Integer> connectionIds = new ArrayList<Integer>();
		connections.forEach(connection -> {
			connectionIds.add(connectionRegistry.get(connection));
		});
		return connectionIds;
	}

	private Map<Node, Integer> identifyNodes(final List<Node> nodes) {
		Map<Node, Integer> registry = new HashMap<Node, Integer>();
		int id = 0;
		for (Node node : nodes) {
			registry.put(node, id);
			id++;
		}
		return registry;
	}

	private Map<Connection, Integer> identifyConnections(final List<Node> nodes) {
		Map<Connection, Integer> registry = new HashMap<Connection, Integer>();
		int id = 0;
		for(Node node : nodes) {
			for (Connection connection : node.getConnections()) {
				registry.put(connection, id);
				id++;
			}
		}
		return registry;
	}
}
