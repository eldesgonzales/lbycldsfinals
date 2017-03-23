package net.tutorial.utilities;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.tutorial.beans.User;

public class UserService extends DBService{
	private static PreparedStatement ps = null;

	public User checkLogin(User u, int filter) {
		this.dbConnection = getConnection();
		System.out.println(u.getUserid() + " checklogin");
		User user = new User();
		String mode = "";
		boolean status = false;

		if (filter == 0) //login
			mode = User.COLUMN_USERNAME + "=? AND " + User.COLUMN_PASSWORD + "=?";
		else // if reconfirmation if sya tlga un
			mode = User.COLUMN_USERID + "=?";
		
		String sql = "SELECT * FROM " + User.TABLE_NAME + " WHERE " + mode;
		System.out.println(sql);
		
		ResultSet rs = null;

		try {
			ps = this.dbConnection.prepareStatement(sql);
			
			if (filter == 0) {
				ps.setString(1, u.getUsername());
				ps.setString(2, u.getPassword());
			} else
				ps.setInt(1, u.getUserid());
			
			rs = ps.executeQuery();
			
			while(rs.next()){
				user.setUserid(rs.getInt(User.COLUMN_USERID));
				status = true;
			}
			
			if (status == false)
				user = null;
			rs.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			this.cleanUp();
		}
		
		return user;
	}
	
	// register
	public int addUser(User u) {
		this.dbConnection = getConnection();
		
		int accountid = 0;
				
		String sql = "INSERT INTO " + User.TABLE_NAME + " (" +  User.COLUMN_USERNAME + ", " + User.COLUMN_PASSWORD + ") VALUES (?,?)";
		System.out.println(sql);
		ResultSet rs = null;

		try {
			ps = this.dbConnection.prepareStatement(sql);
			
			ps.setString(1, u.getUsername());
			ps.setString(2, u.getPassword());

			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}/* finally {
			this.cleanUp();
		}
		*/
		sql = "SELECT " + User.COLUMN_USERID + " FROM " + User.TABLE_NAME + " WHERE " + User.COLUMN_USERNAME + "=? AND "
				+ User.COLUMN_PASSWORD + "=?";
		System.out.println(sql);
		
		try {
			ps = this.dbConnection.prepareStatement(sql);
			ps.setString(1, u.getUsername());
			ps.setString(2, u.getPassword());
			
			rs = ps.executeQuery();
			
			while (rs.next()){
				accountid = rs.getInt(User.COLUMN_USERID);
			}

			rs.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			this.cleanUp();
		}
		
		return accountid;
	}

	// display info
	public User displayUser(int id) {
		this.dbConnection = getConnection();
		User u = new User();
		
		String sql = "SELECT " + User.COLUMN_USERNAME + ", "+ User.COLUMN_USERID + " FROM " + User.TABLE_NAME + " WHERE " + User.COLUMN_USERID + " = ?";
		System.out.println(sql);
		System.out.println(id);
		ResultSet rs = null;

		try {
			ps = this.dbConnection.prepareStatement(sql);
			ps.setInt(1, id);
			
			rs = ps.executeQuery();
			while (rs.next()){
				u.setUserid(rs.getInt(User.COLUMN_USERID));
				u.setUsername(rs.getString(User.COLUMN_USERNAME));
			}
			
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.cleanUp();
		}
		
		return u;
	}
	

}