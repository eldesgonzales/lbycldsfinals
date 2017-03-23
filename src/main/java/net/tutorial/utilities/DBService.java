package net.tutorial.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import net.tutorial.beans.User;

public class DBService {
	private static DBService instance = new DBService();
	public  Connection dbConnection = null;
	private static PreparedStatement ps = null;

	protected DBService() {
		createTable();
	}

	public static DBService getInstance() {
		return instance;
	}


	public void cleanUp() {
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

	static Connection getConnection() {
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

	// initialization
	private void createTable() {
		this.dbConnection = getConnection();

		String createTableSQL = "CREATE TABLE IF NOT EXISTS `user` (" + "`userid` int(11) NOT NULL AUTO_INCREMENT,"
				+ "`username` varchar(45) DEFAULT NULL," + "`password` varchar(45) DEFAULT NULL," + "PRIMARY KEY (`userid`),"
				+ " UNIQUE INDEX `username_UNIQUE` (`username` ASC)) ENGINE=InnoDB DEFAULT CHARSET=utf8;";
		//System.out.println(createTableSQL);
		
		try {
			ps = this.dbConnection.prepareStatement(createTableSQL);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		createTableSQL = "CREATE TABLE IF NOT EXISTS `file` (" + "`fileid` int(11) NOT NULL AUTO_INCREMENT,"
				+ "`filename` varchar(45) DEFAULT NULL," + "`userid` varchar(45) DEFAULT NULL,"
				+ "`timestamp` timestamp DEFAULT CURRENT_TIMESTAMP," + "`visible` varchar(45) DEFAULT 'true'," 
				+ "PRIMARY KEY (`fileid`)"+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";		
		//System.out.println(createTableSQL);
		
		try {
			ps = this.dbConnection.prepareStatement(createTableSQL);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			cleanUp();
		}
	}	
}
