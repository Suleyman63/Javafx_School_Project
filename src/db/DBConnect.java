package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
	private static DBConnect instance = null;
	private Connection con;
	public DBConnect() {
		try {

			String yol = "jdbc:oracle:thin:@localhost:1521/ORCLCDB.localdomain";

			Class.forName("oracle.jdbc.driver.OracleDriver");

			 con = DriverManager.getConnection(yol, "suleyman", "suleyman63");

			System.out.println("Datenbank verbunden...");
		} catch (SQLException | ClassNotFoundException e) {
		
			e.printStackTrace();
		}
	}

	public static DBConnect getInstance() {
		if(instance==null) {
			instance = new DBConnect();
		}
		
		return instance;
	}
	
	
	public Connection connect() {
		return con;
	}
	

}
