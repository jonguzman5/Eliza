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
						String fileName = "myFiles/Session.txt";
						FL.appendToFile(fileName, promptLbl.getText());//ques
						FL.appendToFile(fileName, inputTF.getText());//ans
						QUES = QB.getNextQuestion();
						promptLbl.setText("Session #" + seshNum + " Q" + (quesNum+1) + ". " + QUES);
					}
					if(quesNum == QB.NUM_QUESTIONS){
						String fileName = "myFiles/Session.txt";
						FL.appendToFile(fileName, promptLbl.getText());
						FL.appendToFile(fileName, inputTF.getText());
						startBtn.setText(FINISH);
						logBtn.setVisible(true);
						longestBtn.setVisible(true);
						alphBtn.setVisible(true);	
					}
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
					//System.exit(0);
				break;
				case LOG:
					String fileName = "myFiles/Session.txt";
					String fileContent = FL.readFile(fileName);
					logTA.setVisible(true);
					logTA.setEditable(false);
					logTA.append(fileContent);
				break;
				case LONGEST:
					fileName = "myFiles/Session.txt";
					String theContent = FL.readDelimeterFile(fileName, " ");
					//System.out.println(theContent);
					String[]arr = theContent.split("\n");
					int currMax = 0;
					String longestStr = null;
					for(int i = 0; i < arr.length; i++){
						System.out.println(arr[i]);
						if(arr[i].length() > currMax){
							currMax = arr[i].length();
							longestStr = arr[i]; 
							//System.out.println(longestStr);
							promptLbl.setText(longestStr);
						}	
					}
				break;
				case ALPH:
					fileName = "myFiles/Session.txt";
					theContent = FL.readDelimeterFile(fileName, " ");
					arr = theContent.split("\n");
					currMax = 0;
					longestStr = null;
					String[]temp = new String[arr.length];
					for(int i = 0; i < arr.length; i++){
						if(arr[i].length() > currMax){
							currMax = arr[i].length();
							longestStr = arr[i];
							temp[i] = arr[i];
							//System.out.println(temp[i]);
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
