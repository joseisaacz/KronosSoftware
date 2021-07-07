package com.kronos.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kronos.model.Accord;
import com.kronos.model.Pdf;
import com.kronos.model.State;
import com.kronos.model.Type;

@Repository
public class AccordService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void insertAccord(Accord acc) throws Exception {

		Connection connection = jdbcTemplate.getDataSource().getConnection();

		CallableStatement statement = connection.prepareCall("{call insertAccord(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
		;
		try {
			statement.setString(1, acc.getAccNumber());
			statement.setDate(2, new java.sql.Date(acc.getIncorporatedDate().getTime()));
			statement.setTime(3, java.sql.Time.valueOf(acc.getIncorporatedTime()));
			statement.setDate(4, new java.sql.Date(acc.getDeadline().getTime()));
			statement.setDate(5, new java.sql.Date(acc.getSessionDate().getTime()));
			statement.setInt(6, acc.getType().getId());
			statement.setString(7, acc.getObservations());
			statement.setBoolean(8, acc.isPublished());
			statement.setBoolean(9, acc.isNotified());
			statement.setInt(10, acc.getState().getId());
			statement.setString(11, acc.getUser().getTempUser().getEmail());
			statement.executeUpdate();
			statement.close();

			for (Pdf item : acc.getURL()) {
				CallableStatement statement2 = connection.prepareCall("{call insertAccPdf(?, ?, ?, ?, ?)}");
				statement2.setString(1, acc.getAccNumber());
				statement2.setString(2, item.getURL());
				statement2.setBoolean(3, item.isFinalResponse());
				statement2.setInt(4, item.getIsApproved());
				statement2.setBoolean(5, item.isCanDelete());
				int a = statement2.executeUpdate();

				statement2.close();

			}

			connection.close();
		} catch (Exception e) {
			throw e;
		}

	}

	public void updatePdfState(String accNumber, String url, int state) throws Exception {
		Connection connection = null;
		CallableStatement statement = null;
		try {
			connection = jdbcTemplate.getDataSource().getConnection();
			statement = connection.prepareCall("{call updateAccPdfState(?, ? ,?)}");
			statement.setString(1,accNumber);
			statement.setString(2,url);
			statement.setInt(3,state);
			statement.executeUpdate();
			
		} 
		catch (Exception e) {
			throw e;
		}
		finally {
			try {
				if(statement != null)
					statement.close();
			}
			catch(Exception e) {
				throw e;
			}
			
			try {
				if(connection != null)
					connection.close();
			}
			catch(Exception e) {
				throw e;
			}
		}
			

	}

	public List<Accord> searchAllAccords() throws Exception {
		Connection connection = jdbcTemplate.getDataSource().getConnection();
		CallableStatement statement = connection.prepareCall("{call searchAllAccords()}");
		ResultSet rs = statement.executeQuery();
		Map<String, Accord> map = new HashMap();

		while (rs.next()) {

			String accNumber = rs.getString("ACCNUMBER");
			if (map.isEmpty() || !map.containsKey(accNumber)) { // if the map is empty or the result isn't
				Accord a = new Accord(); // in the map
				a.setAccNumber(accNumber);
				a.setIncorporatedDate(rs.getDate("INCORDATE"));
				a.setDeadline(rs.getDate("DEADLINE"));
				a.setSessionDate(rs.getDate("SESSIONDATE"));
				a.setType(new Type(rs.getInt("TYPE_ID"), rs.getString("TYPE_DESC")));
				a.setObservations(rs.getString("OBSERVATIONS"));
				a.setNotified(rs.getBoolean("NOTIFIED"));
				a.setPublished(rs.getBoolean("PUBLIC"));
				a.setState(new State(rs.getInt("STATE"), rs.getString("STATE_DESC")));
				a.getURL().add(new Pdf(rs.getString("URL"), rs.getBoolean("FINALRESPONSE"), rs.getInt("ISAPPROVED"),
						rs.getBoolean("CAN_DELETE")));
				map.put(accNumber, a);
			} else {
				// if the result isn't in the map or the map isn't empty, just add the URL into
				// result
				map.get(accNumber).getURL().add(new Pdf(rs.getString("URL"), rs.getBoolean("FINALRESPONSE"),
						rs.getInt("ISAPPROVED"), rs.getBoolean("CAN_DELETE")));

			}
		}
		if (rs != null)
			rs.close();

		statement.close();
		connection.close();
		return new ArrayList<>(map.values());
	}

	public List<Accord> searchAllNotCompletedAccords() throws Exception {
		Connection connection = jdbcTemplate.getDataSource().getConnection();
		CallableStatement statement = connection.prepareCall("{call searchAllAccords_NotCompleted()}");
		ResultSet rs = statement.executeQuery();
		Map<String, Accord> map = new HashMap();

		while (rs.next()) {

			String accNumber = rs.getString("ACCNUMBER");
			if (map.isEmpty() || !map.containsKey(accNumber)) { // if the map is empty or the result isn't
				Accord a = new Accord(); // in the map
				a.setAccNumber(accNumber);
				a.setIncorporatedDate(rs.getDate("INCORDATE"));
				a.setDeadline(rs.getDate("DEADLINE"));
				a.setSessionDate(rs.getDate("SESSIONDATE"));
				a.setType(new Type(rs.getInt("TYPE_ID"), rs.getString("TYPE_DESC")));
				a.setObservations(rs.getString("OBSERVATIONS"));
				a.setNotified(rs.getBoolean("NOTIFIED"));
				a.setPublished(rs.getBoolean("PUBLIC"));
				a.setState(new State(rs.getInt("STATE"), rs.getString("STATE_DESC")));
				a.getURL().add(new Pdf(rs.getString("URL"), rs.getBoolean("FINALRESPONSE"), rs.getInt("ISAPPROVED"),
						rs.getBoolean("CAN_DELETE")));
				map.put(accNumber, a);
			} else {
				// if the result isn't in the map or the map isn't empty, just add the URL into
				// result
				map.get(accNumber).getURL().add(new Pdf(rs.getString("URL"), rs.getBoolean("FINALRESPONSE"),
						rs.getInt("ISAPPROVED"), rs.getBoolean("CAN_DELETE")));

			}
		}

		if (rs != null)
			rs.close();

		statement.close();
		connection.close();
		return new ArrayList<>(map.values());
	}

