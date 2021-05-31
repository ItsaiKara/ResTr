/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.Serializable;

/**
 *
 * @author gprebot
 * @author teloy
 * Classe representant les jetons posé sur la grille de jeu
 */
public class Jeton implements Serializable{
    private int x;
    private int y;
    private final boolean equipe;
    private static final long serialVersionUID = 1L;

    public Jeton(int x, int y, boolean equipe) {
        this.x = x;
        this.y = y;
        this.equipe = equipe;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean getEquipe() {
        return equipe;
    }

	@Override
	public String toString() {
		return "Jeton{" + "x=" + x + ", y=" + y + ", equipe=" + equipe + '}';
	}
	
	
    
}
