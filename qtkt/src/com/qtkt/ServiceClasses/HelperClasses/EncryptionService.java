package com.qtkt.ServiceClasses.HelperClasses;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;

public class EncryptionService {
	private final byte[] key = "abcdEFGH".getBytes();
	private SecretKeySpec secretkey;
	private Cipher cipher;
	private String output = "";

	public String doEncryption(String message) {
		byte input[] = message.getBytes();
		secretkey = new SecretKeySpec(key, "DES");

		try {
			cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretkey);

			byte ciphertext[] = cipher.doFinal(input);
			String cipherstring = Hex.encodeHexString(ciphertext);

			output = cipherstring;

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}

		return output;
	}

}
