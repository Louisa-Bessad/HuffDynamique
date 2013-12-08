package compression;

import java.util.ArrayList;





import java.util.HashMap;
import java.util.Set;

import struct.Arbre;

public class Huffman {
	
	String code = "";
	HashMap<String,Arbre> hash = new HashMap<String,Arbre>();
	ArrayList<Arbre> seq = new ArrayList<Arbre>();
	
	
	String text;
	Arbre racine;

	public Huffman(String text){
		this.text = text;
	}
	
	

	public String getCode() {
		return code;
	}



	public void setCode(String code) {
		this.code = code;
	}



	public void compression(){
		racine = new Arbre('#',0);			//create start
		racine.setPos(0);
		seq.add(racine);					//add to list
		int i = 0;							
		while(i < text.length()){		//parcour
			char s = text.charAt(i);  //getchar
			
			if(hash.containsKey(s))
				code = code+getCode(0)+s+" ";	//return code de zero + s
			else
				code = code+getCode()+" ";	//return code de s; // check what to o
			
			System.out.println("char = " + s);
			System.out.println("==========================================================");
			racine = Modification(racine,s);
			i++;
			//System.out.println("Char = " + s);
			System.out.println("before next etapes");
			System.out.println(racine.toString());
		}
	}
	
	
	
	
	
	private Arbre Modification(Arbre a, char s) {
		//System.out.println("modification has been called");
		//System.out.println("==========================================================");
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
		System.out.println("before traitement");
		System.out.println("racine = " + racine);
		System.out.println("q      = " + q);
		return traitement(a,q);
	}



	private Arbre traitement(Arbre a, Arbre q) {
		boolean inc = this.isIncrementable(q);
		System.out.println("incrementable ou pas = " + inc);
		if(inc){
			this.incrementePath(q,a);
			return a;
		}else{

			Arbre m = this.firstIndex(q);
			Arbre b = this.finBloc(m);
			
			System.out.println("m =  " + m.toString());
			System.out.println("b =  " + b.toString());
		
			if((m == b) && (racine ==b)){
				System.out.println("racine");
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
				System.out.println("case they dont have the same father");
				
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

 /*public Arbre firstIndex(Arbre e){
		Arbre start = e;
		while(start.hasPere()){
			if(start.getFreq() == start.getPere().getFreq()){
				return start;
			}
			start = start.getPere();
		}
		return start;
	}
*/
	
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
				res+="0";
			}else{
				res+="1";
			}
			tmp = pere;
		}
		return res;
	}

	public Arbre getA(){
		return racine;
	}
	
//checked
public boolean isIncrementable(Arbre arb){
		int startIndex = arb.getPos()-2;
		while(startIndex < seq.size()-1){
			if(seq.get(startIndex).getFreq() +1  > seq.get(startIndex + 1).getFreq())
				return false;
			startIndex++;
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
