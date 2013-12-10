package huffman;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import struct.Arbre;
import tools.Tools;

public class Decompression {

	
	String text;
	int size ;
	Arbre racine;
	String c;
	ArrayList<Arbre> seq = new ArrayList<Arbre>();
	public static HashMap<String,String> table ;
	HashMap<String,Arbre> hash = new HashMap<String,Arbre>();
	String decomp;
	
	
	public Decompression(String text){
		this.text = text;
		table = Tools.table;
		this.size = table.get(table.keySet().toArray()[0]).length();
		
		printTable();
	}
	
	
	public void decompression(){
		decomp = "";
		c = "";
		racine = new Arbre('#',0);
		seq.add(racine);
		int i = 0;
		boolean cont = false;
		System.out.println("text length = " + text.length());
		while (i < text.length()){
			System.out.println(" i = " + i + " char = " + text.charAt(i));
			if(i > 0){
				if(!cont){
					c = text.substring(i, i+1);
				}else{
					c += text.substring(i, i+1);
					//i = i + 1;
				}
			}
			System.out.println("c = " + c);
			if(c.equals(getCode(seq.get(0)))){			//feuille de #
				String tmp ;
				if(i+size < text.length()){
					System.out.println("cont = " + cont);
					if(i==0){
						tmp = text.substring(i, i+size);
					}else{
						if(cont){
							System.out.println(cont);
							tmp = text.substring(i+1,i+size+1);
						}else{
							tmp = text.substring(i+1, i+size+1); // i =i pas i+1 et i+size depends a i = 0
						}
					}
				}else{
					System.out.println("js ici");
					System.out.println("i + size + 1 " + (i+size+1));
					tmp = text.substring(i+1,i+2);
				}
				System.out.println("tmp = " + tmp);
				if(table.containsValue(tmp)){
					System.out.println("contains");
					racine = Modification(racine,getKey(tmp).charAt(0));
					decomp += getKey(tmp).charAt(0);
					if (!cont){
						i = i+size+c.length();
					}
					else{
						i = i+size+tmp.length()-1;
					}
					//i = i+size+c.length();
				}
				//System.out.println("=====================asdasdasdasdasd==");	
				cont = false;
			}else{
				System.out.println("cont of now = " + cont);
				Arbre tmp2 = this.cheminExisit(c);
				if(tmp2 != null && tmp2.isFeuille()){
					racine = Modification(racine,tmp2.getValue());
					decomp += tmp2.getValue();
					//i += c.length()+1;
					i+= c.length();
					cont = false;
				}else{
					cont = true;
					i = i+1;  
				}
			}
			System.out.println("decomp = " + decomp);
			System.out.println("================");
			System.out.println("cont = " + cont);
			System.out.println("i = " + i);
			}
		}
	
	
	public void test(){
		System.out.println("called");
		decomp = "";
		c = "";
		racine = new Arbre('#',0);
		seq.add(racine);
		int i = 0;
		System.out.println("length = " + text.length());
		while(i< text.length()){
			System.out.println("i = " + i); 
			System.out.println("c = " + text.substring(i, i+1));
			if(i == 10){
				System.out.println("yayayayayaya");
			}
			if(i > 0){
				c = text.substring(i, i+1);
			}

			if(c.equals(this.getCode(seq.get(0))) || cheminExisit(c).isFeuille()){
				System.out.println("found #");
				String tmp;
				if(c.equals(this.getCode(seq.get(0)))){
					if(i == 0)
						tmp = text.substring(i, i+size);
					else
						tmp = text.substring(i+1, i+size+1);
					if(table.containsValue(tmp)){
						System.out.println("contains 1");
						racine = Modification(racine,getKey(tmp).charAt(0));
						decomp += getKey(tmp).charAt(0);
					}
					if(i==0)
						i = i+size;
					else
						i = i+size+1;
				}else{
					System.out.println("feuille");
					racine = Modification(racine,cheminExisit(c).getValue());
					decomp += cheminExisit(c).getValue();
					if(i==0)
						i = i+size;
					else
						i = i+1;
				}
				System.out.println("i after a = " + i);
			}else{
				String tmp = c;
				System.out.println("tmp in start = " + tmp );
				int k = i+1;
				tmp += text.charAt(k);
				System.out.println("tmp in after add = " + tmp );
				System.out.println("MTP = " + tmp);
				System.out.println("K = " + k);
				System.out.println("CODE DE # = " + this.getCode(seq.get(0)));
				while(!tmp.equals(this.getCode(seq.get(0))) && !table.containsValue(tmp) && k<text.length()){				
					if(k < text.length()-1)
						k++;
					else 
						break;
					System.out.println("k = " + k);
					tmp += text.charAt(k);
					System.out.println("inside the loop tmp = " + tmp);		
				}
				System.out.println("2 tmp = " + tmp);
				System.out.println("FEUILLE ?? " + this.cheminExisit(tmp).isFeuille() );
				System.out.println("k = " + k);
				if(tmp.equals(this.getCode(seq.get(0)))){
					tmp = text.substring(k+1,k+size+1);
					System.out.println("tmp = " + tmp);
					k = k+size;
				}
				if(table.containsValue(tmp)){
					System.out.println("contains 2");
					racine = Modification(racine,getKey(tmp).charAt(0));
					decomp += getKey(tmp).charAt(0);
				}
				System.out.println("K = " + k);
				System.out.println("I = " + i);
				i = k+1;
			}
			System.out.println("decomp = " + decomp);
			System.out.println("i at the end =  " + i);
		}
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
			//ON FAIT L'ECHANGE
			if(m.getPere() == b.getPere() && m.getPere() !=null){
				System.out.println(m.getPere().toString());
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
			//System.out.println("after traitement = " + racine.toStringComplete());
			//System.out.println("am sending next  = " + m.getPere().toString());
			//System.out.println("==================================");

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
	
	private String  printTable(){
		Object[] listKey = table.keySet().toArray();
		for(int i = 0 ; i < listKey.length ; i++){
			System.out.println(listKey[i] + " , " + table.get(listKey[i]));
		}
		return null;
	}
	
	private String getCode(Arbre a) {
		if(racine == seq.get(0)){
			return "";
		}
		
		String res = "";
		Arbre tmp = a;
		//System.out.println("tmp = " + tmp.toString());
		while(tmp.hasPere()){
			Arbre pere = tmp.getPere();
			if(pere.getFilsG() == tmp){
				res="0"+res;
			}else{
				res="1"+res;
			}
			tmp = pere;
		}
		//System.out.println("res at the end = " + res);
		return res;
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
			// System.out.println("Noeud start"+"["+start.getLetter()+";"+start.getWeight()+";"+start.getIndex()+"]");
			// System.out.println("Noeud startp"+"["+startp.getLetter()+";"+startp.getWeight()+";"+startp.getIndex()+"]");
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
		if(i == ch.length()){
			return tmp;
		}	
		return null;
	}

	public String getDecomp(){
		return this.decomp;
	}
}
