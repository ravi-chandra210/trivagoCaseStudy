package com.trivago.pages;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class fileReader {
	private Properties prop;
	private final String propFilePath = "configs//config.properties";
	public BufferedReader reader;
	
	public fileReader() {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(propFilePath));
			prop = new Properties();
			prop.load(reader);
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getFilePath() {
		String filepath = prop.getProperty("FilePath");
		if(filepath!=null) return filepath;
		else throw new RuntimeException("Filepath is not given in config.properties file.");
	}
	
	public String getBrowser() {
		String filepath = prop.getProperty("Browser");
		if(filepath!=null) return filepath;
		else throw new RuntimeException("Browser is not given in config.properties file.");
	}
	
	public String getURL() {
		String filepath = prop.getProperty("URL");
		if(filepath!=null) return filepath;
		else throw new RuntimeException("URL is not given in config.properties file.");
	}

}
