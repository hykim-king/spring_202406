package com.pcwk.ehr;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserDaoDoSave implements StatementStrategy {

	final Logger log = LogManager.getLogger(getClass());

	private User user;

	public UserDaoDoSave() {

	}

	public UserDaoDoSave(User user) {
		this.user = user;
	}

	@Override
	public PreparedStatement makePreparedStatement(Connection conn) throws SQLException {
		StringBuilder sb = new StringBuilder(200);
		sb.append(" INSERT INTO member VALUES (?, ?, ?, ?) \n");

		log.debug("2.sql:\n" + sb.toString());

		PreparedStatement pstmt = conn.prepareStatement(sb.toString());
		pstmt.setString(1, user.getUserId());
		pstmt.setString(2, user.getName());
		pstmt.setString(3, user.getPassword());
		pstmt.setString(4, user.getBirthday());

		return pstmt;
	}

}
