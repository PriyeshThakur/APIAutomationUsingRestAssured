package com.APIAutomation;

import java.nio.file.Files;
import java.nio.file.Paths;

public class GenericHelpers {

	/**
	 * @param fileName  from local drive  
	 * @return file as string
	 * turns txt file into a string  
	 */
 
 public static String readFileAsString( String fileName) throws Exception
 	{
	  String data = "";
	  data = new String(Files.readAllBytes(Paths.get(fileName)));
	  return data;
	  
 	}
}
