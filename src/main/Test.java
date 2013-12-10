package main;



import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import tools.Tools;

public class Test {
	
	
	public static void main(String args[]) throws IOException{
		
		
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("kaka"));
		String s = " 1 0  0  1  1  1  1  1  1  1  1 123123 123-=0123901823120312-39-1239-12931230-912312";
		bos.write(s.getBytes());
		bos.flush();
		bos.close();
		
		PrintWriter out = new PrintWriter("filename.txt");
		out.println(s);
		out.close();
		
	}
	

}

	


