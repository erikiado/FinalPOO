import javax.swing.ImageIcon;


public class Sana extends Manzana {

	public Sana(int nx, int ny, int bloque) {
		super(nx, ny, bloque);
		img = new ImageIcon(getClass().getResource("manzana.png"));
	}
	
	
}
