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


	

}