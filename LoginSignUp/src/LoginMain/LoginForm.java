package LoginMain;

import java.sql.*;
import java.util.Scanner;

import SQLSetup.DBConnect;

import UserCredentials.UserInformation;

public class LoginForm {
	
	private static Scanner scan = new Scanner(System.in);

	private static String query;
	private static String inputEmailUser;
	
	public static void Login(UserInformation user) {
		
		while(true) {
			
			//ArrayList<String> setUsername = new ArrayList<String>();
			
			System.out.print("Email or Username: ");
			String EmailUser = scan.nextLine().trim();
			
			CheckEmailUser(user, EmailUser);
			
			System.out.print("Password: ");
			String password = scan.nextLine();
			
			if(!CheckPassword(user, password)) continue;
			
			if(!user.getEmail().isEmpty()){
				query = "SELECT * FROM USERS WHERE EMAIL = ? AND PASSWORD = ?";
				inputEmailUser = user.getEmail();
			} else {
				query = "SELECT * FROM USERS WHERE USERNAME = ? AND PASSWORD = ?";
				inputEmailUser = user.getUserName();
			}
			
			try(Connection connect = DBConnect.getConnection();
				PreparedStatement stmnt = connect.prepareStatement(query)){
					
					stmnt.setString(1, inputEmailUser);
					stmnt.setString(2, password);
					
					try(ResultSet result = stmnt.executeQuery()){
						
						if(!result.next()) {
							System.out.println();
							System.out.println("#########################");
							System.out.println("    NO RECORDS FOUND!");
							System.out.println("#########################");
							System.out.println();
							continue;
						}
						
						System.out.println();
						System.out.println("Welcome! " + user.getUserName());
						System.out.println();
						break;
					}
					
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void CheckEmailUser(UserInformation user, String EmailUser) {
		
		user.setEmail("");
		user.setUserName("");
		
		if(EmailUser.isEmpty()) {
			System.out.println("Please enter your email or username!");
			return;
		}
		
		if (EmailUser.contains("@") && EmailUser.contains(".com")) {
			user.setEmail(EmailUser);
			return;
			
		} else {
			user.setUserName(EmailUser);
			return;
		}
		
	}
	
	private static boolean CheckPassword(UserInformation user, String password) {
		
		if(password.isEmpty()) {
			System.out.println("Please enter your password");
			return false;
		}
		
		user.setPassword(password);
		
		return true;
	}
}
