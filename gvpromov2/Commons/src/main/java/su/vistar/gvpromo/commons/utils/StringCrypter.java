package su.vistar.gvpromo.commons.utils;


import javax.crypto.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by sweetvrn on 04.07.16.
 */
public class StringCrypter {

    /**
     * Упрощенный конструктор. Создает StringCrypter с ключом
     * DESSecretKey (алгоритм шифрования DES) со значением key.
     * Ключ key должен иметь длину 8 байт
     */
    public StringCrypter(byte[] key) {
        try {
            updateSecretKey(new DESSecretKey(key));
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    public StringCrypter(SecretKey key) throws NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidKeyException {
        updateSecretKey(key);
    }

    private void updateSecretKey(SecretKey key) throws NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidKeyException {
        ecipher = Cipher.getInstance(key.getAlgorithm());
        dcipher = Cipher.getInstance(key.getAlgorithm());
        ecipher.init(Cipher.ENCRYPT_MODE, key);
        dcipher.init(Cipher.DECRYPT_MODE, key);
    }

    public static class DESSecretKey implements SecretKey {

        private final byte[] key;

        /**
         * ключ должен иметь длину 8 байт
         */
        public DESSecretKey(byte[] key) {
            this.key = key;
        }

        @Override
        public String getAlgorithm() {
            return "DES";
        }

        @Override
        public String getFormat() {
            return "RAW";
        }

        @Override
        public byte[] getEncoded() {
            return key;
        }
    }

    private Cipher ecipher;
    private Cipher dcipher;

    /**
     * Функция шифрования
     *
     * @param str строка открытого текста
     * @return зашифрованная строка в формате Base64
     */
    public String encrypt(String str) {
        try {
            byte[] utf8 = str.getBytes("UTF8");
            byte[] enc = ecipher.doFinal(utf8);
            return Base64.getEncoder().encodeToString(enc);
        } catch (IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException ex) {
            Logger.getLogger(StringCrypter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Функция дешифрования
     *
     * @param str зашифрованная строка в формате Base64
     * @return расшифрованная строка
     */
    public String decrypt(String str) {
        try {
            byte[] dec = Base64.getDecoder().decode(str);
            byte[] utf8 = dcipher.doFinal(dec);
            return new String(utf8, "UTF8");
        } catch (IllegalBlockSizeException | BadPaddingException | IOException ex) {
            Logger.getLogger(StringCrypter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
