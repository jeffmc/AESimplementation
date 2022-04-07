package net.mcmillan.cryptography;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.Comparator;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.Random;

public class Test {
	
	private static Encoder enc = Base64.getUrlEncoder().withoutPadding();
	private static Decoder dec = Base64.getUrlDecoder();
	
	public static AES aes = new AES();
	public static B64 b64 = new B64();
	public static MyB64 mb64 = new MyB64();
	public static JeffBase64 jb64 = new JeffBase64();
	
	public static void main(String args[]) {
//		test(new byte[] {0x4d, 0x61, 0x6e});
		megaTest(100,64);
	}
	
	private static void megaTest(int iters, int maxLength) {
		Random r = new Random();
		for (int len=1;len<=maxLength;len++) {
			byte[] rand = new byte[len];
			for (int i=0;i<iters;i++) {
				r.nextBytes(rand);
				test(rand);
			}
		}
	}

	private static void test(byte[] data) {
		String encMy = jb64.encode(data);
		String encLib = encb64(data);
		
		if (!encMy.equals(encLib)) {
			throw new IllegalStateException("Enc str not equal");
		}
		
		byte[] decMy = jb64.decode(encMy);
		byte[] decLib = decb64(encMy);

		if (!Arrays.equals(decMy, decLib)) {
			throw new IllegalStateException("Byte[] not equal");
		}
	}
	
	private static void strtest(String str) {
		System.out.println(str);
		String encMy = mb64.encrypt(str);
		String encLib = b64.encrypt(str);
		
		assert encMy.equals(encLib);
		
		String decMy = mb64.decrypt(encMy);
		String decLib = mb64.decrypt(encMy);
		
		assert decMy.equals(decLib);
	}
	
	private static String encb64(byte[] arr) {
		return enc.encodeToString(arr);
	}
	private static byte[] decb64(String in) {
		return dec.decode(in);
	}
	private static final byte[] HEX_ARRAY = "0123456789ABCDEF".getBytes(StandardCharsets.US_ASCII); // https://stackoverflow.com/questions/9655181/how-to-convert-a-byte-array-to-a-hex-string-in-java
	public static String bytesToHex(byte[] bytes) {
	    byte[] hexChars = new byte[bytes.length * 2];
	    for (int j = 0; j < bytes.length; j++) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2] = HEX_ARRAY[v >>> 4];
	        hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
	    }
	    return new String(hexChars, StandardCharsets.UTF_8);
	}
}
