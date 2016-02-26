import java.awt.Color;
import java.awt.Graphics;


public abstract class Manzana {
	protected int x, y;
	protected int bloque;
	protected Color col;
	
	public Manzana(int nx, int ny, int bloque, Color c){
		x = nx;
		y = ny;
		this.bloque = bloque;
		col = c;
	}
	
	public void dibujar(Graphics g){
		g.setColor(this.getCol());
		g.fillOval(x, y, bloque, bloque);
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

	public int getBloque() {
		return bloque;
	}

	public void setBloque(int bloque) {
		this.bloque = bloque;
	}

	public Color getCol() {
		return col;
	}

	public void setCol(Color col) {
		this.col = col;
	}
	
}
