import java.awt.Color;
import java.awt.Graphics;


public class Podrida extends Manzana{
	
	public Podrida(int nx, int ny, int bloque) {
		super(nx, ny, bloque);
		
	}

	public void dibujar(Graphics g){
		g.setColor(new Color(11441408));
		g.fillOval(x, y, bloque, bloque);
	}
	
	
}
