import java.io.*;
import java.net.*;

public class ServletCom {

	public static void main(String[] args) throws Exception {

		HttpURLConnection conn = null;
		DataOutputStream dos = null;

		String exsistingFileName = "D:\\Buda\\semestr4\\AlgorytmyIStrukturyDanych\\wzorki.doc";

		String lineEnd = "";
		String twoHyphens = "--";
		String boundary = "*****";

		int bytesRead, bytesAvailable, bufferSize;

		byte[] buffer;

		int maxBufferSize = 1 * 1024 * 1024;

		String urlString = "https://localhost:8443/Logowanie/PoierzDaneOdKlienta";

		try {
			// ------------------ CLIENT REQUEST
System.out.println("przed utworzeniem strumienia wejsciowego");
			FileInputStream fileInputStream = new FileInputStream(new File(
					exsistingFileName));
			System.out.println("po otworzeniu strumienia " +fileInputStream);
			// open a URL connection to the Servlet

			URL url = new URL(urlString);
			System.out.println("po stworzeniu url "+ url);
			// Open a HTTP connection to the URL

			conn = (HttpURLConnection) url.openConnection();
			System.out.println("po polaczeniu z url "+ conn);
			// Allow Inputs
			conn.setDoInput(true);
			System.out.println("po setDoInput ");
			// Allow Outputs
			conn.setDoOutput(true);
			System.out.println("po setDoOutput(true);");
			// Don't use a cached copy.
			conn.setUseCaches(false);
			System.out.println("po setUseCaches(false)");
			// Use a post method.
			conn.setRequestMethod("POST");
			System.out.println("po setRequestMethod(POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			System.out.println("po setRequestProperty");
			conn.setRequestProperty("Content-Type",
					"multipart/form-data;boundary=" + boundary);
			System.out.println("po setRequestProperty");
			dos = new DataOutputStream(conn.getOutputStream());
			System.out.println("po stworzeniu strumienia dataOutput "+ dos);
			dos.writeBytes(twoHyphens + boundary + lineEnd);
			System.out.println("po zapisaniu do strumienia bytow");
			dos.writeBytes("Content-Disposition: form-data; name=\"upload\";"
					+ " filename=\" " + exsistingFileName + "\"");
			System.out.println("po writeBytes");
			dos.writeBytes(lineEnd);
			System.out.println("po writeBytes(lineEnd)");
			// create a buffer of maximum size

			bytesAvailable = fileInputStream.available();
			System.out.println(" zrobioniu fileInputStream avalilable");
			bufferSize = Math.min(bytesAvailable, maxBufferSize);
			System.out.println("po bufferSiz");
			buffer = new byte[bufferSize];
			System.out.println("po buffer");
			// read file and write it into form...

			bytesRead = fileInputStream.read(buffer, 0, bufferSize);
			System.out.println("w while przes zapisaniem do strumienia");
			while (bytesRead > 0) {
				System.out.println("zapisuje start");
				dos.write(buffer, 0, bufferSize);
				bytesAvailable = fileInputStream.available();
				bufferSize = Math.min(bytesAvailable, maxBufferSize);
				bytesRead = fileInputStream.read(buffer, 0, bufferSize);
				System.out.println("zapisuje end");
			}

			// send multipart form data necesssary after file data...
			System.out.println("po wysjciu z while");
			dos.writeBytes(lineEnd);
			System.out.println("po writeBytes(lineEnd)");
			dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
			System.out.println("przed zamknieciem strumienia");
			// close streams

			fileInputStream.close();
			System.out.println("po zamknieciu  ");
			dos.flush();
			dos.close();

		} catch (MalformedURLException ex) {
			System.out.println("From ServletCom CLIENT REQUEST:" + ex);
		}

		catch (IOException ioe) {
			ioe.printStackTrace();
			System.out.println("From ServletCom CLIENT REQUEST:" + ioe);
		}

		// ------------------ read the SERVER RESPONSE

		// try
		// {
		// inStream = new DataInputStream ( conn.getInputStream() );
		// String str;
		// while (( str = inStream.readLine())
		// This comment continued in next comment...
	}
}
