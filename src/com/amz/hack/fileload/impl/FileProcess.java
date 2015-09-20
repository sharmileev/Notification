package com.amz.hack.fileload.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.amz.hack.database.ConnectionClass;
import com.amz.hack.fileload.FileLoader;

public class FileProcess implements FileLoader {
	Connection con = null;
	PreparedStatement preStmt =null;
	ResultSet rs =null;
	private final String des_path="D:\\Temp_File"; 
	@Override
	public List<String> getLocalCopy(String filepath) {
		// TODO Auto-generated method stub
		FileProcess fp = new FileProcess();
		List<File> fileList = fp.loadFileDetails(filepath);
		List<String> fileNameList = new ArrayList<String>();
		try{
		for(File afile:fileList){
			String desfilestr = des_path+"\\"+afile.getName()+"_"+afile.lastModified()+".csv";
			fp.getFileDetails(afile,new File(desfilestr));
			fileNameList.add(desfilestr);
		}
		}catch(Exception ee){
			ee.printStackTrace();
		}
		return fileNameList;
	}
	

	private void getFileDetails(File source, File Dest) throws IOException {
		// TODO Auto-generated method stub
		InputStream is = null;
	    OutputStream os = null;
	    try {
	        is = new FileInputStream(source);
	        os = new FileOutputStream(Dest);
	        byte[] buffer = new byte[1024];
	        int length;
	        while ((length = is.read(buffer)) > 0) {
	            os.write(buffer, 0, length);
	        }
	    } finally {
	        is.close();
	        os.close();
	    }
	}

	@Override
	public List<File> loadFileDetails(String filepath) {
		File directory = new File(filepath);	     
	    File[] fileList = directory.listFiles();
	    List<File> finalList = new ArrayList<File>();
	    FileProcess fp = new FileProcess();
	    for (File file : fileList) {
	        if (file.isFile()) {
	        		String str = fp.verifyFileDetails(file);
	        		int i = 0;
	        		if(str==null){
	        			i=fp.insertFileInfo(file);	        			
	        		}else{	        			
	        			i=fp.updateFileInfo(file);
	        		}
	        		if(i!=0){
	        			finalList.add(file);
	        		}	          			        		        	
	        } 
	    }	  
	    return finalList;
	}
	
	private int insertFileInfo(File aFile){
		int count =0;
		try{
			con = ConnectionClass.getInstance().conn();
			preStmt = con.prepareStatement("INSERT INTO FILE_UPLOAD_DETAILS(FILE_NAME,DATE_OF_MODIFIED,"+
					"NO_OF_TIME_MODIFIED,FILE_PATH,FILE_STATUS,FILE_MODIFIED_VALUE)"+
					"VALUES(?,?,?,?,?,?)");
			preStmt.setString(1, aFile.getName());
			preStmt.setDate(2, new Date(aFile.lastModified()));
			preStmt.setInt(3, 1);
			preStmt.setString(4, aFile.getPath());
			preStmt.setString(5, "new");
			preStmt.setLong(6, aFile.lastModified());
			count = preStmt.executeUpdate();
		}catch(Exception ee){
			ee.printStackTrace();
		}
		return count;
		
	}
	private int updateFileInfo(File aFile){
		int count=0;
		try{
			con = ConnectionClass.getInstance().conn();
			preStmt = con.prepareStatement("UPDATE FILE_UPLOAD_DETAILS SET DATE_OF_MODIFIED=?"+
					" WHERE FILE_NAME=? AND FILE_STATUS='Done'");
			
			preStmt.setDate(1, new Date(aFile.lastModified()));
			preStmt.setString(2, aFile.getName());
			count = preStmt.executeUpdate();
		}catch(Exception ee){
			ee.printStackTrace();
		}
		return count;
	}
	private String verifyFileDetails(File aFile){
		String file_details= null;
		con = ConnectionClass.getInstance().conn();
		try{
			preStmt = con.prepareStatement("SELECT FILE_NAME FROM FILE_UPLOAD_DETAILS"+
					" WHERE FILE_NAME=? and FILE_MODIFIED_VALUE=?");
			preStmt.setString(1, aFile.getName());
			preStmt.setLong(2,aFile.lastModified());
			rs = preStmt.executeQuery();
			if(rs.next()){
				file_details = rs.getString("FILE_NAME");
			}
		}catch(Exception ee){
			ee.printStackTrace();
		}			
		return file_details;
	
	}

	

}
