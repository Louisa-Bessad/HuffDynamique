package huffman;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;





import java.util.HashMap;
import java.util.Set;
import java.util.Stack;

import struct.Arbre;
import tools.Tools;

public class Compression {
	
	String code = "";
	HashMap<String,Arbre> hash = new HashMap<String,Arbre>();
	ArrayList<Arbre> seq = new ArrayList<Arbre>();
	
	int nb = 1;
	
	Arbre racine;

	public Compression(){
	}
	
	

	public String getCode() {
		return code;
	}



	public void setCode(String code) {
		this.code = code;
	}



	public void compression(File f) throws IOException{
		racine = new Arbre('#',0);			//create start
		racine.setPos(0);
		seq.add(racine);					//add to list
		int i = 0;
		int r;
		  InputStream in = new FileInputStream(f);
	      Reader reader = new InputStreamReader(in);
	      Reader buffer = new BufferedReader(reader);
	      while ((r = buffer.read()) != -1) {
	    	  char s = (char) r;
			if(!hash.containsKey(String.valueOf(s))){
				if(s =='|'){
					System.out.println("the idiot one");
					System.out.println(Tools.table.get(String.valueOf(s)));
				}
				Tools.toFile(getCode(0)+Tools.table.get(String.valueOf(s)));	//return code de zero + s
				code+=getCode(0)+Tools.table.get(String.valueOf(s));
			}else{
				Tools.toFile(getCode(hash.get(String.valueOf(s)).getPos()));	//return code de s; // check what to o
				code+=getCode(hash.get(String.valueOf(s)).getPos());
			}
			racine = Modification(racine,s);
			i++;
			/*if(i % 10000 == 0){*/
				//System.out.println("i = " + i);
			//}
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
			nb = nb +2;
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



	private Arbre traitement(Arbre a, Arbre q){
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

	
	private void parCour(){
		ArrayList<Arbre> file = new ArrayList<Arbre>();
		ArrayList<Arbre> seqTmp = new ArrayList<Arbre>();
		file.add(racine);
		int i = nb;
		while(!file.isEmpty()){
			Arbre tmp = file.remove(0);
			seqTmp.add(0, tmp);
			tmp.setPos(i);
			
			if(!tmp.isFeuille()){
				file.add(tmp.getFilsD());
				System.out.println("tmpD = " + tmp.getFilsD());
				file.add(tmp.getFilsG());

				System.out.println("tmpG = " + tmp.getFilsG());
				System.out.println("========");
			}
			i--;
		}
		seq = seqTmp;
		
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
	
	private Arbre finBloc(Arbre q) {
		int index = q.getPos();
		while(index < seq.size() - 1){
			if(seq.get(index).getFreq() < seq.get(index+1).getFreq())
				return seq.get(index);
			index++;
		}
		return seq.get(index);		//still to check what to return.
	}

	private String getCode(int i) {
		String res = "";
		Arbre tmp = seq.get(i);
		while(tmp.hasPere()){
			Arbre pere = tmp.getPere();
			if(pere.getFilsG() == tmp){
				res="0"+res;
			}else{
				res="1"+res;
			}
			tmp = pere;
		}
		return res;
	}

	public Arbre getA(){
		return racine;
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




private void addToList2(int x){
	for(int i = 3 ; i < seq.size() ; i++){
		seq.get(i).setPos(seq.get(i).getPos()+x);
	}
}

}
