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

	public static boolean victoire(ArrayList<Jeton>jetons){
		boolean blocageHaut = false;
		boolean blocageBas = false;
		boolean blocageGauche = false;
		boolean blocageDroit = false;

		for (Jeton jeton : jetons) {
			for (Jeton jetonAutre : jetons) {
				if (jeton.getX()-1 == jetonAutre.getX() && jeton.getEquipe() != jetonAutre.getEquipe()) {
			if (jeton.getY() == jetonAutre.getY()) {
				blocageHaut = true;
			}
				}
				else if (jeton.getX()+1 == jetonAutre.getX()  && jeton.getEquipe() != jetonAutre.getEquipe()) {
					if (jeton.getY() == jetonAutre.getY()) {
				blocageBas = true;
			}
				}
				else if (jeton.getY()-1 == jetonAutre.getY()  && jeton.getEquipe() != jetonAutre.getEquipe()){
					if (jeton.getX() == jetonAutre.getX()) {
				blocageGauche = true;
			}
				}
				else if (jeton.getY()+1 == jetonAutre.getY()  && jeton.getEquipe() != jetonAutre.getEquipe()) {
					if (jeton.getX() == jetonAutre.getX()) {
				blocageDroit = true;
			}
				}
			}
			if (blocageBas && blocageHaut || blocageGauche && blocageDroit) {
				if (!jeton.getEquipe()) {
					System.out.println("Victoire equipe Bleu");
				}
				else{
					System.out.println("Victoire equipe Rouge");
				}
				return true;
			}
			else{
				blocageBas = false;
				blocageHaut = false;
				blocageDroit = false;
				blocageGauche = false;
			}
		}
		return false;
	}

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

		while (!victoire(Jetons)) {            
			for (Jeton Jeton : Jetons) {
				try {
					String recu = input.readLine();
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
							System.exit(0);
					}
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
	}
}
