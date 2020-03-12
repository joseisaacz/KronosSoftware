package com.kronos.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kronos.model.Act;

@Repository
public class ActService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public boolean isActInDB(Date date) throws Exception {

		Connection connection = jdbcTemplate.getDataSource().getConnection();
		CallableStatement statement = connection.prepareCall("{call isActInDB(?) }");
		statement.setDate(1, new java.sql.Date(date.getTime()));

		// statement.registerOutParameter(1, new );
		ResultSet rs = statement.executeQuery();
		boolean isInDb = rs.next();
		statement.close();
		connection.close();
		return isInDb;

	}

	public boolean insertAct(Date date) throws Exception {

		Connection connection = jdbcTemplate.getDataSource().getConnection();
		CallableStatement statement = connection.prepareCall("{call  insertTempAct(?) }");
		statement.setDate(1, new java.sql.Date(date.getTime()));

		// statement.registerOutParameter(1, new );
		int aux = statement.executeUpdate();
		statement.close();
		connection.close();
		System.out.println(aux);
		return true;

	}

	public List<Act> findSessionDates() {
		try {
			Connection connection= jdbcTemplate.getDataSource().getConnection();
			CallableStatement statement= connection.prepareCall("{call sessionDates() }");
			ResultSet rs=statement.executeQuery();
			List<Act> result=this.mapRowList(rs);
			statement.close();
			connection.close();
			return result;
		} catch (Exception e) {
			System.out.println("\n\n\n\n\n\n\n\nERROR\n\n\n\n\n"+e.getMessage()); 
		}
		return null;
	}
	
	private List<Act> mapRowList(ResultSet rs) throws SQLException{ 
		  List<Act> result= new ArrayList<>(); 
		  while(rs.next()) { 
		 Act t=new Act();
		 t.setSessionDate(rs.getDate("SESSIONDATE"));
		  result.add(t); 
		  }
 
	 return result; 
	 }
	
	public void addAct(Act acta)throws Exception{
		Connection connection= jdbcTemplate.getDataSource().getConnection();
		CallableStatement statement= connection.prepareCall("{call addAct(?,?,?,?,?)}");
		statement.setString(1, acta.getSessionType());
		statement.setDate(2, new java.sql.Date(acta.getSessionDate().getTime()));
		statement.setString(3, acta.getUrl());
		statement.setBoolean(4, acta.getPublc());
		statement.setBoolean(5, acta.getActive());
		statement.executeUpdate();
		statement.close();
		connection.close();
	}

}
