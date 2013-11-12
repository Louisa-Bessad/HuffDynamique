package main;

import java.io.File;

import tools.Tools;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		File f = new File("Data/Textual Data/Bible/BDS/40010000");
		String res = Tools.readFile(f);
		System.out.println(res);
	}

}