	public List<Accord> searchBySessionDate(Date date) throws Exception {
		Connection connection = jdbcTemplate.getDataSource().getConnection();
		CallableStatement statement = connection.prepareCall("{call searchAccordsessionDate(?)}");
		statement.setDate(1, new java.sql.Date(date.getTime()));
		ResultSet rs = statement.executeQuery();
		Map<String, Accord> map = new HashMap();

		while (rs.next()) {

			String accNumber = rs.getString("ACCNUMBER");
			if (map.isEmpty() || !map.containsKey(accNumber)) { // if the map is empty or the result isn't
				Accord a = new Accord(); // in the map
				a.setAccNumber(accNumber);
				a.setIncorporatedDate(rs.getDate("INCORDATE"));
				a.setDeadline(rs.getDate("DEADLINE"));
				a.setSessionDate(rs.getDate("SESSIONDATE"));
				a.setType(new Type(rs.getInt("TYPE_ID"), rs.getString("TYPE_DESC")));
				a.setObservations(rs.getString("OBSERVATIONS"));
				a.setNotified(rs.getBoolean("NOTIFIED"));
				a.setPublished(rs.getBoolean("PUBLIC"));
				a.setState(new State(rs.getInt("STATE"), rs.getString("STATE_DESC")));
				a.getURL().add(new Pdf(rs.getString("URL"), rs.getBoolean("FINALRESPONSE"), rs.getInt("ISAPPROVED"),
						rs.getBoolean("CAN_DELETE")));
				map.put(accNumber, a);
			} else {
				// if the result isn't in the map or the map isn't empty, just add the URL into
				// result
				map.get(accNumber).getURL().add(new Pdf(rs.getString("URL"), rs.getBoolean("FINALRESPONSE"),
						rs.getInt("ISAPPROVED"), rs.getBoolean("CAN_DELETE")));

			}
		}

		if (rs != null)
			rs.close();

		statement.close();
		connection.close();
		return new ArrayList<>(map.values());
	}

	public List<Accord> searchByIncorDate(Date date) throws Exception {
		Connection connection = jdbcTemplate.getDataSource().getConnection();
		CallableStatement statement = connection.prepareCall("{call searchAccordIncorDate(?)}");
		statement.setDate(1, new java.sql.Date(date.getTime()));
		ResultSet rs = statement.executeQuery();
		Map<String, Accord> map = new HashMap();

		while (rs.next()) {

			String accNumber = rs.getString("ACCNUMBER");
			if (map.isEmpty() || !map.containsKey(accNumber)) { // if the map is empty or the result isn't
				Accord a = new Accord(); // in the map
				a.setAccNumber(accNumber);
				a.setIncorporatedDate(rs.getDate("INCORDATE"));
				a.setDeadline(rs.getDate("DEADLINE"));
				a.setSessionDate(rs.getDate("SESSIONDATE"));
				a.setType(new Type(rs.getInt("TYPE_ID"), rs.getString("TYPE_DESC")));
				a.setObservations(rs.getString("OBSERVATIONS"));
				a.setNotified(rs.getBoolean("NOTIFIED"));
				a.setPublished(rs.getBoolean("PUBLIC"));
				a.setState(new State(rs.getInt("STATE"), rs.getString("STATE_DESC")));
				a.getURL().add(new Pdf(rs.getString("URL"), rs.getBoolean("FINALRESPONSE"), rs.getInt("ISAPPROVED"),
						rs.getBoolean("CAN_DELETE")));
				map.put(accNumber, a);
			} else {
				// if the result isn't in the map or the map isn't empty, just add the URL into
				// result
				map.get(accNumber).getURL().add(new Pdf(rs.getString("URL"), rs.getBoolean("FINALRESPONSE"),
						rs.getInt("ISAPPROVED"), rs.getBoolean("CAN_DELETE")));

			}
		}

		if (rs != null)
			rs.close();

		statement.close();
		connection.close();
		return new ArrayList<>(map.values());
	}

