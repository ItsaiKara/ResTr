
import java.util.ArrayList;
import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author TYF
 */
public class Joueur {
	private int equipe;
	private ArrayList<Point> points;

	public Joueur(int equipe) {
		this.equipe = equipe;
		points = new ArrayList<>();
		this.repartisPoints();
	}
	
	public void  repartisPoints(){
		if (equipe == 1) {
			Point p1 = new Point(3, 1, 1);
			Point p2 = new Point(4, 2, 2);
			Point p3 = new Point(6, 1, 3);
			Point p4 = new Point(5, 2, 4);
			this.points.add(p1);	this.points.add(p2);	this.points.add(p3);	this.points.add(p4);
		} else {
			Point p5 = new Point(3, 8, 5);
			Point p6 = new Point(4, 7, 6);
			Point p7 = new Point(6, 8, 7);
			Point p8 = new Point(5, 7, 8);
			this.points.add(p5);	this.points.add(p6);	this.points.add(p7);	this.points.add(p8);
		}
		
	}

	public int getEquipe() {
		return equipe;
	}

	public ArrayList<Point> getPoints() {
		return points;
	}
	
	
	public static void main(String[] args) {
		
		/*System.out.println(j.points.size());
		for (int i = 0; i < j.getPoints().size(); i++) {
			System.out.println(j.getPoints().get(i).toString());
		}*/
	}
}
