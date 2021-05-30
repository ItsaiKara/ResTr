import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 * @author gprebot
 * @author teloy
 *
 */
public class TPCanvas extends Canvas {

	int size =1000;
	int nbPosition = 10;
	byte [] etat;
	ArrayList<Jeton> jetons = new ArrayList<>(); 
	
	Color [] color = {Color.black,Color.blue,Color.red, Color.green, Color.yellow}; 
	// 0 black
	// 1 blue
	// 2 red
	// 3 green (bloqué blue)
	// 4 yellow (bloqué red);
	
	public TPCanvas(byte [] pEtat, ArrayList<Jeton> jt)
	{
		this.etat = pEtat; //unused
		this.jetons = jt; //Liste des jetons
	}
		
	public void paint(Graphics win)
	{
		//System.out.println("Paint");
		paintCarte(win);
		drawEtat(win);
	}
	
	public Dimension getMinimumSize() {
	    return new Dimension(size, size); 
	 
	  }

	/**
	 * Affiche la carte de jeu
	 * @param win 
	 */
	public void paintCarte(Graphics win)
	{
		int h,w;
		int larg = 10;
		h = getSize().height;
		w = getSize().width;
		win.drawRect(0,0, size-1, size-1);	// Draw border
		for (int i=1; i < 10; i++)
		{
			// Dessin
			win.setColor(Color.black);
			win.drawLine(i*size/nbPosition, 0, i*size/nbPosition, size);
			win.drawLine(0, i*size/nbPosition, size, i*size/nbPosition);
		}
	}

	/**
	 * Appelé a chaque actualisation et repaint, prétraitement pour l'affichage des joueurs
	 * @param win 
	 */
	public void drawEtat(Graphics win){ 
		for (int i=0;i<=10;i++){ //axe X
			for (int j = 0; j <= 10; j++) { //axe Y
				for (Jeton jeton : getJetons()) { //La liste des jetons
					if (jeton.getX() == i && jeton.getY() == j) { //Si un jeton dans la liste poséde les coordonées <i,j> (i = x, j= y)
						if (jeton.getEquipe()) { //Appel la fonction drawplayer avec la bonne equipe en parametre (affichage des couleurs)
							drawPlayer(win, jeton.getY(), jeton.getX(), 1);
						} else {
							drawPlayer(win, jeton.getY(), jeton.getX(), 2);
						}
						
					}
				}
			}
		}
	}
	/**
	 * Affiche les joueurs dans les bonnes cases
	 * @param win
	 * @param x
	 * @param y
	 * @param type 
	 */
	public void drawPlayer(Graphics win, int x, int y, int type)
	{
		 win.setColor(color[type]);
		 win.fillOval ((x*size/nbPosition)+1,(y*size/nbPosition)+1, size/nbPosition-1, size/nbPosition-1);
	}

	/**
	 * Setter simple
	 * @param jetons 
	 */
	public void setJetons(ArrayList<Jeton> jetons) {
		this.jetons = jetons;
	}

	/**
	 * Getter simple
	 * @return 
	 */
        public ArrayList<Jeton> getJetons() {
            return jetons;
        }
        
        
	
	
}
