import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class VentanaJuego extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private final int vAncho = 500, vAlto = 500, vAnchoLado = 200;
	private final int vAnchoTotal = vAncho + vAnchoLado;
	
	private Tablero main;
	private JPanel lado, lSuperior, lInferior, lRecords, lNumRecords;
	private JLabel labNombre, labPuntajeActual, labPuntajeTotal, labRecords;
	private JTextField txtNombre, txtPuntajeActual, txtPuntajeTotal;
	private boolean salir;
	
	public VentanaJuego(){
		salir = false;
		this.setVisible(true);
		this.setLayout(new BorderLayout());
		main = new Tablero(vAncho,vAlto);
		
		
		
		add(main,BorderLayout.CENTER);
		lado = new JPanel();
		lado.setLayout(new GridLayout(2,1,10,10));
			lSuperior = new JPanel();
			lSuperior.setLayout(new GridLayout(6,1,10,10));
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
				lSuperior.add(labNombre);
				lSuperior.add(txtNombre);
				lSuperior.add(labPuntajeActual);
				lSuperior.add(txtPuntajeActual);
				lSuperior.add(labPuntajeTotal);
				lSuperior.add(txtPuntajeTotal);
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
		
		main.requestFocus();
		
		
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
		txtNombre.setForeground(Color.RED);
	}

}
