package ai.jbon.jbon.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import ai.jbon.jbon.Network;
import ai.jbon.jbon.data.dto.ConnectionDTO;
import ai.jbon.jbon.data.dto.NetworkDTO;
import ai.jbon.jbon.data.dto.NodeDTO;

public class ResourceLoader {
	
	private NetworkBuilder networkBuilder;
	private JSONParser json;
	
	public ResourceLoader() {
		this.networkBuilder = new NetworkBuilder();
		json = new JSONParser();
	}

	public Network loadNetwork(final File file) throws IOException, ParseException {
		return networkBuilder.assemble(file, loadNetworkDTO(file));
	}
	
	public NetworkDTO loadNetworkDTO(final File file) throws IOException, ParseException {
		String content = readAll(file);
		JSONObject network = (JSONObject) this.json.parse(content);
		String name = (String) network.get("name");
		JSONArray nodes = (JSONArray) network.get("nodes");
		JSONArray connections = (JSONArray) network.get("connections");
		return new NetworkDTO(name, parseNodes(nodes), parseConnections(connections));
	}
	
	private List<NodeDTO> parseNodes(JSONArray json){
		List<NodeDTO> dtos = new ArrayList<NodeDTO>();
		Iterator<JSONObject> iterator = json.iterator();
		iterator.forEachRemaining(obj -> {
			NodeDTO dto = new NodeDTO((int) (long) obj.get("id"), (String) obj.get("name"),
					(String) obj.get("function"), parseConnectionIdList(((JSONArray) obj.get("connections"))));
			dtos.add(dto);
		});
		return dtos;
	}
	
	private List<Integer> parseConnectionIdList(JSONArray json){
		List<Integer> list = new ArrayList<Integer>();
		json.parallelStream().forEach(obj -> {
			list.add((int)(long) obj);
		});
		return list;
	}
	
	private List<ConnectionDTO> parseConnections(JSONArray json){
		List<ConnectionDTO> dtos = new ArrayList<ConnectionDTO>();
		Iterator<JSONObject> iterator = json.iterator();
		iterator.forEachRemaining(obj -> {
			ConnectionDTO dto = new ConnectionDTO((int) (long) obj.get("id"), (int) (long) obj.get("targetId"), 
					(float) (double) obj.get("weight"));
			dtos.add(dto);
		});
		return dtos;
	}
	
	public void storeNetwork(final Network network) throws FileNotFoundException {
		NetworkDTO dto = networkBuilder.disassemble(network);
		JSONObject json = new JSONObject();
		JSONArray array = new JSONArray();
		json.put("name", dto.getName());
		json.put("nodes", generateNodeJSONArray(dto.getNodes()));
		json.put("connections", generateConnectionJSONArray(dto.getConnections()));
		writeAll(json.toJSONString(), network.getFile());
	}
	
	private JSONArray generateNodeJSONArray(List<NodeDTO> dtos) {
		JSONArray array = new JSONArray();
		dtos.forEach(dto ->  {
			JSONObject obj = new JSONObject();
			obj.put("id", dto.getId());
			obj.put("name", dto.getName());
			obj.put("function", dto.getFunction());
			obj.put("connections", dto.getConnections());
			array.add(obj);
		});
		return array;
	}
	
	private JSONArray generateConnectionJSONArray(List<ConnectionDTO> dtos) {
		JSONArray array = new JSONArray();
		dtos.forEach(dto -> {
			JSONObject obj = new JSONObject();
			obj.put("id", dto.getId());
			obj.put("targetId", dto.getTargetId());
			obj.put("weight", dto.getWeight());
			array.add(obj);
		});
		return array;
	}
	
	private String readAll(File file) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		StringBuilder content = new StringBuilder();
		String line;
		while((line = reader.readLine()) != null) {
			content.append(line + "\n");
		}
		reader.close();
		return content.toString();
	}
	
	private void writeAll(final String string, final File file) throws FileNotFoundException {
		PrintWriter writer = new PrintWriter(file);
		writer.write(string);
		writer.close();
	}
	
	private Properties loadProperties(final File file) throws IOException, FileNotFoundException {
		Reader reader = new FileReader(file);
		Properties properties = new Properties();
		properties.load(reader);
		return properties;
	}
	
	private void storeProperties(final Properties properties, final File file, final String comment) throws IOException {
		Writer writer = new PrintWriter(file);
		properties.store(writer, comment);
		writer.close();
	}
	
	public List<File> getAllFilesFromDir(File directory){
		List<File> files = new ArrayList<File>();
		for(File file : directory.listFiles()) {
			if(file.isDirectory()) {
				files.addAll(getAllFilesFromDir(file));
			}else {
				files.add(file);
			}
		}
		return files;
	}
	
	public List<File> loadPluginFiles(File pluginDir){
		List<File> files = getAllFilesFromDir(pluginDir);
		files.stream()
			.filter(file -> file.getPath().endsWith(".jar"))
			.collect(Collectors.toList());
		return files;
	}
}