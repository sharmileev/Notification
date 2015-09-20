package com.amz.hack.fileload.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.amazon.hack.util.*;
import com.amz.hack.core.DataProcessImpl;
import com.amz.hack.fileload.DataProcess;
import com.amz.hack.fileload.FileLoader;


public class CsvReader {
	public static void main(String[] args) {		
		BufferedReader br = null;
		File file = null;
		FileUtils fileutil = new FileUtils();
		List fileList = new ArrayList();
		DataProcess dp = new DataProcessImpl();
		FileLoader fl = new FileProcess();
		try{		
		String dir= "c:\\data1";	
		fileList = fl.getLocalCopy(dir);
		//fileList = fileutil.loadFileDetails(dir);	
		HashMap<String,String> hm = new HashMap<String, String>();
		List<String> ls = new ArrayList<String>();
		if(fileList.size()>0){
		for (int i=0; i<fileList.size(); i++){			
			br = new BufferedReader(new FileReader(fileList.get(i).toString()));			
			String line;
			String splitBy = ",";
			int count =0;
			while((line = br.readLine())!=null){	
				System.out.println("line  : "+line);
				String[] b =  line.split(splitBy);	
				 if(count>0){
					 //hm.put(b[0], b[1]);
					 ls.add(b[0]);
				 //dp.loadFileData(b);
					 System.out.println("Item title"+b[1]);
				 }
				 count++;
			}			
		}
        br.close();
		}
		System.out.println("Executed successfully");
		}catch (Exception e){
			System.out.println("Exception while reading csv file "+e);
			e.printStackTrace();
		}
       
	} 
}
