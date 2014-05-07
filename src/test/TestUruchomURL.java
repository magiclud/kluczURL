package test;
import static org.junit.Assert.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.junit.Test;

public class TestUruchomURL {

	// @Test
	// public void test() {
	// String klucz = "123dfk2";
	// // javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier( new
	// javax.net.ssl.HostnameVerifier(){ public boolean verify(String hostname,
	// javax.net.ssl.SSLSession sslSession) { if (hostname.equals("localhost"))
	// { return true; } return false; } });
	//
	// try {
	// // String adresURL =
	// "https://localhost:8443/Logowanie/KluczUzytkownika?klucz="
	// // + klucz;
	// String adresURL = "https://localhost:8443/";
	// URL myURL = new URL(adresURL);
	// URLConnection myURLConnection = myURL.openConnection();
	// myURLConnection.connect();
	// }
	// catch (MalformedURLException e) {
	// e.printStackTrace();
	//
	// }
	// catch (IOException e) {
	// // openConnection() failed
	// e.printStackTrace();
	// // ...
	// }
	static {
		// for localhost testing only
		javax.net.ssl.HttpsURLConnection
				.setDefaultHostnameVerifier(new javax.net.ssl.HostnameVerifier() {

					@Override
					public boolean verify(String hostname,
							javax.net.ssl.SSLSession sslSession) {
						if (hostname.equals("localhost")) {
							return true;
						}
						return false;
					}
				});
	}

	public static void main(String[] args) {
		try {
			URL myURL = new URL("https://localhost:8443/");
			URLConnection myURLConnection = myURL.openConnection();
			myURLConnection.connect();
//			myURLConnection.setDoOutput(true);
//			myURLConnection.setDoInput(true);
//			myURLConnection.setAllowUserInteraction(false);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("ok");
	}
}

// }
