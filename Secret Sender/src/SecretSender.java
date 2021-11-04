import java.awt.*;
import java.awt.event.*;
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
public class SecretSender extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
			
	/**
	 * Attributi 
	 */
	private JFrame impostazioniG;
	private int code;
	private ArrayList<Inbox> inboxes;
	private Inbox inboxAttuale = null;
	private JButton impostazioni;
	private JTextField ricerca;
	private JTextField infoChat;
	private JPanel listaChat;
	private JTextArea cronologia;
	private JTextField zonaMessaggio;
	private JPanel tipoCrittografia;
	private JButton inviaMessaggio;
	
	private JRadioButton cesare, vigenere;
	private JTextField chiave;
	
	private JButton rimuoviChat;
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
		
		impostazioni = new JButton("Settings");
		impostazioni.setBounds(0,0,75,55);
		impostazioni.addActionListener(this);
		
		ricerca = new JTextField();
		ricerca.setBounds(75,0,198,56);
		ricerca.setText("Ricerca");
		
		infoChat = new JTextField();
		infoChat.setBounds(270,0,660,56);
		infoChat.setText("");
		infoChat.setVisible(false);
		
		listaChat = new JPanel();
		listaChat.setBorder(LineBorder.createBlackLineBorder());
		listaChat.setBounds(0,55,271,456);
		listaChat.setLayout(null);
		listaChat.setBackground(Color.white);
		JScrollPane scrollPane = new JScrollPane(listaChat,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setPreferredSize(new Dimension(600,600));
		scrollPane.setBounds(0,55,271,456);
		
		cronologia = new JTextArea();
		cronologia.setBorder(LineBorder.createBlackLineBorder());
		cronologia.setBounds(270,55,660,350);
		cronologia.setVisible(false);
		cronologia.setEnabled(false);
		
		zonaMessaggio = new JTextField();
		zonaMessaggio.setBounds(270,455,601,57);
		zonaMessaggio.setText("Scrivi un messaggio...");
		zonaMessaggio.setVisible(false);
		
		inviaMessaggio = new JButton("Send");
		inviaMessaggio.setBounds(870,455, 60, 55);
		inviaMessaggio.setVisible(false);
		
		chiave = new JTextField();
		chiave.setPreferredSize(new Dimension(75, 25));
		chiave.setEnabled(false);
		
		cesare = new JRadioButton("Cesare");
		vigenere = new JRadioButton("Vigenerè");
		cesare.addActionListener(this);
		vigenere.addActionListener(this);
		ButtonGroup r = new ButtonGroup();
		r.add(cesare);
		r.add(vigenere);
		
		tipoCrittografia = new JPanel();
		tipoCrittografia.add(cesare);
		tipoCrittografia.add(vigenere);
		tipoCrittografia.add(chiave);
		tipoCrittografia.setBounds(270, 407, 660, 50);
		tipoCrittografia.setBorder(LineBorder.createBlackLineBorder());
		tipoCrittografia.setBackground(Color.white);
		tipoCrittografia.setVisible(false);
		
		creaChat = new JButton("Nuova chat");
		creaChat.addActionListener(this);
		rimuoviChat = new JButton("Rimuovi chat");
		rimuoviChat.addActionListener(this);
		rimuoviChat.setVisible(false);
		
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
		pp.add(rimuoviChat);
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
		add(tipoCrittografia);
		add(zonaMessaggio);
		add(inviaMessaggio);
		add(scrollPane);
		
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private void aggiornaChat() {
		listaChat.removeAll();
		JButton tmp;
		for(Inbox p : inboxes) {
			tmp = new JButton(p.getIP() + " - " + p.getPorta());
			tmp.addActionListener(this);
			tmp.setBounds(1, 1+(inboxes.indexOf(p))*50, 266, 50);
			listaChat.add(tmp);
		}
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
				Inbox p;
				String[] info = risposta.split(" ");
				p = new Inbox(info[0], Integer.valueOf(info[1]));
				if(!contiene(inboxes, p)) inboxes.add(p);
				else JOptionPane.showMessageDialog(null, "La inbox � gi� presente", "Errore", 0);
				aggiornaChat();
			}catch(Exception exp) {
				JOptionPane.showMessageDialog(null, "Inserire correttamente IP e porta", "Errore", 0);
			}
		}else if(e.getSource().equals(rimuoviChat)) {
			System.out.println(inboxes.toString());
			if(contiene(inboxes,inboxAttuale)) {
				inboxes.remove(inboxAttuale);
				System.out.println(inboxes.toString());
				inboxAttuale= null;
				
				infoChat.setText("");
				infoChat.setVisible(false);
				cronologia.setVisible(false);
				zonaMessaggio.setVisible(false);
				inviaMessaggio.setVisible(false);
				tipoCrittografia.setVisible(false);
				if(inboxes.isEmpty()) rimuoviChat.setVisible(false);
				
				aggiornaChat();
			}
		}
		else if(e.getSource().equals(cesare)) {
			chiave.setEnabled(true);
		}
		else if(e.getSource().equals(vigenere)) {
			chiave.setEnabled(true);
		}
		else {
			for(int i = 0; i < listaChat.getComponentCount();i ++) {
				if(listaChat.getComponent(i).equals(e.getSource())) {
					rimuoviChat.setVisible(true);
					inboxAttuale = inboxes.get(i);
					
					infoChat.setVisible(true);
					cronologia.setVisible(true);
					inviaMessaggio.setVisible(true);
					zonaMessaggio.setVisible(true);
					tipoCrittografia.setVisible(true);
					infoChat.setText(inboxAttuale.getIP() + " - " + inboxAttuale.getPorta());
				}
			}
		}
	}
	private static boolean contiene(ArrayList<Inbox> a, Inbox p) {
		boolean risultato = false;
		for(Inbox coppia : a) {
			if(coppia.getIP().equals(p.getIP()) && coppia.getPorta() == p.getPorta()) risultato = true;
		}
		return risultato;
	}
}