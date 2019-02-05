package fr.sacADos;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import fr.methodes.MethodeApprochee;
import fr.methodes.MethodeExDyn;
import fr.methodes.MethodePSE;
import fr.objet.Objet;

public class SacADos {
	private String lastMeth;
	private Objet[] objs;
	private double poidMax;
	private long tempsExec;
	
	private static final String[] METHODE = {"app","dyn","pse"};
	
	public SacADos() {
		lastMeth="";
		tempsExec = -1;
	}

	public SacADos(String path, float poidMax) {
		this();
		this.objs = objetsFromFile(path);
		this.poidMax = poidMax;
	}
	
	public static void testMethode(String methode) {
		for (int i = 0; i < METHODE.length; i++)
			if (methode.equals(METHODE[i]))
				return;
		System.err.println("Methode invalide (app, dyn, pse)");
		System.exit(0);
	}
	
	/**
	 * @brief Récupere les objets pour initialiser le sac a partir d'un fichier formaté
	 */
	private Objet[] objetsFromFile(String path) {
		try {
			@SuppressWarnings("resource")
			Scanner sc = new Scanner(new File(path));
			
			// ArrayList temporaire
			ArrayList<Objet> a = new ArrayList<>();
			
			// Lecture du fichier
			while (sc.hasNextLine()) {
				String s = sc.next();
				while (true) {
					String tmp = sc.next();
					if (tmp.equals(";")) break;
					s += " "+tmp;
				}
				double val = Double.parseDouble(sc.next());
				sc.next();
				double poid = Double.parseDouble(sc.next());
				a.add(new Objet(s,val,poid));
			}
			// Tranfo en tableau statique
			return a.toArray(new Objet[a.size()]);
		} catch (FileNotFoundException e) {
			System.err.println("Le path n'est pas valide ou le fichier ne peut pas etre lu (" + path + ")");
			System.exit(0);
		} catch (Exception e) {
			System.err.println("Erreur de syntaxe dans le fichier (" + path + ")");
			System.exit(0);
		}
		return null;
	}
	
	private double poidTotal() {
		return poidTotal(0);
	}
	
	/**
	 * @param i
	 * @return poid cumulé de tous les objets
	 */
	private double poidTotal(int i) {
		return i<objs.length?objs[i].getPoid()+poidTotal(i+1):0;
	}
	
	/**
	 * @brief Lance la methode correspondante pour la résolution du sac
	 * @param methode (app, dyn, pse)
	 */
	public void resoudre(String methode) {
		testMethode(methode);
		if (tempsExec == -1)
			tempsExec = System.nanoTime();
		switch(methode) {
		case "app":
			lastMeth = methode;
			objs = MethodeApprochee.process(objs, poidMax);
			break;
		case "dyn":
			lastMeth = methode;
			objs = MethodeExDyn.process(objs, poidMax);
			break;
		case "pse":
			lastMeth = methode;
			objs = MethodePSE.process(objs, poidMax);
			break;
		}
		// Calcul du temps d'éxécution de la methode
		tempsExec = (System.nanoTime() - tempsExec)/1000000;
		// Ecriture du sac trié (stat + objets) dans sacTrie.txt
		writeInFile("sacTrie.txt",toString());
	}
	
	public void setObjs(String path) {
		objs = objetsFromFile(path);
	}
	
	public void setPoidMax(double pm) {
		poidMax = pm;
	}
	
	public void setTempsExec(long start) {
		tempsExec = start;
	}
	
	public String toString() {
		return toStringStat()+"\n\n"+toStringObjet();
	}
	/**
	 * @return chaine de caracteres formatée des objets contenus dans le sac
	 * @see objetsFromFile
	 */
	public String toStringObjet() {
		String s = "";
		for (int i = 0; i < objs.length; i++)
			s += objs[i].toString()+(i+1<objs.length?"\n":"");
		return s;
	}
	/**
	 * @return chaine de caracteres formatée des statistiques du sac (valeur, poid)
	 */
	public String toStringStat() {
		String s = "Poid maximal du sac : " + poidMax + "\n";
		s += "Valeur totale : " + ((double)Math.round(valTotal()*100)/100) + "\n";
		s += "Poid total : " + poidTotal();
		s += lastMeth!=""?"\nTemps de résolution par " + 
				lastMeth + " : " + tempsExec + " ms":"";
		return s;
	}
	
	/**
	 * @return valeur cumulée des objets du sac
	 */
	private double valTotal() {
		return valTotal(0);
	}
	private double valTotal(int i) {
		return i<objs.length?objs[i].getVal()+valTotal(i+1):0;
	}
	
	/**
	 * @brief Ecrit la chaine s dans le fichier path
	 * @param path
	 * @param s
	 */
	public void writeInFile(String path, String s) {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(path));
			@SuppressWarnings("resource")
			Scanner sc = new Scanner(s);
			while (sc.hasNextLine()) {
				out.write(sc.nextLine());
				if (sc.hasNextLine()) out.newLine();
			}
			out.close();
		} catch (IOException e) {
			System.err.println("Impossible de creer / d'ouvrir le fichier en ecriture (" + path + ")");
			System.exit(0);
		}
	}
}
