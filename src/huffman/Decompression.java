package huffman;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import struct.Arbre;
import tools.BitInputStream;
import tools.Tools;
import tools.bitFileStreamIn;
import tools.bitFileStreamOut2;

public class Decompression {

	
	int size ;
	Arbre racine;
	String c;
	ArrayList<Arbre> seq = new ArrayList<Arbre>();
	public static HashMap<String,String> table ;
	HashMap<String,Arbre> hash = new HashMap<String,Arbre>();
	String decomp;
	bitFileStreamIn bit;
	
	public Decompression(){
		table = Tools.table;
		this.size = table.get(table.keySet().toArray()[0]).length();
	}
	
	public void test3(File f) throws IOException{		
		
		
		FileWriter fw = new FileWriter(new File("decompressed.txt"));
		BufferedWriter bw = new BufferedWriter(fw);
		
		bit = new bitFileStreamIn(new FileInputStream(f));
		int i = 0 ;
	
		
		i++;
		decomp = "";
		c = "";
		racine = new Arbre('#',0);
		seq.add(racine);


		String tmp ="";
	
		for(int x = 0 ; x < size ; x++){
			boolean t = bit.read();
			if(t){
				tmp+=1;
			}else{
				tmp+=0;
			}
		}
		racine = Modification(racine,getKey(tmp).charAt(0));
		bw.write(""+getKey(tmp).charAt(0));
		
		char cha ;
		i = size;
		tmp = "";
		
		while(i < Tools.nb - 1){
			boolean t = bit.read();
			if(t){
				tmp+="1";
			}else{
				tmp+="0";
			}
			Arbre a = cheminExisit(tmp);
			if(a.isFeuille()){
				if(a.getValue()=='#'){
					String tmp2 = read(size);
					bw.write(""+getKey(tmp2).charAt(0));
					cha = getKey(tmp2).charAt(0);
					i = i + size ;
				}else{
					cha = a.getValue();
					bw.write(""+cha);
					i++;
				}
				racine = Modification(racine,cha);
				tmp = "";
			}else{
				i++;
			}
		}
		bw.close();
	}

	
    private String read(int e) throws IOException{
    	int i = 0;
    	String tmp ="";
    	while(i < e){
    		if(bit.read()){
    			tmp+="1";
    		}else{
    			tmp+="0";
    		}
    		i++;
    	}
    	return tmp;
    }
	
	private Arbre Modification(Arbre a, char s) {
		Arbre q = null;
		if(!hash.containsKey(String.valueOf(s))){
			if(seq.size() >= 2){
				q = seq.get(2);
			}
			Arbre letter = new Arbre(s,1);	
			Arbre newPere = new Arbre(' ',1);	
			newPere.setFilsD(letter);			
			letter.setPere(newPere);			// son pere
			seq.add(1, letter);
			letter.setPos(1);
			seq.add(2,newPere);
			newPere.setPos(2);
			hash.put(String.valueOf(s), letter);
			this.addToList2(2);
		
			newPere.setFilsG(seq.get(0));		
			seq.get(0).setPere(newPere);			
			
			
			if(q == null){	
				newPere.setFreq(0);
				racine = newPere;						// arbre principal
				a = newPere;
				q = newPere;			
			}else{
				newPere.setPere(q);
				q.setFilsG(newPere);
			}
		}else{
			q = hash.get(String.valueOf(s));
			if(q.getBrother() == seq.get(0) && q.getPere() == this.finBloc(q))
				q = q.getPere();
		}
			return traitement(a,q);
	}
	
	private Arbre traitement(Arbre a, Arbre q) {
		boolean inc = this.isIncrementable(q);
		if(inc){
			this.incrementePath(q,a);
			return a;
		}else{

			Arbre m = this.firstIndex(q);
			Arbre b = this.finBloc(m);
			
			if((m == b) && (racine ==b)){
					this.incrementePath(q, m);
					return a;
			}
			this.incrementePath(q, m);
			if(m.getPere() == b.getPere() && m.getPere() !=null){
				int indexM = m.getPos();
				
				if(m.getPere().getFilsG() == m){
					Arbre pere = m.getPere();
					pere.setFilsD(m);
					pere.setFilsG(b);					
					seq.set(b.getPos(), m);
					m.setPos(b.getPos());
					seq.set(indexM, b);
					b.setPos(indexM);	
				}else{
					Arbre pere = m.getPere();
					pere.setFilsG(m);
					pere.setFilsD(b);
					seq.set(b.getPos(), m);
					m.setPos(b.getPos());
					seq.set(indexM, b);
					b.setPos(indexM);
					
				}
			}else{
	
				int indexM = m.getPos();
				int indexB = b.getPos();
				
				Arbre pereM = m.getPere();
				Arbre pereB = b.getPere();
				
				if(m.getPere().getFilsG() == m){			// m is the gauche of his father
					pereM.setFilsG(b);
				}else{
					pereM.setFilsD(b);
				}
				if(b.getPere().getFilsG() == b){
					pereB.setFilsG(m);
				}else{
					pereB.setFilsD(m);
				}
				
				b.setPere(pereM);
				m.setPere(pereB);
				
				b.setPos(indexM);
				m.setPos(indexB);
				
				seq.set(indexB, m);
				seq.set(indexM, b);
			} 
			return this.traitement(a, m.getPere()); 
		} 
	}
	
	
	
	private String getKey(String tmp){
		Object[] listKey = table.keySet().toArray();
		for(int i = 0 ; i < listKey.length ; i++){
			if(table.get(listKey[i]).equals(tmp)){
				return (String) listKey[i];
			}
		}
		System.out.println("should not arrive here");
		return null;
	}
	
	
	private void addToList2(int x){
		for(int i = 3 ; i < seq.size() ; i++){
			seq.get(i).setPos(seq.get(i).getPos()+x);
		}
	}

	private Arbre finBloc(Arbre q) {
		int index = q.getPos();
		while(index < seq.size() - 1){
			if(seq.get(index).getFreq() < seq.get(index+1).getFreq())
				return seq.get(index);
			index++;
		}
		return seq.get(index);		//still to check what to return.
	}
	
	public boolean isIncrementable(Arbre e){
		Arbre start = e;
		int index = 0;
		Arbre startp = null;
		while(start.hasPere()){
			 index =start.getPos();
			 startp = seq.get(index+1);
			 if(start.getFreq() >= startp.getFreq())
					return false;
				
				start= start.getPere();
			}
			return true;
		}


	//checked
	public void incrementePath(Arbre s, Arbre e){
		Arbre start = s;
		while(start != e && start.hasPere()){
			start.setFreq(start.getFreq()+1);
			start= start.getPere();
		}
		start.setFreq(start.getFreq()+1);
	}

	private Arbre firstIndex(Arbre e){
		int index = e.getPos();
		while(index < seq.size() - 1){
			if(seq.get(index).getFreq() == seq.get(index+1).getFreq()){
				return seq.get(index);
			}
			index++;
		}
		return seq.get(index);
	}
	
	private Arbre cheminExisit(String ch){
		Arbre tmp = racine;
		
		int i = 0 ;
		while(i < ch.length() && !tmp.isFeuille()){
			if(ch.charAt(i) == '1'){
				tmp = tmp.getFilsD();
			}else{
				tmp = tmp.getFilsG();
			}
			i++;
		}
		return tmp;
	}

	public String getDecomp(){
		return this.decomp;
	}
}
