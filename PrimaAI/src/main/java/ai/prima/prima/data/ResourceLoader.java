package ai.prima.prima.data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ai.prima.prima.Network;
import ai.prima.prima.PrimaAI;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class ResourceLoader {

	private ObjectMapper mapper;

	public ResourceLoader() {
		mapper = new ObjectMapper();
		mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_CONCRETE_AND_ARRAYS);
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
	}

	public Network loadNetwork(File file) throws IOException {
		return mapper.readValue(file, Network.class);
	}

	public void storeNetwork(final Network network) throws IOException {
		File file = new File(PrimaAI.NETWORK_DIR + "//" + network.getName() + "." + PrimaAI.NETWORK_EXTENSION);
		mapper.writeValue(file, network);
	}

	public List<File> getAllFilesFromDir(File directory){
		List<File> files = new ArrayList<>();
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