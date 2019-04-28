package ai.prima.prima.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ai.prima.prima.Network;

public class ResourceLoader {

	public ResourceLoader() {
	}

	public Network loadNetwork(final File file) throws IOException {
		return null;
	}

	
	public void storeNetwork(final Network network) throws FileNotFoundException {

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