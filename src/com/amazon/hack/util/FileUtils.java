package com.amazon.hack.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;

import com.amz.hack.database.ConnectionClass;
import com.amz.hack.fileload.FileLoader;

public class FileUtils implements FileLoader{
	private Connection con = null;
	private PreparedStatement preStmt =null;
	private ResultSet rs =null;
	
	private File[] getLatestFile(File[] files) {		
		if (files == null || files.length == 0) {
			return null;
		}		
		File lastModifiedFile = files[0];
		File[] lastModifiedList = null;
		int j = 0;		
		for (int i = 0; i < files.length; i++) {
			System.out.println("inside "+files[i].lastModified());
			if (lastModifiedFile.lastModified() > files[i].lastModified()) {				
				lastModifiedList[j] = files[i];				
				j++;
			}			
		}
		System.out.println("lastModifiedFile  :"+lastModifiedFile);
		return lastModifiedList;
	}

	@Override
	public List getLocalCopy(String aFile) {
		return null;
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public List<String> loadFileDetails(String filepath) {
		
		File directory = new File(filepath);	     
	    File[] fileList = directory.listFiles();
	    List finalFileList = null;
	    List<String> finalList = new ArrayList<String>();
	    URL path = null;
	    File[] files = getLatestFile(fileList);
	    System.out.println("files :"+fileList);
	    for (File file : fileList) {
	        if (file.isFile()) {	        	
	          	//path = ClassLoader.getSystemResource("c:\\data1\\"+file.getName());
	           	//System.out.println("file.getName() :"+path);
	        	//if(path!=null) {
	        	String csvFile = file.getName().split(".")[1].toString();
	        	if (csvFile.equalsIgnoreCase("csv")){
	        		finalList.add(file.getName());
	        	}
	        		//System.out.println("path.getFile :"+path.getFile());
	        	     //The file was not found, insert error handling here
	        	//}	        	
	        } 
	    }	  
	    return finalList;
		
		
	}
	
	private Map getExistingFileDetails(){
		con = ConnectionClass.getInstance().conn();
		Map<String,String> exFileDetails = new HashMap<String, String>();
		try{
			preStmt = con.prepareStatement("SELECT FILE_NAME,DATE_OF_MODIFIED from FILE_UPLOAD_DETAILS");
			rs = preStmt.executeQuery();
			while(rs.next()){
				exFileDetails.put(rs.getString("FILE_NAME"),rs.getString("DATE_OF_MODIFIED"));
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return null;
		
	}

	
		 
}
