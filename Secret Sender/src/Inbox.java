/**
* Classe inbox
* @author francy111
* Usata per accoppiare IP e Porta di un Secret Inbox
*/
public class Inbox{
	private int port;
	private String IP;
	public Inbox() {}
	public Inbox(String ip, int port) {
		this.IP = ip;
		this.port = port;
	}
	public void setIP(String ip) { this.IP = ip;}
	public void setPorta(int porta) { this.port = porta;}
	public int getPorta() { return port;}
	public String getIP() { return IP;}
	@Override
	public String toString() {
		return IP + "-" + port;
	}
}