import java.awt.*;
import java.util.*;

import javax.swing.ImageIcon;

public class Snake {
	private int cPosX, cPosY;
	private LinkedList<Point> cuerpo;
	private int bloque;
	private ImageIcon cabeza;
	private boolean[] direccion;
	
	public Snake(int x , int y, int bloque){
		cPosX = x;
		cPosY = y;
		this.bloque = bloque;
		direccion = new boolean[] {true,false,false,false};
		cuerpo = new LinkedList<Point>();
		cuerpo.add(new Point(cPosX,cPosY));
		cabeza = new ImageIcon(getClass().getResource("cSerpiente0.png"));
	}
	
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
	
	public void crecer(){
		cuerpo.add(cuerpo.getLast());
	}
	
	public boolean colisionPropia(){
		for(int i = 1; i < cuerpo.size()-1; i++){
			if(cuerpo.get(i).getX() == cPosX && cuerpo.get(i).getY() == cPosY){
				return true;
			}
		}
		return false;
	}
	
	public ImageIcon getCabeza() {
		return cabeza;
	}

	public void setCabeza(ImageIcon cabeza) {
		this.cabeza = cabeza;
	}

	public int getcPosX() {
		return cPosX;
	}

	public LinkedList<Point> getCuerpo() {
		return cuerpo;
	}

	public void setCuerpo(LinkedList<Point> cuerpo) {
		this.cuerpo = cuerpo;
	}

	public void setcPosX(int cPosX) {
		this.cPosX = cPosX;
	}

	public int getcPosY() {
		return cPosY;
	}

	public void setcPosY(int cPosY) {
		this.cPosY = cPosY;
	}

	public int getBloque() {
		return bloque;
	}

	public void setBloque(int bloque) {
		this.bloque = bloque;
	}

	public boolean[] getDireccion() {
		return direccion;
	}

	public void setDireccion(boolean[] direccion) {
		this.direccion = direccion;
	}
}
