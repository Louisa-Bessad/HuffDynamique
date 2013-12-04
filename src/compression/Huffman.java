package compression;

import java.util.ArrayList;





import struct.Arbre;

public class Huffman {
	
	String code = "";
	ArrayList<Arbre> list = new ArrayList<Arbre>();
	String text;
	Arbre a;
	
	Arbre windows;
	
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
		//System.out.println("CALLING WITH TEXT = " + text);
		a = new Arbre('#',0);			//create start
		windows = a ;
		list.add(a);					//add to list
		int i = 0;							
		while(i < text.length()){		//parcour
			char s = text.charAt(i);  //getchar
			int index = inList(s);	// exisits ou pas
			if( index == -1){
				code = code+getCode(0)+s+" ";	//return code de zero + s
			}else{
				code = code+getCode(index)+" ";	//return code de s;
			}
			a = Modification(a,s);
			i++;
			System.out.println("Char = " + s);
			System.out.println(a.toString());
		}
	}
	
	
	
	
	
	private Arbre Modification(Arbre a, char s) {
		int index = inList(s);
		Arbre q = null;
		if(index == -1){
			q = windows;							// je suis le pere
			if(!q.hasPere()){						// ilya que #
				Arbre newPere = new Arbre(' ',0);	// noeud avec 1
				newPere.setFilsG(q);				// son filsG #
				q.setPere(newPere);					// # son new pere
				Arbre letter = new Arbre(s,1);		// le noeud avec char
				q.setNext(letter); 					// setting next
				letter.setPere(newPere);			// son pere
				letter.setNext(newPere);
				newPere.setFilsD(letter);			// pere filsD noeud de char
				a = newPere;						// arbre principal
				q = newPere;
				//list.add(letter);
				//list.add(newPere);
			}else{
				System.out.println("ici");
				Arbre newPere = new Arbre(' ',0);	//new pere
				newPere.setFilsG(q);				//son fils #
				newPere.setPere(q.getPere());		//son pere et le pere de #
				q.getPere().setFilsG(newPere);		//change pour le pere 
				q.setPere(newPere);					// pere pour #
				Arbre letter = new Arbre(s,1);		// letter
				letter.setPere(newPere);			// pere de letter
				q.setNext(letter); 
				newPere.setFilsD(letter);			// should swap as well
				letter.setNext(newPere);
				q = newPere;
			}
		}else{
			System.out.println("i am here 2");
			q = list.get(index);			
			if(q.getPere().getFilsG().getValue()=='#' && q.getPere() == finBloc(a,q)){ //IF NORMAL 
				q = q.getPere();													// DE SUJET
			}
		}
		return traitement(a,q);
	}



	private Arbre traitement(Arbre a, Arbre q) {
		boolean inc = this.isIncrementable(q);
		//System.out.println("incrementable ou pas = " + inc);
		if(inc){
			this.incrementePath(q,a);
			return a;
		}else{
			Arbre m = this.getFirstIndexEqualWeight(q);
			Arbre b = this.finBloc(a, m);
			this.incrementePath(q, m);				//added poids from q till qm;
			//ON FAIT L'ECHANGE
			if(b == m.getPere()){
				if(m == b.getFilsD()){
					Arbre tmp = b.getFilsG();
					b.setFilsG(m);
					b.setFilsD(tmp);
					int inG = this.inList(tmp.getValue());
					int inD = this.inList(m.getValue());
					list.set(inG, m);
					list.set(inD, tmp);
				}else{
					//System.out.println("here");
					Arbre tmp = b.getFilsD();
					int inD = this.inList(b.getValue());
					int inG = this.inList(m.getValue());
					b.setFilsD(m);
					b.setFilsG(tmp);
					list.set(inG, tmp);
					list.set(inD, m);
				}
			}else{
				System.out.println("rest du code");
				System.out.println("b = " + b.toString());
				System.out.println("m = " + m.toString());
			}
			//System.out.println("a = " + a.toString());
			return this.traitement(a, m.getPere()); 
		}
	}


	public Arbre getFirstIndexEqualWeight(Arbre e){
		//Arbre start = e;
		//int index;
		//System.out.println(e.toString());
	/*	while(start.getFreq() != this.list.get(this.inList(start.getValue())+1).getFreq() && start.hasPere()){
			start=start.getPere();
			//System.out.println("loop");
		}*/
		Arbre start = e;
		while(start.hasNext() && start.getFreq() != start.getNext().getFreq())
			start = start.getNext();
		return start;
	}

	private Arbre finBloc(Arbre a, Arbre q) {
		/*int index = this.inList(q.getValue());
		while( index < this.list.size() && this.list.get(index).getFreq() == this.list.get(index+1).getFreq())
			index++;
		return list.get(index);*/
		
		while(q.hasNext() && q.getFreq()==q.getNext().getFreq())
			q = q.getNext();
		return q;
	}





	private String getCode(int i) {
		String res = "";
		Arbre tmp = list.get(i);
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





	//returns index;
	public int inList(char s){
		for(int i = 0 ; i < list.size() ; i++){
			if(list.get(i).getValue() == s){
				return i;
			}
		}
		return -1;
	}
	
	public Arbre getA(){
		return a;
	}
	
	
public boolean isIncrementable(Arbre a){
		
		Arbre start = a;
		//int index ;
		//Arbre startp;
		while(start.hasNext()){	
			if(start.getFreq()+1 > start.getNext().getFreq()){
				return false;
			}
			start= start.getNext();
		}
		return true;
	}
	
	
public void incrementePath(Arbre s, Arbre e){
	Arbre start = s;
	while(start != e && start.hasPere()){
		start.setFreq(start.getFreq()+1);
		start= start.getPere();
	}
	start.setFreq(start.getFreq()+1);
}


}
