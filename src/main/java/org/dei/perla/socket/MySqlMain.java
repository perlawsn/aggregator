package org.dei.perla.socket;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class MySqlMain {
	
	
	
	public static void main(String args[]){
		Connection con = null;
		Statement cmd = null;
		
		MySqlPerLa myPerla = new MySqlPerLa();
		try {
		con = myPerla.connect("jdbc:mysql://localhost/perla_database");
		cmd = con.createStatement();
		
		
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String insertingQuery="INSERT INTO st_el (nome, cognome, eta)"
				+ "VALUES (2, 3, 4);";
		
		try {
			cmd.executeUpdate(insertingQuery);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
