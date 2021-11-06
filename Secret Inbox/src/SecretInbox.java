import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.LineBorder;
/**
 * Classe secret inbox
 * @author francy111
 * @version 1.0
 * Finestra grafica del secret inbox
 * 
 * Inserire porta di ascolto
 * Selezionare il messaggio da decifrare
 * Scegliere l'algoritmo e chiave di decifratura
 * Provare brute force
 */
public class SecretInbox extends JFrame{

	/**
	 * ID di versione seriale
	 */
	private static final long serialVersionUID = -2861248906830752849L;
	
	/**
	 * Contiene i componenti per impostare la porta di ascolto
	 */
	private JPanel panel_Porta;
	/**
	 *  Etichetta "Porta"
	 */
	private JLabel label_portaAscolto;
	/**
	 * Campo di testo per inserire la porta di ascolto
	 */
	private JTextField textField_portaAscolto;
	/**
	 * Pulsante con il quale si imposta la porta di ascolto
	 */
	private JButton button_impostaPorta;
	
	
	/**
	 * Contiene i componenti per ricevere, mostrare e selezionare i messaggi
	 */
	private JPanel panel_Msg;
	/**
	 * Pannello con barra di scorrimento
	 */
	private JScrollPane scrollPane_Msg;
	/**
	 * Pannello per contenere i pulsanti
	 */
	private JPanel panel_Interno;
	/**
	 * Lista contenente ogni messaggio ricevuto
	 */
	private ArrayList<JButton> messaggi;
	/**
	 * Messaggio selezionato per la decifratura
	 */
	private byte[] msg;
	
	
	/**
	 * Contiene i componenti per scegliere l'algoritmo di decifratura, impostare la chiave o provare la brute force
	 */
	private JPanel panel_Decifra;
	/**
	 * Etichetta "Decifratura messaggi"
	 */
	private JLabel label_decifratura;
	/**
	 * Pulsante radio per selezionare l'algoritmo di cesare
	 */
	private JRadioButton rbutton_cesare;
	/**
	 * Pulsante radio per selezionare l'algoritmo di vigenère
	 */
	private JRadioButton rbutton_vigenere;
	/**
	 * Gruppo di pulsanti per raggruppare i pulsanti radio precedenti
	 */
	private ButtonGroup rgroup_algoritmo;
	/**
	 * Etichetta "Chiave"
	 */
	private JLabel label_chiave;
	/**
	 * Campo di testo per inserire la chiave di decifratura
	 */
	private JTextField textField_chiave;
	/**
	 * Pulsante per eseguire la Brute Force
	 */
	private JButton button_BruteForce;
	
	/**
	 * Costruttore default
	 */
	public SecretInbox() {
		super("Secret Inbox");
		setBounds(100, 100, 755, 500);
		setResizable(false);
		setLayout(null);
		
		panel_Porta = new JPanel();
		panel_Porta.setBorder(LineBorder.createBlackLineBorder());
		panel_Porta.setBounds(10, 10, 175, 100);
		label_portaAscolto = new JLabel("Porta: ");
		textField_portaAscolto = new JTextField();
		textField_portaAscolto.setPreferredSize(new Dimension(75, 25));
		button_impostaPorta = new JButton("Utilizza porta");
		
		panel_Porta.add(label_portaAscolto);
		panel_Porta.add(textField_portaAscolto);
		panel_Porta.add(button_impostaPorta);
		
		
		panel_Decifra = new JPanel();
		panel_Decifra.setBorder(LineBorder.createBlackLineBorder());
		panel_Decifra.setBounds(10, 120, 175, 350);
		
		
		panel_Msg = new JPanel();
		panel_Msg.setBorder(LineBorder.createBlackLineBorder());
		panel_Msg.setBounds(195, 10, 550, 460);
		
		
		
		add(panel_Porta);
		add(panel_Decifra);
		add(panel_Msg);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) { new SecretInbox(); }
}
