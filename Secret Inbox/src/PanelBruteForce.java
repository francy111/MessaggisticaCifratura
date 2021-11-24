import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.LineBorder;

/**
 * Classe panel decifrazione brute force
 * @author francy111
 * @version 1.0
 * Mostra il messaggio cifrato e quello decifrato, insieme alle possibili chiavi per decifrare
 */
class PanelBruteForce extends JFrame implements ActionListener{

	/**
	 * ID di versione seriale
	 */
	private static final long serialVersionUID = -3078384404698824954L;
	/**
	 * Aree di testo
	 */
	private JTextArea p1, p2;
	/**
	 * Pannello
	 */
	private JPanel p3;
	/**
	 * Campi di testo
	 */
	JTextField k1, k5;
	/**
	 * Menu a tendina
	 */
	JComboBox<Character> k2, k3, k4;
	/**
	 * Pulsante per decifrare il messaggio
	 */
	JButton decifra;
	/**
	 * Messaggio cifrato
	 */
	private byte[] cifrato;
	/**
	 * Una delle possibili chiavi di decifratura
	 */
	private byte[] forseChiave;
	
	/**
	 * Costruttore
	 */
	PanelBruteForce() {
		super("Decifrazione brute force");
		setLayout(null);
		setBounds(100, 100, 700, 400);
		setResizable(false);
		
		JLabel l1, l2, l3;
		l1 = new JLabel("Messaggio cifrato");
		l1.setBounds(125, 5, 200, 25);
		l2 = new JLabel("Messaggio decifrato");
		l2.setBounds(455, 105, 200, 25);
		l3 = new JLabel("Chiave di decifrazione");
		l3.setBounds(455, 5, 200, 25);
		
		p1 = new JTextArea();
		p1.setTransferHandler(null);
		p1.setEnabled(false);
		p1.setForeground(Color.black);
		p1.setLineWrap(true);
		
		p2 = new JTextArea();
		p2.setTransferHandler(null);
		p2.setEnabled(false);
		p2.setForeground(Color.black);
		p2.setLineWrap(true);

		p3 = new JPanel();
		p3.setBorder(LineBorder.createBlackLineBorder());
		p3.setBounds(355, 30, 335, 70);
		p3.setBackground(Color.white);
		k1 = new JTextField("");
		k1.setEditable(false);
		k1.setForeground(Color.black);
		k2 = new JComboBox<Character>();
		k2.setEditable(false);
		k2.setForeground(Color.black);
		k3 = new JComboBox<Character>();
		k3.setEditable(false);
		k3.setForeground(Color.black);
		k4 = new JComboBox<Character>();
		k4.setEditable(false);
		k4.setForeground(Color.black);
		k5 = new JTextField("");
		k5.setEditable(false);
		k5.setForeground(Color.black);
		k1.setPreferredSize(new Dimension(50, 25));
		k5.setPreferredSize(new Dimension(50, 25));
		decifra = new JButton("Prova chiave");
		decifra.addActionListener(this);
		p3.add(k1);
		p3.add(k2);
		p3.add(k3);
		p3.add(k4);
		p3.add(k5);
		p3.add(decifra);
		
		JScrollPane s1 = new JScrollPane(p1), s2 = new JScrollPane(p2);
		s1.setBounds(10, 30, 335, 340);
		s1.setBorder(LineBorder.createBlackLineBorder());
		s2.setBounds(355, 130, 335, 240);
		s2.setBorder(LineBorder.createBlackLineBorder());

		add(l1);
		add(l2);
		add(l3);
		add(s1);
		add(s2);
		add(p3);
		setVisible(false);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
	}
	/**
	 * Imposta il messaggio cifrato
	 * @param msg Messaggio cifrato
	 */
	void setCifrato(byte[] msg) {
		this.cifrato = new byte[msg.length];
		for(int i = 0;i<cifrato.length;i++) this.cifrato[i] = msg[i];
		p1.setText(new String(cifrato));

		forseChiave = Decifratore.decifraBruteForce(cifrato);
		setChiave(forseChiave);
	}
	/**
	 * Imposta il messaggio decifrato
	 * @param msg Messaggio decifrato
	 */
	void setDecifrato(byte[] msg) {
		String ms = new String(msg);
		p2.setText(ms);
	}
	/**
	 * Imposta la chiave
	 * @param chiave Possibile chiave di decifratura
	 */
	private void setChiave(byte[] chiave) {
		k1.setText(""+(char)chiave[0]);
		for(int i = 0; i < 10; i++) k2.addItem((char)(chiave[1] + (byte)i));
		for(int i = 0; i < 10; i++) k3.addItem((char)(chiave[2] + (byte)i));
		for(int i = 0; i < 10; i++) k4.addItem((char)(chiave[3] + (byte)i));
		k5.setText(""+(char)chiave[4]);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(decifra)) {
			String chiave = "";
			chiave += k1.getText();
			chiave += k2.getItemAt(k2.getSelectedIndex());
			chiave += k3.getItemAt(k3.getSelectedIndex());
			chiave += k4.getItemAt(k4.getSelectedIndex());
			chiave += k5.getText();
			
			setDecifrato(Decifratore.decifraVigenere(cifrato, chiave));
		}
	}
}
