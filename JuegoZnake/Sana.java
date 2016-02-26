import java.awt.Color;
import java.awt.Graphics;


public class Sana extends Manzana {

	public Sana(int nx, int ny, int bloque) {
		super(nx, ny, bloque);
		
	}
	
	
	public void dibujar(Graphics g){
		g.setColor(Color.RED);
		g.fillOval(x, y, bloque, bloque);
	}
	
	public void colision(Snake s){
		
	}
}
