package main;

import java.io.File;

import compression.Huffman;

import tools.Tools;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//Huffman c = new Huffman("abracadabra");
		Huffman c = new Huffman("abrac");
		
		c.compression();
		System.out.println(c.getCode());
		//System.out.println(c.getA() == null);
		System.out.println("THE LAST ARBRE ====");
		System.out.println(c.getA().toString());
	}

}