	public List<Accord> searchByType(int type) throws Exception {
		Connection connection = jdbcTemplate.getDataSource().getConnection();
		CallableStatement statement = connection.prepareCall("{call searchAccordType(?)}");
		statement.setInt(1, type);
		ResultSet rs = statement.executeQuery();
		Map<String, Accord> map = new HashMap();

		while (rs.next()) {

			String accNumber = rs.getString("ACCNUMBER");
			if (map.isEmpty() || !map.containsKey(accNumber)) { // if the map is empty or the result isn't
				Accord a = new Accord(); // in the map
				a.setAccNumber(accNumber);
				a.setIncorporatedDate(rs.getDate("INCORDATE"));
				a.setDeadline(rs.getDate("DEADLINE"));
				a.setSessionDate(rs.getDate("SESSIONDATE"));
				a.setType(new Type(rs.getInt("TYPE_ID"), rs.getString("TYPE_DESC")));
				a.setObservations(rs.getString("OBSERVATIONS"));
				a.setNotified(rs.getBoolean("NOTIFIED"));
				a.setPublished(rs.getBoolean("PUBLIC"));
				a.setState(new State(rs.getInt("STATE"), rs.getString("STATE_DESC")));
				a.getURL().add(new Pdf(rs.getString("URL"), rs.getBoolean("FINALRESPONSE"), rs.getInt("ISAPPROVED"),
						rs.getBoolean("CAN_DELETE")));
				map.put(accNumber, a);
			} else {
				// if the result isn't in the map or the map isn't empty, just add the URL into
				// result
				map.get(accNumber).getURL().add(new Pdf(rs.getString("URL"), rs.getBoolean("FINALRESPONSE"),
						rs.getInt("ISAPPROVED"), rs.getBoolean("CAN_DELETE")));

			}
		}

		if (rs != null)
			rs.close();

		statement.close();
		connection.close();
		return new ArrayList<>(map.values());
	}

	public List<Accord> searchByUser(String user) throws Exception {
		Connection connection = jdbcTemplate.getDataSource().getConnection();
		CallableStatement statement = connection.prepareCall("{call getAccordByUser(?)}");
		statement.setString(1, user);
		ResultSet rs = statement.executeQuery();
		Map<String, Accord> map = new HashMap();

		while (rs.next()) {

			String accNumber = rs.getString("ACCNUMBER");
			if (map.isEmpty() || !map.containsKey(accNumber)) { // if the map is empty or the result isn't
				Accord a = new Accord(); // in the map
				a.setAccNumber(accNumber);
				a.setIncorporatedDate(rs.getDate("INCORDATE"));
				a.setDeadline(rs.getDate("DEADLINE"));
				a.setSessionDate(rs.getDate("SESSIONDATE"));
				a.setType(new Type(rs.getInt("TYPE_ID"), rs.getString("TYPE_DESC")));
				a.setObservations(rs.getString("OBSERVATIONS"));
				a.setNotified(rs.getBoolean("NOTIFIED"));
				a.setPublished(rs.getBoolean("PUBLIC"));
				a.setState(new State(rs.getInt("STATE"), rs.getString("STATE_DESC")));
				a.getURL().add(new Pdf(rs.getString("URL"), rs.getBoolean("FINALRESPONSE"), rs.getInt("ISAPPROVED"),
						rs.getBoolean("CAN_DELETE")));
				map.put(accNumber, a);
			} else {
				// if the result isn't in the map or the map isn't empty, just add the URL into
				// result
				map.get(accNumber).getURL().add(new Pdf(rs.getString("URL"), rs.getBoolean("FINALRESPONSE"),
						rs.getInt("ISAPPROVED"), rs.getBoolean("CAN_DELETE")));

			}
		}

		if (rs != null)
			rs.close();

		statement.close();
		connection.close();
		ArrayList<Accord> result = new ArrayList<>(map.values());
		return result;

	}

	public List<Accord> searchByAccNumber(String accordNumber) throws Exception {
		Connection connection = jdbcTemplate.getDataSource().getConnection();
		CallableStatement statement = connection.prepareCall("{call searchAccordNumber(?)}");
		statement.setString(1, accordNumber);
		ResultSet rs = statement.executeQuery();
		Map<String, Accord> map = new HashMap();

		while (rs.next()) {

			String accNumber = rs.getString("ACCNUMBER");
			if (map.isEmpty() || !map.containsKey(accNumber)) { // if the map is empty or the result isn't
				Accord a = new Accord(); // in the map
				a.setAccNumber(accNumber);
				a.setIncorporatedDate(rs.getDate("INCORDATE"));
				a.setDeadline(rs.getDate("DEADLINE"));
				a.setSessionDate(rs.getDate("SESSIONDATE"));
				a.setType(new Type(rs.getInt("TYPE_ID"), rs.getString("TYPE_DESC")));
				a.setObservations(rs.getString("OBSERVATIONS"));
				a.setNotified(rs.getBoolean("NOTIFIED"));
				a.setPublished(rs.getBoolean("PUBLIC"));
				a.setState(new State(rs.getInt("STATE"), rs.getString("STATE_DESC")));
				a.getURL().add(new Pdf(rs.getString("URL"), rs.getBoolean("FINALRESPONSE"), rs.getInt("ISAPPROVED"),
						rs.getBoolean("CAN_DELETE")));
				map.put(accNumber, a);
			} else {
				// if the result isn't in the map or the map isn't empty, just add the URL into
				// result
				map.get(accNumber).getURL().add(new Pdf(rs.getString("URL"), rs.getBoolean("FINALRESPONSE"),
						rs.getInt("ISAPPROVED"), rs.getBoolean("CAN_DELETE")));

			}
		}

		if (rs != null)
			rs.close();

		statement.close();
		connection.close();
		return new ArrayList<>(map.values());
	}

