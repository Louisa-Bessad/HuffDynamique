package tools;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;

public class Tools {

	
	
	public static int nbchar = 0;
	public static HashMap<String,String> table ;
	
	public static String readFile(File f){
		String res ="";	 
		try {
			InputStream in = new FileInputStream(f);
            Reader reader = new InputStreamReader(in);
            Reader buffer = new BufferedReader(reader);
            int r;
            while ((r = buffer.read()) != -1) {
                char ch = (char) r;
                res+=ch;
            }
            return res;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
             
	}
	
  public static void nbDiff(String s){
	  
	  
	  HashMap<String,Integer> list = new HashMap<String,Integer>();
	  
	  
	  ArrayList<String> listString = new ArrayList<String>();
	  
 	  int cpt = 0;
 	  int nb = 0;
	  for(int i = 0 ; i < s.length() ; i++){
		  if(!list.containsKey(s.substring(i, i+1))){
			  list.put(s.substring(i, i+1), 1);
			  listString.add(s.substring(i, i+1));
			  cpt++;
		  }
	  }
	  double log = Math.log(cpt)/Math.log(2);
	
	  java.util.Collections.sort(listString);
	  
	  
	 if(log % 2 != 0){
		 nb = ((int)log + 1);
	 }else{
		 nb = (int) log;
	 }
	 
	
	 String start = "";
	 for(int i  = 0 ; i < nb ; i++){
		 start+='0';
	 }
	 table = new HashMap<String,String>();
	 if(cpt == 1){
		table.put(listString.get(0),"0");
		return;
	 }
	 for(int i = 0 ; i < listString.size() ; i++){

		 table.put(listString.get(i), start);
		 
		 String input1 = "1";

		// Use as radix 2 because it's binary    
		int number0 = Integer.parseInt(start, 2);
		int number1 = Integer.parseInt(input1, 2);

		int sum = number0 + number1;
		start = Integer.toBinaryString(sum);

		if(start.length() < nb){
			int diff = nb - start.length();
			String tmp = "";
			for(int j = 0 ; j < diff ; j++)
				tmp+="0";
			start = tmp + start;
		}

	 }
  }
  
  
public static void nbDiff(File f) throws IOException{
	  
      
	  
	  HashMap<String,Integer> list = new HashMap<String,Integer>();
	  
	  
	  ArrayList<String> listString = new ArrayList<String>();
	  
	  InputStream in = new FileInputStream(f);
      Reader reader = new InputStreamReader(in);
      @SuppressWarnings("resource")
      Reader buffer = new BufferedReader(reader);
	  
 	  int cpt = 0;
 	  int nb = 0;
 	  
 	  int r;
      while ((r = buffer.read()) != -1) {
          char ch = (char) r;
          String tmp = String.valueOf(ch);
		  if(!list.containsKey(tmp)){
			  list.put(tmp, 1);
			  listString.add(tmp);
			  cpt++;
		  }
      }
	  double log = Math.log(cpt)/Math.log(2);
	
	  java.util.Collections.sort(listString);
	  
	  
	 if(log % 2 != 0){
		 nb = ((int)log + 1);
	 }else{
		 nb = (int) log;
	 }
	 
	
	 String start = "";
	 for(int i  = 0 ; i < nb ; i++){
		 start+='0';
	 }
	 table = new HashMap<String,String>();
	 if(cpt == 1){
		table.put(listString.get(0),"0");
		return;
	 }
	 for(int i = 0 ; i < listString.size() ; i++){

		 table.put(listString.get(i), start);
		 
		 String input1 = "1";

		// Use as radix 2 because it's binary    
		int number0 = Integer.parseInt(start, 2);
		int number1 = Integer.parseInt(input1, 2);

		int sum = number0 + number1;
		start = Integer.toBinaryString(sum);

		if(start.length() < nb){
			int diff = nb - start.length();
			String tmp = "";
			for(int j = 0 ; j < diff ; j++)
				tmp+="0";
			start = tmp + start;
		}

	 }
  }
 	
  public static String printValue(byte[] te){
	  String res = "";
	  for(int i = 0 ; i < te.length ; i++){
		  res += ""+te[i];
	  }
	  return res;
  }
	
  static FileOutputStream bos ;
  static bitFileStreamOut bit ;
  public static int nb = 0 ;
  
  public static void init(String file) throws FileNotFoundException{
	
	  bos = new FileOutputStream(file);
	  bit = new bitFileStreamOut(bos);
  }
  
  public static String code ="";
  
  public static void toFile(String s) throws IOException{
	  for(int i = 0 ; i < s.length() ; i++){
		  code+= s.charAt(i);
		  if(s.charAt(i)=='1')
			 bit.write(true);
		 else
			 bit.write(false);
		 nb++;
	 }
  }
  public static void end(){
	  try {
		bos.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
  
  public static void print() throws IOException{
	  FileInputStream bos2 = new FileInputStream("compressed.txt");
	  bitFileStreamIn bit2 = new bitFileStreamIn(bos2);
	  int i = 0;
	  String res = "";
	  while(i<nb){
		  boolean x = bit2.read();
		  if(x)
			  res+="1";
		  else
			  res+="0";
	  i++;
	  }
	  System.out.println(res);
}


}

