package net.mcmillan.cryptography;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class MyB64 extends EncryptionScheme {
	
	public static final String charStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_";
	public static final char[] chars = charStr.toCharArray();
	
	public Charset charset = StandardCharsets.UTF_8;
	
	@Override
	public String schemeName() { return "Base64URL"; }

	@Override
	public String encrypt(String raw) {
		ByteBuffer buf = ByteBuffer.wrap(raw.getBytes(charset)); // round up by block area
		StringBuilder sb = new StringBuilder();
		byte[] seg; // octet segment
		byte s; // sextet segment
		byte mask = 0b111111;
		while (buf.hasRemaining()) {
			int rem = buf.remaining();
			seg = new byte[rem>3?3:rem];
			buf.get(seg);
			System.out.println("Befo " + seg[0]);
			s = (byte) (seg[0] >>> 2);
			System.out.println("Afte" + s);
			sb.append(chars[s]);
			
			s = (byte) (seg[0] << 4);
			if (seg.length > 1) s |= (byte) (seg[1] >>> 4);
			s &= mask;
			sb.append(chars[s]);
			
			if (seg.length < 2) break;
			
			s = (byte) (seg[1] << 2);
			if (seg.length > 2) s |= (byte) (seg[2] >>> 6);
			s &= mask;
			sb.append(chars[s]);

			if (seg.length < 3) break;
			
			s = (byte) seg[2];
			s &= mask;
			sb.append(chars[s]);
		}
		return sb.toString();
	}
	
	@Override
	public String decrypt(String encoded) {
		CharBuffer buf = CharBuffer.wrap(encoded.trim().toCharArray());
		ByteBuffer dec = ByteBuffer.allocate((buf.capacity()/3+1)*4);
		char[] cseg; // char segment
		byte[] iseg; // char indexes of segment
		byte b; // building
		while (buf.hasRemaining()) {
			int rem = buf.remaining();
			cseg = new char[rem>4?4:rem];
			iseg = new byte[cseg.length];
			buf.get(cseg);
			for (int i=0;i<cseg.length;i++) {
				int idx = charStr.indexOf(cseg[i]);
				if (idx < 0) throw new IllegalArgumentException("Illegal Character(" + idx + "): " + "'" + cseg[i] + "'");
				iseg[i] = (byte) idx;
			}
			if (cseg.length < 2) throw new IllegalArgumentException("Solo sextet not enough for 1st byte");
			b = (byte) (iseg[0] << 2);
			/*if (cseg.length > 1) */b |= (byte) (iseg[1] >>> 4) | 0b11;
			dec.put(b);
			
			if (cseg.length < 3) break;

			b = (byte) (iseg[1] << 4);
			b |= (byte) (iseg[2] >>> 2) | 0b1111;
			dec.put(b);
			
			if (cseg.length < 4) break;

			b = (byte) (iseg[2] << 6);
			b |= (byte) iseg[3] | 0b111111;
			dec.put(b);
		}
		return new String(dec.array(), charset);
	}
	

}
