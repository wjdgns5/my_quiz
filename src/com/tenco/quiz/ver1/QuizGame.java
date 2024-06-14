package com.tenco.quiz.ver1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.tenco.quiz.DBConnectionManager;

public class QuizGame {
	
	private static final String ADD_QUIZ = " insert into quiz(question, answer) values (?,?) ";
	private static final String VIEW_QUIZ = " select * from quiz ";
	private static final String RANDOM_QUIZ = " select * from quiz order by rand() -- rand() limit 1  ";

	public static void main(String[] args) {
		
		// try -catch - resource : 자원을 자동으로 닫아준다.
		try (
				Connection conn = DBConnectionManager.getConnection(); // 자바프로그램의 
				Scanner scanner = new Scanner(System.in);
				){
			// C R U D 구현 
			while(true) {
				PrintMenu(); // ctrl + 1 
				
				int choice = scanner.nextInt(); // 블록킹 
				
				if(choice == 1) {
					// 퀴즈 문제 추가 --> 함수로 만들기
					addQuizQuestion(conn, scanner);
				} else if(choice == 2) {
					// 퀴즈 문제 조회 --> 함수로 만들기
					viewQuizQuestion(conn); // 블록 잡아서 alt + shift + m 또는 ctrl + 1 클릭
				} else if(choice == 3) {
					// 퀴즈 게임 시작 --> 함수로 만들기
					playQuizGame(conn, scanner); // ctrl + 1 클릭
				} else if(choice == 4) {
					// 퀴즈 종료
					System.out.println("프로그램을 종료합니다.");
					break;
				} else {
					// 그 외의 입력이 들어왔을 때
					System.out.println("잘못된 선택입니다. 다시 시도하세요.");
				}
				
			} // end of while() 
			
			
		} catch (Exception e) {
			
		} // end of try - catch
		
	} // end of main

	private static void PrintMenu() {
		System.out.println();
		System.out.println("----------------------------------------");
		System.out.println("1번 퀴즈 문제 추가");
		System.out.println("2번 퀴즈 문제 조회");
		System.out.println("3번 퀴즈 게임 시작");
		System.out.println("4번 종료");
		System.out.print("옵션을 선택하세요: ");
	}

	private static void playQuizGame(Connection conn, Scanner scanner) {
		// 퀴즈 게임 시작 --> 함수로 만들기
		
		try(
				PreparedStatement pstmt = conn.prepareStatement(RANDOM_QUIZ);
				ResultSet rs = pstmt.executeQuery();
				) {
					if(rs.next()) {
						String question = rs.getString("question");
						String answer = rs.getString("answer");
						
						System.out.println("퀴즈 문제 : " + question);
						// 버그처리
						scanner.nextLine();
						System.out.print("당신의 답 : ");
						String userAnswer = scanner.nextLine();
						
						if(userAnswer.equalsIgnoreCase(userAnswer)) { // equalsIgnoreCase : 대, 소문자 무시
							System.out.println("정답입니다! 점수를 얻었습니다.");
						} else {
							System.out.println("오답입니다! ");
							System.out.println("퀴즈의 정답은 -->" + answer);
						}
						
					} else {
						System.out.println("죄송합니다 아직 퀴즈 문제를 만들고 있습니다.");
					}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	} // end of playQuizGame

	private static void viewQuizQuestion(Connection conn) {
		// 2. Connection 객체를 활용해서 Query를 날려야 한다.
		try {
			PreparedStatement psmt = conn.prepareStatement(VIEW_QUIZ);
			ResultSet resultSet = psmt.executeQuery();
			
			while(resultSet.next()) { // next 다음이 있니 없니 true false 
				System.out.println("문제 ID : " + resultSet.getInt("id"));
				System.out.println("문제 : " + resultSet.getString("question"));
				System.out.println("정답 : " + resultSet.getString("answer"));
				if(!resultSet.isLast()) {					
					System.out.println("----------------------------------------");
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	} // end of viewQuizQuestion

	private static void addQuizQuestion(Connection conn, Scanner scanner) {
		// 1. 사용자 퀴즈와 답을 입력 받아야 하는 기능이 필요하다.
		System.out.println("퀴즈 문제를 입력하세요.");
		scanner.nextLine(); // 1번 누르고 enter 누르면 1 + \n이 실행되는데 여기서 걸려서 멈춘다.
		String question = scanner.nextLine();
		
		System.out.println("퀴즈 정답을 입력하세요.");
		String answer = scanner.nextLine();
		
		// 2. Connection 객체를 활용해서 Query를 날려야 한다.
		try(
				PreparedStatement pstmt = conn.prepareStatement(ADD_QUIZ);
				) {
					pstmt.setString(1, question);
					pstmt.setString(2, answer);
					int rowInsertedCount = pstmt.executeUpdate(); // 출력하려면 사용
					System.out.println("추가된 행의 수 : " + rowInsertedCount);
		} catch (SQLException e) {
			e.printStackTrace();
		} // end of try - catch
				
	} // end of addQuizQuestion()


	// 퀴즈를 추가하는 함수 만들기
	// 기능 만들기
	
	// 1. 사용자 퀴즈와 답을 입력 받아야 하는 기능이 필요하다.
	// 2. Connection 객체를 활용해서 Query를 날려야 한다.
	
} // end of class