	public Optional<Accord> getAccord(String accordNumber) throws Exception {
		Connection connection = jdbcTemplate.getDataSource().getConnection();
		CallableStatement statement = connection.prepareCall("{call getAccord(?)}");
		statement.setString(1, accordNumber);
		ResultSet rs = statement.executeQuery();
		Map<String, Accord> map = new HashMap();

		while (rs.next()) {

			String accNumber = rs.getString("ACCNUMBER");
			if (map.isEmpty() || !map.containsKey(accNumber)) { // if the map is empty or the result isn't
				Accord a = new Accord(); // in the map
				a.setAccNumber(accNumber);
				a.setIncorporatedDate(rs.getDate("INCORDATE"));
				a.setDeadline(rs.getDate("DEADLINE"));
				a.setSessionDate(rs.getDate("SESSIONDATE"));
				a.setType(new Type(rs.getInt("TYPE_ID"), rs.getString("TYPE_DESC")));
				a.setObservations(rs.getString("OBSERVATIONS"));
				a.setNotified(rs.getBoolean("NOTIFIED"));
				a.setPublished(rs.getBoolean("PUBLIC"));
				a.setState(new State(rs.getInt("STATE"), rs.getString("STATE_DESC")));
				a.getURL().add(new Pdf(rs.getString("URL"), rs.getBoolean("FINALRESPONSE"), rs.getInt("ISAPPROVED"),
						rs.getBoolean("CAN_DELETE")));
				map.put(accNumber, a);
			} else {
				// if the result isn't in the map or the map isn't empty, just add the URL into
				// result
				map.get(accNumber).getURL().add(new Pdf(rs.getString("URL"), rs.getBoolean("FINALRESPONSE"),
						rs.getInt("ISAPPROVED"), rs.getBoolean("CAN_DELETE")));

			}
		}

		if (rs != null)
			rs.close();

		statement.close();
		connection.close();
		List<Accord> list = new ArrayList<Accord>(map.values());
		if (list.size() > 1 || list.isEmpty())
			return Optional.empty();
		else
			return Optional.of(list.get(0));
	}

	public void insertActPdf(String accNumber, Pdf pdf) throws Exception {
		Connection connection = jdbcTemplate.getDataSource().getConnection();
		CallableStatement statement = connection.prepareCall("{call insertAccPdf(?,?,?,?,?)}");
		statement.setString(1, accNumber);
		statement.setString(2, pdf.getURL());
		statement.setBoolean(3, pdf.isFinalResponse());
		statement.setInt(4, pdf.getIsApproved());
		statement.setBoolean(5, pdf.isCanDelete());
		statement.executeUpdate();
		statement.close();
		connection.close();
	}

	public void deleteAccPdf(String accNumber, String url) throws Exception {
		Connection connection = jdbcTemplate.getDataSource().getConnection();
		CallableStatement statement = connection.prepareCall("{call deletePdf(?,?)}");
		statement.setString(1, accNumber);
		statement.setString(2, url);
		statement.executeUpdate();
		statement.close();
		connection.close();
	}

	public void deleteAccord(String accNumber, String user) throws Exception {
		Connection connection = jdbcTemplate.getDataSource().getConnection();
		CallableStatement statement = connection.prepareCall("{call deleteAccord(?,?)}");
		statement.setString(1, accNumber);
		statement.setString(2, user);
		statement.executeUpdate();
		statement.close();
		connection.close();
	}

	public void updateAccordState(String accNumber, int state) throws Exception {
		Connection connection = jdbcTemplate.getDataSource().getConnection();
		CallableStatement statement = connection.prepareCall("{call updateAccordState(?,?)}");
		statement.setString(1, accNumber);
		statement.setInt(2, state);
		statement.executeUpdate();
		statement.close();
		connection.close();
	}

	public void updateAccord(Accord acc) throws Exception {

		Connection connection = jdbcTemplate.getDataSource().getConnection();
		CallableStatement statement = connection.prepareCall("{call updateAccord(?, ?, ?, ?, ?, ?, ?, ?, ?)}");

		statement.setString(1, acc.getAccNumber());
		statement.setDate(2, new java.sql.Date(acc.getDeadline().getTime()));
		statement.setDate(3, new java.sql.Date(acc.getSessionDate().getTime()));
		statement.setString(4, acc.getObservations());
		statement.setBoolean(5, acc.isPublished());
		statement.setBoolean(6, acc.isNotified());
		statement.setInt(7, acc.getState().getId());
		statement.setString(8, String.valueOf(acc.getType().getId()));
		statement.setString(9, acc.getUser().getTempUser().getEmail());
		statement.executeUpdate();
		statement.close();
		connection.close();

	}

	public List<Accord> emailInfo(Date today, Date limit) throws Exception {
		Connection connection = jdbcTemplate.getDataSource().getConnection();
		CallableStatement statement = connection.prepareCall("{call emailInfo(?,?)}");
		statement.setDate(1, new java.sql.Date(today.getTime()));
		statement.setDate(2, new java.sql.Date(limit.getTime()));
		ResultSet rs = statement.executeQuery();
		Map<String, Accord> map = new HashMap();
		while (rs.next()) {
			String accNumber = rs.getString("ACCNUMBER");
			if (map.isEmpty() || !map.containsKey(accNumber)) { // if the map is empty or the result isn't
				Accord a = new Accord(); // in the map
				a.setAccNumber(accNumber);
				a.setIncorporatedDate(rs.getDate("INCORDATE"));
				a.setDeadline(rs.getDate("DEADLINE"));
				map.put(accNumber, a);
			}
		}

		if (rs != null)
			rs.close();

		statement.close();
		connection.close();

		return new ArrayList<>(map.values());
	}

