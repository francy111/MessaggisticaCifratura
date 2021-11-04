/**
 * Classe background worker
 * @author francy111
 * @version 1.0
 * Conterrà le funzionalità del secret sender
 */
public class BackgroundWorker implements Runnable{
	private int code;
	private byte[] o_message;
	private String chiave;
	private int tipoCrittografia;
	private Inbox inbox;
	
	public BackgroundWorker(int code, String msg, String chiave, int tipoCrittografia, Inbox inbox) {
		this.code = code;
		this.o_message = msg.getBytes();
		this.chiave = chiave;
		this.tipoCrittografia = tipoCrittografia;
		this.inbox.setIP(inbox.getIP());
		this.inbox.setPorta(inbox.getPorta());
	}

	@Override
	public void run() {
		byte[] msg = criptaMessaggio(o_message, chiave);
		inviaMessaggio(msg);
	}
	
	private byte[] criptaMessaggio(byte[] msg, String chiave) {
		byte[] msgCriptato;
		if(tipoCrittografia == 0) {
			msgCriptato = cifraturaCesare(msg, Integer.valueOf(chiave));
		}else{
			msgCriptato = cifraturaVigenere(msg, chiave);
		}
		return msgCriptato;
	}
	
	
	private byte[] cifraturaCesare(byte[] msg, int chiave) { return null; }
	private byte[] cifraturaVigenere(byte[] msg, String chiave) { return null; }
	
	public void inviaMessaggio(byte[] msg) {}
}
