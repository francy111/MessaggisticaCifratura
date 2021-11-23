	import java.awt.*;
	import java.awt.event.*;
	import java.util.ArrayList;
	import javax.swing.*;
	import javax.swing.border.LineBorder;
	import javax.swing.text.AttributeSet;
	import javax.swing.text.BadLocationException;
	import javax.swing.text.DefaultStyledDocument;
	
	/**
	 * Classe secret sender
	 * @author francy111
	 * @version 1.0
	 * Contiene� le funzionalita'� del secret sender
	 * 
	 * Inserire codice agente
	 * Inserire IP-Porta secret inbox
	 * Digitare il messaggio da inviare
	 * Selezionare la modalità di cifratura
	 */
	public class SecretSender extends JFrame implements ActionListener {
		
	/**
	 * ID di versione seriale
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Frame principale
	 */
	private JFrame impostazioniG;
	
	/**
	 * Codice agente
	 */
	private int code;
	
	/**
	 * Lista di tutte le inbox al quale si inviano messaggi
	 */
	private ArrayList<Inbox> inboxes;
	
	/**
	 * Inbox attuale
	 */
	private Inbox inboxAttuale = null;
	
	/**
	 * Finestra delle impostazioni
	 */
	private JButton impostazioni;
	
	/**
	 * Zona di testo in cui viene ripostato l'IP e la porta della inbox alla quale inviamo messaggi
	 */
	private JTextField infoChat;
	
	/**
	 * Pannello in cui troviamo tutte le chat avviate
	 */
	private JPanel listaChat;
	
	/**
	 * Area di testo in cui rimangono i messaggi inviati alla inbox, visualizzati senza cifratura
	 */
	private JTextArea cronologia;
	
	/**
	 * Area di testo in cui scrivere il messaggio da inviare
	 */
	private JTextArea zonaMessaggio;
	
	/**
	 * Pannello che comprende i pulsanti radio e il campo della chiabe
	 */
	private JPanel tipoCrittografia;
	
	/**
	 * Pulsante utilizzato per inviare il messaggio alla inbox
	 */
	private JButton inviaMessaggio;
	
	/**
	 * Pulsanti radio per scegliere l'algoritmo di cifratura
	 */
	private JRadioButton cesare, vigenere;
	
	/**
	 * Zona di testo in cui inserire la chiave di cifratura
	 */
	private JTextArea chiaveC, chiaveV;
	
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
	 * Pannello temporaneo per quando non ci sono chat avviate
	 */
	private JPanel temp;
	
	
	/**
	 * Costruttore default
	 * @param code Codice dell'agente che ha eseguito l'accesso
	 */
	public SecretSender(int code) {
		
		// Si imposta la dimensioni della finestra
		super("Secret Sender - Session ID:" + code);
		setBounds(100, 100, 948, 550);
		setLayout(null);
		setResizable(false);
		
		setBackground(Color.white);
		
		/* Inizializzazione attributi
		 */
		this.code = code;
		inboxes = new ArrayList<>();
		
		// Finestra delle impostazioni
		impostazioni = new JButton("Settings");
		impostazioni.setBounds(0,0,268,55);
		impostazioni.addActionListener(this);
		
		// Riporta l' IP e la porta della inbox in alto a centro/destra della finestra
		infoChat = new JTextField();
		infoChat.setBounds(270,0,660,56);
		infoChat.setText("");
		infoChat.setHorizontalAlignment(JTextField.CENTER);
		infoChat.setVisible(false);
		infoChat.setEditable(false);
		infoChat.setForeground(Color.black);
		infoChat.setBackground(Color.white);
		infoChat.setBorder(LineBorder.createBlackLineBorder());
		
		// Pannello con la lista di tutte le inbox
		listaChat = new JPanel();
		listaChat.setLayout(null);
		listaChat.setBackground(Color.white);
		JScrollPane scrollPane = new JScrollPane(listaChat);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(0,55,268,454);
		scrollPane.setBorder(LineBorder.createBlackLineBorder());
		
		temp = new JPanel();
		temp.setBounds(275, 5, 655, 505);
		temp.setBorder(LineBorder.createBlackLineBorder());
		temp.setBackground(Color.white);
		temp.add(new JLabel("Seleziona una Inbox per inviare messaggi"));
		
		// Area di testo in cui vengono riportati tutti i messaggi inviati ad una inbox
		cronologia = new JTextArea();
		cronologia.setBorder(LineBorder.createBlackLineBorder());
		cronologia.setBounds(270,57,660,348);
		cronologia.setVisible(false);
		cronologia.setEnabled(false);
		cronologia.setLineWrap(true);
		
		// Area di testo in cui inserire il messaggio da inviare alla inbox
		zonaMessaggio = new JTextArea(
				new DefaultStyledDocument() {
					private static final long serialVersionUID = 1L;
	
					@Override
					public void insertString(int offs, String str, AttributeSet a) {
						if((getLength() + str.length()) <= 512)
							try {
								super.insertString(offs, str, a);
							} catch (BadLocationException e) {
								e.printStackTrace();
							}
					}
				}
		);
		zonaMessaggio.setText("Scrivi un messaggio...");
		zonaMessaggio.setLineWrap(true);
		zonaMessaggio.setVisible(false);
		JScrollPane p = new JScrollPane(zonaMessaggio);
		p.setBounds(270,458,598,51);
		p.setBorder(LineBorder.createBlackLineBorder());
		
		// Pulsante per inviare il messaggio alla inbox
		inviaMessaggio = new JButton(">");
		inviaMessaggio.setBounds(870,458, 60, 51);
		inviaMessaggio.setVisible(false);
		inviaMessaggio.addActionListener(this);
		
		// Area di testo per inserire la chiave per l'algoritmo di cesare
		chiaveC = new JTextArea(
			new DefaultStyledDocument() {
				private static final long serialVersionUID = 1L;
				@Override
				public void insertString(int offs, String str, AttributeSet a) {
					try {
						str = str.replaceAll("[^\\d-]", "");
						super.insertString(offs, str, a);
					} catch (BadLocationException e) {
						e.printStackTrace();
					}
				}
			}
		);
		chiaveC.setPreferredSize(new Dimension(75, 25));
		chiaveC.setVisible(false);
		chiaveC.setBorder(LineBorder.createBlackLineBorder());
		chiaveC.setTransferHandler(null);

		// Area di testo per inserire la chiave per l'algoritmo di vigenere
		chiaveV = new JTextArea(
			new DefaultStyledDocument() {
				private static final long serialVersionUID = 1L;
				@Override
				public void insertString(int offs, String str, AttributeSet a) {
					if((getLength() + str.length()) <= 5)
					try {
						super.insertString(offs, str, a);
					} catch (BadLocationException e) {
						e.printStackTrace();
					}
				}
			}
		);
		chiaveV.setPreferredSize(new Dimension(75, 25));
		chiaveV.setVisible(false);
		chiaveV.setBorder(LineBorder.createBlackLineBorder());
		chiaveV.setTransferHandler(null);
		
		// Pulsante radio per selezionare l'algoritmo di cifratura
		cesare = new JRadioButton("Cesare");
		cesare.setBackground(Color.white);
		cesare.addActionListener(this);
		vigenere = new JRadioButton("Vigenere'");
		vigenere.setBackground(Color.white);
		vigenere.addActionListener(this);
		ButtonGroup r = new ButtonGroup();
		r.add(cesare);
		r.add(vigenere);
		
		// Pannello che contiene i due pulsanti radio per selezioanre l'algoritmo di cifratura
		tipoCrittografia = new JPanel();
		tipoCrittografia.add(cesare);
		tipoCrittografia.add(vigenere);
		tipoCrittografia.add(chiaveC);
		tipoCrittografia.add(chiaveV);
		tipoCrittografia.setBounds(270, 407, 660, 50);
		tipoCrittografia.setBorder(LineBorder.createBlackLineBorder());
		tipoCrittografia.setBackground(Color.white);
		tipoCrittografia.setVisible(false);
		
		// Pulsante per inserire IP e porta di una inbox
		creaChat = new JButton("Nuova chat");
		creaChat.addActionListener(this);
		
		// Pulsante per rimuovere la inbox attuale
		rimuoviChat = new JButton("Rimuovi chat");
		rimuoviChat.addActionListener(this);
		rimuoviChat.setVisible(false);
		
		// Pulsante per chiudere l'applicazione
		chiudiApplicazione = new JButton("Exit");
		chiudiApplicazione.addActionListener(this);
		
		// Pulsante per eseguire il logout dall'applicazione
		logout = new JButton("Esegui logout");
		logout.addActionListener(this);
		
		// Pulsante per aprire la finestra delle impostazioni
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
		add(p);
		add(inviaMessaggio);
		add(temp);
		add(scrollPane);
		
		setVisible(true);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
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
			tmp.setBounds(1, 1+(inboxes.indexOf(p))*50, 247, 50);
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
			if(risposta != null)
				try {
					int porta = Integer.parseInt((JOptionPane.showInputDialog(null, "Porta Secret Inbox", "Nuova chat",3)));
					if(porta != JOptionPane.CANCEL_OPTION) {
						if(porta<50000 || porta>65535) throw new Exception();
						Inbox p;
						p = new Inbox();
						p.setIP(risposta);
						p.setPorta(porta);
						if(!contiene(inboxes, p)) { 
							inboxes.add(p);
						}
						else JOptionPane.showMessageDialog(null, "La inbox � gi� presente", "Errore", 0);
						aggiornaChat();
						impostazioniG.dispose();
						
					}
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
				temp.setVisible(true);
				aggiornaChat();
			}
		}
		
		// Si imposta come tipo di cifratura la cifratura di Cesare, si attiva il campo per inserire la chiave
		else if(e.getSource().equals(cesare)) {
			chiaveC.setVisible(true);
			chiaveC.setText("");
			chiaveV.setVisible(false);
			chiaveV.setText("");
		}
		
		// Si imposta come tipo di cifratura la cifratura di vigenerè, si attiva il campo per inserire la chiave
		else if(e.getSource().equals(vigenere)) {
			chiaveC.setVisible(false);
			chiaveC.setText("");
			chiaveV.setVisible(true);
			chiaveV.setText("");
		}
		
		// Si crea un worker che cifra il messaggio e lo invia alla inbox indicata
		else if(e.getSource().equals(inviaMessaggio)) {
			if (zonaMessaggio.getText().isEmpty());
			else {
				if(cesare.isSelected()) {
					try {
						if(chiaveC.getText().isEmpty()) throw new Exception();
						int key = Integer.valueOf(chiaveC.getText());
						
						BackgroundWorker worker = new BackgroundWorker(inboxAttuale);
						worker.setUp(code, zonaMessaggio.getText(), ""+key, 0, this);
						worker.exec();
		
						cronologia.setText(cronologia.getText()+zonaMessaggio.getText()+"\n");
						zonaMessaggio.setText("");
					}catch(Exception exp) {
						JOptionPane.showMessageDialog(null, "Inserire la chiave di cifratura (deve essere un numero)", "Errore", JOptionPane.ERROR_MESSAGE);
					}
				}else if(vigenere.isSelected()) {
					try {
						if(chiaveV.getText().isEmpty()) throw new Exception();
						if(chiaveV.getText().length()!=5)
							JOptionPane.showMessageDialog(null, "La chiave deve essere una parola di 5 caratteri", "Errore", JOptionPane.ERROR_MESSAGE);
						else {
							String key = chiaveV.getText();
							BackgroundWorker worker = new BackgroundWorker(inboxAttuale);
							worker.setUp(code, zonaMessaggio.getText(), key, 1, this);
							worker.exec();
							
						cronologia.setText(cronologia.getText()+zonaMessaggio.getText()+"\n");
							zonaMessaggio.setText("");
						}
					}catch(Exception exp) {
						JOptionPane.showMessageDialog(null, "Inserire la chiave di cifratura (deve essere un numero)", "Errore", JOptionPane.ERROR_MESSAGE);
					}
				}else {
					int res = JOptionPane.showConfirmDialog(null, "Sicuro di inviare il messaggio senza cifratura?", "Attenzione", JOptionPane.WARNING_MESSAGE);
					if (res==JOptionPane.YES_OPTION) {
						BackgroundWorker worker = new BackgroundWorker(inboxAttuale);
						worker.setUp(code, zonaMessaggio.getText(), null, -1, this);
						worker.exec();
						cronologia.setText(cronologia.getText()+zonaMessaggio.getText()+"\n");
						zonaMessaggio.setText("");
					}
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
	
					temp.setVisible(false);
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