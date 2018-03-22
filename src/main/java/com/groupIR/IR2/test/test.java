package com.groupIR.IR2.test;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.groupIR.IR2.indexer.IndexFile;


import com.groupIR.IR2.parser.*;
public class test {
	
	public static void main(String args[]) throws SAXException, ParserConfigurationException {
	      
		IndexFile.callIndex("outputFolderFT/", "INDEX_PATH");
		
//		String s1= "This is me & you who are the best friends forever";
//		if(s1.contains("&")) {
//			String s2 = s1.replace("&", "&amp;");
//			System.out.println(s2);
		}
		
	}

	
	
	

