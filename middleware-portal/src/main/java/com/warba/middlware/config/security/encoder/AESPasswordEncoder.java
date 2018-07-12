package com.warba.middlware.config.security.encoder;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import com.warba.middlware.util.AESEncryptor;

/**
 * 
 * @author Salah Abu Msameh
 */
public class AESPasswordEncoder implements PasswordEncoder {

	public static final String ENCRYPTION_KEY = "U$er?Pa$$Enc%KEY";
	public static final String INIT_VECTOR = "P@S$Enc#Init@Vec";
	
	@Override
	public String encode(CharSequence rawPassword) {
		return AESEncryptor.encrypt(ENCRYPTION_KEY, INIT_VECTOR, rawPassword.toString());
	}
	
	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		
		if(StringUtils.isEmpty(rawPassword) || StringUtils.isEmpty(encodedPassword)) {
			return false;
		}
		
		return rawPassword.toString().equals(AESEncryptor.decrypt(ENCRYPTION_KEY, INIT_VECTOR, encodedPassword));
	}
}
