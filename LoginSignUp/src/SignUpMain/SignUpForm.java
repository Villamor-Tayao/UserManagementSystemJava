package SignUpMain;

import java.util.Scanner;
import java.sql.*;

import SQLSetup.DBConnect;
import UserCredentials.UserInformation;

public class SignUpForm {
	
	private static Scanner scan = new Scanner(System.in);
	
	public static void SignUp(UserInformation user) {
		
		while(true) {
			System.out.print("Enter Email: ");
			String Email = scan.nextLine().trim();
			
			if(!CheckEmail(user, Email)) continue;
			
			System.out.print("Enter Username: ");
			String UserName = scan.nextLine();
			
			if(!CheckUserName(user, UserName)) continue;
			
			System.out.print("Enter Password: ");
			String password = scan.nextLine();
			
			if(!CheckPassword(user, password)) continue;
			
			try {
				InsertData(user, Email, UserName, password);
				break;
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private static boolean CheckEmail(UserInformation user, String Email) {
		if(Email.isEmpty()) {
			System.out.println("Please enter your email");
			return false;
		}
		
		if(!Email.contains("@") || !Email.contains(".com")) {
			System.out.println("Please input a proper email address!");
			return false;
		}
		
		return true;
	}
	
	private static boolean CheckUserName(UserInformation user, String UserName) {
		if(UserName.isEmpty()) {
			System.out.println("Please enter your username!");
			return false;
		}
		
		if(UserName.length() < 8) {
			System.out.println("Username is too short!");
			return false;
		}
		
		return true;
	}
	
	private static boolean CheckPassword(UserInformation user, String password) {
		String specials = "!@#$%^&*()_+-={}|:\"<>?[]\\;',./";
		
		boolean hasSpecials = false;
		boolean hasDigits = false;
		boolean hasUppers = false;
		boolean hasLength = false;
		
		for(char c : specials.toCharArray()) {
			if(password.indexOf(c) != -1) {
				hasSpecials = true;
			}
		}
		
		
		for(char c : password.toCharArray()) {
			if(Character.isUpperCase(c)) {
				hasUppers = true;
			}
			
			if(Character.isDigit(c)) {
				hasDigits = true;
			}
		}
		
		if(password.length() > 7) {
			hasLength = true;
		}
		
		if(!hasSpecials) {
			System.out.println("Password needs special digits!");
			return false;
		}
		
		if(!hasDigits) {
			System.out.println("Password needs digits 0-9!");
			return false;
		}
		
		if(!hasUppers) {
			System.out.println("Password needs at least 1 capital leter!");
			return false;
		}
		
		if(!hasLength) {
			System.out.println("Password needs to have more than 8 characters!");
			return false;
		}
		
		return true;
	}
	
	private static void InsertData(UserInformation user, String Email, String UserName, String password) {
		  
		try (Connection connect = DBConnect.getConnection()){
			user.setEmail(Email);
			user.setUserName(UserName);
			user.setPassword(password);
			
			String query = "INSERT INTO USERS VALUES (FLOOR(100000000 + RAND() * 900000000), ?, ?, ?);";
			
			try(PreparedStatement stmnt = connect.prepareStatement(query)){
				
				stmnt.setString(1, user.getEmail());
				stmnt.setString(2, user.getUserName());
				stmnt.setString(3, user.getPassword());
				
				stmnt.executeUpdate();
				
				System.out.println("Account created successfully!");
				return;
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
