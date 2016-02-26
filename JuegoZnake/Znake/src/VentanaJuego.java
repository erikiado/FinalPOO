import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import javax.swing.*;
import javax.swing.border.*;

public class VentanaJuego extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	private final int vAncho = 500, vAlto = 500, vAnchoLado = 200;
	private final int vAnchoTotal = vAncho + vAnchoLado;
	private Tablero main;
	private JPanel lado, lSuperior, lInferior, lRecords, lNumRecords;
	private JLabel labNombre, labPuntajeActual, labPuntajeTotal, labRecords;
	private JTextField txtNombre, txtPuntajeActual, txtPuntajeTotal;
	private boolean salir;
	private String nScore[],pScore[], nomScore;
	private JButton empezar;


	//Constructor: crea la ventana acomodada
	public VentanaJuego(){
		salir = false;
		this.setVisible(true);
		this.setLayout(new BorderLayout());
		main = new Tablero(vAncho,vAlto);
		nScore = new String[5];
		pScore = new String[5];
		recuperarPuntajes();
		add(main,BorderLayout.CENTER);
		lado = new JPanel();
		lado.setLayout(new GridLayout(2,1,10,10));
			lSuperior = new JPanel();
			lSuperior.setLayout(new GridLayout(7,1,10,10));
				labNombre = new JLabel("Nombre:",JLabel.LEFT);
				txtNombre = new JTextField("erikiado");
				labPuntajeActual = new JLabel("Puntaje Actual:");
				txtPuntajeActual = new JTextField("0");
					txtPuntajeActual.setEditable(false);
					txtPuntajeActual.setHorizontalAlignment(JLabel.RIGHT);
				labPuntajeTotal = new JLabel("Puntaje Total:");
				txtPuntajeTotal = new JTextField("0");
					txtPuntajeTotal.setEditable(false);
					txtPuntajeTotal.setHorizontalAlignment(JLabel.RIGHT);
				empezar = new JButton("Start!");
					empezar.addActionListener(this);
				lSuperior.add(labNombre);
				lSuperior.add(txtNombre);
				lSuperior.add(labPuntajeActual);
				lSuperior.add(txtPuntajeActual);
				lSuperior.add(labPuntajeTotal);
				lSuperior.add(txtPuntajeTotal);
				lSuperior.add(empezar);
				lSuperior.setBorder(new EmptyBorder(10, 10, 10, 10));
			lado.add(lSuperior);
			lInferior = new JPanel();
			lInferior.setLayout(new BorderLayout());
				labRecords = new JLabel("Records",JLabel.CENTER);
				lInferior.add(labRecords,BorderLayout.NORTH);
				lNumRecords = new JPanel();
				lNumRecords.setLayout(new GridLayout(5, 1));
					for(int i = 1; i < 6; i++)
						lNumRecords.add(new JLabel(String.valueOf(i)));
				lInferior.add(lNumRecords,BorderLayout.WEST);
				lRecords = new JPanel();
				lRecords.setLayout(new GridLayout(5, 2, 10, 10));
					for(int i = 0; i < 5; i++){
						lRecords.add(new JLabel(nScore[i]));
						lRecords.add(new JLabel(pScore[i]));
					}
					lRecords.setBorder(new EmptyBorder(0,10,0,10));
				lInferior.add(lRecords,BorderLayout.CENTER);
				lInferior.setBorder(new EmptyBorder(10,10,10,10));
			lado.add(lInferior);
		lado.setPreferredSize(new Dimension(vAnchoLado,vAlto));
		add(lado, BorderLayout.EAST);
		this.setResizable(false);
		this.setSize(vAnchoTotal+6, vAlto+29);
		this.setLocationRelativeTo(null);
		JOptionPane.showMessageDialog(null, "Flechas: Direccion\nEspacio: Pausa\n\n6 Rojas - Nivel++\n\nRojas:\n        Actual +10\n        Total +10\nPodrida:\n        Actual -50\n        Total -5","Instrucciones",2);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	//Colocar el texto de los puntajes segun los puntajes del main
	public void actualizarPuntajes(){
		txtPuntajeActual.setText(String.valueOf(main.getPuntajeActual()));
		txtPuntajeTotal.setText(String.valueOf(main.getPuntajeTotal()));
	}

	//Actualizar el tablero de records
	public void actualizarRecords(){
		((JLabel)(lRecords.getComponent(0))).setText(nScore[0]);
		((JLabel)(lRecords.getComponent(1))).setText(pScore[0]);
		((JLabel)(lRecords.getComponent(2))).setText(nScore[1]);
		((JLabel)(lRecords.getComponent(3))).setText(pScore[1]);
		((JLabel)(lRecords.getComponent(4))).setText(nScore[2]);
		((JLabel)(lRecords.getComponent(5))).setText(pScore[2]);
		((JLabel)(lRecords.getComponent(6))).setText(nScore[3]);
		((JLabel)(lRecords.getComponent(7))).setText(pScore[3]);
		((JLabel)(lRecords.getComponent(8))).setText(nScore[4]);
		((JLabel)(lRecords.getComponent(9))).setText(pScore[4]);
	}

	//Actualizar graficamente los puntajes y si se pierde en el main guardar los nuevos records
	//Excepcion al intentar dormir el ciclo infinito hasta que la animacion del main se actualice
	public void vivir(){
		while(true){
			actualizarPuntajes();

			try {
				Thread.sleep(main.getFPS());
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}

			if(main.boolTerminar() && !salir){
				acomodarPuntajes();
				guardarPuntajes();
				actualizarRecords();
				salir = true;
				txtNombre.setBackground(Color.red);
			}

		}
	}

	//Si al perder se obtuvo un record, acomoda el record en su posicion y recorre los mas bajos
	public void acomodarPuntajes(){
		if(main.getPuntajeTotal() > Integer.parseInt(pScore[4])){
			int x = 4;
			for(int i = 4; i >= 0; i--){
				if(main.getPuntajeTotal() >= Integer.parseInt(pScore[i]) ){
					x = i;
				}
			}
			String nom = nScore[x];
			String pun = pScore[x];
			nScore[x] = txtNombre.getText();
			pScore[x] = txtPuntajeTotal.getText();
			for(int i = x+1; i < 5; i++){
				String a = nScore[i];
				String b = pScore[i];
				nScore[i] = nom;
				pScore[i] = pun;
				nom = a;
				pun = b;
			}
		}
	}

	//Excepcion al intentar leer el archivo 'score.txt', si no se encuentra asignar a la linea leida una linea por defecto
	//Cortar la linea leida guardandola en los nombres y puntajes del programa
	public void recuperarPuntajes(){
		BufferedReader lector;
		File f = new File("score.txt");
		String s = "";
		try{
			lector = new BufferedReader(new FileReader(f));
			s = lector.readLine();
		}catch(Exception e){
			s = "Nadie,0,Nadie,0,Nadie,0,Nadie,0,Nadie,0,";
		}

		for(int i = 0; i < 5; i++){
			int cont = s.indexOf(",");
			nScore[i] = s.substring(0, cont);
			s = s.substring(cont+1);
			cont = s.indexOf(",");
			pScore[i] = s.substring(0, cont);
			s = s.substring(cont+1);
		}
	}

	//Crear una linea (nombre,puntaje,...) de los records actualizados y guardarla en el archivo 'score.txt'
	//Excepcion al intentar guardar el archivo, lanzar mensaje que no se pudo
	public void guardarPuntajes(){
		String s = "";
		FileOutputStream writer;
		File f = new File("score.txt");
		for(int i = 0; i < 5; i ++){
			s += nScore[i] + ","+pScore[i]+",";
		}
		try{
			writer = new FileOutputStream(f);
			writer.write(s.getBytes());
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "Error: No se pudieron guardar los puntajes");
		}
	}

	@Override
	//Al darle click al boton Start! inicia el juego
	public void actionPerformed(ActionEvent arg0) {
		iniciarJuego();
	}

	//iniciar el (re)iniciar el juego principal, cambiar el color rojo de cuando se pierde, y guardar el nombre
	//solicitar que el juego pueda ser controlado
	public void iniciarJuego(){
		main.iniciar();
		txtNombre.setBackground(Color.GREEN);
		nomScore = txtNombre.getText();
		main.requestFocus();
		salir = false;
	}

}
