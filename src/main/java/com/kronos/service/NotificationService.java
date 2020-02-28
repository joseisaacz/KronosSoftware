package com.kronos.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kronos.model.Accord;
import com.kronos.model.User;

@Repository
public class NotificationService {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void insertNotification(Accord acc, String us) throws Exception {

			
			Connection connection = jdbcTemplate.getDataSource().getConnection();
			CallableStatement statement = connection.prepareCall("{call  insertNotification(?, ?) }");
			statement.setString(1, acc.getAccNumber());
			statement.setString(2, us);
			
			
			// statement.registerOutParameter(1, new );
			int aux = statement.executeUpdate();
			statement.close();
			connection.close();


	}
	
	
	public boolean isInNotification(Accord acc, String us) throws Exception {
		Connection connection = jdbcTemplate.getDataSource().getConnection();
		CallableStatement statement = connection.prepareCall("{call  isInNotification(?, ?) }");
		statement.setString(1, acc.getAccNumber());
		statement.setString(2, us);
		
		
		// statement.registerOutParameter(1, new );
		ResultSet rs = statement.executeQuery();
		
		statement.close();
		connection.close();
		return rs.next();
	}

}
