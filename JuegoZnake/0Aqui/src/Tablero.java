import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.swing.*;
import javax.swing.Timer;


public class Tablero extends JPanel implements ActionListener, KeyListener{

	private static final long serialVersionUID = 1L;
	
	private final int BLOQUE = 10;
	private int fps;
	private Snake s;
	private Sana m;
	private ArrayList<Podrida> podridas;
	private Timer t, tManzana;
	private Random r;
	private int puntajeActual, puntajeTotal;
	private boolean gameOver;
	private int contManzanas;
	private ImageIcon fondo;
	private boolean pause;
	private Font fontGame, fontText;
	
	public Tablero(int ancho, int alto){
		fps = 100;
		this.setBackground(Color.BLACK);
		this.setSize(new Dimension(ancho,alto));
		this.setFocusable(true);
		this.addKeyListener(this);
		fondo = new ImageIcon(getClass().getResource("fondo.png"));
		r = new Random();
		m = new Sana(this.getWidth()/2,(this.getHeight()/2)-50,BLOQUE);
		t = new Timer(fps, this);
		tManzana = new Timer(5000,this);
		s = new Snake(this.getWidth()/2,this.getHeight()/2,BLOQUE);
		puntajeActual = 0;
		puntajeTotal = 0;
		gameOver = true;
		pause = true;
		podridas = new ArrayList<Podrida>();
		agregarPodrida();
		contManzanas = 0;
		fontGame = new Font("Stencil",Font.BOLD,35);
		fontText = new Font("Helvetica",Font.PLAIN,20);
	}
	
	public void paint(Graphics g){
		if(gameOver){
			if(pause){
				g.setColor(new Color(230,150,50));
				g.fillRect(0, 0, this.getWidth(), this.getHeight());
				g.setColor(Color.GREEN);
				g.setFont(fontGame);
				g.drawString("Game Over", (this.getWidth()/2)-100,(this.getHeight()/2)-100);
				g.setFont(fontText);
				g.drawString("Click Start!", (this.getWidth()/2)-50,(this.getHeight()/2));
			}else{
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, this.getWidth(), this.getHeight());
				g.setColor(Color.GREEN);
				g.setFont(fontGame);
				g.drawString("Game Over", (this.getWidth()/2)-100,(this.getHeight()/2)-100);
				g.setFont(fontText);
				g.drawString("Click Start!", (this.getWidth()/2)-50,(this.getHeight()/2));
			}	
		}else{
			if(pause){
				
			}else{
				//JUEGO EN CURSO
				g.drawImage(fondo.getImage(),0, 0, null);
				for(Podrida p : podridas){
					p.dibujar(g);
				}
				m.dibujar(g);
				s.dibujarSerpiente(g);
			}
		}
		
			
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource().equals(t)){
			s.moverSerpiente();	
			for(int i = 0; i < podridas.size(); i++){
				colision(podridas.get(i));
			}
			this.colision(m);
		}
		
		if(e.getSource().equals(tManzana)){
			podridas.add(new Podrida(Math.abs((r.nextInt()%(this.getWidth()/BLOQUE))*10),Math.abs((r.nextInt()%(this.getWidth()/BLOQUE))*10),BLOQUE));
		}
		
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		if(key == KeyEvent.VK_UP && !s.getDireccion()[2]){
			s.setDireccion(new boolean[] {true,false,false,false});
			s.setCabeza(new ImageIcon(getClass().getResource("cSerpiente0.png")));
		}else if(key == KeyEvent.VK_RIGHT && !s.getDireccion()[3]){
			s.setDireccion(new boolean[] {false,true,false,false});
			s.setCabeza(new ImageIcon(getClass().getResource("cSerpiente1.png")));
		}else if(key == KeyEvent.VK_DOWN && !s.getDireccion()[0]){
			s.setDireccion(new boolean[] {false,false,true,false});
			s.setCabeza(new ImageIcon(getClass().getResource("cSerpiente2.png")));
		}else if(key == KeyEvent.VK_LEFT && !s.getDireccion()[1]){
			s.setDireccion(new boolean[] {false,false,false,true});
			s.setCabeza(new ImageIcon(getClass().getResource("cSerpiente3.png")));
		}
		
		if(key == KeyEvent.VK_ENTER){
			pause = false;
			t.start();
			tManzana.start();
		}
		
		if(key == KeyEvent.VK_SPACE){
			pause = true;
			t.stop();
			tManzana.stop();
			
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public int getPuntajeActual() {
		return puntajeActual;
	}

	public void setPuntajeActual(int puntajeActual) {
		this.puntajeActual = puntajeActual;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	public int getPuntajeTotal() {
		return puntajeTotal;
	}

	public void setPuntajeTotal(int puntajeTotal) {
		this.puntajeTotal = puntajeTotal;
	}
	
	
	public void colision(Manzana man){
		if(s.getcPosX() == man.x && s.getcPosY() == man.y){
			if(man instanceof Sana){
				int mX = Math.abs((r.nextInt()%(this.getWidth()/BLOQUE))*10);
				int mY = Math.abs((r.nextInt()%(this.getHeight()/BLOQUE))*10);
				m = new Sana(mX,mY,BLOQUE);
				if(r.nextInt()%6 == 0){
					podridas.add(new Podrida(man.x,man.y,BLOQUE));
				}
				contManzanas++;
				puntajeActual += 10;
				puntajeTotal += 10;
				s.crecer();
				subirNivel();
			}else if(man instanceof Podrida){
				podridas.remove(man);
				puntajeActual -= 50;
				puntajeTotal -= 5;
			}
		}
	}
	
	public void subirNivel(){
		if(contManzanas % 10 == 0){
			puntajeTotal += 100;
			puntajeActual += 50;
			fps -= 20;
			t.setDelay(fps);
		}
	}
	
	public void terminarJuego(){
		if(colisionBordes() || s.colisionPropia() || puntajeActual < 0){
			gameOver = true;
			tManzana.stop();
			t.stop();
		}
	}
	
	public boolean colisionBordes(){
		if(s.getcPosX() < 0 || s.getcPosY() < 0 || s.getCuerpo().getFirst().getX() >= this.getWidth() || s.getCuerpo().getFirst().getY() >= this.getHeight()){
			return true;
		}
		return false;
	}
	
	public int getFPS(){
		return fps;
	}
	
	public void agregarPodrida(){
		podridas.add(new Podrida(Math.abs((r.nextInt()%(this.getWidth()/BLOQUE))*10),Math.abs((r.nextInt()%(this.getWidth()/BLOQUE))*10),BLOQUE));
	}
	
	public void iniciar(){
		puntajeActual = 0;
		puntajeTotal = 0;
		s = new Snake(this.getWidth()/2,this.getHeight()/2,BLOQUE);
		m = new Sana(this.getWidth()/2,(this.getHeight()/2)-50,BLOQUE);
		podridas = new ArrayList<Podrida>();
		agregarPodrida();
		contManzanas = 0;
		t.start();
		tManzana.start();
	}
	
}
