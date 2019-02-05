package fr.methodes;

import java.util.ArrayList;

import fr.objet.Objet;

public class MethodeExDyn {
	
	private MethodeExDyn() {}
	
	private static double margin = 0.0001;
	
	/**
	 * @param objs
	 * @param poidMax
	 * @return liste optimale des objets a mettre dans le sac
	 */
	public static Objet[] process(Objet[] objs, double poidMax) {
		
		// Creation matrice
		Double[][] tmp = new Double[objs.length][(int) (poidMax+1)];
		
		// Init de la matrice a 0.
		for (int i = 0; i < tmp.length; i++)
			for (int j = 0; j < tmp[1].length; j++)
				tmp[i][j] = 0.;
		
		// Premiere ligne
		for (int i = 0; i < poidMax+1; i++) {
			if (objs[0].getPoid() > i)
				tmp[0][i] = 0.;
			else
				tmp[0][i] = objs[0].getVal();
		}
		// Lance la fonction principale pour creer la matrice et recupere les objets
		return getBag(process(tmp, objs),objs,(int)poidMax);
	}
	
	/**
	 * @brief Création de la matrice
	 * @param tmp
	 * @param objs
	 * @return
	 */
	private static Double[][] process(Double[][] tmp, Objet[] objs) {
		// ligne par ligne
		for (int i = 1; i < tmp.length; i++) {
			// Parcourt horizontal de gaucghe a droite des cases de la ligne
			for (int j = 0; j < tmp[0].length; j++) {
				if (objs[i].getPoid() > j)
					tmp[i][j] = tmp[i-1][j];
				else				
					tmp[i][j] = max(tmp[i-1][j],tmp[i-1][(int) (j-objs[i].getPoid())]+objs[i].getVal());
			}
		}
		return tmp;
	}
	
	/**
	 * @param v1
	 * @param v2
	 * @return v1 si v1>v2, v2 sinon
	 */
	private static double max(double v1, double v2) {
		if (v1>v2) return v1;
		return v2;
	}
	
	@SuppressWarnings("unused")
	/**
	 * @brief Affiche la matrice dans system.out
	 * @param tmp
	 */
	private static void displayMat(Double[][] tmp) {
		for (int i = 0; i < tmp.length; i++) {
			for (int j = 0; j < tmp[0].length; j++)
				System.out.print((double) Math.round(tmp[i][j] * 100) / 100 + "|");
			System.out.println();
		}
	}
	
	/**
	 * @param tmp : matrice complete
	 * @param objs
	 * @param poidMax
	 * @return tableau statique des objets a mettre dans le sac
	 */
	private static Objet[] getBag(Double[][] tmp, Objet[] objs, int poidMax) {
		System.out.println();
		int i = objs.length-1, j=poidMax;
		ArrayList<Objet> sac = new ArrayList<>();
		while(Math.abs(tmp[i][j] - tmp[i][j-1]) <= margin) j--;
		while(j>0) {
			while (i>0 && Math.abs(tmp[i][j] - tmp[i-1][j]) <= margin) i--;
			j -= objs[i].getPoid();
			if (j>=0) {
				sac.add(objs[i]);
			}
			i--;
		}
		return sac.toArray(new Objet[sac.size()]);
	}
}
