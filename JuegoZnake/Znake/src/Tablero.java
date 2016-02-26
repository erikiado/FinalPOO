import java.awt.*;
import java.awt.event.*;
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
	private Timer t, tManzana, tPodrida;
	private Random r;
	private int puntajeActual, puntajeTotal;
	private boolean gameOver;
	private int contManzanas;
	private ImageIcon fondo, start, over,pausa;
	private boolean pause;
	private int nivel;

	//Constructor
	public Tablero(int ancho, int alto){
		nivel = 1;
		fps = 100;
		this.setBackground(Color.BLACK);
		this.setSize(new Dimension(ancho, alto));
		this.setFocusable(true);
		this.addKeyListener(this);
		fondo = new ImageIcon(getClass().getResource("fondo.png"));
		start = new ImageIcon(getClass().getResource("start.png"));
		pausa = new ImageIcon(getClass().getResource("pausa.png"));
		over = new ImageIcon(getClass().getResource("over.png"));
		r = new Random();
		m = new Sana(this.getWidth()/2,(this.getHeight()/2)-50,BLOQUE);
		t = new Timer(fps, this);
		tManzana = new Timer(5000,this);
		tPodrida =  new Timer(10000,this);
		s = new Snake(this.getWidth()/2,this.getHeight()/2);
		puntajeActual = 0;
		puntajeTotal = 0;
		gameOver = true;
		pause = true;
		podridas = new ArrayList<Podrida>();
		agregarPodrida();
		contManzanas = 0;
	}

	 //Depende del estado actual del juego para dibujar la pantalla adecuada
	public void paint(Graphics g){
		if(gameOver){
			if(pause){
				g.drawImage(start.getImage(),0,0,null);
			}else{
				g.drawImage(over.getImage(),0,0,null);
			}	
		}else{
			if(pause){
				g.drawImage(pausa.getImage(),0,0,null);
			}else{
				//JUEGO EN CURSO
				g.drawImage(fondo.getImage(),0, 0, null);
				for(Podrida p : podridas){
					p.dibujar(g);
				}
				m.dibujar(g);
				s.dibujarSerpiente(g);
				g.setColor(Color.black);
				g.drawString("Nivel: " + nivel, this.getWidth() - 65, 15);
			}
		}
		
			
	}

	@Override
	//t acciona la vida del juego y verifica colisiones
	//tManzana agrega n podridas dependiendo del nivel, cada 5 segundos de juego
	//tPodrida lanza el dado de cada podrida en la lista cada 10 seg, si le toca se elimina sola sin perder puntaje
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource().equals(t)){
			s.moverSerpiente();
			for(int i = 0; i < podridas.size(); i++){
				colision(podridas.get(i));
			}
			//System.out.print("S - M: " + "(" + s.getcPosX() + "," + s.getcPosY() + ") - (" + m.getX() + "," + m.getY() + ")\n");
			colision(m);
			terminarJuego();
		}
		
		if(e.getSource().equals(tManzana)){
			for(int i = 0; i < nivel; i++){
				agregarPodrida();
			}
		}

		if(e.getSource().equals(tPodrida)){
			for(int i = 0; i < podridas.size(); i++){
				if(podridas.get(i).lanzarDado()){
					podridas.remove(i);
				}
			}
		}
		
		repaint();
	}

	@Override
	//logica del teclado
	//flechas: cambian la direccion de la serpiente
	//espacio: entrar y salir a pausa
	//Enter: al perder para volver a la pantalla principal
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
		
		if(key == KeyEvent.VK_SPACE){
			if(pause && !gameOver){
				pause = false;
				t.start();
				tManzana.start();
				tPodrida.start();
				repaint();
			}else if(!pause && !gameOver) {
				pause = true;
				t.stop();
				tManzana.stop();
				tPodrida.stop();
				repaint();
			}
		}

		if(key == KeyEvent.VK_ENTER){
			if(!pause && gameOver){
				pause = true;
				repaint();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent arg0) {}

	//revisa colisiones entre serpiente y manzanas
	//Roja: borra y crea una nueva aleatoriamente, aumenta puntajes, hace crecer a la serpiente y revisa si se sube de nivel
	//Podrida: reduce puntajes y destruye la podrida comida
	public void colision(Manzana man){
		if(s.getcPosX()+BLOQUE > man.x && s.getcPosX() < man.x+BLOQUE ){
			if(s.getcPosY()+BLOQUE > man.y && s.getcPosY() < man.y+BLOQUE){
				if(man instanceof Sana){
					int mX = Math.abs((r.nextInt()%(this.getWidth()/BLOQUE))*10);
					int mY = Math.abs((r.nextInt()%(this.getHeight()/BLOQUE))*10);
					if(r.nextInt()%6 == 0) {
						if(s.getDireccion()[0]){
							podridas.add(new Podrida(man.x,man.y+BLOQUE,BLOQUE));
						}else if(s.getDireccion()[1]){
							podridas.add(new Podrida(man.x-BLOQUE, man.y, BLOQUE));
						}else if(s.getDireccion()[2]){
							podridas.add(new Podrida(man.x, man.y-BLOQUE, BLOQUE));
						}else if(s.getDireccion()[3]) {
							podridas.add(new Podrida(man.x+BLOQUE, man.y, BLOQUE));
						}
					}
					contManzanas++;
					puntajeActual += 10;
					puntajeTotal += 10;
					s.crecer();
					m = new Sana(mX,mY,BLOQUE);
					subirNivel();
				}else if(man instanceof Podrida) {
					podridas.remove(man);
					puntajeActual -= 50;
					puntajeTotal -= 5;
				}
			}
		}
	}

	//Se revisa si el contador de manzana es un multiplo de 6
	//aumenta puntajes, actualiza la velocidad
	public void subirNivel(){
		if(contManzanas % 6 == 0){
			puntajeTotal += 100;
			puntajeActual += 50;
			fps = 100 - (nivel-1)*15;
			t.setDelay(fps);
			nivel++;
		}
	}

	//Revida si existe colision propia o con bordes o si se comio demasiadas podridas, si es asi para el juego
	public void terminarJuego(){
		if(colisionBordes() || s.colisionPropia() || puntajeActual < 0){
			gameOver = true;
			tManzana.stop();
			tPodrida.stop();
			t.stop();
			nivel = 1;
			fps = 100;
		}
	}

	//regresa verdadero al perder
	public boolean boolTerminar(){
		return colisionBordes() || s.colisionPropia() || puntajeActual < 0;
	}

	//Verdadero si la serpiente sale de los margenes del panel
	public boolean colisionBordes(){
		return s.getcPosX() < 0 || s.getcPosY() < 0 || s.getCuerpo().getFirst().getX() >= this.getWidth() || s.getCuerpo().getFirst().getY() >= this.getHeight();
	}

	//Agregar una podrida a la lista aletoriamente
	public void agregarPodrida(){
		podridas.add(new Podrida(Math.abs((r.nextInt()%(this.getWidth()/BLOQUE))*10),Math.abs((r.nextInt()%(this.getWidth()/BLOQUE))*10),BLOQUE));
	}

	//resetea los puntajes, recrea instancias necesarias, inicia timers
	public void iniciar(){
		puntajeActual = 0;
		puntajeTotal = 0;
		s = new Snake(this.getWidth()/2,this.getHeight()/2);
		m = new Sana(this.getWidth()/2,(this.getHeight()/2)-50,BLOQUE);
		podridas = new ArrayList<Podrida>();
		agregarPodrida();
		contManzanas = 0;
		t.start();
		tPodrida.start();
		tManzana.start();
		gameOver = false;
		pause = false;
	}



	//Getter - Setters

	public int getPuntajeActual() {
		return puntajeActual;
	}

	public int getPuntajeTotal() {
		return puntajeTotal;
	}

	public int getFPS(){
		return fps;
	}


}
