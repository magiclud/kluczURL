import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class AESMain {

	public static void main(String[] args) throws InvalidKeyException,
			NoSuchAlgorithmException, KeyStoreException, CertificateException,
			IOException, UnrecoverableKeyException, NoSuchPaddingException,
			IllegalBlockSizeException, BadPaddingException,
			InvalidAlgorithmParameterException {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

		String nazwaUzytkownika = "Wiktoria";
		if (args.length != 0) {
			nazwaUzytkownika = args[0];
			System.out
					.println("nazwaUztkownika (argument) " + nazwaUzytkownika);
		}

		String hasloDoKeystora = "ala ma kota";
		String aliasHasla = nazwaUzytkownika;
		String sciezkaDoKeyStore = "D:\\eclipse\\Semestr4\\lista6Klucz\\keyStore.ks";

		Key key = AES.dodajPobierzKlucz(sciezkaDoKeyStore, hasloDoKeystora,
				new String(aliasHasla));
		String kluczUzytkownika = key.toString();
		String klasaKlucza[] = kluczUzytkownika.split("@");
		String klucz = klasaKlucza[1];
		
		System.out.println(klucz);
		
		AES.utowrzURl(klucz);
		
	}
}
