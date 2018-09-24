package com.soft.common.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.exception.ExceptionUtils;

import com.soft.common.Log;


/**
 * 
 * @author Salah Abu Msameh
 */
public class AESEncryptor {

	private static final String CHARSET = "UTF-8";
	private static final String SECRET_KEY_SPEC = "AES";
	private static final String TRANSFORMATION = "AES/CBC/PKCS5PADDING";
	
	/**
	 * Encrypt value based on a specific initial vector and encryption key
	 * 
	 * @param key
	 * @param initVector
	 * @param value
	 * @return
	 */
	public static String encrypt(String key, String initVector, String value) {
		
		try {
			Cipher cipher = init(key, initVector, Cipher.ENCRYPT_MODE);
			return Base64.encodeBase64String(cipher.doFinal(value.getBytes()));
		} 
		catch (Exception ex) {
			Log.error(AESEncryptor.class, ExceptionUtils.getFullStackTrace(ex));
		}

		return null;
	}
	
	/**
	 * Decrypt value based on a specific initial vector and encryption key
	 * 
	 * @param key
	 * @param initVector
	 * @param encryptedValue
	 * @return
	 */
	public static String decrypt(String key, String initVector, String encryptedValue) {
		
		try {
			Cipher cipher = init(key, initVector, Cipher.DECRYPT_MODE);
			return new String(cipher.doFinal(Base64.decodeBase64(encryptedValue)));
		} 
		catch (Exception ex) {
			Log.error(AESEncryptor.class, ExceptionUtils.getFullStackTrace(ex));
		}
		
		return null;
	}
	
	/**
	 * Initialize encryption cipher 
	 * 
	 * @param key
	 * @param initVector
	 * @param initializationMode
	 * @return
	 * @throws Exception
	 */
	private static Cipher init(String key, String initVector, int initializationMode) throws Exception {
		
		IvParameterSpec iv = new IvParameterSpec(initVector.getBytes(CHARSET));
		SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(CHARSET), SECRET_KEY_SPEC);
		
		Cipher cipher = Cipher.getInstance(TRANSFORMATION);
		cipher.init(initializationMode, skeySpec, iv);
		
		return cipher;
	}
}
