import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.LineBorder;

/**
 * Classe secret sender
 * @author francy111
 * @version 1.0
 * Conterr√† le funzionalit√† del secret sender
 * 
 * Inserire codice agente
 * Inserire IP-Porta secret inbox
 * Digitare il messaggio da inviare
 * Selezionare la modalit√† di cifratura
 */
public class SecretSender extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
			
	/**
	 * Attributi 
	 */
	private JFrame impostazioniG;
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

	private JButton creaChat;
	private JButton chiudiApplicazione;
	private JButton logout;
	
	/**
	 * Costruttore default
	 * @param code Codice dell'agente che ha eseguito l'accesso
	 */
	public SecretSender(int code) {
		super("Secret Sender - Session ID:" + code);
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
		impostazioni.addActionListener(this);
		
		ricerca = new JTextField();
		ricerca.setBounds(55,0,217,56);
		ricerca.setText("Ricerca");
		
		infoChat = new JTextField();
		infoChat.setBounds(270,0,660,56);
		infoChat.setText("Placeholder - ip - porta");
		//infoChat.setVisible(false);
		
		listaChat = new JPanel();
		listaChat.setBorder(LineBorder.createBlackLineBorder());
		listaChat.setBounds(0,55,271,456);
		listaChat.setLayout(new BoxLayout(listaChat,BoxLayout.Y_AXIS));
		listaChat.setBackground(Color.white);
		JScrollPane scrollPane = new JScrollPane(listaChat,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setPreferredSize(new Dimension(600,600));
		scrollPane.setBounds(0,55,271,456);
		
		cronologia = new JTextArea();
		cronologia.setBorder(LineBorder.createBlackLineBorder());
		cronologia.setBounds(270,55,660,400);
		//cronologia.setVisible(false);
		
		zonaMessaggio = new JTextField();
		zonaMessaggio.setBounds(270,455,601,57);
		zonaMessaggio.setText("Scrivi un messaggio...");
		//zonaMessaggio.setVisible(false);
		
		inviaMessaggio = new JButton("TK");
		inviaMessaggio.setBounds(870,455, 60, 55);
		//inviaMessaggio.setVisible(false);
		
		cesare = new JRadioButton();
		vigenere = new JRadioButton();
		ButtonGroup r = new ButtonGroup();
		r.add(cesare);
		r.add(vigenere);
		
		creaChat = new JButton("Nuova chat");
		creaChat.addActionListener(this);
		
		chiudiApplicazione = new JButton("Exit");
		chiudiApplicazione.addActionListener(this);
		
		logout = new JButton("Esegui logout");
		logout.addActionListener(this);
		
		impostazioniG = new JFrame("Impostazioni");
		impostazioniG.setLayout(new BorderLayout());
		JPanel pp = new JPanel();
		pp.setLayout(new BoxLayout(pp, BoxLayout.Y_AXIS));
		impostazioniG.setBounds(50, 100, 300, 150);
		pp.add(creaChat);
		pp.add(logout);
		pp.add(chiudiApplicazione);
		impostazioniG.add(pp, BorderLayout.CENTER);
		setVisible(false);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		
		/* Aggiunta dei componenti alla
		 * finestra
		 */
		add(impostazioni);
		add(ricerca);
		add(infoChat);
		add(cronologia);
		add(zonaMessaggio);
		add(inviaMessaggio);
		add(scrollPane);
		
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
		public Pair(String ip, int port) {
			this.IP = ip;
			this.port = port;
		}
	}

	private void aggiornaChat() {
		listaChat.removeAll();
		
		for(Pair p : inboxes)
			listaChat.add(new JButton(p.IP + " - " + p.port));
		listaChat.revalidate();
		listaChat.repaint();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(impostazioni)) {
			impostazioniG.setVisible(true);
		}
		else if(e.getSource().equals(chiudiApplicazione)) {
			// si chiude le socket
			dispose();
			impostazioniG.dispose();
		}
		else if(e.getSource().equals(logout)) {
			//chiudere le socket
			dispose();
			impostazioniG.dispose();
			new Login();
		}else if(e.getSource().equals(creaChat)) {
			String risposta = JOptionPane.showInputDialog(null, "IP e porta Secret Inbox", "Nuova chat",3);
			try {
				Pair p;
				String[] info = risposta.split(" ");
				p = new Pair(info[0], Integer.valueOf(info[1]));
				if(!contiene(inboxes, p)) inboxes.add(p);
				else JOptionPane.showMessageDialog(null, "La inbox Ë gi‡ presente", "Errore", 0);
				aggiornaChat();
			}catch(Exception exp) {
				JOptionPane.showMessageDialog(null, "Inserire correttamente IP e porta", "Errore", 0);
			}
		}
	}
	private static boolean contiene(ArrayList<Pair> a, Pair p) {
		boolean risultato = false;
		for(Pair coppia : a) {
			if(coppia.IP.equals(p.IP) && coppia.port == p.port) risultato = true;
		}
		return risultato;
	}
}