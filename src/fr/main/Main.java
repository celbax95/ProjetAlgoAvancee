package fr.main;

import fr.sacADos.SacADos;

public class Main {
	// sac.jar nomDuFichier.txt poidMaxDuSac Methode(app,dyn,pse)
	public static void main(String[] args) {
		System.out.println();
		
		try {
			// Verification de l'existance de la methode
			SacADos.testMethode(args[2]);
			// Creation du sac avec le nom du fichier et le poid max du sac
			SacADos sad = new SacADos(args[0],Integer.parseInt(args[1]));
			
			// init du chrono d'execution
			System.out.println(" --- SAC INIT ---\n");
			System.out.println(sad+"\n");
			
			// Resolution en fction de la methode choisie
			sad.resoudre(args[2]);
			System.out.println("\n --- SAC RES ---\n\n"+sad);			
		} catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("Appel du programme : \n"
					+ "prog.jar fichierFormate.txt poidSac methode(app, dyn, pse)\n");
			System.exit(0);
		} catch (NumberFormatException e) {
			System.err.println("Le poid donné est invalide");
			System.exit(0);
		}
	}
}
