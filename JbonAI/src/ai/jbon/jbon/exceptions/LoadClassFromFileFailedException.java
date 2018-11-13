package ai.jbon.jbon.exceptions;

import java.io.File;

public class LoadClassFromFileFailedException extends Exception{

	public LoadClassFromFileFailedException(File file) {
		super("Failed to load Class from File " + file.getAbsolutePath());
	}
}
