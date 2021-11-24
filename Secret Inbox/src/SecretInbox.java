import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
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
public class SecretInbox extends JFrame implements ActionListener{

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
	private JTextArea textField_portaAscolto;
	/**
	 * Pulsante con il quale si imposta la porta di ascolto
	 */
	private JButton button_impostaPorta;
	/**
	 * Numero di porta di ascolto
	 */
	private int porta;
	
	
	/**
	 * Contiene i componenti per ricevere, mostrare e selezionare i messaggi
	 */
	private JScrollPane panel_Msg;
	/**
	 * Pannello per contenere i pulsanti
	 */
	private JPanel panel_Interno;
	/**
	 * Lista contenente ogni messaggio ricevuto
	 */
	private LinkedList<Pair<JButton, byte[]> > messaggi;
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
	 * Pulsante radio per selezionare l'algoritmo di vigenere
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
	private JTextArea textField_chiaveCesare, textField_chiaveVigenere;
	/**
	 * Pulsante per eseguire la decifratura
	 */
	private JButton button_decifra;
	/**
	 * Pulsante per eseguire la Brute Force
	 */
	private JButton button_BruteForce;
	/**
	 * Finestra dove si visualizzano i risultati della decifrazione
	 */
	private PanelDecifrazione panel_dec;
	/**
	 * Finestra dove si provano i vari tentativi delle chiavi trovate con brute force
	 */
	private PanelBruteForce panel_bf;
	/**
	 * Pulsante con il quale si chiude l'applicazione
	 */
	private JButton esci;
	
	/**
	 * Lavoratore in background
	 */
	private BackgroundWorker worker;
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
		textField_portaAscolto = new JTextArea(
			new DefaultStyledDocument() {
				private static final long serialVersionUID = 1L;

				@Override
				public void insertString(int offs, String str, AttributeSet a) {
					if((getLength() + str.length()) <= 5)
						try {
							str = str.replaceAll("[^\\d]", "");
							super.insertString(offs, str, a);
						} catch (BadLocationException e) {
							e.printStackTrace();
						}
				}
			}
		);
		textField_portaAscolto.setTransferHandler(null);
		textField_portaAscolto.setPreferredSize(new Dimension(75, 25));
		textField_portaAscolto.setBorder(LineBorder.createBlackLineBorder());
		button_impostaPorta = new JButton("Utilizza porta");
		button_impostaPorta.addActionListener(this);
		
