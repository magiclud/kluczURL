import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.junit.Test;


public class TestUruchomURL {

	@Test
	public void test() {
		String klucz = "123dfk2";
	//	javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier( new javax.net.ssl.HostnameVerifier(){ public boolean verify(String hostname, javax.net.ssl.SSLSession sslSession) { if (hostname.equals("localhost")) { return true; } return false; } });

		try {
			String adresURL = "https://localhost:8443/Logowanie/KluczUzytkownika?klucz="
					+ klucz;
			URL url;
			url = new URL(adresURL);

			QName qname = new QName(
					"https://localhost:8443/Logowanie/KluczUzytkownika",
					"KluczUzytkownika");

			Service service = Service.create(url, qname);
			AESMain startWeb = service.getPort(AESMain.class);

			System.out.println(adresURL);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
