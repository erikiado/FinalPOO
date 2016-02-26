
public class Juego {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		VentanaJuego snake = new VentanaJuego();	
		System.out.print(snake);
		while(snake.getMain().isGameOver()){
			snake.actualizarPuntajes();
			snake.getMain().terminarJuego();
			try {
			    Thread.sleep(snake.getMain().fps);
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
		}
		snake.perder();
	}

}
