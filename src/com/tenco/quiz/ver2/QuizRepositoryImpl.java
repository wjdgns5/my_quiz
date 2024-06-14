package com.tenco.quiz.ver2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tenco.quiz.DBConnectionManager;

public class QuizRepositoryImpl implements QuizRepository {
	
	public static final String ADD_QUIZ = " insert into quiz(question, answer) values (?,?) ";
	public static final String VIEW_QUIZ = " select * from quiz ";
	public static final String RANDOM_QUIZ = " select * from quiz order by rand() -- rand() limit 1  ";

	@Override
	public int addQuizQuestion(String question, String answer) throws SQLException {
		
		int resultRowCount = 0;
		
		
		try(
				Connection conn = DBConnectionManager.getConnection();
			) {
				PreparedStatement pstmt = conn.prepareStatement(ADD_QUIZ);
				// 트랜잭션 처리 가능 - 수동모드 커밋 사용 가능
				pstmt.setString(1, question);
				pstmt.setString(2, answer);
				pstmt.executeUpdate();
		}
		return resultRowCount;
	}

	@Override
	public List<QuizDTO> viewQuizQuestion()throws SQLException {
		
		List<QuizDTO> list = new ArrayList<>();
		
		try(
				Connection conn = DBConnectionManager.getConnection()
			) {
				PreparedStatement pstmt = conn.prepareStatement(VIEW_QUIZ);
				ResultSet rs = pstmt.executeQuery();
				while(rs.next()) {
					int id = rs.getInt("id");
					String question = rs.getString("question");
					String answer = rs.getString("answer");
					
					list.add( new QuizDTO(id, question, answer) );
				}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return list;
	}

	@Override
	public QuizDTO playQuizGame()throws SQLException {
		
		QuizDTO quizDTO = null;
		
		try (Connection conn = DBConnectionManager.getConnection()) {
			PreparedStatement pstmt = conn.prepareStatement(RANDOM_QUIZ);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				
				int id = rs.getInt("id");
				String question = rs.getString("question");
				String answer = rs.getString("answer");
				quizDTO = new QuizDTO(id, question, answer);
			}
	
		} 
		
		return quizDTO;
	}
	
} // end of class
	
