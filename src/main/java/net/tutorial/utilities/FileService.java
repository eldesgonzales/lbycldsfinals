package net.tutorial.utilities;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import net.tutorial.beans.File;

public class FileService extends DBService{
	private static PreparedStatement ps = null;
	
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
			System.out.println(e.getMessage());
		} finally {
			this.cleanUp();
		}
	}
	
	// display files
	public ArrayList<File> displayFiles(int id) {
		this.dbConnection = getConnection();

		ArrayList<File> fileList = new ArrayList<File>();
		String sql = "SELECT " + File.COLUMN_FILEID + ", " + File.COLUMN_FILENAME + ", " + File.COLUMN_USERID
				+ ", " + File.COLUMN_TIMESTAMP + " FROM " + File.TABLE_NAME + " WHERE " + File.COLUMN_USERID + " = ? AND "
				+ File.COLUMN_VISIBLE + " = true";
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
			System.out.println(e.getMessage());
		} finally {
			this.cleanUp();
		}

		return fileList;
	}
	
	// delete File
	public void deleteFile(String id) {
		this.dbConnection = getConnection();

		try {
			String sSQL = "UPDATE FROM " + File.TABLE_NAME + "SET " + File.COLUMN_VISIBLE + " = `false` WHERE " 
					+ File.COLUMN_FILEID + " =?";
			ps = this.dbConnection.prepareStatement(sSQL);
			ps.setString(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			this.cleanUp();
		}
	}
}