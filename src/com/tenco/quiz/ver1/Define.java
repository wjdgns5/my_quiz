package com.tenco.quiz.ver1;

public class Define {
	// 예를 들어 이것도 분리할 수도 있다. 라는것을 보여줌
	private static final String ADD_QUIZ = " insert into quiz(question, answer) values (?,?) ";
	private static final String VIEW_QUIZ = " select * from quiz ";
	private static final String RANDOM_QUIZ = " select * from quiz order by rand() -- rand() limit 1  ";

}
