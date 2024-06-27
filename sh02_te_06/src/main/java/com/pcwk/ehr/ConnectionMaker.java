package com.pcwk.ehr;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionMaker {	
	public Connection makeConnection() throws ClassNotFoundException, SQLException;
}
