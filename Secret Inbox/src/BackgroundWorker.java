import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.util.LinkedList;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;

/**
 * Classe BackgrounWorker
 * @author francy111
 * @version 1.0
 * Thread che riceve i messaggi dal Sender e li aggiunge alla finestra della Inbox
 */
public class BackgroundWorker extends Thread{
	/**
	 * Numero di porta sulla quale si ricevono i messaggi
	 */
	private int porta;
	/**
	 * Socket con la quale riceviamo i messaggi
	 */
	private DatagramSocket socket;
	/**
	 * Indica se il thread sta lavorando o meno
	 */
	private boolean vivo;
	/**
	 * Lista di coppie JButton, array di byte, rappresentano i messaggi ricevuti e il pulsante che è associalo a loro
	 */
	private LinkedList<Pair<JButton, byte[]> >messaggi;
	/**
	 * Pannello sul quale aggiungere i pulsanti
	 */
	private JPanel panel;
	/**
	 * Inbox che ha generato il Worker
	 */
	private SecretInbox ibx;
	/**
	 * Costruttore
	 * @param porta Porta della socket su cui ascoltare
	 * @param msgs Lista di coppie pulsante - messaggio
	 * @param panel Pannello sul quale aggiungere i pulsanti
	 * @param ibx Inbox che ha generato il worker
	 */
	public BackgroundWorker(int porta, LinkedList<Pair<JButton, byte[]> > msgs, JPanel panel, SecretInbox ibx) {
		vivo = true;
		messaggi = msgs;
		this.porta = porta;
		this.panel = panel;
		this.ibx = ibx;
	}
	/**
	 * Metodo per impostare la porta
	 * @param porta Porta sulla quale mettersi in ascolto
	 */
	public void setPorta(int porta) {this.porta = porta;}
	/**
	 * Metodo che restituisce la porta in utilizzo
	 * @return Porta
	 */
	public int getPorta() {return porta;}
	/**
	 * Metodo che imposta chiude la socket
	 */
	public void die() {
		this.vivo = false;
		socket.close();
	}
	/**
	 * Metodo che ci dice se il worker sta lavorando o meno
	 * @return true, il worker sta lavorando - false, il worker non sta lavorando
	 */
	public boolean isVivo() {return vivo;}
	
	@Override
	public void run() {
		try {
			socket = new DatagramSocket(porta);
			byte[] msg = new byte[512];
			DatagramPacket p = new DatagramPacket(msg, 512);
			DatagramPacket risposta;
			JButton tmp;
			String ipSender;
			byte[] messaggio;
			BufferedWriter bw;
			File f;
			int i = 0;
			while(vivo) {
				try {
					for(int k = 0; k < msg.length; k++) msg[k] = 0;
					p.setData(new byte[512]);
					socket.receive(p);
					ipSender = p.getSocketAddress().toString();
					ipSender = ipSender.substring(1, ipSender.indexOf(':'));
					risposta = new DatagramPacket(
							new byte[] {'o','k'},
							2, 
							InetAddress.getByName(ipSender),
							p.getPort()
					);
					socket.send(risposta);
					
					
					int k;
					for(k = 0; i < p.getData().length;k++)
						if(p.getData()[k]==0) break;
					messaggio = new byte[k];
					for(int aa = 0; aa < k; aa++)
						messaggio[aa] = p.getData()[aa];
					/**
					 * Creazione file di log, se non esiste si crea
					 * Si scrive sul file:
					 *     IP del sender
					 *     Porta del sender
					 *     Data e Ora in cui e' arrivato il pacchetto
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
					
					tmp = new JButton(java.time.LocalDate.now().toString() + " " + java.time.LocalTime.now().toString());
					tmp.addActionListener(ibx);
					messaggi.add(new Pair<>(tmp, messaggio));
					panel.add(messaggi.get(i).getPrimo());
					panel.revalidate();
					panel.repaint();
					i++;
				}catch(Exception coc) {}
			}
		}catch(Exception e) {}
	}
	/**
	 * Metodo utilizzato per cambiare la porta di ascolto
	 * @param porta Nuova porta sulla quale mettersi in ascolto
	 */
	public void cambiaPorta(int porta) {
		socket.close();
		this.porta = porta;
		try {
			socket = new DatagramSocket(porta);
		}catch(Exception e) {}
	}
}
