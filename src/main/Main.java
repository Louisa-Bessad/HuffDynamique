package main;

import huffman.Compression;
import huffman.Decompression;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import tools.Tools;

public class Main {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
	
		File f = new File(args[0]);
		Tools.nbDiff(f);
		
		
		System.out.println("Table initial : ");
		System.out.println("===============");
		Object[] listKey = Tools.table.keySet().toArray();
		for(int i = 0 ; i < listKey.length ; i++){
			System.out.println(listKey[i] + " , " + Tools.table.get(listKey[i]));
		}
		
		System.out.println("exp-rnd-05.txt");
		System.out.println("TAILLE DE CODAGE : " + Tools.table.get(Tools.table.keySet().toArray()[0]).length());
		Tools.init("compressed.txt");
		Compression c = new Compression();
		System.out.println("taille avant compression = " + f.length() + "kb");
		System.out.println("Compression:");
		System.out.println("============");
		long start = System.currentTimeMillis();  
		c.compression(f);
		long elapsedTimeMillis = System.currentTimeMillis()-start;
		float elapsedTimeSec = elapsedTimeMillis/1000F;
		System.out.println("Temp pris pour compression : " + elapsedTimeSec);
		Tools.end();
		
		File f2 = new File("compressed.txt");
		
		
		System.out.println("taille apres compression = " + f2.length()+ "kb");
		double x = (double)f.length() -  (double)f2.length();
		double y = x / (double)f.length();
		System.out.println("taux = " + (y*100) + "%");

		Decompression d = new Decompression();
		System.out.println("Decompression:");
		System.out.println("==============");
		
		start = System.currentTimeMillis();
		d.test3(f2);
		elapsedTimeMillis = System.currentTimeMillis()-start;
		elapsedTimeSec = elapsedTimeMillis/1000F;
		System.out.println("Temp pris pour decompression : " + elapsedTimeSec);
		
		
		System.out.println("c = " + c.getCode());
	
	}

}
