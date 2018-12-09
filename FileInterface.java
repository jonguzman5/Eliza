package Eliza;

public interface FileInterface {
	public void appendToFile(String fileName, String text);
	public String readFile(String fileName);
	public String readDelimeterFile(String fileName, String delimeter);
}
