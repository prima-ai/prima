package ai.jbon.jbon.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * 
 * The FileUtils class provides static methods to manage files
 * 
 * Saved files will get Encrypted and decrypted when importing them
 * 
 * @author jostsi
 *
 */
public class FileUtils {

	private static final String KEY = "DE1nLi5IscHDeK3Y-M33yBe_tHeM.AnBO1d stud_daGAe";
	
	public static final int DECRYPTED = 0;
	public static final int ENCRYPTED = 1;
	
	public static void main(String[] args){
		System.out.println(encrypt("Hallo, das ist ein test :^)"));
	}
	
	/**
	 * 
	 * @param path - path to the desired file to be loaded
	 * @param dataState - in which state you want to receive the data
	 * @return returns the file's content in the defined format
	 * @throws FileNotFoundException
	 */
	public static String loadFile(String path, int format) throws IOException{
		
		String content = null;
		
		File file = new File(path);
		
		FileReader fr = new FileReader(file);
		BufferedReader reader = new BufferedReader(fr);
		
		StringBuilder builder = new StringBuilder();
		
		String line;
		
		try{
			
			while((line = reader.readLine()) != null){
			
				builder.append(line);
			}
			
			content = builder.toString();
			
		}catch(IOException e){
			throw e;
		}
		
		return content;
	}
	
	/**
	 * 
	 * encrypts the given data into unreadable characters
	 * 
	 * @param data - the data to encrypt
	 * @return returns the encrypted data
	 */
	private static String encrypt(String data){
		
		StringBuilder builder = new StringBuilder();
		
		int keyPointer = 0;
		
		for(int i=0 ; i < data.length() ; i++){
			
			char c = data.charAt(i);
			
			c *= KEY.charAt(keyPointer);
			
			builder.append(c);
			
			keyPointer ++;
			
			if(keyPointer >= KEY.length()){
				keyPointer = 0;
			}
		}
		
		return builder.toString();
	}
	
	/**
	 * 
	 * decrypts the given data into the initial text
	 * 
	 * @param data - the data to decrypt
	 * @return returns  the decrypted data
	 */
	private static String decrypt(String data){
		
		StringBuilder builder = new StringBuilder();
		
		int keyPointer = 0;
		
		for(int i=0 ; i < data.length() ; i++){
			
			char c = data.charAt(i);
			
			c /= KEY.charAt(keyPointer);
			
			builder.append(c);
			
			keyPointer ++;
			
			if(keyPointer >= KEY.length()){
				keyPointer = 0;
			}
		}
		
		return builder.toString();
	}
}
