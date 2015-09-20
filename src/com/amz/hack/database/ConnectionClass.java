package com.amz.hack.database;

import java.sql.Connection;
import java.sql.DriverManager;

import com.amz.hack.common.ConstantsClass;

public class ConnectionClass {
	private static ConnectionClass concls =null;
	private ConnectionClass(){		
	}
	public static ConnectionClass getInstance(){
		if(concls==null){
			concls = new ConnectionClass();
		}
		return concls;
	}
	
	public Connection conn(){
		Connection con =null;
		try{
			Class.forName(ConstantsClass.DRIVER_NAME);			
			con = DriverManager.getConnection(ConstantsClass.CONNECTION_URL, ConstantsClass.DB_USERNAME, ConstantsClass.DB_PWD);
		}catch(Exception e){
			e.printStackTrace();
		}	
		return con;
	}
}
