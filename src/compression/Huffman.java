package compression;

import java.util.ArrayList;





import struct.Arbre;

public class Huffman {
	
	String code = "";
	ArrayList<Arbre> list = new ArrayList<Arbre>();
	String text;
	Arbre a;
	
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
		System.out.println("CALLING WITH TEXT = " + text);
		a = new Arbre('#',0);			//create start
		list.add(a);					//add to list
		int i = 0;							
		while(i < text.length()){		//parcour
			//System.out.println("i = " + i);
			//System.out.println("CODE IS " + code);
			char s = text.charAt(i);  //getchar
			System.out.println(s);
			int index = inList(s);	// exisits ou pas
			//System.out.println("INDEX = " + index);
			if( index == -1){
				code = code+getCode(0)+s+" ";	//return code de zero + s
			}else{
				code = code+getCode(index)+" ";	//return code de s;
			}
			a = Modification(a,s);
			i++;
			System.out.println(a.toString());
		}
		
		//System.out.println("in the end" + list.get(0).toString());
	}
	
	
	
	
	
	private Arbre Modification(Arbre a, char s) {
		int index = inList(s);
		Arbre q = null;
		if(index == -1){
			q = list.get(this.inList('#'));						// je suis le pere
			if(!q.hasPere()){						// ilya que #
				Arbre newPere = new Arbre(' ',0);	// noeud avec 1
				newPere.setFilsG(q);				// son filsG #
				q.setPere(newPere);					// # son new pere
				Arbre letter = new Arbre(s,1);		// le noeud avec char
				letter.setPere(newPere);			// son pere
				newPere.setFilsD(letter);			// pere filsD noeud de char
				a = newPere;						// arbre principal
				q = newPere;
				list.add(letter);
				list.add(newPere);
			}else{
				Arbre newPere = new Arbre(' ',0);	//new pere
				newPere.setFilsG(q);				//son fils #
				newPere.setPere(q.getPere());		//son pere et le pere de #
				q.getPere().setFilsG(newPere);		//change pour le pere 
				q.setPere(newPere);					// pere pour #
				Arbre letter = new Arbre(s,1);		// letter
				letter.setPere(newPere);			// pere de letter
				newPere.setFilsD(letter);			// should swap as well
				q = newPere;
				list.add(letter);
				list.add(newPere);
			}
		}else{
			q = list.get(index);			
			if(q.getPere().getFilsG().getValue()=='#' && q.getPere() == finBloc(a,q)){ //IF NORMAL 
				q = q.getPere();													// DE SUJET
			}
		}
		return traitement(a,q);
	}



	private Arbre traitement(Arbre a, Arbre q) {
		boolean inc = this.isIncrementable(q);
		System.out.println("incrementable ou pas = " + inc);
		if(inc){
			this.incrementePath(q,a);
			return a;
		}else{
			Arbre m = this.getFirstIndexEqualWeight(q);
			Arbre b = this.finBloc(a, m);
			this.incrementePath(q, m);				//added poids from q till qm;
			//ON FAIT L'ECHANGE
			Arbre bG = b.getFilsG();
			int inBG = this.inList(bG.getValue());
			Arbre bD = b.getFilsD();
			int inBD = this.inList(bD.getValue());
			Arbre mG = m.getFilsG();
			int inMG = this.inList(mG.getValue());
			Arbre mD = m.getFilsD();
			int inMD = this.inList(mD.getValue());
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
					System.out.println("here");
					Arbre tmp = b.getFilsD();
					b.setFilsD(m);
					b.setFilsG(tmp);
				}
			}else{
				System.out.println("rest du code");
				
			}
			System.out.println("a = " + a.toString());
			return this.traitement(a, m.getPere()); 
		}
	}


	public Arbre getFirstIndexEqualWeight(Arbre e){
		Arbre start = e;
		//int index;
		//System.out.println(e.toString());
		while(start.getFreq() != this.list.get(this.inList(start.getValue())+1).getFreq() && start.hasPere()){
			start=start.getPere();
			//System.out.println("loop");
		}
		return start;
	}

	private Arbre finBloc(Arbre a, Arbre q) {
		int index = this.inList(q.getValue());
		while( index < this.list.size() && this.list.get(index).getFreq() == this.list.get(index+1).getFreq())
			index++;
		return list.get(index);
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
		int index ;
		Arbre startp;
		while(start.hasPere()){	
			index = inList(start.getValue());
			//startp = list.get(index+1);
			startp = start.getPere();
			/*if(start.getFreq() > startp.getFreq())
				return false;*/
			if(start.getFreq()+1 > startp.getFilsD().getFreq() && start == startp.getFilsG()){
				return false;
			}
			start= start.getPere();
		}
		return true;
	}
	
	
public void incrementePath(Arbre s, Arbre e){
	Arbre start = s;
	while(start != e){
		start.setFreq(start.getFreq()+1);
		start= start.getPere();
	}
	start.setFreq(start.getFreq()+1);
}


}
