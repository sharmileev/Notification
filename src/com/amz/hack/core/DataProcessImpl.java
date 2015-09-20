package com.amz.hack.core;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.amz.hack.database.ConnectionClass;
import com.amz.hack.fileload.DataProcess;

public class DataProcessImpl implements DataProcess {

	Connection con =null;
	PreparedStatement preStmt =null;
	@Override
	public void validateData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadFileData(String[] Data) {
		// TODO Auto-generated method stub
		try{
			con = ConnectionClass.getInstance().conn();
			preStmt = con.prepareStatement("INSERT INTO ITEM_DETAILS(ITEM_CODE,ITEM_TITLE,"+
					"ITEM_AUTHORS,ITEM_RELEASE_DATE,ITEM_LISTPRICE,ITEM_PUBLISHER)"+
					"VALUES(?,?,?,?,?,?)");
			preStmt.setString(1, Data[0]);
			preStmt.setString(2, Data[1]);
			preStmt.setString(3, Data[2]);
			preStmt.setString(4, Data[3]);
			preStmt.setDouble(5, Double.valueOf(Data[4]));
			preStmt.setString(6, Data[5]);
			preStmt.executeUpdate();
		}catch(Exception ee){
			ee.printStackTrace();
		}
	}

}
