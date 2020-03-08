package com.kronos.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

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

	/*public List<Act> findSessionDates() {
		try {
			Connection connection= jdbcTemplate.getDataSource().getConnection();
			CallableStatement statement= connection.prepareCall("{call sessionDates() }");
			ResultSet rs=statement.executeQuery();
			
		} catch (Exception e) {
		}
		return null;
	}

	/*
	 * public List<Type> findAll() {
	 * 
	 * try { Connection connection = jdbcTemplate.getDataSource().getConnection();
	 * CallableStatement statement =
	 * connection.prepareCall("{call searchAllTypes() }");
	 * 
	 * // statement.registerOutParameter(1, new ); ResultSet
	 * rs=statement.executeQuery();
	 * 
	 * List<Type> result= this.mapRowList(rs);
	 * 
	 * statement.close(); connection.close(); return result;
	 * 
	 * 
	 * } catch(Exception e){
	 * System.out.println("\n\n\n\n\n\n\n\nERROR\n\n\n\n\n"+e.getMessage()); }
	 * 
	 * 
	 * 
	 * return null; }
	 * 
	 * 
	 * private List<Type> mapRowList(ResultSet rs) throws SQLException{
	 * 
	 * List<Type> result= new ArrayList<>(); while(rs.next()) { Type t=new Type();
	 * t.setDescription(rs.getString("DESCRIPTION"));
	 * t.setId(rs.getString("ID").charAt(0)); result.add(t); }
	 * 
	 * return result;
	 * 
	 * }
	 */

}
