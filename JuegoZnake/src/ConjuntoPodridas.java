import java.awt.Graphics;
import java.util.LinkedList;


public class ConjuntoPodridas {
	private LinkedList<Podrida> p;
	
	public ConjuntoPodridas(){
		p = new LinkedList<Podrida>();
	}
	
	public void dibujarPodridas(Graphics g){
		for(Podrida p : p){
			p.dibujar(g);
		}
	}
	
}
