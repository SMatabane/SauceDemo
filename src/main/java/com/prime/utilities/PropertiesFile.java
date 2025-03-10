package com.prime.utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;



public class PropertiesFile {
	
	private static final Logger logs=Logger.getLogger(PropertiesFile.class);
	
	private Properties property;
	
	
	public PropertiesFile() {
		try {
			FileInputStream fs = new FileInputStream(".\\configs\\config.properties");
			property=new Properties();
			property.load(fs);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			logs.error("File not found", e);
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logs.error("Error occured", e);
			e.printStackTrace();
		}
		
	}
	
	public String getProperties(String p) {
		return property.getProperty(p);
		
	}
	

}
