package tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class Tools {

	
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
	
	
}