	public List<Accord> searchByLimitDate(Date today, Date limit) throws Exception {
		Connection connection = jdbcTemplate.getDataSource().getConnection();
		CallableStatement statement = connection.prepareCall("{call searchExpiredAccords(?,?)}");
		statement.setDate(1, new java.sql.Date(today.getTime()));
		statement.setDate(2, new java.sql.Date(limit.getTime()));
		ResultSet rs = statement.executeQuery();
		Map<String, Accord> map = new HashMap();

		while (rs.next()) {

			String accNumber = rs.getString("ACCNUMBER");
			if (map.isEmpty() || !map.containsKey(accNumber)) { // if the map is empty or the result isn't
				Accord a = new Accord(); // in the map
				a.setAccNumber(accNumber);
				a.setIncorporatedDate(rs.getDate("INCORDATE"));
				a.setDeadline(rs.getDate("DEADLINE"));
				a.setSessionDate(rs.getDate("SESSIONDATE"));
				a.setType(new Type(rs.getInt("TYPE_ID"), rs.getString("TYPE_DESC")));
				a.setObservations(rs.getString("OBSERVATIONS"));
				a.setNotified(rs.getBoolean("NOTIFIED"));
				a.setPublished(rs.getBoolean("PUBLIC"));
				a.setState(new State(rs.getInt("STATE"), rs.getString("STATE_DESC")));
				a.getURL().add(new Pdf(rs.getString("URL"), rs.getBoolean("FINALRESPONSE"), rs.getInt("ISAPPROVED"),
						rs.getBoolean("CAN_DELETE")));
				map.put(accNumber, a);
			} else {
				// if the result isn't in the map or the map isn't empty, just add the URL into
				// result
				map.get(accNumber).getURL().add(new Pdf(rs.getString("URL"), rs.getBoolean("FINALRESPONSE"),
						rs.getInt("ISAPPROVED"), rs.getBoolean("CAN_DELETE")));

			}
		}

		if (rs != null)
			rs.close();

		statement.close();
		connection.close();
		return new ArrayList<>(map.values());
	}

	public void updatePDF(String accNumber, Pdf pdf) throws Exception {

		Connection connection = jdbcTemplate.getDataSource().getConnection();
		CallableStatement statement = connection.prepareCall("{call updatePdf(?, ?, ?)}");

		statement.setString(1, accNumber);
		statement.setString(2, pdf.getURL());
		statement.setBoolean(3, pdf.isFinalResponse());
		statement.executeUpdate();
		statement.close();
		connection.close();

	}

	public Optional<Pdf> haveFinalResponse(String accNumber) throws Exception {
		Connection connection = jdbcTemplate.getDataSource().getConnection();
		CallableStatement statement = connection.prepareCall("{call haveFinalResponse(?)}");
		statement.setString(1, accNumber);
		ResultSet rs = statement.executeQuery();
		Pdf pdf = null;
		while (rs.next()) {
			pdf = new Pdf(rs.getString("URL"), rs.getBoolean("FINALRESPONSE"));
		}
		if (rs != null)
			rs.close();

		statement.close();
		connection.close();

		return (pdf != null) ? Optional.of(pdf) : Optional.empty();

	}

	public void changeState(int state, Date today) throws Exception {
		Connection connection = jdbcTemplate.getDataSource().getConnection();
		CallableStatement statement = connection.prepareCall("{call changeAccordState(?, ?)}");
		statement.setInt(1, state);
		statement.setDate(2, new java.sql.Date(today.getTime()));
		statement.executeUpdate();
		statement.close();
		connection.close();
	}

	public List<Accord> getAccordsSecretary() throws Exception {
		Connection connection = jdbcTemplate.getDataSource().getConnection();
		CallableStatement statement = connection.prepareCall("{call aMSearchAccords()}");
		ResultSet rs = statement.executeQuery();
		Map<String, Accord> map = new HashMap<>();

		while (rs.next()) {

			String accNumber = rs.getString("ACCNUMBER");
			if (map.isEmpty() || !map.containsKey(accNumber)) { // if the map is empty or the result isn't
				Accord a = new Accord(); // in the map
				a.setAccNumber(accNumber);
				a.setIncorporatedDate(rs.getDate("INCORDATE"));
				a.setDeadline(rs.getDate("DEADLINE"));
				a.setSessionDate(rs.getDate("SESSIONDATE"));
				a.setType(new Type(rs.getInt("TYPE_ID"), rs.getString("TYPE_DESC")));
				a.setObservations(rs.getString("OBSERVATIONS"));
				a.setNotified(rs.getBoolean("NOTIFIED"));
				a.setPublished(rs.getBoolean("PUBLIC"));
				a.setState(new State(rs.getInt("STATE"), rs.getString("STATE_DESC")));
				a.getURL().add(new Pdf(rs.getString("URL"), rs.getBoolean("FINALRESPONSE"), rs.getInt("ISAPPROVED"),
						rs.getBoolean("CAN_DELETE")));
				map.put(accNumber, a);
			} else {
				// if the result isn't in the map or the map isn't empty, just add the URL into
				// result
				map.get(accNumber).getURL().add(new Pdf(rs.getString("URL"), rs.getBoolean("FINALRESPONSE"),
						rs.getInt("ISAPPROVED"), rs.getBoolean("CAN_DELETE")));

			}
		}
		if (rs != null)
			rs.close();

		statement.close();
		connection.close();
		return new ArrayList<>(map.values());
	}

