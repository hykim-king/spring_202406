package com.pcwk.ehr;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDaoDeleteAll extends UserDao {

	@Override
	public PreparedStatement makeStatement(Connection conn) throws SQLException {
		PreparedStatement pstmt = null;
		
		StringBuilder sb = new StringBuilder(200);
		sb.append(" DELETE FROM member \n");			
		log.debug("2.sql:\n" + sb.toString());			
		
		pstmt = conn.prepareStatement(sb.toString());
		log.debug("4.PreparedStatement:" + pstmt);
		
		return pstmt;
	}

}
