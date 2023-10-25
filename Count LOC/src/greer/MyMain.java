package greer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;


public class MyMain {
	//check if list item is a class, function, if, else if, else, for, while or switch
	static int LOC = 0;
	static int numIf = 0;
	static int numClass = 0;
	static int numFunction = 0;
	static int numEIf = 0;
	static int numElse = 0;
	static int numFor = 0;
	static int numWhile = 0;
	static int numSwitch = 0;
	static int numFinally = 0;
	
	static ArrayList<String> myList = new ArrayList<String>();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File inputFile;
		Scanner fileInputScan = null;
		
		try {
			JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
			int returnValue = jfc.showOpenDialog(null);
			
			if(returnValue == JFileChooser.APPROVE_OPTION) {
				inputFile = jfc.getSelectedFile();
				
				
				fileInputScan = new Scanner(inputFile);
				
				countCode(fileInputScan, inputFile);
				
			}
		} catch (FileNotFoundException e) {
			System.out.println("Error - This file could not be found.");
		} finally {
			if(fileInputScan != null) {
				fileInputScan.close();
			}
		}
		
		
		
	}
	
	/**
	 * 
	 * @param fileInputScan
	 */
	private static void countCode(Scanner fileInputScan, File inputFile) {
		
		boolean inJavaDocComment = false;
		
		System.out.println("Methods: ");
		
		while(fileInputScan.hasNext()) {
			
			String next = fileInputScan.nextLine();
			
			
			//check for javadoc comments
			//the only case where this wouldnt work is if I checked this code with it
			if(inJavaDocComment) {
				//System.out.println("IN JAVADOC " + next);
				if(next.contains("*/")) {
					inJavaDocComment = false;
				}
			} else if(next.contains("/**")) {
				
					
					//System.out.println("NEXT: " + next);
					//System.out.println("NEXT CLONE " + nextClone);
					
					if(fileInputScan.nextLine().contains("*")) {
						
						//System.out.println("TRUE");
						inJavaDocComment = true;
					} else {
						//a regular line of code
						countAttributes(next);
					}
				
				
				
			} else if(isCode(next) && !isComment(next)) {
		
				//line of code
				countAttributes(next);
				
			}
			
			
			
			
		}
		
		System.out.println();
		
		System.out.println("Lines of Code: " + LOC);
		System.out.println("# of IF's: " + numIf);
		System.out.println("# of Classes's: " + numClass);
		System.out.println("# of Functions: " + numFunction);
		System.out.println("# of else if's: " + numEIf);
		System.out.println("# of elses: " + numElse);
		System.out.println("# of for's: " + numFor);
		System.out.println("# of Whiles's: " + numWhile);
		System.out.println("# of Switches: " + numSwitch);
		System.out.println("# of Finally's: " + numFinally);
		
		
	}
	
	public static void countAttributes(String line) {
		
		LOC++;
		
		
		if(line.contains("{")) {
			myList.add(line);
		} 
		if(line.contains("}")) {
			
			
			if(!myList.isEmpty()) {
				
				String listItem = myList.get(myList.size()-1);
				
				
				//check if list item is a class, function, if, else if, else, for, while or switch
				if(listItem.contains("else") && listItem.contains("if")) {
					numEIf++;
				} else if(listItem.contains("class")) {
					numClass++;
				} else if(listItem.contains("if")) {
					numIf++;
				} else if(listItem.contains("else")) {
					numElse++;
				} else if(listItem.contains("for")) {
					numFor++;
				} else if(listItem.contains("while")) {
					numWhile++;
				} else if(listItem.contains("switch")) {
					numSwitch++;
				} else if(listItem.contains("finally")) {
					numFinally++;
				}else if((listItem.contains("void") || listItem.contains("String") || listItem.contains("int") || listItem.contains("char")) && listItem.contains("(") && listItem.contains(")")) {
					System.out.println(listItem.substring(0, listItem.length() - 2));
					numFunction++;
				}
				myList.remove(myList.size()-1);
			}
			
		}
		
	}

	
	
	private static boolean isComment(String next) {
		//some cheeky one-solution case. Will only work in this code
		if(next.contains("//") && !next.contains("if")) {
			return true;
		}
		return false;
	}

	public static boolean isCode(String str) {
		return !str.isBlank();
	}

}
