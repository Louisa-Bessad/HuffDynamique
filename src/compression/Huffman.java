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
			
			System.out.println("char = " + s);
			System.out.println("==========================================================");
			if(!hash.containsKey(String.valueOf(s))){
				code = code+getCode(0)+s+" ";	//return code de zero + s
			}else{
				code = code+getCode(hash.get(String.valueOf(s)).getPos())+" ";	//return code de s; // check what to o
			}
			
			racine = Modification(racine,s);
			i++;
			//System.out.println("Char = " + s);
			System.out.println("before next etapes");
			System.out.println("ARBRE PRINCINPAL =======  " + racine.toStringComplete());
			System.out.println(seq.toString());
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
		System.out.println("first appel");
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
					this.incrementePath(q, m);
					return a;
			}
			this.incrementePath(q, m);
			//ON FAIT L'ECHANGE
			if(m.getPere() == b.getPere() && m.getPere() !=null){
				System.out.println("same pere");
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
			System.out.println("after traitement = " + racine.toStringComplete());
			System.out.println("am sending next  = " + m.getPere().toString());
			System.out.println("==================================");

			return this.traitement(a, m.getPere()); 
		} 
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
		System.out.println("tmp = " + tmp.toString());
		while(tmp.hasPere()){
			Arbre pere = tmp.getPere();
			/*System.out.println("pere = " + pere.toStringComplete());
			System.out.println("tmp  = " + tmp.toString());
			System.out.println("res = " + res);*/
			if(pere.getFilsG() == tmp){
				res="0"+res;
			}else{
				res="1"+res;
			}
			tmp = pere;
		}
		System.out.println("res at the end = " + res);
		return res;
	}

	public Arbre getA(){
		return racine;
	}
	
//checked
/*public boolean isIncrementable(Arbre arb){
		System.out.println("inside is incrementable");
		System.out.println("has been called with arb = " + arb.toStringComplete());
		if(arb == racine)
			return true;
		int startIndex = arb.getPos();
		while(startIndex < seq.size()-1){
			System.out.println(seq.get(startIndex).toString());
			System.out.println(seq.get(startIndex+1).toString());
			System.out.println((seq.get(startIndex-1).getPere()));
			System.out.println(seq.get(startIndex-1).getPere() == seq.get(startIndex));
			if(seq.get(startIndex).getFreq() +1 > seq.get(startIndex + 1).getFreq() && (arb.dansMonChemin(seq.get(startIndex)) /*&& /*arb.dansMonChemin(seq.get(startIndex)) /*&& seq.get(startIndex-1).getPere() == seq.get(startIndex) ){
					
					return false;
				}				
			startIndex++;
		}
		return true;
	} 
	*/
	
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




private void addToList2(int x){
	for(int i = 3 ; i < seq.size() ; i++){
		seq.get(i).setPos(seq.get(i).getPos()+x);
	}
}

}