	public List<Accord> todayDeadlineAccors(Date today) throws Exception {
		Connection connection = jdbcTemplate.getDataSource().getConnection();
		CallableStatement statement = connection.prepareCall("{call todayDeadlineAccords(?)}");
		statement.setDate(1, new java.sql.Date(today.getTime()));
		ResultSet rs = statement.executeQuery();
		Map<String, Accord> map = new HashMap();

		while (rs.next()) {

			String accNumber = rs.getString("ACCNUMBER");
			if (map.isEmpty() || !map.containsKey(accNumber)) { // if the map is empty or the result isn't
				Accord a = new Accord(); // in the map
				a.setAccNumber(accNumber);
				a.setIncorporatedDate(rs.getDate("INCORDATE"));
				a.setDeadline(rs.getDate("DEADLINE"));
				a.setSessionDate(rs.getDate("SESSIONDATE"));
				a.setType(new Type(rs.getInt("TYPE_ID"), rs.getString("TYPE_DESC")));
				a.setObservations(rs.getString("OBSERVATIONS"));
				a.setNotified(rs.getBoolean("NOTIFIED"));
				a.setPublished(rs.getBoolean("PUBLIC"));
				a.setState(new State(rs.getInt("STATE"), rs.getString("STATE_DESC")));
				a.getURL().add(new Pdf(rs.getString("URL"), rs.getBoolean("FINALRESPONSE"), rs.getInt("ISAPPROVED"),
						rs.getBoolean("CAN_DELETE")));
				map.put(accNumber, a);
			} else {
				// if the result isn't in the map or the map isn't empty, just add the URL into
				// result
				map.get(accNumber).getURL().add(new Pdf(rs.getString("URL"), rs.getBoolean("FINALRESPONSE"),
						rs.getInt("ISAPPROVED"), rs.getBoolean("CAN_DELETE")));

			}
		}

		if (rs != null)
			rs.close();

		statement.close();
		connection.close();
		return new ArrayList<>(map.values());
	}

	public List<Accord> NotAssignedAccords() throws Exception {
		Connection connection = jdbcTemplate.getDataSource().getConnection();
		CallableStatement statement = connection.prepareCall("{call notAssignedAccords()}");
		ResultSet rs = statement.executeQuery();
		Map<String, Accord> map = new HashMap<>();

		while (rs.next()) {

			String accNumber = rs.getString("ACCNUMBER");
			if (map.isEmpty() || !map.containsKey(accNumber)) { // if the map is empty or the result isn't
				Accord a = new Accord(); // in the map
				a.setAccNumber(accNumber);
				a.setIncorporatedDate(rs.getDate("INCORDATE"));
				a.setDeadline(rs.getDate("DEADLINE"));
				a.setSessionDate(rs.getDate("SESSIONDATE"));
				a.setType(new Type(rs.getInt("TYPE_ID"), rs.getString("TYPE_DESC")));
				a.setObservations(rs.getString("OBSERVATIONS"));
				a.setNotified(rs.getBoolean("NOTIFIED"));
				a.setPublished(rs.getBoolean("PUBLIC"));
				a.setState(new State(rs.getInt("STATE"), rs.getString("STATE_DESC")));
				a.getURL().add(new Pdf(rs.getString("URL"), rs.getBoolean("FINALRESPONSE"), rs.getInt("ISAPPROVED"),
						rs.getBoolean("CAN_DELETE")));
				map.put(accNumber, a);
			} else {
				// if the result isn't in the map or the map isn't empty, just add the URL into
				// result
				map.get(accNumber).getURL().add(new Pdf(rs.getString("URL"), rs.getBoolean("FINALRESPONSE"),
						rs.getInt("ISAPPROVED"), rs.getBoolean("CAN_DELETE")));

			}
		}
		if (rs != null)
			rs.close();

		statement.close();
		connection.close();
		return new ArrayList<>(map.values());
	}

	public List<Accord> alreadyAssignedAccords() throws Exception {
		Connection connection = jdbcTemplate.getDataSource().getConnection();
		CallableStatement statement = connection.prepareCall("{call alreadyAssignedAccords()}");
		ResultSet rs = statement.executeQuery();
		Map<String, Accord> map = new HashMap<>();

		while (rs.next()) {

			String accNumber = rs.getString("ACCNUMBER");
			if (map.isEmpty() || !map.containsKey(accNumber)) { // if the map is empty or the result isn't
				Accord a = new Accord(); // in the map
				a.setAccNumber(accNumber);
				a.setIncorporatedDate(rs.getDate("INCORDATE"));
				a.setDeadline(rs.getDate("DEADLINE"));
				a.setSessionDate(rs.getDate("SESSIONDATE"));
				a.setType(new Type(rs.getInt("TYPE_ID"), rs.getString("TYPE_DESC")));
				a.setObservations(rs.getString("OBSERVATIONS"));
				a.setNotified(rs.getBoolean("NOTIFIED"));
				a.setPublished(rs.getBoolean("PUBLIC"));
				a.setState(new State(rs.getInt("STATE"), rs.getString("STATE_DESC")));
				a.getURL().add(new Pdf(rs.getString("URL"), rs.getBoolean("FINALRESPONSE"), rs.getInt("ISAPPROVED"),
						rs.getBoolean("CAN_DELETE")));
				map.put(accNumber, a);
			} else {
				// if the result isn't in the map or the map isn't empty, just add the URL into
				// result
				map.get(accNumber).getURL().add(new Pdf(rs.getString("URL"), rs.getBoolean("FINALRESPONSE"),
						rs.getInt("ISAPPROVED"), rs.getBoolean("CAN_DELETE")));

			}
		}
		if (rs != null)
			rs.close();

		statement.close();
		connection.close();
		return new ArrayList<>(map.values());
	}

