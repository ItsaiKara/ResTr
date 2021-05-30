
import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author gprebot
 */
public class Jeton implements Serializable{
    private int x;
    private int y;
    private final boolean equipe;

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
    
}
