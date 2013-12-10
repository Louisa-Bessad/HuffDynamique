package huffman;

import java.util.ArrayList;
import java.util.HashMap;

import struct.Arbre;

public class ToolsHuffman {

	
	public static Arbre Modification(Arbre a, char s , HashMap<String,Arbre> hash , ArrayList<Arbre> seq) {
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
			addToList2(2,seq);
		
			newPere.setFilsG(seq.get(0));		
			seq.get(0).setPere(newPere);			
			
			
			if(q == null){	
				newPere.setFreq(0);
				a = newPere;						// arbre principal
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
		System.out.println("racine = " + a);
		System.out.println("q      = " + q);
		System.out.println("first appel");
		return traitement(a,q);
	}
	
	private Arbre traitement(Arbre a, Arbre q , ArrayList<Arbre> seq) {
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
			//System.out.println("after traitement = " + racine.toStringComplete());
			//System.out.println("am sending next  = " + m.getPere().toString());
			//System.out.println("==================================");

			return this.traitement(a, m.getPere()); 
		} 
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




	public static void addToList2(int x,ArrayList<Arbre> seq){
		for(int i = 3 ; i < seq.size() ; i++){
			seq.get(i).setPos(seq.get(i).getPos()+x);
		}
	}

}
