import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

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
	private byte[] o_message;
	
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
	 * Costruttore default
	 * @param inbox Inbox (IP/porta)
	 */
	public BackgroundWorker(Inbox inbox) {
		this.inbox = new Inbox(inbox.getIP(), inbox.getPorta());
		try {
			socket = new DatagramSocket();
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
	 */
	public void setUp(int code, String msg, String chiave, int tipoCrittografia) {
		this.code = code;
		this.o_message = (this.code + ": " + msg).getBytes();
		this.chiave = chiave;
		this.tipoCrittografia = tipoCrittografia;
	}

	/**
	 * Metodo che cripta il messaggio e lo invia alla
	 * inbox indicata, per inviare il messaggio
	 * apre una datagram socket e alla fine
	 * la chiude, in quanto non deve aspettare nessuna
	 * risposta
	 */
	public void exec() {
		byte[] msg = cifraMessaggio(o_message, chiave);
		
		try {
			DatagramPacket p = new DatagramPacket(msg, msg.length, InetAddress.getByName(inbox.getIP()),inbox.getPorta());
			socket.send(p);
			
			socket.close();
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
	private byte[] cifraMessaggio(byte[] msg, String chiave) {
		byte[] msgCriptato;
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
	private byte[] cifraturaCesare(byte[] msg, int chiave) {
		byte[] cifrato = new byte[msg.length];
		byte key = (byte)(chiave%255);

		for(int i = 0; i < msg.length; i++)
			cifrato[i] = (byte)(msg[i] + key);
		return cifrato;
	}
	
	/**
	 * Metodo utilizzato per cifrare il messaggio con il cifrario di Vigenerè
	 * @param msg Messaggio da cifrare
	 * @param chiave Chiave di cifratura (messaggio)
	 * @return Messaggio cifrato con il cifrario di Vigenerè
	 */
	private byte[] cifraturaVigenere(byte[] msg, String chiave) {
		byte[] cifrato = new byte[msg.length];
		byte[] key = chiave.getBytes();
		
		for(int i = 0; i < msg.length; i++) {
			cifrato[i] = (byte)(msg[i] + key[i%key.length]);
		}
		
		return  cifrato;
	}
}
