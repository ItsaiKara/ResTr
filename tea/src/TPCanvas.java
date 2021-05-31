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
    ArrayList<Jeton> jetons = new ArrayList<>(); //Liste des jetons en jeu
    
    Color [] color = {Color.black,Color.blue,Color.red, Color.green, Color.yellow}; //La list des couleurs disponibles
    // 0 black
    // 1 blue
    // 2 red
    // 3 green (bloqué blue)
    // 4 yellow (bloqué red);
    
	/**
	 * Constructeur de la classe
	 * @param pEtat non implémenté
	 * @param jt liste des jetons en jeu
	 */
    public TPCanvas(byte [] pEtat, ArrayList<Jeton> jt)
    {
        this.etat = pEtat;
        this.jetons = jt;
    }
    
    public void paint(Graphics win)
    {
        System.out.println("Paint");
        paintCarte(win);
        drawEtat(win);
    }
    
    public Dimension getMinimumSize() {
        return new Dimension(size, size);
        
    }
    
	/**
	 * Dessine la carte de jeu sur la fenetre donnée en parametre
	 * @param win la fenetre
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
	 * Gere les cordonées des joueurs et appel la méthode pour les dessiner
	 * @param win La fentre ou s'afficherons les joueurs
	 */
    public void drawEtat(Graphics win)
    {
        for (int i=0;i<=10;i++){ //parcour de l'axe X
            for (int j = 0; j <= 10; j++) { //parcours de l'axe Y
                for (Jeton jeton : getJetons()) { // Pour chaque jetons qu'il y a en jeu ...
                    
                    if (jeton.getX() == i && jeton.getY() == j) { //Verifie si un jeton est présent aux coordonées vérifiées
                        //System.err.println("Match");
                        if (jeton.getEquipe()) { //Si le jeton est un jeton de l'equipe rouge alors affiche le en rouge aux bonnes coordonées
                            drawPlayer(win, jeton.getY(), jeton.getX(), 1);
                        } else { //Sinon affiche le en bleue aux bonnes coordonées
                            drawPlayer(win, jeton.getY(), jeton.getX(), 2);
                        }
                    }
                }
            }
        }
    }
    
	/**
	 * Affiche le jeton aux coordonées et de la couleur donnée
	 * @param win La fentre ou afficher le jeton
	 * @param x la coordonée en X
	 * @param y la coordonée en Y
	 * @param type la couleur (parmis la liste en attribut)
	 */
    public void drawPlayer(Graphics win, int x, int y, int type)
    {
        win.setColor(color[type]);
        win.fillOval ((x*size/nbPosition)+1,(y*size/nbPosition)+1, size/nbPosition-1, size/nbPosition-1);
    }
    
	/**
	 * Setter de l'attribut jetons
	 * @param jetons 
	 */
    public void setJetons(ArrayList<Jeton> jetons) {
        this.jetons = jetons;
    }
    
	/**
	 * Getter de l'attribut jeton
	 * @return 
	 */
    public ArrayList<Jeton> getJetons() {
        return jetons;
    }
}
