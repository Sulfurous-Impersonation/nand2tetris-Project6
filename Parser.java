import java.util.Scanner;
import java.io.File;

public class Parser 
{
	private String myInstruct;
	private Scanner myReader;
	private int lineNumber;

	Parser(String fileName)
	{
		myInstruct = "";
		lineNumber = 0;
		try {
			myReader = new Scanner(new File(fileName));
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/*
	 * 
	 * Getting current instruction
	 * 
	 */
	public boolean hasMoreLines()
	{
		//Checks if there is more work to do
		return myReader.hasNextLine();
	}

	public String advance()
	{
		//Gets next instruction and makes it current
		//returns null if there's no next line
		if (hasMoreLines())
		{
			myInstruct = myReader.nextLine();
			removeComment();
			myInstruct = myInstruct.trim();
			if (!myInstruct.equals("") && !instructionType().equals("L_INSTRUCTION"))
			{
				lineNumber++;
				System.out.println("---------myInstruct: " + myInstruct + "\n---------lineNumber: " + lineNumber);
			}
			System.out.println("Instruction: " + myInstruct + "\nType: " + instructionType());
			return myInstruct;
		}
		else
			return null;
	}

	/*
	 * 
	 * Parsing the current instruction
	 * 
	 */
	public String instructionType()
	{
		//return instruction type, or null if none
		if (myInstruct == null || myInstruct.equals("")) //check for null
			return null;
		if (myInstruct.substring(0, 1).contains("/")) //check for leading comment
			return null;
		
		if (myInstruct.contains("@"))
			return "A_INSTRUCTION";
		if (myInstruct.contains("=") || myInstruct.contains(";"))
			return "C_INSTRUCTION";
		if (myInstruct.contains("("))
			return "L_INSTRUCTION";
		
		return null; //else return null
	}
	
	public String symbol()
	{
		//retun instruction's symbol field
		String symbol = null;
		if (myInstruct.contains("@"))
		{
			int start = myInstruct.indexOf('@');
			symbol = myInstruct.substring(start+1);
		}
		else if (myInstruct.contains("("))
		{
			int start = myInstruct.indexOf('(');
			int end = myInstruct.indexOf(')');
			symbol = myInstruct.substring(start+1, end);
		}
		return symbol;
	}

	public String dest()
	{
		//return instruction's dest field
		if (myInstruct.contains("="))
			return myInstruct.substring(0, myInstruct.indexOf('='));
		//if (myInstruct.contains(";"))
		//	return myInstruct.substring(0, myInstruct.indexOf(';'));
		return null;
	}

	public String comp()
	{
		//return instruction's comp field
		int eq = myInstruct.indexOf('=');
		if (eq < 0)
			return myInstruct.substring(eq+1, myInstruct.indexOf(';'));
		if (myInstruct.contains(";"))
			return myInstruct.substring(eq+1, myInstruct.indexOf(';'));
		return myInstruct.substring(eq+1);
	}

	public String jump()
	{
		//return instruction's jump field
		if (myInstruct.contains(";"))
			return myInstruct.substring(myInstruct.indexOf(';')+1);
		return null;
	}

	public int getLineNumber()
	{
		return lineNumber;
	}

	private void removeComment()
	{
		int index = myInstruct.indexOf('/');
		if (index >= 0)
			myInstruct = myInstruct.substring(0, index);
	}
}
