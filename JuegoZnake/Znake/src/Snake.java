import java.awt.*;
import java.util.*;
import javax.swing.ImageIcon;

public class Snake {

	private int cPosX, cPosY;
	private LinkedList<Point> cuerpo;
	private int bloque;
	private ImageIcon cabeza;
	private boolean[] direccion;

	//Constructor: Crea una serpiente en (x,y) en direccion hacia arriba
	public Snake(int x , int y){
		cPosX = x;
		cPosY = y;
		bloque = 10;
		direccion = new boolean[] {true,false,false,false};
		cuerpo = new LinkedList<Point>();
		cuerpo.add(new Point(cPosX,cPosY));
		cuerpo.add(new Point(cPosX,cPosY-bloque));
		cabeza = new ImageIcon(getClass().getResource("cSerpiente0.png"));
	}

	//Paint
	public void dibujarSerpiente(Graphics g){
		g.setColor(Color.GREEN);
		for(Point p: cuerpo){
			if(p.equals(cuerpo.getFirst())){
				g.drawImage(cabeza.getImage(),cPosX, cPosY, null);
			}else{
				g.fillRect(p.x, p.y, bloque,bloque);
			}
		}
		
	}

	//Dependiendo de la direccion mover un bloque la cabeza y anadir al cuerpo la nueva cabeza
	public void moverSerpiente(){
		cuerpo.remove(cuerpo.getLast());
		
		if(direccion[0]){
			//ADELANTE
			cPosY -= bloque;
		}else if(direccion[1]){
			//DERECHA
			cPosX += bloque;
		}else if(direccion[2]){
			//ABAJO
			cPosY += bloque;
		}else if(direccion[3]){
			//IZQUIERDA
			cPosX -= bloque;
		}
		
		cuerpo.addFirst(new Point(cPosX,cPosY));
	}

	//Agregar un pedazo de cuerpo en la cola al subir de nivel
	public void crecer(){
		cuerpo.add(cuerpo.getLast());
	}

	//Comprueba si la cabeza colisiona con su cuerpo
	public boolean colisionPropia(){
		for(int i = 1; i < cuerpo.size(); i++){
			if(cuerpo.get(i).getX() == cPosX && cuerpo.get(i).getY() == cPosY){
				return true;
			}
		}
		return false;
	}

	//Getters - Setters
	public void setCabeza(ImageIcon cabeza) {
		this.cabeza = cabeza;
	}

	public int getcPosX() {
		return cPosX;
	}

	public LinkedList<Point> getCuerpo() {
		return cuerpo;
	}

	public int getcPosY() {
		return cPosY;
	}

	public boolean[] getDireccion() {
		return direccion;
	}

	public void setDireccion(boolean[] direccion) {
		this.direccion = direccion;
	}
}