	public List<Accord> getBossAccords(String user) throws Exception {

		Connection connection = jdbcTemplate.getDataSource().getConnection();

		CallableStatement statement = connection.prepareCall("{call getBossAccords(?)}");
		statement.setString(1, user);
		ResultSet rs = statement.executeQuery();
		Map<String, Accord> map = new HashMap<>();

		while (rs.next()) {

			String accNumber = rs.getString("ACCNUMBER");
			if (map.isEmpty() || !map.containsKey(accNumber)) { // if the map is empty or the result isn't
				Accord a = new Accord(); // in the map
				a.setAccNumber(accNumber);
				a.setIncorporatedDate(rs.getDate("INCORDATE"));
				a.setDeadline(rs.getDate("DEADLINE"));
				a.setSessionDate(rs.getDate("SESSIONDATE"));
				a.setType(new Type(rs.getInt("TYPE_ID"), rs.getString("TYPE_DESC")));
				a.setObservations(rs.getString("OBSERVATIONS"));
				a.setNotified(rs.getBoolean("NOTIFIED"));
				a.setPublished(rs.getBoolean("PUBLIC"));
				a.setState(new State(rs.getInt("STATE"), rs.getString("STATE_DESC")));
				a.getURL().add(new Pdf(rs.getString("URL"), rs.getBoolean("FINALRESPONSE"), rs.getInt("ISAPPROVED"),
						rs.getBoolean("CAN_DELETE")));
				map.put(accNumber, a);
			} else {
				// if the result isn't in the map or the map isn't empty, just add the URL into
				// result
				map.get(accNumber).getURL().add(new Pdf(rs.getString("URL"), rs.getBoolean("FINALRESPONSE"),
						rs.getInt("ISAPPROVED"), rs.getBoolean("CAN_DELETE")));

			}
		}
		if (rs != null)
			rs.close();

		statement.close();
		connection.close();
		return new ArrayList<>(map.values());
	}

	public List<Accord> searchByPendingAccordsDepartment(char department) throws Exception {

		Connection connection = jdbcTemplate.getDataSource().getConnection();

		CallableStatement statement = connection.prepareCall("{call pendingAccordsDepartment(?)}");
		statement.setString(1, String.valueOf(department));
		ResultSet rs = statement.executeQuery();
		Map<String, Accord> map = new HashMap<>();
			
		while (rs.next()) {

			String accNumber = rs.getString("ACCNUMBER");
			if (map.isEmpty() || !map.containsKey(accNumber)) { // if the map is empty or the result isn't
				Accord a = new Accord(); // in the map
				a.setAccNumber(accNumber);
				a.setIncorporatedDate(rs.getDate("INCORDATE"));
				a.setDeadline(rs.getDate("DEADLINE"));
				a.setSessionDate(rs.getDate("SESSIONDATE"));
				a.setType(new Type(rs.getInt("TYPE_ID"), rs.getString("TYPE_DESC")));
				a.setObservations(rs.getString("OBSERVATIONS"));
				a.setNotified(rs.getBoolean("NOTIFIED"));
				a.setPublished(rs.getBoolean("PUBLIC"));
				a.setState(new State(rs.getInt("STATE"), rs.getString("STATE_DESC")));
				a.getURL().add(new Pdf(rs.getString("URL"), rs.getBoolean("FINALRESPONSE"), rs.getInt("ISAPPROVED"),
						rs.getBoolean("CAN_DELETE")));
				map.put(accNumber, a);
			} else {
				// if the result isn't in the map or the map isn't empty, just add the URL into
				// result
				map.get(accNumber).getURL().add(new Pdf(rs.getString("URL"), rs.getBoolean("FINALRESPONSE"),
						rs.getInt("ISAPPROVED"), rs.getBoolean("CAN_DELETE")));

			}
		}

		if (rs != null)
			rs.close();

		statement.close();
		connection.close();
		return new ArrayList<>(map.values());
	}

	public List<Accord> getAllBossAccords(String user) throws Exception {
		Connection connection = jdbcTemplate.getDataSource().getConnection();
		CallableStatement statement = connection.prepareCall("{call getAllBossAccords(?)}");
		statement.setString(1, user);
		ResultSet rs = statement.executeQuery();
		Map<String, Accord> map = new HashMap<>();

		while (rs.next()) {
			String accNumber = rs.getString("ACCNUMBER");
			if (map.isEmpty() || !map.containsKey(accNumber)) { // if the map is empty or the result isn't
				Accord a = new Accord(); // in the map
				a.setAccNumber(accNumber);
				a.setIncorporatedDate(rs.getDate("INCORDATE"));
				a.setDeadline(rs.getDate("DEADLINE"));
				a.setSessionDate(rs.getDate("SESSIONDATE"));
				a.setType(new Type(rs.getInt("TYPE_ID"), rs.getString("TYPE_DESC")));
				a.setObservations(rs.getString("OBSERVATIONS"));
				a.setNotified(rs.getBoolean("NOTIFIED"));
				a.setPublished(rs.getBoolean("PUBLIC"));
				a.setState(new State(rs.getInt("STATE"), rs.getString("STATE_DESC")));
				a.getURL().add(new Pdf(rs.getString("URL"), rs.getBoolean("FINALRESPONSE"), rs.getInt("ISAPPROVED"),
						rs.getBoolean("CAN_DELETE")));
				map.put(accNumber, a);
			} else {
				// if the result isn't in the map or the map isn't empty, just add the URL into
				// result
				map.get(accNumber).getURL().add(new Pdf(rs.getString("URL"), rs.getBoolean("FINALRESPONSE"),
						rs.getInt("ISAPPROVED"), rs.getBoolean("CAN_DELETE")));

			}
		}

		if (rs != null)
			rs.close();

		statement.close();
		connection.close();
		return new ArrayList<>(map.values());
	}

