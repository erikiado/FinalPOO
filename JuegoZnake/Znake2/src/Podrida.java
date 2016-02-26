import javax.swing.ImageIcon;


public class Podrida extends Manzana{

	public Podrida(int nx, int ny, int bloque) {
		super(nx, ny, bloque);
		img = new ImageIcon(getClass().getResource("podrida.png"));

	}

	public boolean lanzarDado(){
		int dado = (int)(Math.random()*10);
		return dado % 10 == 0;
	}
	
}
