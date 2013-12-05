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
			System.out.println("char = " + s);
			System.out.println("==========================================================");
			a = Modification(a,s);
			i++;
			System.out.println("Char = " + s);
			System.out.println(a.toString());
		}
	}
	
	
	
	
	
	private Arbre Modification(Arbre a, char s) {
		System.out.println("modification has been called");
		System.out.println("==========================================================");
		int index = inList(s);
		Arbre q = null;
		if(index == -1){
			//System.out.println("doesnt exisit " + s);
			q = windows.getPere();								// je suis le pere
			if(q == null){							// ilya que #
				//System.out.println("# has no pere");
				Arbre letter = new Arbre(s,1);		// le noeud avec char
				Arbre newPere = new Arbre(' ',0);	// noeud avec 1
	
				newPere.setFilsG(windows);				// son filsG #
				newPere.setFilsD(letter);			// pere filsD noeud de char
				
				windows.setPere(newPere);					// # son new pere
				windows.setNext(letter); 					// setting next
				
				letter.setPere(newPere);			// son pere
				letter.setNext(newPere);
				
				a = newPere;						// arbre principal
				q = newPere;
				
				//System.out.println("after just finished the inseration");
				//System.out.println("a  = " + a.toString());
				
				list.add(letter);
				list.add(newPere);
			
			
			}else{
				//System.out.println("q has a pere");
				Arbre newPere = new Arbre(' ',1);	//new pere
				newPere.setFilsG(windows);							//son fils #		
				newPere.setPere(q);					//son pere et le pere de #
				q.setFilsG(newPere);				//change pour le pere 		
				windows.setPere(newPere);					// pere pour #
				
				Arbre letter = new Arbre(s,1);		// letter
				letter.setPere(newPere);			// pere de letter
				letter.setNext(newPere);
				newPere.setFilsD(letter);			// should swap as well
				
				windows.setNext(letter); 
				//q = newPere;
				
				//System.out.println("q has a droit " + q.getFilsD());
				newPere.setNext(q.getFilsD());
				//System.out.println(newPere.getNext());
				
				//System.out.println("a = " + a.toString());
				//System.out.println("q = " + q.toString());
				
				list.add(letter);
				list.add(newPere);
			
			}
		}else{
			//System.out.println("i am here 2");
			q = list.get(index);			
			if(q.getPere().getFilsG().getValue()=='#' && q.getPere() == finBloc(q)){ //IF NORMAL 
				q = q.getPere();													// DE SUJET
			}
		}
		//System.out.println("q = " + q.hasNext());
		System.out.println("fin de modification");
		System.out.println("==========================================================");
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
	
				
				Arbre tmp = b.getPere();
				Arbre tmp2 = m.getPere();
				
				System.out.println(b);
				System.out.println(m);
				
				b.setPere(tmp2);
				m.setPere(tmp);
				
				tmp2.setFilsG(b);
				tmp.setFilsD(m);
				
				b.setNext(tmp2.getFilsD());
				m.setNext(tmp);
				System.out.println("after the swap");
				System.out.println(a.toString());
				
			}
			//System.out.println("a = " + a.toString());
			return this.traitement(a, m.getPere()); 
		}
	}


	public Arbre firstIndex(Arbre e){
		Arbre start = e;
		while(start.hasNext() && start.getFreq() != start.getNext().getFreq()){
			if(start.hasPere()){
				System.out.println("grandfather exisits first index");
				if(start.getPere().getFilsG() != start.getPere() && start.getPere() != a ){
					System.out.println("mon pere est droit");
					System.out.println(start.getFreq()+1);
					if(start.getPere().getFilsG().getFreq() < start.getFreq()+1){
						System.out.println("inside the loop");
						System.out.println(start);
						System.out.println(start.getPere().getFilsG().getValue());
						System.out.println("i come out");
						return start;
					}
				}
			}
			start = start.getNext();
		}
			
		return start;
	}

	private Arbre finBloc(Arbre q) {
		while(q.hasNext() && q.getFreq()==q.getNext().getFreq()){
			q = q.getNext();
			if(q.hasPere()){
				System.out.println("fuck");
				if(q.getPere().getFilsG() != q.getPere() && q.getPere() != a ){
					System.out.println("this");
					System.out.println(q.getFreq()+1);
					if(q.getPere().getFilsG().getFreq() < q.getFreq()+1){
						System.out.println("bloody");
						System.out.println(q);
						System.out.println(q.getPere().getFilsG().getValue());
						System.out.println("shit");
						return q;
					}
				}
			}
		}
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
	
//checked
public boolean isIncrementable(Arbre arb){
		Arbre start = arb;
		System.out.println(start.toString() + " has a next " + start.getNext());
		while(start.hasNext()){
			if(start.getFreq() +1 > start.getNext().getFreq() ){
				System.out.println("je sors d'ici");
				return false;
			}
			if(start.hasPere()){
				System.out.println("grandfather exisits");
				if(start.getPere().getFilsG() != start.getPere() && start.getPere() != a ){
					System.out.println("mon pere est droit");
					System.out.println(start.getFreq()+1);
					if(start.getPere().getFilsG().getFreq() < start.getFreq()+1){
						System.out.println("inside the loop");
						System.out.println(start);
						System.out.println(start.getPere().getFilsG().getValue());
						System.out.println("i come out");
						return false;
					}
				}
			}
			start= start.getNext();
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


}
