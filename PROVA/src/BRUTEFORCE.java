
public class BRUTEFORCE {
	public static void main(String[] args) {
		String msg = "1234: Ciao, come stai?";
		char[] coc = msg.toCharArray();
		byte[] cock = new byte[coc.length];
		
		for(char c : coc) System.out.print(c);
		System.out.println();
		coc = cifraturaCesare(coc, 2);
		for(char c : coc) System.out.print(c);
		System.out.println();
		for(int i= 0; i <coc.length;i++) cock[i] = (byte)coc[i];
		cock = decifraBruteForce(cock);
		for(byte c : cock) System.out.print((char)c);
		
	}
	
	private static char[] cifraturaCesare(char[] msg, int chiave) {
		char[] cifrato = new char[msg.length];
		char key = (char)(chiave%255);

		for(int i = 0; i < msg.length; i++)
			cifrato[i] = (char)(msg[i] + key);
		return cifrato;
	}
	public static byte[] decifraBruteForce(byte[] msg) {
		byte[] res = new byte[msg.length];
		return res;
	}
}
