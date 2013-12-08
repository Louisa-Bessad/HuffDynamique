package struct;

public class Arbre {

	Arbre pere;
	Arbre filsG;
	Arbre filsD;
	Arbre next;
	char value;
	int freq;
	
	int pos;
	
	
	
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
	
	
	public Arbre getBrother(){
		if(pere.getFilsD() == this){
			return pere.getFilsG();
		}else{
			return pere.getFilsD();
		}
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
	
	public boolean hasNext(){
		return !(next==null);
	}
	
	
	public Arbre getNext() {
		return next;
	}

	public void setNext(Arbre next) {
		this.next = next;
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

	public String toStringNode(){
		String res = "";
		res+= "["+value+","+freq+"]";
		return res;
	}
	
	public int getPos() {
		return pos;
	}

	public void setPos(int pos) {
		this.pos = pos;
	}
	
	
	
	
}
