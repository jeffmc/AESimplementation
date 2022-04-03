package net.mcmillan.cryptography;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

public class B64 extends EncryptionScheme {

	private Encoder b64encoder = Base64.getUrlEncoder().withoutPadding();
	private Decoder b64decoder = Base64.getUrlDecoder();
	public Charset charset = StandardCharsets.UTF_8;
	
	@Override
	public String schemeName() { return "Base64URL"; }

	@Override
	public String encrypt(String raw) {
		return b64encoder.encodeToString(raw.getBytes(charset));
	}
	
	@Override
	public String decrypt(String coded) {
		return new String(b64decoder.decode(coded), charset);
	}
	

}
