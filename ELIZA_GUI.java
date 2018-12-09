package Eliza;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ELIZA_GUI extends JFrame{
	private JPanel jpMain;
	private JLabel promptLbl;
	private JTextField inputTF;
	private BUTTONS BTNS; 
	private JTextArea logTA;
	
	public ELIZA_GUI(){
		JPanel jpMain = new JPanel();
		jpMain.setLayout(new GridLayout(4, 1));
		
		promptLbl = new JLabel("Welcome to the Eliza therapy session. \"Start Session\" to begin");
		jpMain.add(promptLbl);
		
		inputTF = new JTextField();
		inputTF.setSize(10, 50);
		inputTF.setVisible(false);
		jpMain.add(inputTF);
		
		BTNS = new BUTTONS();
		jpMain.add(BTNS);
		
		logTA = new JTextArea(50, 500);
		logTA.setVisible(false);
		jpMain.add(logTA, BorderLayout.NORTH);
		
		setSize(500, 500);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		add(jpMain);
	}

	private class BUTTONS extends JPanel implements ActionListener{
		private FileLogic FL = new FileLogic();
		private QuestionBank QB = new QuestionBank();
		private String SESSION = "myFiles/Session.txt";
		private String ANSWERS = "myFiles/Answers.txt";
		int quesNum = 0;
		int seshNum = 1;
		
		private JButton startBtn;
		private JButton logBtn, longestBtn, alphBtn;
		public static final String START = "Start Session";
		private static final String NEXT = "Next Question";
		private static final String FINISH = "Finish Session";
		private static final String LOG = "View all Q & A";
		private static final String LONGEST = "View Longest Words";
		private static final String ALPH = "View Longest Words Alphabetically";
		
		public BUTTONS(){
			setLayout(new GridLayout(5, 1));
			startBtn = new JButton(START);
			
			logBtn = new JButton(LOG);
			longestBtn = new JButton(LONGEST);
			alphBtn = new JButton(ALPH);
				
			startBtn.addActionListener(this);
			logBtn.addActionListener(this);
			longestBtn.addActionListener(this);
			alphBtn.addActionListener(this);
			
			logBtn.setVisible(false);
			longestBtn.setVisible(false);
			alphBtn.setVisible(false);
			
			add(startBtn);
			add(logBtn);
			add(longestBtn);
			add(alphBtn);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			String btnFace = e.getActionCommand();
			
			switch(btnFace){
				case START:
					inputTF.setVisible(true);
					startBtn.setText(NEXT);
					String QUES = QB.getNextQuestion();
					promptLbl.setText("Session #" + seshNum + " Q" + (quesNum+1) + ". " + QUES);
				break;
				case NEXT: 
					quesNum++;
					if(quesNum < QB.NUM_QUESTIONS){
						FL.appendToFile(SESSION, promptLbl.getText());//ques
						FL.appendToFile(SESSION, inputTF.getText());//ans
						FL.appendToFile(ANSWERS, inputTF.getText());//ans only
						QUES = QB.getNextQuestion();
						promptLbl.setText("Session #" + seshNum + " Q" + (quesNum+1) + ". " + QUES);
					}
					if(quesNum == QB.NUM_QUESTIONS){
						FL.appendToFile(SESSION, promptLbl.getText());
						FL.appendToFile(SESSION, inputTF.getText());
						FL.appendToFile(ANSWERS, inputTF.getText());
						startBtn.setText(FINISH);
						logBtn.setVisible(true);
						longestBtn.setVisible(true);
						alphBtn.setVisible(true);	
					}
					//inputTF.setText("");
				break;
				case FINISH:
					quesNum = 0;
					seshNum++;
					promptLbl.setText("Press \"Start Session\" to begin session " + seshNum);
					startBtn.setText(START);
					inputTF.setVisible(false);
					logBtn.setVisible(false);
					longestBtn.setVisible(false);
					alphBtn.setVisible(false);	
					
					String theContent = FL.readDelimeterFile(ANSWERS, " ");
					String[]arr = theContent.split("\n");
					
					int currMax = 0;
					String longestWord = null;
					for(int i = 0; i < arr.length; i++){
						System.out.println(arr[i]);
						if(arr[i].length() > currMax){
							currMax = arr[i].length();
							longestWord = arr[i]; 
						}	
					}
					
					int currMin = 10;
					String shortestWord = null;
					for(int i = 0; i < arr.length; i++){
						if(arr[i].length() < currMin){
							currMin = arr[i].length();
							shortestWord = arr[i]; 
						}	
					}
					
					String analysis = "Wow " + longestWord + " and " + shortestWord + " seem very important to you";
					FL.appendToFile(SESSION, analysis);
					//RESET NOT WORKING
					currMax = 0;
					longestWord = null;
					currMin = 10;
					shortestWord = null;
				break;
				case LOG:
					String fileContent = FL.readFile(SESSION);
					logTA.setVisible(true);
					logTA.setEditable(false);
					logTA.append(fileContent);
				break;
				case LONGEST://WORKS @ 2 ROUND MIN
					theContent = FL.readDelimeterFile(ANSWERS, " ");
					arr = theContent.split("\n");
					currMax = 0;
					longestWord = null;
					String[]temp = new String[arr.length];
					
					for(int i = 0; i < arr.length; i++){
						if(arr[i].length() > currMax){
							currMax = arr[i].length();
							longestWord = arr[i];
							temp[i] = arr[i];
							//System.out.println(temp[i]);
						}
					}
					
					logTA.setVisible(true);
					for(int i = 0; i < temp.length; i++){
						if(arr[i] != null){
							logTA.append(temp[i]);//APPENDS ONLY WHEN LONGWORD RECORD BEATEN
							logTA.append(" ");
						}
					}
				break;
				case ALPH:
					theContent = FL.readDelimeterFile(ANSWERS, " ");
					arr = theContent.split("\n");
					currMax = 0;
					longestWord = null;
					temp = new String[arr.length];
					
					for(int i = 0; i < arr.length; i++){
						if(arr[i].length() > currMax){
							currMax = arr[i].length();
							longestWord = arr[i];
							temp[i] = arr[i];
						}
					}
					logTA.setVisible(true);
					for(int i = 0; i < temp.length; i++){
						if(arr[i] != null){
							logTA.append(temp[i]);
							logTA.append(" ");
						}
					}
				break;
			}
		}
		
	}
}
