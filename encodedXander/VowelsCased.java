package encodedXander;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File ;
import java.io.FileNotFoundException;
import java.io.FileReader ;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/*
 * Author: Alexander Pereira MARCH 2015, Croydon, UK
 * 
 * Util Class that can add a rule to convert one character into another or vice-versa, can simulate uppercase to lowercase conversion or comma to pipe chracter or vice-versa within a string
 * Rules can be added or removed.
 * Uses a HashMap
 * 
 */
public class VowelsCased {
	
	private static HashMap<String, String> vow = new HashMap<>() ; // even if it is made final only the HashMap address pos would be final not the contents 
	
	public VowelsCased()
	{
		System.out.println(System.getProperty("java.version"));
		System.out.println(System.getProperty("user.dir"));
		System.out.println("Constructor:- Instance of VowelsCased being created ....");
	}
	
	void addRule(String k, String v) throws Exception
	{
		if ((k == null) || (v == null) || k.equals("") || v.equals(""))
		{
			// this restriction might have to be removed if you are planning of substituting a mapped character with a ZERO-length character - because that in effect removes it in the output file !!!!
			throw new Exception("No Null Keys or Values or No Zero-Length Keys or Values atleast for now, k=" + k + " v=" + v);
		}
		
		/*
		 * Another USE CASE ASSUMPTION
		 * We are HAPPY that a to be converted to A
		 * However we are assuming that A the lower case will be a - fair enough
		 * 
		 * However for 9 mapped to @
		 * The rule    @ mapped to 9 which is the reverse will be implicitly implied over here
		 */
		vow.put(k, v);
		vow.put(v, k);
	}
	
	void printRules()
	{
		System.out.println("vow.size()="+ vow.size());
		Set<String> keys = vow.keySet();
		
		for ( String k : keys)
		{
			System.out.print ( k ) ;
			System.out.print("=");
			System.out.println( vow.get(k));
		}
		
	}
	
	void removeRule(String k)
	{
		System.out.println("Now attempting to remove possible occurence of key=" + k);
		String v = vow.remove(k);
		if (null == v)
		{
			System.out.println("WARNING Key=" + k + " does not exist - nothing removed");
		}
		else
		{
			System.out.println("Key="+k + " has been removed");
			System.out.println("Now attempting to remove possible occurence of key=" + v );
			String k1 = vow.remove(v) ;
			if (null == k1)
			{
				System.out.println("WARNING Key=" + k1 + " does not exist - nothing removed");
			}
			else
			{
				System.out.println("Key="+v + " has been removed");
			}
		}
	}
	

	String convert(String input)
	{
		String output = "" ;
		int lastpos = input.length() - 1 ;
		int currpos = 0 ;
				
		while ( currpos <= lastpos )
		{
			//String possbKey = String.valueOf(input.charAt(currpos)) ; // no need to convert from char to String - THOUGH i do not think it makes a difference in performance and is a lot lot more READABLE by another developer
			String possbKey = input.substring(currpos,currpos + 1) ;    // System.out.print(possbKey);
			String vowelValexists = vow.get( possbKey );                // not using containsKey() assuming that user will NEVER input a Null as a key
			if (null == vowelValexists)
			{
				output = output + possbKey ; // no need to change it put the original 
			}
			else
			{
				
				output = output + vowelValexists ; // matched so put it's mapped changed value
			}
			
			currpos = currpos + 1 ;
		}
		//System.out.println("");
		
		return output;
	}
	
	public static void main(String[] args)
	{
		VowelsCased x = new VowelsCased();
		
		try 
		{
		x.addRule("a","A");
		x.addRule("e","E");
		x.addRule("i","I");
		x.addRule("o","O");
		x.addRule("u","U");
		x.addRule("D", "d");
		x.addRule("H", "h");
		x.addRule("X", "x");
		
		
		System.out.println("Original=" + "hxAEIOUaeiouHXTY" );
		System.out.println(x.convert("hxAEIOUaeiouHXTY"));

		x.printRules();
		
		x.removeRule("W");
		x.removeRule("x");
		
		//x.addRule(null, null);
		x.addRule("", null);
		//x.addRule("", "");
		//x.addRule("F", "");

		} catch (Exception e) {
			e.printStackTrace();
		}

		
		/*
		
		String outZ = x.convert("The put method receives two arguments. The first argument is the key we are trying to add to the HashMap. And the second is the value linked to that keyU") ;
		String inZ  = x.convert(outZ);
		
		System.out.println(inZ);
		System.out.println(outZ);
		
		outZ = x.convert("The quick brown fox jumps over the lazy dog.");
		inZ = x.convert(outZ);
		
		System.out.println(inZ);
		System.out.println(outZ);

		outZ = x.convert("abcdefghijklmnopqrstuvwxyz");
		inZ = x.convert(outZ);
		
		System.out.println(inZ);
		*/
	}
	
	

}
