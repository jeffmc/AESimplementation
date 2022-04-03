package net.mcmillan.cryptography;

public abstract class EncryptionScheme { // Identical to https://github.com/jeffmc/AndroidCryptography/blob/main/app/src/main/java/com/example/androidcryptography/EncryptionScheme.java

    public abstract String schemeName();
    public abstract String encrypt(String raw);
    public abstract String decrypt(String coded);

    @Override
    public String toString() {
        return this.schemeName();
    }

}