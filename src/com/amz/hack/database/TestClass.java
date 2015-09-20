package com.amz.hack.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestClass {

	/**
	 * @param args
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		Connection con = ConnectionClass.getInstance().conn();
		PreparedStatement st = con.prepareStatement("select * from item_details");
		ResultSet rs = st.executeQuery();
		while(rs.next()){
			System.out.println(rs.getString(2));
		}
	}

}
