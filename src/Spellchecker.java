import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Spellchecker {
	/*
	Write a program, Spellchecker.java, that goes through a file and every time it finds
	a word not in the dictionary it offers the user a list of similar words to choose from
	using the SoundEx algorithm (up to 20 marks) or the Levenshtein algorithm (up
	to 30 marks). It prompts the user either to accept a word from the list or to
	enter a replacement. New words entered by the user should be added to a local
	dictionary. The correct spellchecked file should be stored in a different file.

	My comment:
	Local dictionary in an arraylist, whole different file is being stored word by word in an array.
	When program doesn't recognize the word, then shows 5 similar and offers to enter new word.

	making local dictionary from a text file

	text file dictionary is never changed, program can change it only when its loaded in an array

	*/
	
	public void check(String fileR, String fileT){
		// scanner for input
		Scanner reader = new Scanner(System.in);
		// array that will be dictionary
		ArrayList<String> dictionary = new ArrayList<String>(); 
		// array for text being read
		ArrayList<String> text = new ArrayList<String>();
		
		// adding words from file to array
		BufferedReader br = null;
	    try {
			br = new BufferedReader(new FileReader("dictionary.txt"));
			String CurrentLine;
			while ((CurrentLine = br.readLine()) != null) {
				dictionary.add(CurrentLine);				 					 
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	    
	    // setting files to read and write
	    String file1=fileR;
	    String file2=fileT;
	    
	    
	    // reading the target file
	    try {
			br = new BufferedReader(new FileReader(file1));
			String CurrentLine;
			while ((CurrentLine = br.readLine()) != null) {
				 String[] words = CurrentLine.split(" ");    
				 for ( String s1 : words) { 
					 text.add(s1);
				 }
				 text.add(">>>>");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
	    // comparing with dictionary
	    for(int x=0; x<text.size(); x++){
	    	// flag for removing the dot
	    	boolean dotRemove=false;
	    	// removing dot it there's one at the end of the word
	    	if(text.get(x).charAt(text.get(x).length()-1)=='.'){
	    		text.set(x, text.get(x).substring(0,text.get(x).length()-1));
	    		dotRemove=true;
	    	}
	    	
	    	// flag for removing the coma
	    	boolean comaRemove=false;
	    	// removing dot it there's one at the end of the word
	    	if(text.get(x).charAt(text.get(x).length()-1)==','){
	    		text.set(x, text.get(x).substring(0,text.get(x).length()-1));
	    		comaRemove=true;
	    	}
	    	
	    	// flag for passing through dictionary
	    	boolean pass=false;
	    	
	    	//  making word lower case
	    	String save = text.get(x);
	    	text.set(x, text.get(x).toLowerCase());
	    	
	    	for(int v=0; v<dictionary.size(); v++){
	    		if(Objects.equals(text.get(x), dictionary.get(v))){
	    			pass=true;
	    		}
	    	}
	    	// if its mark for next line then pass
	    	if(Objects.equals(text.get(x), ">>>>")){
	    		pass=true;
	    	}
	    	
	    	// if pass was failed show replacements
	    	if(!pass){
	    		// array with similar words to the unrecognized one
	    		ArrayList<String> similarWords = new ArrayList<String>(); 
	    		// make the Levenshtein object
	    		Levenshtein l = new Levenshtein();
	    		int level=1;
	    		// keep checking the dictionary until 5 similar words are found
	    		do{
	    		for(int q=0; q<dictionary.size(); q++){
	    			// calling the Levenshtein algorithm
	    			if(l.count(text.get(x), dictionary.get(q))==level ){
	    				similarWords.add(dictionary.get(q));
	    				if(similarWords.size()==5){
	    					break;
	    				}
	    			}
	    		}
	    		// checking for less similar words if 5 matching the distance of 1 were not found
	    		level++;
	    		}while(similarWords.size()<5);
	    		
	    		System.out.println("The word <"+text.get(x)+"> doesn't match the dictionary.");
	    		System.out.println("Enter the number of the word to replace it or write your own:");
	    		
	    		for(int v=0; v<similarWords.size(); v++){
	    	    	System.out.println((v+1)+": "+similarWords.get(v));
	    	    }
	    		// read the input
	    		String input = reader.nextLine();
	    		// set the replacement
	    		if(Objects.equals(input, "1")){
	    			if(Character.isUpperCase(save.charAt(0))){
	    				String input1=similarWords.get(0).substring(0, 1).toUpperCase();
	    				String input2=similarWords.get(0).substring(1, similarWords.get(0).length());
	    				input = input1+input2;
	    				text.set(x, input);
	    			}else{
	    				text.set(x, similarWords.get(0));
	    			}	    			
	    		}else
	    		if(Objects.equals(input, "2")){
	    			if(Character.isUpperCase(save.charAt(0))){
	    				String input1=similarWords.get(1).substring(0, 1).toUpperCase();
	    				String input2=similarWords.get(1).substring(1, similarWords.get(1).length());
	    				input = input1+input2;
	    				text.set(x, input);
	    			}else{
	    				text.set(x, similarWords.get(1));
	    			}
	    		}else
	    		if(Objects.equals(input, "3")){
	    			if(Character.isUpperCase(save.charAt(0))){
	    				String input1=similarWords.get(2).substring(0, 1).toUpperCase();
	    				String input2=similarWords.get(2).substring(1, similarWords.get(2).length());
	    				input = input1+input2;
	    				text.set(x, input);
	    			}else{
	    				text.set(x, similarWords.get(2));
	    			}    			
	    		}else
	    		if(Objects.equals(input, "4")){
	    			if(Character.isUpperCase(save.charAt(0))){
	    				String input1=similarWords.get(3).substring(0, 1).toUpperCase();
	    				String input2=similarWords.get(3).substring(1, similarWords.get(3).length());
	    				input = input1+input2;
	    				text.set(x, input);
	    			}else{
	    				text.set(x, similarWords.get(3));
	    			}
	    		}else
	    		if(Objects.equals(input, "5")){
	    			if(Character.isUpperCase(save.charAt(0))){
	    				String input1=similarWords.get(4).substring(0, 1).toUpperCase();
	    				String input2=similarWords.get(4).substring(1, similarWords.get(4).length());
	    				input = input1+input2;
	    				text.set(x, input);
	    			}else{
	    				text.set(x, similarWords.get(4));
	    			}    			
	    		}else{
	    			// add new word to a dictionary, first make it whole lowercase
	    			input=input.toLowerCase();
	    			dictionary.add(input);
	    			// if the first letter was big make the first word in new letter big as well
	    			if(Character.isUpperCase(save.charAt(0))){
	    				String input1=input.substring(0, 1).toUpperCase();
	    				String input2=input.substring(1, input.length());
	    				input = input1+input2;
	    			}
	    			// replace the word in the text
	    			text.set(x, input);
	    		}
		    			    			    	
	    	}else{
	    		// make word upper case again
		    	text.set(x, save);
	    	}
	    	
	    	// append the dot at the end of the word if there was one
	    	if(dotRemove){
    			text.set(x, text.get(x)+'.');
    		}
	    	if(comaRemove){
    			text.set(x, text.get(x)+',');
    		}
	    }
	    reader.close();
	    // print the text array
	    /*
	    for(int k=0; k<text.size(); k++){
	    	System.out.println(text.get(k));
	    }
	    */
	    //saving to a file
	    try{
	        PrintWriter writer = new PrintWriter(file2, "UTF-8");
	        for(int k=0; k<text.size(); k++){
	        	if(Objects.equals(text.get(k), ">>>>")){
	        		writer.println("");
	        	}else{
	        		writer.print(text.get(k));
	        		writer.print(" ");
	        	}
	        }	        	        
	        writer.close();
	    } catch (Exception e) {
	       // do something
	    }
	    
	    // the end flag
	    System.out.println("Done");
	}
}
