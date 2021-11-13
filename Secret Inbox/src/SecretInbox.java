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
	private JTextField textField_portaAscolto;
	/**
	 * Pulsante con il quale si imposta la porta di ascolto
	 */
	private JButton button_impostaPorta;
	/**
	 * Pulsante con il quale si termina l'applicazione
	 */
	private JButton button_exit;
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
	private LinkedList<JButton> messaggi;
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
	private JTextField textField_chiaveCesare;
	private JTextPane textField_chiaveVigenere;
	/**
	 * Pulsante per eseguire la decifratura
	 */
	private JButton button_decifra;
	/**
	 * Pulsante per eseguire la Brute Force
	 */
	private JButton button_BruteForce;
	
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
		textField_portaAscolto = new JTextField();
		textField_portaAscolto.setPreferredSize(new Dimension(75, 25));
		button_impostaPorta = new JButton("Utilizza porta");
		button_impostaPorta.addActionListener(this);
		button_exit = new JButton("Exit");
		button_exit.addActionListener(this);
		
		panel_Porta.add(label_portaAscolto);
		panel_Porta.add(textField_portaAscolto);
		panel_Porta.add(button_impostaPorta);
		panel_Porta.add(button_exit);
		
		
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
		textField_chiaveCesare = new JTextField();
		textField_chiaveCesare.setPreferredSize(new Dimension(75, 25));
		textField_chiaveVigenere = new JTextPane(
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
		textField_chiaveVigenere.setPreferredSize(new Dimension(75, 25));
		textField_chiaveVigenere.setVisible(false);
		button_decifra = new JButton("Decifra messaggio");
		button_decifra.addActionListener(this);
		button_BruteForce = new JButton("!");
		button_BruteForce.setForeground(Color.red);
		button_BruteForce.addActionListener(this);
		
		JLabel tmp1 = new JLabel(), tmp2 = new JLabel(), tmp3 = new JLabel();
		tmp1.setBorder(new CompoundBorder(tmp1.getBorder(), new EmptyBorder(10,80,10,80)));
		tmp2.setBorder(new CompoundBorder(tmp2.getBorder(), new EmptyBorder(10,120,10,120)));
		tmp3.setBorder(new CompoundBorder(tmp3.getBorder(), new EmptyBorder(10,120,10,120)));
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
		
		panel_Interno = new JPanel();
		panel_Interno.setLayout(new BoxLayout(panel_Interno, BoxLayout.Y_AXIS));
		panel_Msg = new JScrollPane(panel_Interno);
		panel_Msg.setBorder(LineBorder.createBlackLineBorder());
		panel_Msg.setBounds(195, 10, 550, 460);
		messaggi = new LinkedList<JButton>();
		msg = null;
		add(panel_Porta);
		add(panel_Decifra);
		add(panel_Msg);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) { new SecretInbox(); }

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(button_impostaPorta)) {
			int porta;
			try {
				porta = Integer.parseInt(textField_portaAscolto.getText());
				if(porta!=this.porta) {
					this.porta = porta;
					if(worker!=null) worker.die();
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
							byte[] risultato = Decifratore.decifra(msg, chiave);
							JOptionPane.showMessageDialog(null, new String(risultato), "Risultato decifratura", JOptionPane.INFORMATION_MESSAGE);
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
						byte[] risultato = Decifratore.decifra(msg, chiave);
						JOptionPane.showMessageDialog(null, new String(risultato), "Risultato decifratura", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}else {
				JOptionPane.showMessageDialog(null, "Specificare l'algoritmo di decifratura", "Attenzione", JOptionPane.WARNING_MESSAGE);
			}
		}
		else if(e.getSource().equals(button_BruteForce)) {
			
		}
		else if(e.getSource().equals(button_exit)) {
			if(worker!= null) worker.die();
		}
		else {
			for (JButton p : messaggi) {
				if(p.equals(e.getSource())) {
					msg = p.getText().getBytes();
				}
			}
		}
	}
}
