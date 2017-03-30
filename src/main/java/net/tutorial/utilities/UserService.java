package net.tutorial.utilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.tutorial.beans.User;

public class UserService{
	DBService dbService = null;
	private Connection dbConnection = null;
	private PreparedStatement ps = null;
	
	public UserService() {
		this.dbService = DBService.getInstance();
	}

	// checks if tama ung credentials
	public User checkLogin(User u) {
		User u2 = new User();
		
		this.dbConnection = this.dbService.getConnection();
		boolean verified = false;
		
		String sql = "SELECT * FROM " + User.TABLE_NAME + " WHERE " + User.COLUMN_USERNAME + "=? AND " + User.COLUMN_PASSWORD + "=?";
		System.out.println(sql);
		
		ResultSet rs = null;

		try {
			this.ps = this.dbConnection.prepareStatement(sql);
			this.ps.setString(1, u.getUsername());
			this.ps.setString(2, u.getPassword());

			rs = this.ps.executeQuery();
			
			while (rs.next()){
				verified = true;
				u2.setUserid(rs.getInt(User.COLUMN_USERID));
				u2.setUsername(rs.getString(User.COLUMN_USERNAME));
			}
			
			if (!verified)
				u2 = null;
			
			rs.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			this.dbService.cleanUp();
		}
		
		return u2;
	}	
	
	// register
	public int addUser(User u) {
		this.dbConnection = this.dbService.getConnection();
		
		int accountid = 0;
				
		String sql = "INSERT INTO " + User.TABLE_NAME + " (" +  User.COLUMN_USERNAME + ", " + User.COLUMN_PASSWORD + ") VALUES (?,?)";
		System.out.println(sql);
		ResultSet rs = null;

		try {
			this.ps = this.dbConnection.prepareStatement(sql);
			
			this.ps.setString(1, u.getUsername());
			this.ps.setString(2, u.getPassword());

			this.ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		sql = "SELECT " + User.COLUMN_USERID + " FROM " + User.TABLE_NAME + " WHERE " + User.COLUMN_USERNAME + "=? AND "
				+ User.COLUMN_PASSWORD + "=?";
		System.out.println(sql);
		
		try {
			this.ps = this.dbConnection.prepareStatement(sql);
			this.ps.setString(1, u.getUsername());
			this.ps.setString(2, u.getPassword());
			
			rs = this.ps.executeQuery();
			
			while (rs.next()){
				accountid = rs.getInt(User.COLUMN_USERID);
			}

			rs.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			this.dbService.cleanUp();
		}
		
		return accountid;
	}
	
	// check if username already exists
	public boolean checkExists(String id) {
		this.dbConnection = this.dbService.getConnection();
		
		boolean unique = false;
				
		String sql = "SELECT * FROM " + User.TABLE_NAME + " WHERE " + User.COLUMN_USERNAME  + " = ?";
		System.out.println(sql);
		ResultSet rs = null;

		try {
			this.ps = this.dbConnection.prepareStatement(sql);
			this.ps.setString(1, id);
			rs = this.ps.executeQuery();
			
			unique = rs.next();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			this.dbService.cleanUp();
		}
		
		return unique;
	}	
	
	// display info
	public User displayUser(int id) {
		this.dbConnection = this.dbService.getConnection();
		User u = new User();
		
		String sql = "SELECT " + User.COLUMN_USERNAME + ", "+ User.COLUMN_USERID + " FROM " + User.TABLE_NAME + " WHERE " + User.COLUMN_USERID + " = ?";
		System.out.println(sql);

		ResultSet rs = null;

		try {
			this.ps = this.dbConnection.prepareStatement(sql);
			this.ps.setInt(1, id);
			
			rs = this.ps.executeQuery();
			while (rs.next()){
				u.setUserid(rs.getInt(User.COLUMN_USERID));
				u.setUsername(rs.getString(User.COLUMN_USERNAME));
			}
			
			rs.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			this.dbService.cleanUp();
		}
		
		return u;
	}	
	

}