package com.github.aracwong.commons;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.Arrays;


/**
 * desc:
 *
 * @author zpwang
 * @date 2019/5/31 18:46
 * @since 1.0.0
 */
public class SecureKit {

    /**
     * AES算法
     */
    public static final String AES_ALGORITHM = "AES";

    /**
     * HMACSHA1 加解密密算法
     */
    public static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";


    /**
     * 加解密算法/模式/填充方式
     */
    public static final String AES_ECB_PKCS7_PADDING = "AES/ECB/PKCS7Padding";

    /**
     * 加解密算法/模式/填充方式
     */
    public static final String AES_ECB_PKCS5_PADDING = "AES/ECB/PKCS5Padding";

    private static final int INITIALIZATION_VECTOR_SIZE = 16;
    private static final int BLOCK_SIZE = 20;
    /**
     * The length of the signature
     */
    private static final int SIGNATURE_SIZE = 4;


    static {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }

    /**
     * 参数加密
     * AES/ECB/PKCS7Padding
     *
     * @param str 明文串
     * @param key 秘钥
     * @return
     */
    public static String encryptByPKCS7AndEncodeBase64(String str, String key) throws Exception {
        try {
            byte[] encryptBytes = encrypt(AES_ECB_PKCS7_PADDING, str.getBytes(), key.getBytes("utf-8"));
            String price = Base64.encodeBase64URLSafeString(encryptBytes);
            return price;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 参数加密
     * AES/ECB/PKCS5Padding
     *
     * @param str 明文串
     * @param key 秘钥
     * @return
     */
    public static String encryptByPKCS5AndEncodeBase64(String str, String key) throws Exception {
        try {
            byte[] encryptBytes = encrypt(AES_ECB_PKCS5_PADDING, str.getBytes(), key.getBytes("utf-8"));
            String price = Base64.encodeBase64URLSafeString(encryptBytes);
            return price;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 加密
     *
     * @param model 填充模式
     * @param data  待加密的数据
     * @param keys  秘钥
     * @return
     * @throws Exception
     */
    private synchronized static byte[] encrypt(String model, byte[] data, byte[] keys) throws Exception {
        Key key = toKey(keys);
        Cipher decipher = Cipher.getInstance(model, "BC");
        decipher.init(Cipher.ENCRYPT_MODE, key);
        return decipher.doFinal(data);
    }

    /**
     * 参数解密
     *
     * @param encryptStr 加密串
     * @param key        秘钥
     * @return
     */
    public static String decodeBase64AndDecryptByPKCS7(String encryptStr, String key) throws Exception {
        try {
            byte[] decryptBytes = decrypt(AES_ECB_PKCS7_PADDING, Base64.decodeBase64(encryptStr), key.getBytes("utf-8"));
            String price = new String(decryptBytes, "utf-8");
            return price.trim();
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 参数解密
     * AES/ECB/PKCS5Padding
     *
     * @param encryptStr 加密串
     * @param key        秘钥
     * @return
     */
    public static String decodeBase64AndDecryptByPKCS5(String encryptStr, String key) throws Exception {
        try {
            byte[] decryptBytes = decrypt(AES_ECB_PKCS5_PADDING, Base64.decodeBase64(encryptStr), key.getBytes("utf-8"));
            String price = new String(decryptBytes, "utf-8");
            return price.trim();
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 解密
     *
     * @param model 填充模式
     * @param data  待解密数据
     * @param keys  秘钥
     * @return
     * @throws Exception
     */
    private synchronized static byte[] decrypt(String model, byte[] data, byte[] keys) throws Exception {
        Key key = toKey(keys);
        Cipher decipher = Cipher.getInstance(model, "BC");
        decipher.init(Cipher.DECRYPT_MODE, key);
        return decipher.doFinal(data);
    }

    private static Key toKey(byte[] key) throws Exception {
        SecretKey secretKey = new SecretKeySpec(key, AES_ALGORITHM);
        return secretKey;
    }

    /**
     * @param secretKey
     * @param data
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws IOException
     */
    public static BigDecimal decodeBase64AndSha1(String secretKey, String data) throws NoSuchAlgorithmException, InvalidKeyException, IOException {
        byte[] cipherText = Base64.decodeBase64(data);
        final int plaintext_length = cipherText.length - INITIALIZATION_VECTOR_SIZE;
        if (plaintext_length < 0) {
            throw new RuntimeException("The plain text length can't be negative.");
        }
        byte[] iv = Arrays.copyOf(cipherText, INITIALIZATION_VECTOR_SIZE);
        final Mac hmacer = Mac.getInstance(HMAC_SHA1_ALGORITHM);
        final int ciphertextEnd = INITIALIZATION_VECTOR_SIZE + plaintext_length;
        final byte[] plainText = new byte[plaintext_length];
        boolean addIvCounterByte = true;
        SecretKey encryptionKey = new SecretKeySpec(secretKey.getBytes(), HMAC_SHA1_ALGORITHM);
        for (int ciphertextBegin = INITIALIZATION_VECTOR_SIZE, plaintextBegin = 0; ciphertextBegin < ciphertextEnd; ) {
            hmacer.reset();
            hmacer.init(encryptionKey);
            final byte[] pad = hmacer.doFinal(iv);
            int i = 0;
            while (i < BLOCK_SIZE && ciphertextBegin != ciphertextEnd) {
                plainText[plaintextBegin++] = (byte) (cipherText[ciphertextBegin++] ^ pad[i++]);
            }
            if (!addIvCounterByte) {
                final int index = iv.length - 1;
                addIvCounterByte = ++iv[index] == 0;
            }
            if (addIvCounterByte) {
                addIvCounterByte = false;
                iv = Arrays.copyOf(iv, iv.length + 1);
            }
        }
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(plainText));
        final long value = dis.readLong();
        dis.close();
        return new BigDecimal(value);
    }

    /**
     * @param _encryptionKey
     * @param _integrityKey
     * @param data
     * @return
     * @throws Exception
     */
    public static BigDecimal decodeBase64AndSha1WithIntegrity(byte[] _encryptionKey, byte[] _integrityKey, String data) throws Exception {
        try {
            SecretKey encryptionKey = new SecretKeySpec(_encryptionKey, SecureKit.HMAC_SHA1_ALGORITHM);
            SecretKey integrityKey = new SecretKeySpec(_integrityKey, SecureKit.HMAC_SHA1_ALGORITHM);

            byte[] cipherText = Base64.decodeBase64(data);

            final int plaintext_length = cipherText.length - INITIALIZATION_VECTOR_SIZE - SIGNATURE_SIZE;
            if (plaintext_length < 0) {
                throw new RuntimeException("The plain text length can't be negative.");
            }
            byte[] iv = Arrays.copyOf(cipherText, INITIALIZATION_VECTOR_SIZE);
            final Mac hmacer = Mac.getInstance(HMAC_SHA1_ALGORITHM);
            final int ciphertextEnd = INITIALIZATION_VECTOR_SIZE + plaintext_length;
            final byte[] plainText = new byte[plaintext_length];
            boolean addIvCounterByte = true;
            for (int ciphertextBegin = INITIALIZATION_VECTOR_SIZE, plaintextBegin = 0; ciphertextBegin < ciphertextEnd; ) {
                hmacer.reset();
                hmacer.init(encryptionKey);
                final byte[] pad = hmacer.doFinal(iv);
                int i = 0;
                while (i < BLOCK_SIZE && ciphertextBegin != ciphertextEnd) {
                    plainText[plaintextBegin++] = (byte) (cipherText[ciphertextBegin++] ^ pad[i++]);
                }
                if (!addIvCounterByte) {
                    final int index = iv.length - 1;
                    addIvCounterByte = ++iv[index] == 0;
                }
                if (addIvCounterByte) {
                    addIvCounterByte = false;
                    iv = Arrays.copyOf(iv, iv.length + 1);
                }
            }
            //校验
            hmacer.reset();
            hmacer.init(integrityKey);
            hmacer.update(plainText);
            hmacer.update(Arrays.copyOf(cipherText, INITIALIZATION_VECTOR_SIZE));
            final byte[] computedSignature = Arrays.copyOf(hmacer.doFinal(), SIGNATURE_SIZE);
            final byte[] signature = Arrays.copyOfRange(
                    cipherText, ciphertextEnd, ciphertextEnd + SIGNATURE_SIZE);
            if (!Arrays.equals(signature, computedSignature)) {
                throw new Exception("Signature mismatch.");
            }
            DataInputStream dis = new DataInputStream(new ByteArrayInputStream(plainText));
            final long value = dis.readLong();
            dis.close();
            return new BigDecimal(value);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("HmacSHA1 not supported.", e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException("Key is invalid for this purpose.", e);
        }
    }

    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    /**
     * Convert char to byte
     *
     * @param c char
     * @return byte
     */
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }
}
