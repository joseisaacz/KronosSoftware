package com.kronos.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kronos.model.TempUser;

@Repository
public class TempUserService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public Optional<TempUser> findByEmail(String email) {

		try {

			Connection connection = jdbcTemplate.getDataSource().getConnection();
			CallableStatement statement = connection.prepareCall("{call searchTempUser(?) }");
			statement.setString(1, email);

			ResultSet rs = statement.executeQuery();
			TempUser us= null;
			while(rs.next()) {
				
			 us=new TempUser(rs.getString("NAME"), rs.getString("EMAIL"));
			}
			statement.close();
			connection.close();
			return (us!=null)? Optional.of(us) :Optional.empty();

		}

		catch (Exception e) {

		}
		return Optional.empty();
	}

	public void insertTempUser(TempUser tmp) throws Exception {
		
			Connection connection = jdbcTemplate.getDataSource().getConnection();
			CallableStatement statement = connection.prepareCall("{call insertTempUser(?, ?)}");
			statement.setString(1, tmp.getName());
			statement.setString(2, tmp.getEmail());
			statement.executeUpdate();
			statement.close();
			connection.close();

	
	}
	
	
	public List<TempUser> getTempUsersByAccord(String accNumber) throws Exception {
		Connection connection=null;
		CallableStatement statement=null;
		ResultSet rs=null;
		
		try {
			 connection = jdbcTemplate.getDataSource().getConnection();
			 statement = connection.prepareCall("{call searchTempUsersByAccord(?)}");
			statement.setString(1, accNumber);

			rs=statement.executeQuery();
			List<TempUser> result= new ArrayList<>();
			
			while(rs.next()) {
				TempUser tmp=new TempUser();
				tmp.setEmail(rs.getString("EMAIL"));
				tmp.setName(rs.getString("NAME"));
				result.add(tmp);
			}
			return result;

		}
		catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			if(rs != null) {
				try {
					rs.close();
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}
			
			if(statement != null) {
				try {
					statement.close();
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}
			if(connection != null) {
				try {
					connection.close();
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		
	}

}
