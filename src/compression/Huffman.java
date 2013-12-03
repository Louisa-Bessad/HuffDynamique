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
		}
		
		//System.out.println("in the end" + list.get(0).toString());
	}
	
	
	
	
	
	private Arbre Modification(Arbre a, char s) {
		int index = inList(s);
		Arbre q = null;
		if(index == -1){
			q = list.get(0);						// je suis le pere
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
		System.out.println(inc);
		if(inc){
			//System.out.println("q = " + q.toString());
			//System.out.println("a = " + a.toString());
			this.incrementePath(q,a);
			return a;
		}else{
			//System.out.println(q.toString()); crashes here
			Arbre m = this.getFirstIndexEqualWeight(q);
			Arbre b = this.finBloc(a, m);
			this.incrementePath(q, m);				//added poids from q till qm;

			Arbre bG = b.getFilsG();
			System.out.println("the gauche of b");
			System.out.println(bG.toString());
			Arbre bD = b.getFilsD();
			System.out.println("the droit of b");
			System.out.println(bD.toString());
			
			Arbre mG = m.getFilsG();
			System.out.println("the gauche of m");
			System.out.println(mG.toString());
			Arbre mD = m.getFilsD();
			System.out.println("the droit of m");
			System.out.println(mD.toString());
			
			m.setFilsD(bD);
			bD.setPere(m);
			
			m.setFilsG(bG);
			bG.setPere(m);
			
			b.setFilsD(mD);
			mD.setPere(b);
			
			b.setFilsG(mG);
			mG.setPere(b);
		
			/*
			Arbre pereb = b.getPere();
			System.out.println(pereb == null);
			Arbre perem = m.getPere();
			System.out.println(perem == null);
			
			if(pereb == perem){
				if(pereb.getFilsD() == b){
					pereb.setFilsG(b);
					b.setPere(pereb);
					pereb.setFilsD(m);
					m.setPere(pereb);
				}else{
					pereb.setFilsD(b);
					b.setPere(pereb);
					pereb.setFilsG(m);
					m.setPere(pereb);
				}
			}else{
			
				if(pereb.getFilsG() == b){
					if(perem.getFilsG() == m){
						pereb.setFilsG(m);
						m.setPere(pereb);
						//pereb.setFilsD(pereb.getFilsD());
						perem.setFilsG(b);
						b.setPere(perem);
						//perem.setFilsD(perem.getFilsD());						
					}else{
						pereb.setFilsG(m);
						m.setPere(pereb);
						perem.setFilsD(b);
						b.setPere(perem);
					}
				}else{
					if(perem.getFilsG() == m){
						System.out.println("do nothing");
					}else{
						System.out.println("do nothing 2");
					}
				}
			}
			*/
			int tmp1 = this.inList(b.getValue());
			int tmp2 = this.inList(m.getValue());
			
			list.set(tmp1, m);
			list.set(tmp2, b);
			
			
			this.traitement(a, m.getPere()); 
		}
		return null;
	}


	public Arbre getFirstIndexEqualWeight(Arbre e){
		Arbre start = e;
		//int index;
		//System.out.println(e.toString());
		while(start.getFreq() != this.list.get(this.inList(start.getValue())+1).getFreq() && start.hasPere()){
			start=start.getPere();
			//System.out.println("loop");
		}
		System.out.println("ended this loop");
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
			startp = list.get(index+1);
			if(start.getFreq() > startp.getFreq())
				return false;
			start= start.getPere();
		}
		return true;
	}
	
	
public void incrementePath(Arbre s, Arbre e){
	Arbre start = s;
	System.out.println("e = " + s.getValue() + " , " + s.getFreq());
	while(start != e){
		start.setFreq(start.getFreq()+1);
		start= start.getPere();
	}
	start.setFreq(start.getFreq()+1);
	System.out.println(e.toString());
}


}
