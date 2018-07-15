package com.warba.middlware.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.exception.ExceptionUtils;

import com.warba.common.utils.Log;

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
			IvParameterSpec iv = new IvParameterSpec(initVector.getBytes(CHARSET));
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(CHARSET), SECRET_KEY_SPEC);

			Cipher cipher = Cipher.getInstance(TRANSFORMATION);
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

			return Base64.encodeBase64String(cipher.doFinal(value.getBytes()));
			
		} catch (Exception ex) {
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
			IvParameterSpec iv = new IvParameterSpec(initVector.getBytes(CHARSET));
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(CHARSET), SECRET_KEY_SPEC);
			
			Cipher cipher = Cipher.getInstance(TRANSFORMATION);
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			
			return new String(cipher.doFinal(Base64.decodeBase64(encryptedValue)));
			
		} catch (Exception ex) {
			Log.error(AESEncryptor.class, ExceptionUtils.getFullStackTrace(ex));
		}
		
		return null;
	}
}
