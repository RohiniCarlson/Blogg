package com.example.ithsblog;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

public class EncryptionUtilities {
	
	private static final String TAG = "EncriptionUtilities";

	public static String encodePassword(Context context, String password)
	{
		try
		{
			CertificateFactory factory = CertificateFactory.getInstance("X509");
			X509Certificate cert = (X509Certificate) factory.generateCertificate(context.getResources().openRawResource(R.raw.iths_blog));
			PublicKey key = cert.getPublicKey();

			Cipher cipher = Cipher.getInstance("RSA/None/OAEPWithSHA1AndMGF1Padding");
			byte[] pwdBytes = password.getBytes("UTF-8");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] encryptedPwd = cipher.doFinal(pwdBytes);
			return Base64.encodeToString(encryptedPwd, Base64.NO_WRAP);    
		} catch (UnsupportedEncodingException e) {
			Log.d(TAG, "Unsupported encoding", e);
		} catch (CertificateException e) {
			Log.e(TAG, "Error generating certificate", e);
		} catch (NoSuchAlgorithmException e) {
			Log.e(TAG, "RSA not recognized as cipher", e);
		} catch (NoSuchPaddingException e) {
			Log.e(TAG, "Unrecognized RSA padding", e);
		} catch (InvalidKeyException e) {
			Log.e(TAG, "Invalid cipher key", e);
		} catch (IllegalBlockSizeException e) {
			Log.e(TAG, "Illegal block size", e);
		} catch (BadPaddingException e) {
			Log.e(TAG, "Bad padding", e);
		} catch (Exception e) {
			Log.e(TAG, "Exception when authenticating", e);
		}
		return null; //failed exception / the method should probably throw an exception to show that the encryption failed, though this should never happen.
	}
}
