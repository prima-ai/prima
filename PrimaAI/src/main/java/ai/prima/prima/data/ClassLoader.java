package ai.prima.prima.data;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import ai.prima.prima.exceptions.LoadClassFromFileFailedException;

public class ClassLoader {
	
	public List<Class<?>> loadClassesFromJar(File file) {
		List<Class<?>> classes = new ArrayList<>();
		try {
			JarFile jar = new JarFile(file);
			URLClassLoader loader = createClassLoader(file);
			classes.addAll(loadAllJarClasses(jar, loader));
		} catch (Exception e) {
			new LoadClassFromFileFailedException(file).printStackTrace();
		}
		return classes;
	}
	
	private URLClassLoader createClassLoader(File file) throws MalformedURLException {
		URL[] urls = {new URL("jar:file:" + file.getAbsolutePath() + "!/")}; 
		return new URLClassLoader(urls);
	}
	
	private List<Class<?>> loadAllJarClasses(JarFile jar, URLClassLoader loader) throws ClassNotFoundException, IOException {
		List<Class<?>> classes = new ArrayList<Class<?>>();
		Enumeration<JarEntry> entries = jar.entries();
		while (entries.hasMoreElements()) {
			JarEntry jarEntry = (JarEntry) entries.nextElement();
			if(!jarEntry.isDirectory() && jarEntry.getName().endsWith(".class")) {
				classes.add(loader.loadClass(parseClassName(jarEntry.getName())));
			}
		}
		jar.close();
		return classes;
	}
	
	private String parseClassName(String path) {
		path = path.replaceAll("/", ".");
		return path.substring(0, path.length() - 6);
	}
}
