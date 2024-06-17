package com.tenco.quiz.ver4;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginExample {
	
	// 개발 테스트를 위한 로깅 처리 및 로그
	
	private static final Logger LOGGER = Logger.getLogger(LoginExample.class.getName());
	
	public static void main(String[] args) {
		
		// DataSource를 활용한 Connection 객체를 사용하자.
		
		try {
			// HikariCP 가 담김
			Connection conn = DBConnectionManager.getConnection();
			// username, password를 받아서 확인 해야 한다.
			
			Scanner scanner = new Scanner(System.in);
			
			System.out.print("username을 입력하세요 : ");
			String username = scanner.nextLine();
			
			System.out.print("passoword를 입력하세요 : ");
			String password = scanner.nextLine();
			
		if (authenticateUser(conn, username, password)) {
			System.out.println("로그인 성공 ! ");
		} else {
			System.out.println("로그인 실패 - username과 password를 확인해주세요.");
		}
			
			// select * from users where username = '홍길동' and passowrd = 'asd123';
			
		} catch (SQLException e) {
			LOGGER.log(Level.INFO, "MySQL 연결 오류 ");
			e.printStackTrace();
	
		}
		
	} // end of main
	
	private static boolean authenticateUser(Connection conn, String username, String password) {
		
		String query = " select * from users where username = ? and password = ? ";
		boolean result = false;
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			result = rs.next();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}

} // end of class
