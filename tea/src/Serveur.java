/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


/**
 *
 * @author gprebot
 * @author teloy
 */
public class Serveur {
	public static final int portEcoute = 2000;

	/* Verification de victoire d'une des équipe */
	public static boolean victoire(ArrayList<Jeton>jetons){
		boolean blocageHaut = false;
		boolean blocageBas = false;
		boolean blocageGauche = false;
		boolean blocageDroit = false;

		for (Jeton jeton : jetons) { // On verifie chaque jetons pour determiner si certains sont bloqué
			for (Jeton jetonAutre : jetons) {
				if (jeton.getX()-1 == jetonAutre.getX() && jeton.getY() == jetonAutre.getY()) { //verifie le blocage par le haut
					if (jeton.getEquipe() != jetonAutre.getEquipe()) { // Si le jeton bloquant n'est pas de la même équipe
						blocageHaut = true;
					}
				}
				else if (jeton.getX()+1 == jetonAutre.getX()  && jeton.getY() == jetonAutre.getY()) { //verifie le blocage par le bas
					if (jeton.getEquipe() != jetonAutre.getEquipe()) {
						blocageBas = true;
					}
				}
				else if (jeton.getY()-1 == jetonAutre.getY()  && jeton.getX() == jetonAutre.getX()){ //verifie le blocage par la gauche
					if (jeton.getEquipe() != jetonAutre.getEquipe()) {
						blocageGauche = true;
					}
				}
				else if (jeton.getY()+1 == jetonAutre.getY()  && jeton.getX() == jetonAutre.getX()) { //verifie le blocage par la droite
					if (jeton.getEquipe() != jetonAutre.getEquipe()) {
						blocageDroit = true;
					}
				}
			}
			if ((blocageBas && blocageHaut) || (blocageGauche && blocageDroit)) { // Si le jeton est bloqué verticalement
				if (!jeton.getEquipe()) {                                         // OU horizontalement alors la victoire est
					System.out.println("Victoire equipe Bleu");                   // déclaré pour l'equipe adverse
				}
				else{
					System.out.println("Victoire equipe Rouge");
				}
				return true;
			}
			else{                           // Sinon on verifie les jetons restant
				blocageBas = false;
				blocageHaut = false;
				blocageDroit = false;
				blocageGauche = false;
			}
		}
		return false;
	}

	/**
	 * Appelé lors du clique sur le boutton eponyme
	 * @param jetonABouge le jeton qui doit bouger
	 * @param jetons la liste de tout les jetons
	 * @return true si le jeton a bougé, false sinon
	 */
	public static boolean haut(Jeton jetonABouge, ArrayList<Jeton>jetons){

		if (jetonABouge.getX()-1 < 0) {
			System.err.println("Deplacement hors terrain !");
			return false;
		}

		for (Jeton jeton : jetons) {
			if (jetonABouge.getX()-1 == jeton.getX() && jetonABouge.getY() == jeton.getY()) {
				System.err.println("Joueurs dejà sur la case !");
				return false;
			}
		}
		return true;
	}
	/**
	 * Appelé lors du clique sur le boutton eponyme
	 * @param jetonABouge le jeton qui doit bouger
	 * @param jetons la liste de tout les jetons
	 * @return true si le jeton a bougé, false sinon
	 */
	public static boolean bas(Jeton jetonABouge, ArrayList<Jeton>jetons){

		if (jetonABouge.getX()+1 > 9) {
			System.err.println("Deplacement hors terrain !");
			return false;
		}

		for (Jeton jeton : jetons) {
			if (jetonABouge.getX()+1 == jeton.getX() && jetonABouge.getY() == jeton.getY()) {
				System.err.println("Joueurs dejà sur la case !");
				return false;
			}
		}
		return true;
	}
	/**
	 * Appelé lors du clique sur le boutton eponyme
	 * @param jetonABouge le jeton qui doit bouger
	 * @param jetons la liste de tout les jetons
	 * @return true si le jeton a bougé, false sinon
	 */
	public static boolean gauche(Jeton jetonABouge, ArrayList<Jeton>jetons){

		if (jetonABouge.getY()-1 < 0) {
			System.err.println("Deplacement hors terrain !");
			return false;
		}

		for (Jeton jeton : jetons) {
			if (jetonABouge.getX() == jeton.getX() && jetonABouge.getY()-1 == jeton.getY()) {
				System.err.println("Joueurs dejà sur la case !");
				return false;
			}
		}
		return true;
	}
	/**
	 * Appelé lors du clique sur le boutton eponyme
	 * @param jetonABouge le jeton qui doit bouger
	 * @param jetons la liste de tout les jetons
	 * @return true si le jeton a bougé, false sinon
	 */
	public static boolean droite(Jeton jetonABouge, ArrayList<Jeton>jetons){

		if (jetonABouge.getY()+1 > 9) {
			System.err.println("Deplacement hors terrain !");
			return false;
		}

		for (Jeton jeton : jetons) {
			if (jetonABouge.getX() == jeton.getX() && jetonABouge.getY()+1 == jeton.getY()) {
				System.err.println("Joueurs dejà sur la case !");
				return false;
			}
		}
		return true;
	}

