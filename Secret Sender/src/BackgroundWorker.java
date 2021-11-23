import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import javax.swing.JOptionPane;

/**
 * Classe background worker
 * @author francy111
 * @version 1.0
 * Conterrà le funzionalità del secret sender
 */
public class BackgroundWorker{
	
	/**
	 * Codice agente
	 */
	private int code;
	
	/**
	 * Messaggio da inviare (e cifrare)
	 */
	private char[] o_message;
	
	/**
	 * Chiave di cifratura
	 */
	private String chiave;
	
	/**
	 * Tipo di cifratura (cesare/vigenere)
	 */
	private int tipoCrittografia;
	
	/**
	 * Inbox alla quale inviare il messaggio
	 */
	private Inbox inbox;
	
	/**
	 * Socket da utilizzare per contattare l'inbox
	 */
	private DatagramSocket socket;
	
	/**
	 * Frame che ha creato il worker
	 */
	private javax.swing.JFrame origin;
	
	/**
	 * Costruttore default
	 * @param inbox Inbox (IP/porta)
	 */
	public BackgroundWorker(Inbox inbox) {
		this.inbox = new Inbox(inbox.getIP(), inbox.getPorta());
		try {
			socket = new DatagramSocket();
			socket.setSoTimeout(5000);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Metodo utilizzato per fare il Set Up di un worker, specificando il necessario
	 * @param code Codice agente
	 * @param msg Messaggio da cifrare
	 * @param chiave Chiave di cifratura
	 * @param tipoCrittografia Tipo di crittografia
	 * @param origin JFrame che ha generato il worker
	 */
	public void setUp(int code, String msg, String chiave, int tipoCrittografia, javax.swing.JFrame origin) {
		this.code = code;
		this.o_message = (this.code + ": " + msg).toCharArray();
		this.chiave = chiave;
		this.tipoCrittografia = tipoCrittografia;
		this.origin = origin;
	}

	/**
	 * Metodo che cripta il messaggio e lo invia alla
	 * inbox indicata, per inviare il messaggio
	 * apre una datagram socket e alla fine
	 * la chiude, in quanto non deve aspettare nessuna
	 * risposta
	 */
	public void exec() {
		char[] msg = cifraMessaggio(o_message, chiave);
		byte [] msgByte = new byte[msg.length];
		for(int i = 0; i < msg.length; i++) msgByte[i] = (byte)(msg[i]);

		try {
			DatagramPacket p = new DatagramPacket(msgByte, msg.length, InetAddress.getByName(inbox.getIP()),inbox.getPorta());
			socket.send(p);
			socket.setSoTimeout(5000);
			Thread t = new Thread() {
				@Override
				public void run() {
					int tentativi = 0;
					try {
						DatagramPacket res = new DatagramPacket(new byte[2], 2, InetAddress.getByName(inbox.getIP()),inbox.getPorta());
						while(tentativi < 4){
							try {
								socket.receive(res);
								
								if(new String(res.getData()).equals("ok"))
									break; //in caso se si arriva qui vuol dire che un pacchetto è stato ricevuto
							}catch(Exception exp) {
								socket.send(p);
								tentativi++;
							}
						}
						if(tentativi == 4) JOptionPane.showMessageDialog(origin, "La inbox non è stata raggiunta o non ha ricevuto il messaggio", "Invio messaggio", JOptionPane.ERROR_MESSAGE);
						
						socket.close();
					}catch(Exception e) {}
				}
			};
			t.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Metodo che ricevuto il messaggio da cifrare e la chiave lo cifra, rendendolo in uscita
	 * @param msg Messaggio da cifrare
	 * @param chiave Chiave di cifratura
	 * @return Messaggio cifrato con la chiave indicata
	 */
	private char[] cifraMessaggio(char[] msg, String chiave) {
		char[] msgCriptato;
		if(tipoCrittografia == 0) {
			msgCriptato = cifraturaCesare(msg, Integer.valueOf(chiave));
		}else if(tipoCrittografia == 1){
			msgCriptato = cifraturaVigenere(msg, chiave);
		} else {
			msgCriptato = msg;
		}
		return msgCriptato;
	}
	
	/**
	 * Metodo utilizzato per cifrare il messaggio con il cifrario di Cesare
	 * @param msg Messaggio da cifrare
	 * @param chiave Chiave di cifratura (numero)
	 * @return Messaggio cifrato con il cifrario di Cesare
	 */
	private char[] cifraturaCesare(char[] msg, int chiave) {
		char[] cifrato = new char[msg.length];
		char key = (char)(chiave%255);

		for(int i = 0; i < msg.length; i++)
			cifrato[i] = (char)(msg[i] + key);
		return cifrato;
	}
	
	/**
	 * Metodo utilizzato per cifrare il messaggio con il cifrario di Vigenerè
	 * @param msg Messaggio da cifrare
	 * @param chiave Chiave di cifratura (messaggio)
	 * @return Messaggio cifrato con il cifrario di Vigenerè
	 */
	private char[] cifraturaVigenere(char[] msg, String chiave) {
		char[] cifrato = new char[msg.length];
		char[] key = chiave.toCharArray();
		for(int i = 0; i < msg.length; i++)
			cifrato[i] = (char)(msg[i] + key[i%key.length]);
		
		return  cifrato;
	}
}
