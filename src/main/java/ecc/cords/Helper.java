package ecc.cords;

import java.util.Scanner;

public class Helper{
	
	private static Scanner input = new Scanner(System.in);
	
	private Helper(){}
	
	public static String askString(String msg){
		System.out.print(msg);
		return input.nextLine();
	}
	
	public static int askNumericInput(String varName) throws InputException{
		System.out.print("Enter numeric value for " + varName + ": ");
		String tmp = input.nextLine();
		if(!isValidNumericInput(tmp)){
			throw  new InputException("Invalid " + varName + " input! " + varName + " must be a number.");
		}

		return Integer.parseInt(tmp);		
	}
	
	public static int countPatternOccurence(String word, String pattern){
		if(pattern.length() > word.length())
			return 0;
		
		int count = 0;
		
		for(int i = 0; i <= word.length()-pattern.length(); i++){
			if(word.substring(i,i+pattern.length()).equals(pattern))
				count++;
		}
		
		return count;	
	}
	
	public static boolean isValidIndex(int val, int max, String varName) throws IndexOutOfBoundsException{
		if(val < 0 || val >= max){
			throw new IndexOutOfBoundsException("Invalid " + varName + " index! " + varName
					+ " must be greater than 0 and less than " + max);
		}
		return true;
		
	}
	
	public static String generateRandomString(int length){
		String randomString = "";
		
		for(int i=0; i<length; i++){
			randomString +=  (char)((Math.random() * 81) + 174);
		}
		
		return randomString;
	}

	public static boolean isValidNumericInput(String var){
		boolean isValid = true;
		for(int index = 0; index < var.length(); index++){
			if(var.length() > 1 && index == 0 && (var.charAt(index) == '-' || var.charAt(index) == '+'))
				continue;
			if(!Character.isDigit(var.charAt(index))){
				isValid = false;
				break;
			}
		}
		return isValid;
	}	
}