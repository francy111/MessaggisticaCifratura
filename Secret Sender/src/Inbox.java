/**
* Classe inbox
* @author francy111
* Usata per accoppiare IP e Porta di un Secret Inbox
*/
public class Inbox{
	
	/**
	 * Porta dell'inbox
	 */
	private int port;
	
	/**
	 * IP dell'inbox
	 */
	private String IP;
	
	/**
	 * Costruttore default
	 */
	public Inbox() {}
	
	/**
	 * Costruttore con parametri
	 * @param ip IP della inbox
	 * @param port Porta della inbox
	 */
	public Inbox(String ip, int port) {
		this.IP = ip;
		this.port = port;
	}
	
	/**
	 * Imposta l'IP della inbox
	 * @param ip IP da impostare
	 */
	public void setIP(String ip) { this.IP = ip;}
	
	/**
	 * Imposta la porta della inbox
	 * @param porta Porta da impostare
	 */
	public void setPorta(int porta) { this.port = porta;}

	/**
	 * Restituisce la porta della inbox
	 * @return Porta
	 */
	public int getPorta() { return port;}
	
	/**
	 * Restituisce l'IP della inbox
	 * @return IP
	 */
	public String getIP() { return IP;}
	
	@Override
	public String toString() {
		return IP + "-" + port;
	}
}