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
		byte[] key = new byte[chiave.length()];
		for(int i = 0; i < key.length; i++) key[i] = (byte)chiave.toCharArray()[i];
		for(int i = 0; i < msg.length; i++) {
			res[i] = (byte)((byte)msg[i] - (byte)key[i%key.length]);
		}
		
		return res;
	}
	public static byte[] decifraBruteForce(byte[] msg){
		byte[] res = new byte[msg.length];
		for(int i = 0; i < msg.length; i++) res[i] = msg[i];
		
		byte[] possibileChiave = new byte[5];
		for(int i = -128; i < 128; i++) {
			if((byte)(res[0] - (byte)i) >= '0' && (byte)(res[0] - (byte)i) <= '9'  && (byte)(res[5] - (byte)i) == ' ') {
				possibileChiave[0] = (byte)i;
				for(int j = -128; j < 128; j++) {
					if((byte)(res[4] - (byte)j) == ':') {
						possibileChiave[4] = (byte)j;
						
						for(int h = 0; h < 3; h++) {
							for(int k = -128; k < 128; k++) {
								if((byte)(res[h+1] - (byte)k) == '9') {
									possibileChiave[h+1] = (byte)k; // il range va da k a k+9 compresi
									break;
								}
							}
						}
						break;
					}
				}
				break;
			}
		}
		return possibileChiave;
	}
}
