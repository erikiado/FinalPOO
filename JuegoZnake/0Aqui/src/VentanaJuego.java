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
	private String nScore[],pScore[];
	private JButton empezar;
	
	
	public VentanaJuego(){
		salir = false;
		this.setVisible(true);
		this.setLayout(new BorderLayout());
		main = new Tablero(vAncho,vAlto);		
		
		add(main,BorderLayout.CENTER);
		lado = new JPanel();
		lado.setLayout(new GridLayout(2,1,10,10));
			lSuperior = new JPanel();
			lSuperior.setLayout(new GridLayout(7,1,10,10));
				labNombre = new JLabel("Nombre:",JLabel.LEFT);
				txtNombre = new JTextField();
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
				lSuperior.setBorder(new EmptyBorder(10,10,10,10));
			lado.add(lSuperior);
			lInferior = new JPanel();
			lInferior.setLayout(new BorderLayout());
				labRecords = new JLabel("Records",JLabel.CENTER);
				lInferior.add(labRecords,BorderLayout.NORTH);
				lNumRecords = new JPanel();
				lNumRecords.setLayout(new GridLayout(5,1));
					for(int i = 1; i < 6; i++)
						lNumRecords.add(new JLabel(String.valueOf(i)));
				lInferior.add(lNumRecords);	
				lInferior.setBorder(new EmptyBorder(10,10,10,10));
			lado.add(lInferior);
		lado.setPreferredSize(new Dimension(vAnchoLado,vAlto));
		add(lado, BorderLayout.EAST);
		this.setResizable(false);
		this.setSize(vAnchoTotal+6, vAlto+28);
		this.setLocationRelativeTo(null);
		
	}
	
	public Tablero getMain(){
		return main;
	}
	
	public void actualizarPuntajes(){
		txtPuntajeActual.setText(String.valueOf(main.getPuntajeActual()));
		txtPuntajeTotal.setText(String.valueOf(main.getPuntajeTotal()));
	}
	
	public void perder(){
		txtNombre.setBackground(Color.RED);
		//txtNombre.setForeground(Color.RED);
	}
	
	public void recuperarPuntajes(){
		BufferedReader lector;
		File f = new File("score.txt");
		String s = "";
		try{
			lector = new BufferedReader(new FileReader(f));
			s = lector.readLine();
		}catch(Exception e){
			System.out.print("No se han podido recuperar los puntajes...\n");
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
			System.out.print("No se pudieron guardar los puntajes!\n");
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		iniciarJuego();
	}
	
	public void iniciarJuego(){
		main.iniciar();
		txtNombre.setBackground(Color.WHITE);
		main.requestFocus();
	}
	
	public void empezar(){
		
	}
}
