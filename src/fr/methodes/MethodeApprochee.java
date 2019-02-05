package fr.methodes;

import java.util.ArrayList;

import fr.objet.Objet;
import fr.quicksort.QuickSort;

public class MethodeApprochee {
	
	private MethodeApprochee() {}
	
	/**
	 * @param objs
	 * @param poidMax
	 * @return tableau optimal selon la meth. approchee des objets a mettre dans le sac 
	 */
	public static Objet[] process(Objet[] objs, double poidMax) {
		// Creation des indices de tri des objets
		double[] indTri = new double[objs.length];
		for (int i = 0; i < objs.length; i++)
			indTri[i] = objs[i].getVal()/objs[i].getPoid();
		// Tri de la liste d'objet
		QuickSort.tri(objs, indTri);
		int poid = 0;
		ArrayList<Objet> sac = new ArrayList<>();
		// Ajout séquenciel des objets triés tant que le poid du sac ne depasse pas le poidMax
		for (int i = 0; i < objs.length; i++) {
			if (poid+objs[i].getPoid() <= poidMax) {
				poid+=objs[i].getPoid();
				sac.add(objs[i]);
			}
		}
		// Tranfo de la liste d'objet en tableau statique
		return sac.toArray(new Objet[sac.size()]);
	}
	
}
