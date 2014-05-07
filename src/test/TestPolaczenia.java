package test;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;


public class TestPolaczenia {
	static String urlToConnect = "https://localhost:8443/Logowanie/";
	static String paramToSend = "fubar";
	static File fileToUpload = new File("D:\\Programy\\eclipseEE\\wokspace\\Logowanie\\plikTestowy.txt");
	static String boundary;

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
		boundary = Long.toHexString(System.currentTimeMillis()); // Just
																	// generate
																	// some
																	// unique
																	// random
																	// value.
		URLConnection connection;
		try {
			connection = new URL(urlToConnect).openConnection();
			connection.setDoOutput(true); // This sets request method to POST.
			connection.setRequestProperty("Content-Type",
					"multipart/form-data; boundary=" + boundary);
			PrintWriter writer = null;
			try {
				writer = new PrintWriter(new OutputStreamWriter(
						connection.getOutputStream(), "UTF-8"));

				writer.println("--" + boundary);
				writer.println("Content-Disposition: form-data; name=\"paramToSend\"");
				writer.println("Content-Type: text/plain; charset=UTF-8");
				writer.println();
				writer.println(paramToSend);

				writer.println("--" + boundary);
				writer.println("Content-Disposition: form-data; name=\"fileToUpload\"; filename=\"file.txt\"");
				writer.println("Content-Type: text/plain; charset=UTF-8");
				writer.println();
				BufferedReader reader = null;
				try {
					reader = new BufferedReader(new InputStreamReader(
							new FileInputStream(fileToUpload), "UTF-8"));
					for (String line; (line = reader.readLine()) != null;) {
						writer.println(line);
					}

				} finally {
					if (reader != null)
						try {
							reader.close();
						} catch (IOException logOrIgnore) {
						}
				}

				writer.println("--" + boundary + "--");
			} finally {
				if (writer != null)
					writer.close();
			}

//			// Connection is lazily executed whenever you request any status.
//			int responseCode = ((HttpsURLConnection) connection)
//					.getResponseCode();
//			System.out.println(responseCode); // Should be 200
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
