package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Statement;

import db.DB;
import db.DbException;
import model.dao.AgendaDao;
import model.entities.Agenda;

public class AgendaDaoJDBC implements AgendaDao{

	private Connection conn; 
	
	public AgendaDaoJDBC(Connection conn) {   //Conexão com o BD
		this.conn = conn;
	}
	
	@Override
	public void insert(Agenda obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO agenda "
					+"(Name, Phone, Cellphone, Email, Birthdate) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			st.setString(1, obj.getName());
			st.setString(2, obj.getPhone());
			st.setString(3, obj.getCellphone());
			st.setString(4, obj.getEmail());
			st.setDate(5, new java.sql.Date(obj.getBirthdate().getTime()));
			
			
			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected > 0) { 
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) { 
					int id = rs.getInt(1);
					obj.setId(id); 
				}
				DB.closeResultSet(rs);
			}else {
				throw new DbException("Unexpected error! No rows affected!");
			}
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);		
		}
	}

	@Override
	public void update(Agenda obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE agenda " +
					"SET Name = ? , Phone = ?, Cellphone = ?, Email = ?, Birthdate = ? " +
					"WHERE Id = ?");
			st.setString(1, obj.getName());
			st.setString(2, obj.getPhone());
			st.setString(3, obj.getCellphone());
			st.setString(4, obj.getEmail());
			st.setDate(5, new java.sql.Date(obj.getBirthdate().getTime()));
			st.setInt(6, obj.getId());

			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM agenda WHERE Id = ?");
			st.setInt(1, id); 

			st.executeUpdate();  
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public List<Agenda> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT * FROM agenda ORDER BY Name");
			rs = st.executeQuery();
			
			List<Agenda> list = new ArrayList<>();
						
			while (rs.next()) {
				Agenda obj = instanciateAgenda(rs);
				list.add(obj);
			}
			return list;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Agenda> findByName(String name) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
					"SELECT * FROM agenda"
					+ "WHERE Name LIKE ? ");
			
			st.setString(1, "%" + name + "%");
			rs = st.executeQuery();
			
			List<Agenda> list = new ArrayList<>();
			
			while (rs.next()) { 
				Agenda ag = instanciateAgenda(rs);
				list.add(ag);
			}
			return list;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally { 
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public Agenda findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(   //conexão com o banco
				"SELECT * FROM agenda WHERE Id = ?");
				st.setInt(1, id);
				rs = st.executeQuery();  

			if (rs.next()) {  
				Agenda ag = instanciateAgenda(rs);
				return ag;
			}
			return null;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally { 	
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	
	private Agenda instanciateAgenda(ResultSet rs) throws SQLException {
		Agenda ag = new Agenda();
		ag.setId(rs.getInt("Id"));
		ag.setName(rs.getString("Name"));
		ag.setPhone(rs.getString("Phone"));
		ag.setCellphone(rs.getString("Cellphone"));
		ag.setEmail(rs.getString("Email"));
		ag.setBirthdate(rs.getDate("Birthdate"));
		return ag;
	}
	
}
