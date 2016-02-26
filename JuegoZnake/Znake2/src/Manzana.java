
import java.awt.Graphics;

import javax.swing.ImageIcon;


public abstract class Manzana {
	protected int x, y;
	protected int bloque;
	protected ImageIcon img;
	
	public Manzana(int nx, int ny, int bloque){
		x = nx;
		y = ny;
		this.bloque = bloque;
	}
	
	public void dibujar(Graphics g){
		g.drawImage(img.getImage(),x, y, null);
	}

}
