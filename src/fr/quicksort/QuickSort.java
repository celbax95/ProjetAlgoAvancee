package fr.quicksort;

import java.util.Random;

public class QuickSort {
	
	private static Random r = new Random();
	private static Object[] t;
	private static double[] indTri;
	
	private QuickSort() {};
	
	/**
	 * @brief intervertit t1 et t2 dans la liste t
	 * @param t1
	 * @param t2
	 */
	private static void swap(int t1, int t2) {
		Object tmp = t[t1];
		t[t1] = t[t2];
		t[t2] = tmp;
		double tmp2 = indTri[t1];
		indTri[t1] = indTri[t2];
		indTri[t2] = tmp2;
	}
	
	/**
	 * @brief repartit l'enssemble des objets autour du pivot
	 * @param prem
	 * @param der
	 * @param p
	 * @return nouvelle position du pivot
	 */
	private static int repartition(int prem, int der, int p) {
		swap(p, der);
		int j = prem;
		
		for (int i = prem; i < der; i++) {
			if (indTri[i] >= indTri[der]) {
				swap(i,j);
				j++;
			}
		}
		swap(der,j);
		return j;
	}
	
	/**
	 * @brief fonction récursive principale
	 * @param prem
	 * @param der
	 */
	private static void tri(int prem, int der) {
		if (prem < der) {
			int p = r.nextInt(der-prem)+prem;
			p = repartition(prem,der,p);
			tri(prem,p-1);
			tri(p+1,der);
		}
	}	
	
	/**
	 * @brief trie le tableau t en fonction des indices de tri respectifs
	 * @param t
	 * @param indTri
	 */
	public static void tri(Object[] t, double[] indTri) {
		QuickSort.t = t;
		QuickSort.indTri = indTri;
		tri(0,t.length-1);
		tri(0,t.length-1);
	}
}
