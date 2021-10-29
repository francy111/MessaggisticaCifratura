import java.awt.Color;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.LineBorder;

/**
 * Classe secret sender
 * @author francy111
 * @version 1.0
 * Conterrà le funzionalità del secret sender
 * 
 * Inserire codice agente
 * Inserire IP-Porta secret inbox
 * Digitare il messaggio da inviare
 * Selezionare la modalità di cifratura
 */
public class SecretSender extends JFrame{
	private static final long serialVersionUID = 1L;
			
	/**
	 * Attributi 
	 */
	private int code;
	private ArrayList<Pair> inboxes;
	private JButton impostazioni;
	private JTextField ricerca;
	private JTextField infoChat;
	private JPanel listaChat;
	private JTextArea cronologia;
	private JTextField zonaMessaggio;
	private JButton inviaMessaggio;
	
	private JRadioButton cesare, vigenere;
	
	private JButton chiudiApplicazione;
	private JButton logout;
	
	/**
	 * Costruttore default
	 * @param code Codice dell'agente che ha eseguito l'accesso
	 */
	public SecretSender(int code) {
		super("Secret Sender");
		setBounds(100, 100, 945, 550);
		setLayout(null);
		setResizable(false);
		setBackground(Color.white);
		
		/* Inizializzazione attributi
		 */
		this.code = code;
		inboxes = new ArrayList<>();
		
		impostazioni = new JButton("TK");
		impostazioni.setBounds(0,0,55,55);
		
		ricerca = new JTextField();
		ricerca.setBounds(55,0,217,56);
		ricerca.setText("Ricerca");
		
		infoChat = new JTextField();
		infoChat.setBounds(270,0,660,56);
		infoChat.setText("Placeholder - ip - porta");
		infoChat.setVisible(false);
		
		listaChat = new JPanel();
		listaChat.setBorder(LineBorder.createBlackLineBorder());
		listaChat.setBounds(0,55,271,456);
		listaChat.setLayout(new BoxLayout(listaChat,BoxLayout.Y_AXIS));
		
		cronologia = new JTextArea();
		cronologia.setBorder(LineBorder.createBlackLineBorder());
		cronologia.setBounds(270,55,660,400);
		cronologia.setVisible(false);
		
		zonaMessaggio = new JTextField();
		zonaMessaggio.setBounds(270,455,601,57);
		zonaMessaggio.setText("Scrivi un messaggio...");
		zonaMessaggio.setVisible(false);
		
		inviaMessaggio = new JButton("TK");
		inviaMessaggio.setBounds(870,455, 60, 55);
		inviaMessaggio.setVisible(false);
		
		cesare = new JRadioButton();
		vigenere = new JRadioButton();
		ButtonGroup r = new ButtonGroup();
		r.add(cesare);
		r.add(vigenere);
		
		chiudiApplicazione = new JButton("Exit");
		chiudiApplicazione.setVisible(false);
		
		logout = new JButton("Esegui logout");
		logout.setVisible(false);
		
		
		/* Aggiunta dei componenti alla
		 * finestra
		 */
		add(impostazioni);
		add(ricerca);
		add(infoChat);
		add(listaChat);
		add(cronologia);
		add(zonaMessaggio);
		add(inviaMessaggio);
		add(chiudiApplicazione);
		add(logout);
		
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	/**
	 * Classe coppia
	 * @author francy111
	 * Usata per accoppiare IP e Porta di un Secret Inbox
	 */
	private class Pair{
		public int port;
		public String IP;
	}
}
