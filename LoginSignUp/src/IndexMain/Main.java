package IndexMain;

import java.sql.*;
import java.util.Scanner;

import LoginMain.LoginForm;
import SignUpMain.SignUpForm;
import UserCredentials.UserInformation;

public class Main {
	
	private static Scanner scan = new Scanner (System.in);

	public static void main(String[] args) {
		
		UserInformation user = new UserInformation();
		
		System.out.println("Sign up or Login!");
		String choice = scan.nextLine().toUpperCase();

		LoginForm Login = new LoginForm();
		SignUpForm SignUp = new SignUpForm();
		
		switch(choice) {
			case "SIGN UP":
				SignUpForm.SignUp(user);
			case "SIGNUP":
				SignUpForm.SignUp(user);
			case "LOGIN":
				LoginForm.Login(user);
		}
	}
}
