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
		
		

		File f = new File("test2");
		String s = Tools.readFile(f);
		s = "abracadb";
		Tools.nbDiff(s);
		Tools.init();
		Compression c = new Compression(s);
		//Huffman c = new Huffman("abr");
		
		c.compression();
		System.out.println(c.getCode());
		//System.out.println(c.getA() == null);
		System.out.println("THE LAST ARBRE ====");
		Tools.end();
		System.out.println(c.getCode());
		System.out.println(c.getA().toStringComplete());
		System.out.println(c.getA() == null);
		
		Decompression d = new Decompression(c.getCode());
		System.out.println("decompression");
		System.out.println("======================");
		//d.decompression();
		//System.out.println(d.getDecomp());
		//System.out.println("asdasdasdasd");
		d.test();
		System.out.println(d.getDecomp());
		
		//Tools.print();
	}

}
