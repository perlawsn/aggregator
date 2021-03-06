package org.dei.perla.socket;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MySqlPerLa {
	
	private  String hostAddr = "jdbc:mysql://localhost";
	private  Connection con;
	private Statement cmd;
	
	/**
	 * Classe per la connessione al database MySql.
	 * @param hostAddr2
	 * @return
	 * @throws SQLException
	 */
	
 public Connection connect(String hostAddr2) throws SQLException {
		//Connection opening and declaration of the Statement
		//If the connection is already open, it closes it and reopen with 
		//new parameters
		if (con!=null){
		con.close();
		this.con = DriverManager.getConnection(hostAddr2, "root", "pass");
		this.cmd=con.createStatement();
		System.out.println(con.toString());
		return con;
		}
		else{
			this.con = DriverManager.getConnection(hostAddr2, "root", "pass");
			this.cmd=con.createStatement();
			
			System.out.println(con.toString());
			return con;
		}
	}
	
	private void closeConnection() {
		System.out.println("Cluster is shutting down...");
	      try {
			cmd.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
