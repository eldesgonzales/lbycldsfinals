package net.tutorial.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import net.tutorial.beans.File;
import net.tutorial.beans.User;

public class DBService {

	public static final int INSERT_RECORD = 1;
	public static final int UPDATE_RECORD = 2;

	private static DBService instance = new DBService();
	Connection dbConnection = null;
	private PreparedStatement ps = null;

	private DBService() {
		createTable();
	}

	public static DBService getInstance() {
		return instance;
	}

	private void cleanUp() {
		try {
			if (ps != null) {
				ps.close();
			}
			if (this.dbConnection != null) {
				this.dbConnection.close();
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	private Connection getConnection() {
		Connection dbConnection = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("MySQL JDBC Driver not found.");
			System.out.println(e.getMessage());
			return null;
		}

		try {
			
			EnvVariables envVar = new EnvVariables();
			Map<String, String> creds = envVar.getCredentials("cleardb");
			dbConnection = DriverManager.getConnection(creds.get("jdbcUrl"));
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}

		return dbConnection;
	}

	private void createTable() {
		this.dbConnection = getConnection();

		String createTableSQL = "CREATE TABLE IF NOT EXISTS `user` (`userid` int(11) NOT NULL AUTO_INCREMENT,"
				+ "`username` varchar(45) DEFAULT NULL, `password` varchar(45) DEFAULT NULL, PRIMARY KEY (`userid`),"
				+ "UNIQUE INDEX `username_UNIQUE` (`username` ASC)) ENGINE=InnoDB DEFAULT CHARSET=utf8;";
		System.out.println(createTableSQL);
		try {
			ps = this.dbConnection.prepareStatement(createTableSQL);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		createTableSQL = "CREATE TABLE IF NOT EXISTS `files` (" + "`fileid` int(11) NOT NULL AUTO_INCREMENT,"
				+ "`filename` varchar(45) DEFAULT NULL," + "`userid` int(11) DEFAULT NULL,"
				+ "`timestamp` timestamp DEFAULT CURRENT_TIMESTAMP," + "`visible` varchar(45) DEFAULT 'true'," 
				+ "PRIMARY KEY (`fileid`)"+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";	
		System.out.println(createTableSQL);
		try {
			ps = this.dbConnection.prepareStatement(createTableSQL);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			cleanUp();
		}//*/
	}

	/* ALL FUNCTIONS HERE */
	// checks if tama ung credentials
	public User checkLogin(User u) {
		User u2 = new User();
		
		this.dbConnection = getConnection();
		boolean verified = false;
		
		String sql = "SELECT * FROM " + User.TABLE_NAME + " WHERE " + User.COLUMN_USERNAME + "=? AND " + User.COLUMN_PASSWORD + "=?";
		System.out.println(sql);
		
		ResultSet rs = null;

		try {
			ps = this.dbConnection.prepareStatement(sql);
			ps.setString(1, u.getUsername());
			ps.setString(2, u.getPassword());

			rs = ps.executeQuery();
			
			while (rs.next()){
				verified = true;
				u2.setUserid(rs.getInt(User.COLUMN_USERID));
				u2.setUsername(rs.getString(User.COLUMN_USERNAME));
			}
			
			if (!verified)
				u2 = null;
			
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			cleanUp();
		}
		
		return u2;
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
			e.printStackTrace();
		}
		
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
			e.printStackTrace();
		} finally {
			cleanUp();
		}
		
		return accountid;
	}
	
	// check if username already exists
	public boolean checkExists(String id) {
		this.dbConnection = getConnection();
		
		boolean unique = false;
				
		String sql = "SELECT * FROM " + User.TABLE_NAME + " WHERE " + User.COLUMN_USERNAME  + " = ?";
		System.out.println(sql);
		ResultSet rs = null;

		try {
			ps = this.dbConnection.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();
			
			unique = rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			cleanUp();
		}
		
		return unique;
	}	
	
	// display info
	public User displayUser(int id) {
		this.dbConnection = getConnection();
		User u = new User();
		
		String sql = "SELECT " + User.COLUMN_USERNAME + ", "+ User.COLUMN_USERID + " FROM " + User.TABLE_NAME + " WHERE " + User.COLUMN_USERID + " = ?";
		System.out.println(sql);

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
			cleanUp();
		}
		
		return u;
	}	
	
	// display files
	public ArrayList<File> displayFiles(int id) {
		this.dbConnection = getConnection();

		ArrayList<File> fileList = new ArrayList<File>();
		String sql = "SELECT * FROM files WHERE userid=?";
		//SELECT * FROM " + File.TABLE_NAME + " WHERE " + File.COLUMN_VISIBLE + " = 'true' AND " 
		// + File.COLUMN_USERID + " = ? 
		System.out.println(sql);
		
		ResultSet rs = null;

		try {
			ps = this.dbConnection.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery(sql);

			while (rs.next()) {
				File f = new File();
				
				f.setFileid(rs.getString(File.COLUMN_FILEID));
				f.setFilename(rs.getString(File.COLUMN_FILENAME));
				f.setTimestamp(rs.getString(File.COLUMN_TIMESTAMP));
				f.setUserId(rs.getInt(File.COLUMN_USERID));
				
				fileList.add(f);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			cleanUp();
		}

		return fileList;
	}	
	
	// upload file
	public void addFile(File f){
		this.dbConnection = getConnection();
		
		String sql = "INSERT INTO " + File.TABLE_NAME + "(" + File.COLUMN_FILENAME + ", " + File.COLUMN_USERID
				+ ") VALUES (?,?)";
		System.out.println(sql);
		
		try {
			ps = this.dbConnection.prepareStatement(sql);
			
			ps.setString(1, f.getFilename());
			ps.setInt(2, f.getUserId());
			
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			cleanUp();
		}
	}
	
	// delete File
	public void deleteFile(String id) {
		this.dbConnection = getConnection();

		String sql = "UPDATE FROM " + File.TABLE_NAME + "SET " + File.COLUMN_VISIBLE + " = `false` WHERE " 
				+ File.COLUMN_FILEID + " =?";
		System.out.println(sql);
		
		try {
			ps = this.dbConnection.prepareStatement(sql);
			ps.setString(1, id);
			this.ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			cleanUp();
		}

	}

}