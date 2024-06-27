package com.pcwk.ehr;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DUserDao extends UserDao {

	@Override
	public Connection getConnection() throws ClassNotFoundException, SQLException {
		final String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";
		final String DB_USER= "pcwk";
		final String DB_PASSWORD= "GOOD";
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
		System.out.println("1.conn:"+conn);
		
		return conn;
	}

}
