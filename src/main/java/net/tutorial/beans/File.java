package net.tutorial.beans;

import java.io.Serializable;

public class File implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public static final String TABLE_NAME = "file";
	public static final String COLUMN_FILEID = "fileid";
	public static final String COLUMN_FILENAME = "filename";
	public static final String COLUMN_USERID = "userid";
	public static final String COLUMN_TIMESTAMP = "timestamp";
	public static final String COLUMN_VISIBLE = "visible";
	
	private String fileid;
	private int userid;
	private String timestamp;
	private String visible;
	private String filename;

	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getFileid() {
		return fileid;
	}
	public void setFileid(String fileid) {
		this.fileid = fileid;
	}
	public int getUserId() {
		return userid;
	}
	public void setUserId(int userid) {
		this.userid = userid;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getVisible() {
		return visible;
	}
	public void setVisible(String visible) {
		this.visible = visible;
	}
	
	
	
}
