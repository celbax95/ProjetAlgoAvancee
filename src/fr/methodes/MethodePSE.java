package fr.methodes;

import java.util.ArrayList;

import fr.arbre.Arbre;
import fr.objet.Objet;

public class MethodePSE {
	private static Objet[] approch;
	
	private static Arbre<Objet> arbre;
	private static double inf, poidMax;
	private static Objet[] objs;
	private static int profMax;
	
	private static ArrayList<Integer> sol;
	/**
	 * @param i
	 * @return valeur maximal du sac atteignable depuis la profondeur i
	 */
	private static double calcBSup(int i) {
		double p = 0.;
		for (int j = i; j < objs.length; j++)
			p += objs[j].getVal();
		return p;
	}
	
	/**
	 * @return tableau statique optimal des objets a mettre dans le sac
	 */
	private static Objet[] getBag() {
		if (sol == null)
			return approch;
		ArrayList<Objet> sac = new ArrayList<>();
		for (Integer i : sol)
			sac.add(objs[i-1]);
		return sac.toArray(new Objet[sac.size()]);
	}
	
	/**
	 * @brief Initialisation de l'arbe binaire de recherche avec les objets
	 * @param objs
	 * @param i
	 * @param a
	 */
	private static void initArbre(Objet[] objs, int i, Arbre<Objet> a) {
		if (i >= profMax)
			return;
		a.setG(new Arbre<Objet>(null));
		initArbre(objs, i+1, a.getG());
		a.setD(new Arbre<Objet>(objs[i]));
		initArbre(objs, i+1, a.getD());
	}
	
	/**
	 * @param objs
	 * @param poidMax
	 * @return liste optimale des objets a mettre dans le sac
	 */
	public static Objet[] process(Objet[] objs, double poidMax) {
		// Initialisation des attributs tampon
		MethodePSE.objs = objs;
		MethodePSE.profMax = objs.length;
		MethodePSE.poidMax = poidMax;
		// Calcul de la borne inférieure
		inf = 0; 
		approch = MethodeApprochee.process(objs, poidMax);
		for (Objet o : approch)
			inf += o.getVal();
		sol = null;
		// Creation de l'arbre
		arbre = new Arbre<Objet>(null);
		initArbre(objs,0,arbre);
		// Lancement de la recherche de la solution optimale
		rech(arbre, new ArrayList<Integer>(), 0, 0, 0);
		return getBag();
	}
	
	/**
	 * @brief Recherche recursive de la meilleure solution par 
	 * 	parcourt de l'arbre, la solution est stockée dans sol
	 * @param a
	 * @param l
	 * @param v
	 * @param p
	 * @param prof
	 */
	@SuppressWarnings("unchecked")
	private static void rech(Arbre<Objet> a, ArrayList<Integer> l, double v, double p, int prof) {
		// Si l'arbre est null (arrivée au feuilles)
		if (a == null)
			return;
		//  Si la branche est un ajout d'objet (branche droite)
		if (a.getVal() != null) {
			v += a.getVal().getVal();
			p += a.getVal().getPoid();			
		}
		// Si la valeur peut améliorer la solution précédente
		// et le poid de la branche n'est pas trop elevé
		if ((v+calcBSup(prof) <= inf) || (p>poidMax))
			return;
		// Si la valeur est valide
		if (a.getVal() != null)
			l.add(prof);
		// Si on est au feuilles
		if (prof >= profMax) {
			inf = v;
			sol = (ArrayList<Integer>) l.clone();
		}
		// Appel récursif a gauche puis a droite
		rech(a.getG(),(ArrayList<Integer>) l.clone(),v,p,prof+1);
		rech(a.getD(),l,v,p,prof+1);
	}
}
