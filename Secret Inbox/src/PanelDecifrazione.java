import java.awt.Color;

import javax.swing.*;
import javax.swing.border.LineBorder;

class PanelDecifrazione extends JFrame{

	/**
	 * ID di versione seriale
	 */
	private static final long serialVersionUID = -3078384404698824954L;
	private JTextArea p1, p2;
	
	PanelDecifrazione() {
		super("Decifrazione");
		setLayout(null);
		setBounds(100, 100, 700, 400);
		
		JLabel l1, l2;
		l1 = new JLabel("Messaggio cifrato");
		l1.setBounds(125, 5, 200, 25);
		l2 = new JLabel("Messaggio decifrato");
		l2.setBounds(455, 5, 200, 25);
		
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

		JScrollPane s1 = new JScrollPane(p1), s2 = new JScrollPane(p2);
		s1.setBounds(10, 30, 335, 340);
		s1.setBorder(LineBorder.createBlackLineBorder());
		s2.setBounds(355, 30, 335, 340);
		s2.setBorder(LineBorder.createBlackLineBorder());
		
		add(l1);
		add(l2);
		add(s1);
		add(s2);
		setVisible(false);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
	}
	void setCifrato(byte[] msg) {
		String ms = new String(msg);
		p1.setText(ms);
	}
	void setDecifrato(byte[] msg) {
		String ms = new String(msg);
		p2.setText(ms);
	}
}
