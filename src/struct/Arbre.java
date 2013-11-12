package struct;

public class Arbre {

	Arbre filsG;
	Arbre filsD;
	char value;
	
	
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
	
	
}
