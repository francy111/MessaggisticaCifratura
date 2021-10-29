/**
 * Classe Finestra grafica
 * @author francy111
 * conterrà ogni elemento presente
 * nell'interfaccia grafica
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Finestra extends JFrame implements ActionListener{
	
	/**
	 * UID di versione seriale
	 */
	private static final long serialVersionUID = 5763669514272369781L;

	public Finestra() {
		setBounds(100, 100, 300, 200);
				
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	/**
	 * Metodo main, crea la finestra grafica
	 * @param args Argomenti passati da riga di comando
	 */
	public static void main(String[] args) {
		new Finestra();
	}
	
	/**
	 * Metodo utilizzato per gestire gli eventi
	 * @param e Eventoì
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
