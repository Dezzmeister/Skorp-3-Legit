package com.dezzy.skorp3legit.gl.fileutil;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.dezzy.skorp3legit.gl.ShaderProgram;

public class FileUtils {
	public static String readFromFile(String path) {
		StringBuilder source = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(ShaderProgram.class.getClassLoader().getResourceAsStream(path)));
			
			String line;
			
			while ((line = reader.readLine()) != null) {
				source.append(line).append("\n");
			}
			
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error loading shader at " + path);
		}
		
		return source.toString();
	}
}
