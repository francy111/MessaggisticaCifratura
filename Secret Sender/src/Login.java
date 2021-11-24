import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;

/**
 * Classe Login (Finestra grafica)
 * @author francy111
 * @version 1.0
 * conterrà ogni elemento presente
 * nell'interfaccia grafica
 */
public class Login extends JFrame implements ActionListener{
	
	/**
	 * UID di versione seriale
	 */
	private static final long serialVersionUID = 5763669514272369781L;

	/*
	 * Attributi
	 */
	
	/**
	 * Etichetta contenente la scritta "Codice agente"
	 */
	private JLabel codice;
	
	/**
	 * Campo di testo in cui si dovra' inserire il codice dell'agente
	 */
	private JTextPane codiceAgente;
	
	/**
	 * Pulsante tramite il quale si conferma il codice, effettuando l'accesso all'applicazione
	 */
	private JButton confermaCodice;
	
	/**
	 * Costruttore default
	 * crea una finestra che fa identificare l'agente tramite il proprio codice.
	 */
	public Login() {
		super("Secret Sender");
		setBounds(100, 100, 400, 150);
				
		codice = new JLabel("Codice agente: ");
		
		/* La text pane accetta un massimo di 4
		 * caratteri (il codice dell'agente sono solo 4 numeri).
		 * Se si prova a digitare qualcosa dopo 4 caratteri non verra' aggiunto niente
		 * alla stringa gia' esistente.
		 * 
		 * Per realizzare questo si esegue l'override di insertString, per
		 * impedire di far inserire caratteri dopo aver raggiunto lunghezza 4.
		 */
		codiceAgente = new JTextPane(
				new DefaultStyledDocument() {
					private static final long serialVersionUID = 1L;

					@Override
					public void insertString(int offs, String str, AttributeSet a) {
						if((getLength() + str.length()) <= 4)
							try {
								str = str.replaceAll("[^\\d]", "");
								super.insertString(offs, str, a);
							} catch (BadLocationException e) {
								e.printStackTrace();
							}
					}
				}
		);
		codiceAgente.setTransferHandler(null);
		codiceAgente.setPreferredSize(new Dimension(75, 22));
		codiceAgente.setBorder(LineBorder.createBlackLineBorder());
		
		confermaCodice = new JButton("Conferma codice");
		confermaCodice.addActionListener(this);
		
		setLayout(new BorderLayout());
		
		JPanel p = new JPanel(), p0 = new JPanel(), p1 = new JPanel();
		
		p.add(new JLabel("Per accedere a Secret Sender inserire il codice agente"));
		p0.add(codice);
		p0.add(codiceAgente);
		p1.add(confermaCodice);
		
		/* Aggiungiamo i componenti alla finestra
		 */
		add(p, BorderLayout.NORTH);
		add(p0, BorderLayout.CENTER);
		add(p1, BorderLayout.SOUTH);
		
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	/**
	 * Metodo main, crea la finestra grafica
	 * @param args Argomenti passati da riga di comando
	 */
	public static void main(String[] args) {
		new Login();
	}
	
	/**
	 * Metodo utilizzato per gestire gli eventi
	 * @param e Evento
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(confermaCodice)) {
			try {
				if(codiceAgente.getText().length()<4) JOptionPane.showMessageDialog(null, "Inserire un codice di 4 cifre", "Errore", JOptionPane.ERROR_MESSAGE);
				else {
					int code = Integer.parseInt(codiceAgente.getText());

					new SecretSender(codiceAgente.getText());
					dispose();
				}
			}catch(Exception exp) {
				JOptionPane.showMessageDialog(null, "Il codice deve contenere solo cifre da 0 a 9", "Errore", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
