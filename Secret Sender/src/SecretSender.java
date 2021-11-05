import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
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
	private JTextField infoChat;
	private JPanel listaChat;
	private JTextArea cronologia;
	private JTextField zonaMessaggio;
	private JPanel tipoCrittografia;
	private JButton inviaMessaggio;
	
	private JRadioButton cesare, vigenere;
	private JTextField chiave;
	
	/**
	 * Pulsante utilizzato per rimuovere una chat
	 */
	private JButton rimuoviChat;
	
	/**
	 * Pulsante utilizzato per creare una nuova chat
	 */
	private JButton creaChat;
	
	/**
	 * Pulsante utilizzato per chiudere l'applicazione
	 */
	private JButton chiudiApplicazione;
	
	/**
	 * Pulsante utilizzato per eseguire il logout e inserire un nuovo codice agente
	 */
	private JButton logout;
	
	/**
	 * Tema dell'applicazione
	 * True - Tema scuro
	 * Falso - Tema chiaro
	 */
	private boolean darkTheme;
	
	/**
	 * Costruttore default
	 * @param code Codice dell'agente che ha eseguito l'accesso
	 */
	public SecretSender(int code) {
		
		// Si imposta la dimensioni della finestra
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
		impostazioni.setBounds(0,0,273,55);
		impostazioni.addActionListener(this);
		
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
		inviaMessaggio.addActionListener(this);
		
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
		add(infoChat);
		add(cronologia);
		add(tipoCrittografia);
		add(zonaMessaggio);
		add(inviaMessaggio);
		add(scrollPane);
		
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	/**
	 * Metodo utilizzato per aggiornare graficamente la
	 * lista di chiat
	 */
	private void aggiornaChat() {
		listaChat.removeAll();
		JButton tmp;
		
		// Aggiungiamo un pulsante per ogni inbox aggiunto
		for(Inbox p : inboxes) {
			tmp = new JButton(p.getIP() + " - " + p.getPorta());
			tmp.addActionListener(this);
			tmp.setBounds(1, 1+(inboxes.indexOf(p))*50, 266, 50);
			listaChat.add(tmp);
		}
		
		// Si ridisegnano i pulsanti
		listaChat.revalidate();
		listaChat.repaint();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		// Se viene premuto il pulsante delle impostazioni si apre il pannello impostazioni
		if(e.getSource().equals(impostazioni)) {
			impostazioniG.setVisible(true);
		}
		// Si chiude l'applicazione chiudendo eventuali socket rimaste aperte
		else if(e.getSource().equals(chiudiApplicazione)) {
			// si chiude le socket
			dispose();
			impostazioniG.dispose();
		}
		
		// Si chiude la finestra attuale (chiudendo le socket) e si ritorna all'applicazione di login
		else if(e.getSource().equals(logout)) {
			//chiudere le socket
			dispose();
			impostazioniG.dispose();
			new Login();
		}
		
		// Si chiedono le informazioni della inbox da aggiungere alla lista
		else if(e.getSource().equals(creaChat)) {
			String risposta = JOptionPane.showInputDialog(null, "IP Secret Inbox", "Nuova chat",3);
			try {
				Inbox p;
				p = new Inbox();
				p.setIP(risposta);
				int porta = Integer.parseInt((JOptionPane.showInputDialog(null, "Porta Secret Inbox", "Nuova chat",3)));
				if(porta<50000 || porta>65535) throw new Exception();
				p.setPorta(porta);
				if(!contiene(inboxes, p)) { 
					inboxes.add(p);
				}
				else JOptionPane.showMessageDialog(null, "La inbox � gi� presente", "Errore", 0);
				aggiornaChat();
				impostazioniG.dispose();
			}catch(Exception exp) {
				JOptionPane.showMessageDialog(null, "Inserire correttamente IP e porta", "Errore", 0);
			}
		}
		
		// Rimuove la chat attuale
		else if(e.getSource().equals(rimuoviChat)) {
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
		
		// Si imposta come tipo di cifratura la cifratura di Cesare, si attiva il campo per inserire la chiave
		else if(e.getSource().equals(cesare)) {
			chiave.setEnabled(true);
			chiave.setText("");
		}
		
		// Si imposta come tipo di cifratura la cifratura di vigenerè, si attiva il campo per inserire la chiave
		else if(e.getSource().equals(vigenere)) {
			chiave.setEnabled(true);
			chiave.setText("");
		}
		
		// Si crea un worker che cifra il messaggio e lo invia alla inbox indicata
		else if(e.getSource().equals(inviaMessaggio)) {
			if(cesare.isSelected()) {
				try {
					if(chiave.getText().isEmpty()) throw new Exception();
					int key = Integer.valueOf(chiave.getText());
					
					BackgroundWorker worker = new BackgroundWorker(inboxAttuale);
					worker.setUp(code, zonaMessaggio.getText(), ""+key, 0);
					worker.exec();

					cronologia.setText(cronologia.getText()+zonaMessaggio.getText()+"\n");
					zonaMessaggio.setText("");
				}catch(Exception exp) {
					JOptionPane.showMessageDialog(null, "Inserire la chiave di cifratura (deve essere un numero)", "Errore", 0);
				}
			}else if(vigenere.isSelected()) {
				try {
					if(chiave.getText().isEmpty()) throw new Exception();
					if(chiave.getText().length()!=5)
						JOptionPane.showMessageDialog(null, "La chiave deve essere una parola di 5 caratteri", "Errore", 0);
					else {
						String key = chiave.getText();
						BackgroundWorker worker = new BackgroundWorker(inboxAttuale);
						worker.setUp(code, zonaMessaggio.getText(), key, 1);
						worker.exec();
						
					cronologia.setText(cronologia.getText()+zonaMessaggio.getText()+"\n");
						zonaMessaggio.setText("");
					}
				}catch(Exception exp) {
					JOptionPane.showMessageDialog(null, "Inserire la chiave di cifratura (deve essere un numero)", "Errore", 0);
				}
			}else {
				int res = JOptionPane.showConfirmDialog(null, "Sicuro di inviare il messaggio senza cifratura?", "Attenzione", 1);
				if (res==JOptionPane.YES_OPTION) {
					BackgroundWorker worker = new BackgroundWorker(inboxAttuale);
					worker.setUp(code, zonaMessaggio.getText(), null, -1);
					worker.exec();
					cronologia.setText(cronologia.getText()+zonaMessaggio.getText()+"\n");
					zonaMessaggio.setText("");
				}
			}
		}
		
		// Si imposta come chat attuale quella premuta
		else {
			for(int i = 0; i < listaChat.getComponentCount();i ++) {
				if(listaChat.getComponent(i).equals(e.getSource())) {
					rimuoviChat.setVisible(true);
					inboxAttuale = inboxes.get(i);
					cronologia.setText("");
					zonaMessaggio.setText("Scrivi un messaggio...");
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
	
	/**
	 * Funzione utilizzata per controllare se la lista di inbox contiente una inbox specifica
	 * @param a ArrayList che contiene tutte le inbox
	 * @param p Inbox che andiamo a cercare
	 * @return True - Inbox presente, False - Inbox non trovata
	 */
	private static boolean contiene(ArrayList<Inbox> a, Inbox p) {
		boolean risultato = false;
		for(Inbox coppia : a) {
			// Si controlla l'IP e la porta dell'inbox
			if(coppia.getIP().equals(p.getIP()) && coppia.getPorta() == p.getPorta()) risultato = true;
		}
		return risultato;
	}
}