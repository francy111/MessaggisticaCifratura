import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Base64;
import java.util.LinkedList;
import javax.swing.JButton;
import javax.swing.JPanel;
public class BackgroundWorker extends Thread{
	private int porta;
	private DatagramSocket socket;
	private boolean vivo;
	private LinkedList<JButton> messaggi;
	private JPanel panel;
	private SecretInbox ibx;
	public BackgroundWorker(int porta, LinkedList<JButton> msgs, JPanel panel, SecretInbox ibx) {
		vivo = true;
		messaggi = msgs;
		this.porta = porta;
		this.panel = panel;
		this.ibx = ibx;
	}
	public void setPorta(int porta) {this.porta = porta;}
	public int getPorta() {return porta;}
	public void die() {this.vivo = false;}
	public boolean isVivo() {return vivo;}
	
	@Override
	public void run() {
		try {
			socket = new DatagramSocket(porta);
			byte[] msg = new byte[512];
			DatagramPacket p = new DatagramPacket(msg, 512);
			String ms;
			JButton tmp;
			int i = 0;
			while(vivo) {
				socket.receive(p);
				int k;
				for(k = 0; i < p.getData().length;k++)
					if(p.getData()[k]==0) break;
				ms = new String(p.getData(), 0, k);
				tmp = new JButton(ms);
				tmp.addActionListener(ibx);
				messaggi.add(tmp);
				panel.add(messaggi.get(i));
				panel.revalidate();
				panel.repaint();
				i++;
			}
			socket.close();
		}catch(Exception e) {}
	}
}
