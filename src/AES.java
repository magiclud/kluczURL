import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.KeyGenerator;

public class AES {

	private String hasloDoKeystora;
	private String aliasHasla;
	private String sciezkaDoKeyStore = "";

	public AES(String uzytkownik, String hasloDoKeystorea) {
		this.sciezkaDoKeyStore = "D:\\eclipse\\Semestr4\\lista6Klucz\\keystore"
				+ uzytkownik + ".ks";
		this.hasloDoKeystora = hasloDoKeystorea;
		this.aliasHasla = uzytkownik;
	}

	public String stworzKeystoreZkluczem() {
		try {
			KeyStore keyStore = KeyStore.getInstance("UBER", "BC");
			InputStream inputStream = null;

			keyStore.load(inputStream, hasloDoKeystora.toCharArray());

			// ProtectionParameter protParam = new KeyStore.PasswordProtection(
			// hasloDoKeystora.toCharArray());
			// keyStore.setEntry(aliasHasla, entry, protParam);

			/******************************************************/
			// certification
			// loading CertificateChain
			// String certfile ="D:\\eclipse\\Semestr4\\lista6Klucz\\certA.crt"
			// ;
			// System.out.println(certfile);
			// CertificateFactory cf = CertificateFactory.getInstance("X.509");
			// InputStream certstream = fullStream (certfile);
			//
			// Collection c = cf.generateCertificates(certstream) ;
			// Certificate[] certs = new Certificate[c.toArray().length];
			//
			// if (c.size() == 1) {
			// certstream = fullStream (certfile);
			// System.out.println("One certificate, no chain.");
			// Certificate cert = cf.generateCertificate(certstream) ;
			// certs[0] = cert;
			// } else {
			// System.out.println("Certificate chain length: "+c.size());
			// certs = (Certificate[])c.toArray();
			// }
			/******************************************************/

			KeyGenerator keyGen = KeyGenerator.getInstance("ARC4", "BC");
			Key secretKey = keyGen.generateKey();
			keyStore.setKeyEntry(aliasHasla, secretKey,
					hasloDoKeystora.toCharArray(), null);

			// zapisz keyStore
			FileOutputStream fos = new FileOutputStream(sciezkaDoKeyStore);
			keyStore.store(fos, hasloDoKeystora.toCharArray());
			fos.close();
			return sciezkaDoKeyStore;
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	static Key pobierzKlucz(String sciezkaDoKeyStore, String aliasHasla,
			String hasloDoKeystora) {

		try {
			KeyStore ks = KeyStore.getInstance("UBER", "BC");
			InputStream inputStream = new FileInputStream(sciezkaDoKeyStore);
			ks.load(inputStream, hasloDoKeystora.toCharArray());

			return ks.getKey(aliasHasla, hasloDoKeystora.toCharArray());
		} catch (UnrecoverableKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
