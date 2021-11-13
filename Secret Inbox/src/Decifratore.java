import java.nio.charset.StandardCharsets;

public class Decifratore {
	private Decifratore() {}

	public static byte[] decifraCesare(byte[] msg, int chiave) {
		byte[] res = new byte[msg.length];
		byte key = (byte)(chiave%255);
		for(int i = 0; i < msg.length; i++)
			res[i] = (byte)(msg[i] - key);
		
		return res;
	}
	public static byte[] decifraVigenere(byte[] msg, String chiave) {
		byte[] res = new byte[msg.length];
		byte[] key = chiave.getBytes();
		System.out.println(new String(key));
		for(int i = 0; i < msg.length; i++) {
			res[i] = (byte)((byte)msg[i] - (byte)key[i%key.length]);
		}
		
		return res;
	}
}