	public List<Accord> getFinishedBossAccords(String user) throws Exception {
		Connection connection = jdbcTemplate.getDataSource().getConnection();
		CallableStatement statement = connection.prepareCall("{call getFinishedBossAccords(?)}");
		statement.setString(1, user);
		ResultSet rs = statement.executeQuery();
		Map<String, Accord> map = new HashMap<>();

		while (rs.next()) {

			String accNumber = rs.getString("ACCNUMBER");
			if (map.isEmpty() || !map.containsKey(accNumber)) { // if the map is empty or the result isn't
				Accord a = new Accord(); // in the map
				a.setAccNumber(accNumber);
				a.setIncorporatedDate(rs.getDate("INCORDATE"));
				a.setDeadline(rs.getDate("DEADLINE"));
				a.setSessionDate(rs.getDate("SESSIONDATE"));
				a.setType(new Type(rs.getInt("TYPE_ID"), rs.getString("TYPE_DESC")));
				a.setObservations(rs.getString("OBSERVATIONS"));
				a.setNotified(rs.getBoolean("NOTIFIED"));
				a.setPublished(rs.getBoolean("PUBLIC"));
				a.setState(new State(rs.getInt("STATE"), rs.getString("STATE_DESC")));
				a.getURL().add(new Pdf(rs.getString("URL"), rs.getBoolean("FINALRESPONSE"), rs.getInt("ISAPPROVED"),
						rs.getBoolean("CAN_DELETE")));
				map.put(accNumber, a);
			} else {
				// if the result isn't in the map or the map isn't empty, just add the URL into
				// result
				map.get(accNumber).getURL().add(new Pdf(rs.getString("URL"), rs.getBoolean("FINALRESPONSE"),
						rs.getInt("ISAPPROVED"), rs.getBoolean("CAN_DELETE")));

			}
		}
		if (rs != null)
			rs.close();

		statement.close();
		connection.close();
		return new ArrayList<>(map.values());
	}
	
	
	
	public List<Accord> searchAccordsessionDatePublish(Date date) throws Exception {
		Connection connection = jdbcTemplate.getDataSource().getConnection();
		CallableStatement statement = connection.prepareCall("{call searchAccordsessionDatePublish(?)}");
		statement.setDate(1, new java.sql.Date(date.getTime()));
		ResultSet rs = statement.executeQuery();
		Map<String, Accord> map = new HashMap();

		while (rs.next()) {

			String accNumber = rs.getString("ACCNUMBER");
			if (map.isEmpty() || !map.containsKey(accNumber)) { // if the map is empty or the result isn't
				Accord a = new Accord(); // in the map
				a.setAccNumber(accNumber);
				a.setIncorporatedDate(rs.getDate("INCORDATE"));
				a.setDeadline(rs.getDate("DEADLINE"));
				a.setSessionDate(rs.getDate("SESSIONDATE"));
				a.setType(new Type(rs.getInt("TYPE_ID"), rs.getString("TYPE_DESC")));
				a.setObservations(rs.getString("OBSERVATIONS"));
				a.setNotified(rs.getBoolean("NOTIFIED"));
				a.setPublished(rs.getBoolean("PUBLIC"));
				a.setState(new State(rs.getInt("STATE"), rs.getString("STATE_DESC")));
				a.getURL().add(new Pdf(rs.getString("URL"), rs.getBoolean("FINALRESPONSE"), rs.getInt("ISAPPROVED"),
						rs.getBoolean("CAN_DELETE")));
				map.put(accNumber, a);
			} else {
				// if the result isn't in the map or the map isn't empty, just add the URL into
				// result
				map.get(accNumber).getURL().add(new Pdf(rs.getString("URL"), rs.getBoolean("FINALRESPONSE"),
						rs.getInt("ISAPPROVED"), rs.getBoolean("CAN_DELETE")));

			}
		}

		if (rs != null)
			rs.close();

		statement.close();
		connection.close();
		return new ArrayList<>(map.values());
	}
	
	
	public List<Accord> searchAllAccordsPublish()throws Exception {
		Connection connection = jdbcTemplate.getDataSource().getConnection();
		CallableStatement statement = connection.prepareCall("{call searchAllAccordsPublish()}");
		ResultSet rs = statement.executeQuery();
		Map<String, Accord> map = new HashMap();

		while (rs.next()) {

			String accNumber = rs.getString("ACCNUMBER");
			if (map.isEmpty() || !map.containsKey(accNumber)) { // if the map is empty or the result isn't
				Accord a = new Accord(); // in the map
				a.setAccNumber(accNumber);
				a.setIncorporatedDate(rs.getDate("INCORDATE"));
				a.setDeadline(rs.getDate("DEADLINE"));
				a.setSessionDate(rs.getDate("SESSIONDATE"));
				a.setType(new Type(rs.getInt("TYPE_ID"), rs.getString("TYPE_DESC")));
				a.setObservations(rs.getString("OBSERVATIONS"));
				a.setNotified(rs.getBoolean("NOTIFIED"));
				a.setPublished(rs.getBoolean("PUBLIC"));
				a.setState(new State(rs.getInt("STATE"), rs.getString("STATE_DESC")));
				a.getURL().add(new Pdf(rs.getString("URL"), rs.getBoolean("FINALRESPONSE"), rs.getInt("ISAPPROVED"),
						rs.getBoolean("CAN_DELETE")));
				map.put(accNumber, a);
			} else {
				// if the result isn't in the map or the map isn't empty, just add the URL into
				// result
				map.get(accNumber).getURL().add(new Pdf(rs.getString("URL"), rs.getBoolean("FINALRESPONSE"),
						rs.getInt("ISAPPROVED"), rs.getBoolean("CAN_DELETE")));

			}
		}
		if (rs != null)
			rs.close();

		statement.close();
		connection.close();
		return new ArrayList<>(map.values());
	}
	
	

}
