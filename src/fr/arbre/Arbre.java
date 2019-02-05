package fr.arbre;

public class Arbre<O> {
	private Arbre<O> g;
	private Arbre<O> d;
	private O val;
	
	@SuppressWarnings("unused")
	private Arbre() {}
	
	public Arbre(O val) {
		this.val = val;
		g = null;
		d = null;
	}
	public Arbre(O val, Arbre<O> g, Arbre<O> d) {
		this.val = val;
		this.g = g;
		this.d = d;
	}
	
	public void setD(Arbre<O> a) {
		d = a;
	}
	public void setG(Arbre<O> a) {
		g = a;
	}
	
	public O getVal() {return val;}
	
	public Arbre<O> getG() {
		return g;
	}
	public Arbre<O> getD() {
		return d;
	}
	
	public String toString() {
		String s = val != null?val.toString():"null";
		s += "\n   d - " + d.getVal();
		s += "\n   g - " + g.getVal();
		return s;
	}
}
