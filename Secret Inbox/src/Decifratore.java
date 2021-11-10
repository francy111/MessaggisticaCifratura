
public class Decifratore {
	private Decifratore() {}

	public static byte[] decifra(byte[] msg, int chiave) {
		byte[] res = new byte[msg.length];
		byte key = (byte)(chiave%255);
		for(int i = 0; i < msg.length; i++)
			res[i] = (byte)(msg[i] - key);
		
		return res;
	}
	public static byte[] decifra(byte[] msg, String chiave) {
		byte[] res = new byte[msg.length];
		byte[] key = chiave.getBytes();
		
		for(int i = 0; i < msg.length; i++)
			res[i] = (byte)(msg[i] - key[i%key.length]);
		
		return res;
	}
}
