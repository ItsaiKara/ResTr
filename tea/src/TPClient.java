import java.awt.Frame;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter; // Window Event
import java.awt.event.WindowEvent; // Window Event

import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;


import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Alain BOUJU
 *
 */
public class TPClient extends Frame {
    
    public static final int portEcoute = 2000;

	byte [] etat = new byte [2*10*10];
	TPPanel tpPanel;
	TPCanvas tpCanvas;
	Timer timer;
        static PrintWriter output = null;
        static ObjectInputStream input = null;
        static ArrayList<Jeton> jetons;

	/** Constructeur */
	public TPClient()
	{
		setLayout(new BorderLayout());
		tpPanel = new TPPanel(this);
		add("North", tpPanel);
		tpCanvas = new TPCanvas(this.etat);
		add("Center", tpCanvas);
		
		timer = new Timer();
		timer.schedule ( new MyTimerTask (  ) , 500,500) ;

	}
	
	/** Action vers droit */
	public synchronized void droit()
	{
		System.out.println("Droit");
		try{
		    output.println("droite");
                    jetons = (ArrayList < Jeton >) input.readObject();
                    tpCanvas.repaint();
		}
		catch(Exception e)
                {
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }
		
	}
	/** Action vers gauche */
	public synchronized void gauche()
	{
		System.out.println("Gauche");
		try{
		    output.println("gauche");
                    tpCanvas.repaint();
		}
		catch(Exception e)
                {
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }
		
	}
	/** Action vers gauche */
	public synchronized void haut()
	{
		System.out.println("Haut");
		try{
                    output.println("haut");
                    tpCanvas.repaint();
		}
		catch(Exception e)
                {
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }
	
	}
	/** Action vers bas */
	public synchronized void bas ()
	{
		System.out.println("Bas");
		try{
		    output.println("bas");
                    tpCanvas.repaint();
		}
		catch(Exception e)
                {
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }
		
	}
	/** Pour rafraichir la situation */
	public synchronized void refresh ()
	{
//		try {
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

		tpCanvas.repaint();
	}
	/** Pour recevoir l'Etat */
//	public void receiveEtat()
//	{
//		try{
//
//		}
//		catch (IOException ex) {
//			ex.printStackTrace();
//		}
//
//	}
	/** Initialisations */
	/**public void minit(int number, int pteam, int px, int py)
	{
		try{
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}

	}**/
	
	public String etat()
	{
		String result = new String();
		return result;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
            
            // Creation de la socket
            Socket socket = null;
            try {
                socket = new Socket("localhost", Serveur.portEcoute);
            } catch(UnknownHostException e) {
                System.err.println("Erreur sur l'hôte : " + e);
                System.exit(-1);
            } catch(IOException e) {
                System.err.println("Creation de la socket impossible : " + e);
                System.exit(-1);
            }

            // Association d'un flux d'entree et de sortie
            //BufferedReader input = null;
            
            try {
                input = new ObjectInputStream(socket.getInputStream());
                output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            } catch(IOException e) {
                System.err.println("Association des flux impossible : " + e);
                System.exit(-1);
            }
            
            try {
                jetons = (ArrayList < Jeton >) input.readObject();
            } catch (IOException e) {
                System.err.println("Erreur de reception des jetons: " + e);
                System.exit(-1);
            } catch (ClassNotFoundException e) {
                System.err.println("Erreur de reception des jetons, classe non trouvé: " + e);
                System.exit(-1);
            }
            
            jetons.forEach(jeton -> {
                System.out.println(jeton.toString());
        });
            
//            System.out.println("args :"+args.length);
//            if (args.length != 4) {
//                    System.out.println("Usage : java TPClient number color positionX positionY ");
//                    System.exit(0);
//            }
            try {
                    TPClient tPClient = new TPClient();
                    // tPClient.minit(number, team, x, y);


                    // Pour fermeture
                    tPClient.addWindowListener(new WindowAdapter() {
                            public void windowClosing(WindowEvent e) {
                                    System.exit(0);
                            }
                    });

                    // Create Panel back forward

                    tPClient.pack();
                    tPClient.setSize(1000, 1000+200);
                    tPClient.setVisible(true);

            } catch(Exception e) {
                    e.printStackTrace();
            }
	}
	/** Pour rafraichir */
	class MyTimerTask extends TimerTask{
		
		public void run ()
		{
			System.out.println("refresh");
          		refresh();
		}
	}
	
}
