import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.Key;
import java.security.cert.Certificate;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Collection;

import javax.crypto.KeyGenerator;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
public class AES {

	
	public static Key dodajPobierzKlucz(String sciezkaDoKeyStore,
			String hasloDoKeystora, String aliasHasla) {
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
			 String certfile ="D:\\eclipse\\Semestr4\\lista6Klucz\\certA.crt"
			 ;
			 System.out.println(certfile);
			 CertificateFactory cf = CertificateFactory.getInstance("X.509");
			 InputStream certstream = fullStream (certfile);
			
			 Collection c = cf.generateCertificates(certstream) ;
			 Certificate[] certs = new Certificate[c.toArray().length];
			
			 if (c.size() == 1) {
			 certstream = fullStream (certfile);
			 System.out.println("One certificate, no chain.");
			 Certificate cert = cf.generateCertificate(certstream) ;
			 certs[0] = cert;
			 } else {
			 System.out.println("Certificate chain length: "+c.size());
			 certs = (Certificate[])c.toArray();
			 }
			/******************************************************/

			KeyGenerator keyGen = KeyGenerator.getInstance("ARC4", "BC");
			Key secretKey = keyGen.generateKey();
			keyStore.setKeyEntry(aliasHasla, secretKey,
					hasloDoKeystora.toCharArray(), certs);

			// zapisz keyStore
			FileOutputStream fos = new FileOutputStream(sciezkaDoKeyStore);
			keyStore.store(fos, hasloDoKeystora.toCharArray());
			fos.close();
			return secretKey;
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

	private static InputStream fullStream(String fname) throws IOException {
		FileInputStream fis = new FileInputStream(fname);
		DataInputStream dis = new DataInputStream(fis);
		byte[] bytes = new byte[dis.available()];
		dis.readFully(bytes);
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		return bais;
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

	public static void utowrzURl(String klucz) {

javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier( new javax.net.ssl.HostnameVerifier(){ public boolean verify(String hostname, javax.net.ssl.SSLSession sslSession) { if (hostname.equals("localhost")) { return true; } return false; } });

		try {
			String adresURL = "https://localhost:8443/Logowanie/KluczUzytkownika?klucz="
					+ klucz;
			URL url;
			url = new URL(adresURL);

			QName qname = new QName(
					"https://localhost:8443/Logowanie/KluczUzytkownika",
					"KluczUzytkownika");

			Service service = Service.create(url, qname);
			AESMain hello = service.getPort(AESMain.class);

			System.out.println(adresURL);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// URL adresURL = new
		// URL("https://localhost:8443/Logowanie/KluczUzytkownika?klucz="+
		// klucz);
		// System.out.println(adresURL);
		// URLConnection myURLConnection = adresURL.openConnection();
		// myURLConnection.connect();
		// }
		// catch (MalformedURLException e) {
		// e.printStackTrace();
		// // new URL() failed
		// // ...
		// }
		// catch (IOException e) {
		// e.printStackTrace();
		// // openConnection() failed
		// // ...
		// }
	}

}
