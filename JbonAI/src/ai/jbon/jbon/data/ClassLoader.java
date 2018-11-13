package ai.jbon.jbon.data;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import ai.jbon.jbon.exceptions.LoadClassFromFileFailedException;

public class ClassLoader {
	
	public List<Class<?>> loadClassesFromJar(File file) throws LoadClassFromFileFailedException{
		try {
		JarFile jar = new JarFile(file);
		URL[] urls = {new URL("jar:file:" + file.getAbsolutePath() + "!/")}; 
		URLClassLoader loader = new URLClassLoader(urls);
		return loadAllJarClasses(jar.entries(), loader);
		} catch (Exception e) {
			throw new LoadClassFromFileFailedException(file);
		}
	}
	
	private List<Class<?>> loadAllJarClasses(Enumeration<JarEntry> entries, URLClassLoader loader) throws ClassNotFoundException {
		List<Class<?>> classes = new ArrayList<Class<?>>();
		while (entries.hasMoreElements()) {
			JarEntry jarEntry = (JarEntry) entries.nextElement();
			if(!jarEntry.isDirectory() && jarEntry.getName().endsWith(".class")) {
				classes.add(loader.loadClass(parseClassName(jarEntry.getName())));
			}
		}
		return classes;
	}
	
	private String parseClassName(String path) {
		path = path.replaceAll("/", ".");
		return path.substring(0, path.length() - 6);
	}
}
