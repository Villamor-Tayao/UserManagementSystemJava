package SQLSetup;

import java.sql.*;

public class DBConnect {
	
	private static Connection connect;
	 
	public static Connection getConnection() {
		 
		 String db_url = System.getenv("DB_URL");
		 String db_user = System.getenv("DB_USER");
		 String db_pass = System.getenv("DB_PASS");
		 
		 connect = null;
		 
		 try{
			 connect = DriverManager.getConnection(db_url, db_user, db_pass);
			 
			 if(connect == null) {
				 System.out.println("Bulok setup mo!");
				 return null;
			 }
			 
			 return connect;
		 } catch (SQLException e) {
			 e.printStackTrace();
		 } 
		 
		 return null;
	 }
}
