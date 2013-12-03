package struct;

public class Arbre {

	Arbre pere;
	Arbre filsG;
	Arbre filsD;
	char value;
	int freq;
	
	
	
	public Arbre(char value,int freq){
		this.value = value;
		this.freq = freq;
	}
	
	public Arbre(Arbre filsG,Arbre filsD,char value){
		this.filsD = filsD;
		this.filsG = filsG;
		this.value = value;
	}
	
	public Arbre(char value){
		this.value = value;
		this.filsD = this.filsG = null;
	}
	
	
	public boolean isFeuille(){
		return (this.filsD == null && this.filsG == null);
	}
	
	
	//GETTERS AND SETTERS.

	public Arbre getFilsG() {
		return filsG;
	}

	public void setFilsG(Arbre filsG) {
		this.filsG = filsG;
	}

	public Arbre getFilsD() {
		return filsD;
	}

	public void setFilsD(Arbre filsD) {
		this.filsD = filsD;
	}

	public char getValue() {
		return value;
	}

	public void setValue(char value) {
		this.value = value;
	}
	
	public void setFreq(int freq){
		this.freq = freq;
	}
	
	public int getFreq(){
		return freq;
	}

	public Arbre getPere() {
		return pere;
	}

	public void setPere(Arbre pere) {
		this.pere = pere;
	}
	
	public boolean hasPere(){
		return !(pere==null);
	}
	
	public String toString(){
		String res = "";
		res+= "["+value+","+freq+"]";
		if(isFeuille()){
			return "F"+res;
		}else{
			res+= this.filsG.toString() + "," + this.filsD.toString();
			return res;
		}
	}
}