	public static void main(String args[]){
		// Liste des jetons de chaque equipe
		ArrayList<Jeton>Jetons = new ArrayList<>();

		// Equipe Bleu (false)
		Jeton JetonBleu1 = new Jeton(0,4,false);
		Jeton JetonBleu2 = new Jeton(0,5,false);
		// Equipe Rouge (true)
		Jeton JetonRouge1 = new Jeton(9,4,true);
		Jeton JetonRouge2 = new Jeton(9,5,true);

		Jetons.add(JetonBleu1);
		Jetons.add(JetonBleu2);
		Jetons.add(JetonRouge1);
		Jetons.add(JetonRouge2);


		// Creation de la socket serveur
		ServerSocket socketServeur = null;
		try {
			socketServeur = new ServerSocket(portEcoute);
		} catch(IOException e) {
			System.err.println("Creation de la socket impossible : " + e);
			System.exit(-1); // code retour pour le système
		}

		// Attente d'une connexion d'un client
		Socket socketClient = null;
		try {
			System.out.println("Attente de connexion");
			socketClient = socketServeur.accept();
			System.out.println("Connexion OK");
		} catch(IOException e) {
			System.err.println("Erreur lors de l'attente d'une connexion : " + e);
			System.exit(-1); // code retour pour le système
		}

		// Association d'un flux d'entree et de sortie
		BufferedReader input = null; //  buffer de lecture
		ObjectOutputStream output = null; // flux d'ecriture
		try {
			input = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
			output = new ObjectOutputStream(socketClient.getOutputStream());
		} catch(IOException e) {
			System.err.println("Association des flux impossible : " + e);
			System.exit(-1);
		}

		try {
			output.writeObject(Jetons);
		} catch (IOException e) {
			System.err.println("Erreur lors de l'envoie des jetons : " + e);
			System.exit(-1);
		}
		// Tant qu'aucune equipe n'a gagnée
		boolean victoire = true;
		while (victoire) {
			for (Jeton Jeton : Jetons) {
				if (victoire(Jetons)) { // verification de victoire
					victoire = false;
					break;
				}
				try {
					String recu = input.readLine(); // Recuperation de la commande de déplacement
					switch(recu){
						case "haut":
							if (haut(Jeton, Jetons)) {
								Jeton.setX(Jeton.getX()-1);
							}
							break;
						case "bas":
							if (bas(Jeton, Jetons)) {
								Jeton.setX(Jeton.getX()+1);
							}
							break;
						case "gauche":
							if (gauche(Jeton, Jetons)) {
								Jeton.setY(Jeton.getY()-1);
							}
							break;
						case "droite":
							if (droite(Jeton, Jetons)) {
								Jeton.setY(Jeton.getY()+1);
							}
							break;
						default:
							System.out.println("Commande non reconnu : " + recu);
							System.exit(0); // Un peu extrême
					}

					/**
					 * Une fois le deplacement effectué, s'il était possible,
					 * on envoi l'état du jeu au client sous la forme d'une
					 * liste contenant les jetons en jeu
					 */
					ArrayList<Jeton>tampon = new ArrayList<>();
					Jetons.forEach(jeton -> {
						tampon.add(new Jeton(jeton.getX(),jeton.getY(),jeton.getEquipe()));
						System.out.println(jeton.toString());
					});
					System.out.println("--------");
					output.writeObject(tampon);
				}
				catch(IOException e) {
					System.err.println("Erreur lors de la lecture : " + e);
					System.exit(-1);
				}
			}
		}
		// Fermeture des flux et des sockets
		try {
			input.close();
			output.close();
			socketClient.close();
			socketServeur.close();
			System.out.println("Serveur déconnecté.");
			System.exit(0);
		} catch(IOException e) {
			System.err.println("Erreur lors de la fermeture des flux et des sockets : " + e);
			System.exit(-1);
		} 
	}
}
