package Eliza;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class FileLogic implements FileInterface{

	@Override
	public void appendToFile(String fileName, String text) {
		PrintWriter outStream = null;
		try {
			outStream = new PrintWriter(new FileOutputStream(fileName, true));
			outStream.println(text);
		}
		catch(FileNotFoundException e){
			System.err.println("File: " + fileName + " could not be created");
			e.printStackTrace();
		}
		finally {
			if(outStream != null){
				outStream.close();
			}
			System.out.println(fileName + " appending sequence complete");
		}	
	}

	@Override
	public String readFile(String fileName) {
		Scanner inStream = null;
		String fileContents = "";
		int lineNum = 0;
		try {
			File theFileObj = new File(fileName);
			inStream = new Scanner(theFileObj);
			
			while(inStream.hasNextLine()){
				fileContents += inStream.nextLine() + "\n";
			}
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		finally {
			if(inStream != null){
				inStream.close();
			}
		}
		return fileContents;
	}

	@Override
	public String readDelimeterFile(String fileName, String delimeter) {
		Scanner inStream = null;
		String token = "";
		String fileContent = "";
		try {
			File theFile = new File(fileName);
			if(theFile.exists() && theFile.canRead()){
				inStream = new Scanner(theFile);
				inStream.useDelimiter(delimeter);
				while(inStream.hasNextLine()){
					String lineIn = inStream.nextLine();
					String[]tokens = lineIn.split(delimeter);
					for(int i = 0; i < tokens.length; i++){
						fileContent += tokens[i] + "\n";
					}
				}
			}
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		finally {
			if(inStream != null){
				inStream.close();
			}
			System.out.println(fileName + " delimeting sequence complete");
		}
		return fileContent;
	}
	
}
