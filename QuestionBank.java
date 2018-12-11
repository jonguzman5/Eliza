package Eliza;

import java.util.Random;

public class QuestionBank {

	private String [] questions;
	private int currQuestionIndex;
	public static final int NUM_QUESTIONS = 10; 
	
	public QuestionBank(){
		questions = new String [NUM_QUESTIONS]; //increase array size if you will add more questions
	}
	
	private void populateQuestionArray(){
		questions[0]= "Which three words describe you best?";
		questions[1]= "Which is your best feature?";
		questions[2]= "Which common saying or phrase describes you?";
		questions[3]= "What’s the best thing that’s happened to you this week?";
		questions[4]= "Who was your role model when you were a child?";
		questions[5]= "Who was your favorite teacher and why?";
		questions[6]= "What was your favorite subject at school?";
		questions[7]= "What did you want to be when you grew up?";
		questions[8]= "If you could have one wish come true what would it be?";
		questions[9]= "Which would you prefer — three wishes over five years or one wish right now?";
}
	public String getNextQuestion() {
		String random = null;
		for(int i = 0; i < questions.length; i++){
			populateQuestionArray();
		}
		random = questions[(int)(Math.random() * questions.length)];
		return random;
	}

}

