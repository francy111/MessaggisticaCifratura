import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.LinkedList;
import javax.swing.JButton;
import javax.swing.JPanel;
<<<<<<< HEAD
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
=======
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
>>>>>>> a0ea2d3aed0052366cb6060980e9c3ac6a0e5301
public class BackgroundWorker extends Thread{
	private int porta;
	private DatagramSocket socket;
	private boolean vivo;
	private LinkedList<Pair<JButton, byte[]> >messaggi;
	private JPanel panel;
	private SecretInbox ibx;
	public BackgroundWorker(int porta, LinkedList<Pair<JButton, byte[]> > msgs, JPanel panel, SecretInbox ibx) {
		vivo = true;
		messaggi = msgs;
		this.porta = porta;
		this.panel = panel;
		this.ibx = ibx;
	}
	public void setPorta(int porta) {this.porta = porta;}
	public int getPorta() {return porta;}
	public void die() {
		this.vivo = false;
		socket.close();
	}
	public boolean isVivo() {return vivo;}
	
	@Override
	public void run() {
		try {
			socket = new DatagramSocket(porta);
			byte[] msg = new byte[512];
			DatagramPacket p = new DatagramPacket(msg, 512);
			String ms;
			JButton tmp;
			byte[] messaggio;
			BufferedWriter bw;
			File f;
			int i = 0;
			while(vivo) {
<<<<<<< HEAD
				try {
					for(int k = 0; k < msg.length; k++) msg[k] = 0;
					p.setData(new byte[512]);
					socket.receive(p);
	
					int k;
					for(k = 0; i < p.getData().length;k++)
						if(p.getData()[k]==0) break;
					ms = new String(p.getData(), 0, k);
					messaggio = new byte[k];
					for(int aa = 0; aa < k; aa++)
						messaggio[aa] = p.getData()[aa];
					/**
					 * Creazione file di log, se non esiste si crea
					 * Si scrive sul file:
					 *     IP del sender
					 *     Porta del sender
					 *     Data e Ora in cui � arrivato il pacchetto
					 *     Il messaggio cifrato
					 */
					f = new File("./log"+porta+".txt");
					if(!f.exists()) f.createNewFile();
					bw = new BufferedWriter(new FileWriter(f));
					bw.append(p.getSocketAddress().toString() + ", ");
					bw.append(java.time.LocalDate.now().toString() + " ");
					bw.append(java.time.LocalTime.now().toString() + ", ");
					for(int z=0;z<k;z++) bw.append((char)p.getData()[z]);
					bw.append("\n");
					bw.close();
					
					tmp = new JButton(ms);
					tmp.addActionListener(ibx);
					messaggi.add(new Pair<>(tmp, messaggio));
					panel.add(messaggi.get(i).getPrimo());
					panel.revalidate();
					panel.repaint();
					i++;
				}catch(Exception coc) {}
=======
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
				
				File file = new File("log"+porta+".txt");
				if(!file.exists()) file.createNewFile();
				BufferedWriter bw = new BufferedWriter(new FileWriter(file));
				bw.append(ms);
				bw.close();
>>>>>>> a0ea2d3aed0052366cb6060980e9c3ac6a0e5301
			}
		}catch(Exception e) {}
	}
	public void cambiaPorta(int porta) {
		socket.close();
		this.porta = porta;
		try {
			socket = new DatagramSocket(porta);
		}catch(Exception e) {}
	}
}
