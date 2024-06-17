package com.tenco.quiz.ver3;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DBConnectionManager {
	
	/*
	 * 커넥션 풀을 활용하는 예제로 수정해 보자.
	 * HikariCP-5.1.0.jar lib 설정
	 */
	
	private static HikariDataSource dataSource;

	
	private static final String URL = "jdbc:mysql://localhost:3306/quizdb?serverTimezone=Asia/Seoul";
	private static final String USER = "root";
	private static final String PASSWORD = "asd123";
	
	static {
		// HikariCP 를 사용하기 위한 설정이 필요하다.
		// HikariConfig --> 제공해줘서 이 클래스를 활용해서 설정을 상세히 할 수 있다.
		HikariConfig config = new HikariConfig(); // 객체를 메모리에 올려본다.
		config.setJdbcUrl(URL); 
		config.setUsername(USER);
		config.setPassword(PASSWORD);
		
		config.setMaximumPoolSize(10); // 최대 연결 수 설정 10개이다.
		
		dataSource = new HikariDataSource(config); // 기본 객체가 생성이 된다.
		
	} 
	
	public static Connection getConnection() throws SQLException{
		System.out.println("HIKAriCP 를 사용한 DATA Source 활용");
		return dataSource.getConnection();
	}
	
	// 테스트 코드 확인
	public static void main(String[] args) {
		
		try {
			Connection conn = DBConnectionManager.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	} // end of main
	
	// 정적 메서드(함수) 커넥션 객체를 리턴하는 함수를 만들어 보자
	// 기본 JDBC 드라이버 사용 버전 
//	public static Connection getConnection() throws SQLException {
//		return DriverManager.getConnection(URL, USER, PASSWORD);
//	}
	

} // end of class
