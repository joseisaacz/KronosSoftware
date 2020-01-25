package com.kronos.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ActService {
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	public boolean isActInDB(Date date) {
		
		try {
			Connection connection = jdbcTemplate.getDataSource().getConnection();
			CallableStatement statement = connection.prepareCall("{call isActInDB(?) }");
			statement.setDate(1, new java.sql.Date(date.getTime()));
			
			// statement.registerOutParameter(1, new );
			ResultSet rs = statement.executeQuery();
			boolean isInDb=rs.next();
			statement.close();
			return isInDb;
		}
		catch(Exception e) {
			
		}
		return false;
		
	}
	
	
	public boolean insertAct(Date date) {
		try {
			Connection connection = jdbcTemplate.getDataSource().getConnection();
			CallableStatement statement = connection.prepareCall("{call  insertTempAct(?) }");
			statement.setDate(1, new java.sql.Date(date.getTime()));
			
			// statement.registerOutParameter(1, new );
			int aux = statement.executeUpdate();
			statement.close();
			
			System.out.println(aux);
			return true;
			
		}
		catch(Exception e) {
			
		}
		return false;
	}
	
	
}