		panel_Porta.add(label_portaAscolto);
		panel_Porta.add(textField_portaAscolto);
		panel_Porta.add(button_impostaPorta);
		
		
		panel_Decifra = new JPanel();
		panel_Decifra.setBorder(LineBorder.createBlackLineBorder());
		panel_Decifra.setBounds(10, 120, 175, 350);
		label_decifratura = new JLabel("Algoritmo di decifratura");
		rbutton_cesare = new JRadioButton("Cesare");
		rbutton_cesare.addActionListener(this);
		rbutton_vigenere = new JRadioButton("Vigenere");
		rbutton_vigenere.addActionListener(this);
		rgroup_algoritmo = new ButtonGroup();
		rgroup_algoritmo.add(rbutton_cesare);
		rgroup_algoritmo.add(rbutton_vigenere);
		label_chiave = new JLabel("Chiave di decifratura");
		textField_chiaveCesare = new JTextArea(
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
		textField_chiaveCesare.setPreferredSize(new Dimension(75, 25));
		textField_chiaveCesare.setBorder(LineBorder.createBlackLineBorder());
		textField_chiaveCesare.setTransferHandler(null);
		textField_chiaveCesare.setVisible(false);
		
		textField_chiaveVigenere = new JTextArea(
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
		textField_chiaveVigenere.setBorder(LineBorder.createBlackLineBorder());
		textField_chiaveVigenere.setTransferHandler(null);
		textField_chiaveVigenere.setPreferredSize(new Dimension(75, 25));
		textField_chiaveVigenere.setVisible(false);
		button_decifra = new JButton("Decifra messaggio");
		button_decifra.addActionListener(this);
		button_BruteForce = new JButton("!");
		button_BruteForce.setForeground(Color.red);
		button_BruteForce.addActionListener(this);
		esci = new JButton("Esci");
		esci.addActionListener(this);
		
		JLabel tmp1 = new JLabel(), tmp2 = new JLabel(), tmp3 = new JLabel(), tmp4 = new JLabel(), tmp5 = new JLabel(), tmp6 = new JLabel();
		tmp1.setBorder(new CompoundBorder(tmp1.getBorder(), new EmptyBorder(10,80,10,80)));
		tmp2.setBorder(new CompoundBorder(tmp2.getBorder(), new EmptyBorder(10,120,10,120)));
		tmp3.setBorder(new CompoundBorder(tmp3.getBorder(), new EmptyBorder(10,120,10,120)));
		tmp4.setBorder(new CompoundBorder(tmp4.getBorder(), new EmptyBorder(10,120,10,120)));
		tmp5.setBorder(new CompoundBorder(tmp5.getBorder(), new EmptyBorder(10,120,10,120)));
		tmp6.setBorder(new CompoundBorder(tmp6.getBorder(), new EmptyBorder(10,120,10,120)));
		panel_Decifra.add(new JLabel("Decifratura messaggio"));
		panel_Decifra.add(tmp1);
		panel_Decifra.add(label_decifratura);
		panel_Decifra.add(rbutton_cesare);
		panel_Decifra.add(rbutton_vigenere);
		panel_Decifra.add(tmp2);
		panel_Decifra.add(label_chiave);
		panel_Decifra.add(textField_chiaveCesare);
		panel_Decifra.add(textField_chiaveVigenere);
		panel_Decifra.add(button_decifra);
		panel_Decifra.add(tmp3);
		panel_Decifra.add(button_BruteForce);
		panel_Decifra.add(tmp4);
		panel_Decifra.add(tmp5);
		panel_Decifra.add(esci);
		
		panel_bf = new PanelBruteForce();
		panel_dec = new PanelDecifrazione();
		
		panel_Interno = new JPanel();
		panel_Interno.setLayout(new BoxLayout(panel_Interno, BoxLayout.Y_AXIS));
		panel_Msg = new JScrollPane(panel_Interno);
		panel_Msg.setBorder(LineBorder.createBlackLineBorder());
		panel_Msg.setBounds(195, 10, 550, 460);
		messaggi = new LinkedList<Pair<JButton, byte[]> >();
		msg = null;
		
		setBackground(Color.white);
		add(panel_Porta);
		add(panel_Decifra);
		add(panel_Msg);
		setVisible(true);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	}
	
	/**
	 * Crea una SecretInbox
	 * @param args Parametri passati da riga di comando
	 */
	public static void main(String[] args) { new SecretInbox(); }

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(button_impostaPorta)) {
			int porta;
			try {
				porta = Integer.parseInt(textField_portaAscolto.getText());
				if(porta!=this.porta) {
					this.porta = porta;
					if(worker!=null) worker.cambiaPorta(this.porta);
					worker = new BackgroundWorker(porta, messaggi, panel_Interno, this);
					worker.start();
				}
			} catch(Exception exp) {
				JOptionPane.showMessageDialog(null, "La porta deve essere un numero", "Errore", JOptionPane.ERROR_MESSAGE);
			}
		}
		else if(e.getSource().equals(rbutton_cesare)) {
			textField_chiaveCesare.setVisible(true);
			textField_chiaveCesare.setText("");
			textField_chiaveVigenere.setVisible(false);
			textField_chiaveVigenere.setText("");
		}
		else if(e.getSource().equals(rbutton_vigenere)) {
			textField_chiaveCesare.setVisible(false);
			textField_chiaveCesare.setText("");
			textField_chiaveVigenere.setVisible(true);
			textField_chiaveVigenere.setText("");
			
		}
		else if(e.getSource().equals(button_decifra)) {
			if(rbutton_cesare.isSelected()) {
				try {
					if(textField_chiaveCesare.getText().isEmpty())
						JOptionPane.showMessageDialog(null, "Inserire la chiave (numero)", "Attenzione", JOptionPane.WARNING_MESSAGE);
					else {
						int chiave = Integer.parseInt(textField_chiaveCesare.getText());
						if(msg == null) JOptionPane.showMessageDialog(null, "Selezionare un messaggio da decifrare", "Attenzione", JOptionPane.WARNING_MESSAGE);
						else {
							byte[] risultato = Decifratore.decifraCesare(msg, chiave);
							panel_dec.setDecifrato(risultato);
							panel_dec.setVisible(true);
						}
					}
				}catch(Exception exp) {
					JOptionPane.showMessageDialog(null, "La chiave deve essere un numero", "Attenzione", JOptionPane.WARNING_MESSAGE);
				}
			}else if(rbutton_vigenere.isSelected()) {
				if(textField_chiaveVigenere.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Inserire la chiave (sequenza di 5 caratteri)", "Attenzione", JOptionPane.WARNING_MESSAGE);
				}else {
					String chiave = textField_chiaveVigenere.getText();
					if(msg == null) JOptionPane.showMessageDialog(null, "Selezionare un messaggio da decifrare", "Attenzione", JOptionPane.WARNING_MESSAGE);
					else {
						byte[] risultato = Decifratore.decifraVigenere(msg, chiave);
						panel_dec.setDecifrato(risultato);
						panel_dec.setVisible(true);
					}
				}
			}else {
				JOptionPane.showMessageDialog(null, "Specificare l'algoritmo di decifratura", "Attenzione", JOptionPane.WARNING_MESSAGE);
			}
		}
		else if(e.getSource().equals(esci)) {
			try {
				if(worker != null) worker.die();
				worker = null;
				for(int i=0;i<messaggi.size();i++)
					messaggi.removeLast();
				messaggi = null;
				panel_dec.dispose();
				panel_dec = null;
				panel_bf.dispose();
				panel_bf = null;
			}catch(Exception coc) {}
			
			dispose();
		}
		else if(e.getSource().equals(button_BruteForce)) {
			try {
				panel_dec.setVisible(false);
				panel_bf.setCifrato(msg);
				panel_bf.setVisible(true);
			}catch(Exception exp) {
				JOptionPane.showMessageDialog(null, "Non e' stato selezionato un messaggio", "Attenzione", JOptionPane.WARNING_MESSAGE);
			}
		}
		else {
			for (int i = 0; i < messaggi.size(); i++) {
				if(messaggi.get(i).getPrimo().equals(e.getSource())) {
					msg = messaggi.get(i).getSecondo();
					panel_dec.setVisible(true);
					panel_dec.setCifrato(messaggi.get(i).getSecondo());
					panel_dec.setDecifrato(new byte[]{' '});
					break; // Se troviamo il pulsante che ha generato l'evento non serve controllare quelli dopo
				}
			}
		}
	}
}
