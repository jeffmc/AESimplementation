package net.mcmillan.cryptography;

public class Test {
	
	public static AES aes = new AES();
	public static B64 b64 = new B64();
	
	public static void main(String args[]) {
		test("A");
		test("A few words");
		test("This is an entire sentence in the english language, using punctuation.");
	}
	
	private static void test(String str) {
		System.out.println("0: "+str);
		System.out.println("1: "+b64.encrypt(str));
		String enc = aes.encrypt(str);
		System.out.println("2: "+enc);
		System.out.println("3: "+aes.decrypt(enc));
		System.out.println();
	}
}
