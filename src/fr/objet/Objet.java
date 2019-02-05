package fr.objet;

public class Objet {
	private String nom;
	private double val;
	private double poid;
	
	public Objet(String nom, double val, double poid) {
		this.nom = nom;
		this.val = val;
		this.poid = poid;
	}
	
	public String toString() {
		return nom + " ; " + val + " ; " + poid;
	}
	
	public String getNom() {return nom;}
	public double getVal() {return val;}
	public double getPoid() {return poid;}
	
}
