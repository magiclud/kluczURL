import java.net.*;
import java.io.*;

public class HTTP_Post {
	static URL u;

	public static void main(String args[]) {
		String s = URLEncoder.encode("A Test string to send to a servlet");

		HTTP_Post post = new HTTP_Post();
		try {
			post.u = new URL(
					"https://localhost:8443/Logowanie/SpecjalneDaneDlaPlayera");
			// Open the connection and prepare to POST
			URLConnection uc = u.openConnection();
			uc.setDoOutput(true);
			uc.setDoInput(true);
			uc.setAllowUserInteraction(false);
			DataInputStream clientData = new DataInputStream(
					uc.getInputStream());
			int ileLogowan = clientData.readInt();
			System.out.println(ileLogowan);
			String uzytkownik = clientData.readLine();
			System.out.println(uzytkownik);
			String[] daneOdServera = uzytkownik.split("#");
			uzytkownik = daneOdServera[0];
			String hasloDoKeyStorea = daneOdServera[1];
			System.out.println("hasloDokeystorea " + daneOdServera[1]
					+ "  ,  uzytkownik    " + daneOdServera[0]);

			// todo if ilosc zalogowan jest mniejsza = 1 to wygeneruj klucz i
			// keystorea i wyslij keystorea // a jesli nie to wyslij komunikat
			clientData.close();
			System.out.println("zilosc zalogowan " + ileLogowan);

			if (ileLogowan <= 1) {
				AES generujKlucz = new AES(uzytkownik, hasloDoKeyStorea);
				String sciezkaDoKeystorea = generujKlucz
						.stworzKeystoreZkluczem();
				post.u = new URL(
						"https://localhost:8443/Logowanie/PobierzDaneOdKlienta");
				System.out
						.println("powiazanie post z url, jestem przed nawiazaniem polaczenia");
				// Open the connection and prepare to POST
				uc = u.openConnection();
				System.out.println("nawiazanie polaczenia ");
				uc.setDoOutput(true);
				uc.setDoInput(true);
				uc.setAllowUserInteraction(false);
				System.out
						.println("otworzenie strumieni: setDoOutput(true), setDoInput(true) , setAllowUserInteraction(false)");

				File myFile = new File(sciezkaDoKeystorea);
				byte[] mybytearray = new byte[(int) myFile.length()];
				FileInputStream fis = new FileInputStream(myFile);
				BufferedInputStream bis = new BufferedInputStream(fis);
				DataInputStream dis = new DataInputStream(bis);
				dis.readFully(mybytearray, 0, mybytearray.length);

				// Sending file name and file size to the server
				DataOutputStream dstream = new DataOutputStream(
						uc.getOutputStream());
				System.out.println("przed wyslaniem Stringa ");
				// The POST line
				dstream.writeUTF(myFile.getName());
				dstream.writeLong(mybytearray.length);
				dstream.write(mybytearray, 0, mybytearray.length);
				dstream.flush();
				System.out.println("!!!!!File " + sciezkaDoKeystorea
						+ " sent to Server.");
				dstream.close();
				System.out
						.println("po zamknieciu DataOutputStream(uc.getOutputStream())");
				// Read Response
				InputStream in = uc.getInputStream();
				System.out
						.println("otworzono strunien do zczytywania odpowiedzi");

				int x;
				System.out.println("~~~~Przed petla while zczytujaca odpwiedz");
				while ((x = in.read()) != -1) {
					System.out.write(x);
				}
				System.out.println("~~~~Po petla while zczytujacej odpwiedz");
				in.close();
				System.out.println("zamknieto strumien wejsciowy ");

				// BufferedReader r = new BufferedReader(new
				// InputStreamReader(in));
				// StringBuffer buf = new StringBuffer();
				// String line;
				// System.out
				// .println("otworzenie strumienia string bbuffer -> input;  przed petla while ");
				// while ((line = r.readLine()) != null) {
				// System.out
				// .println("~~~~w petli~while~~zczytujacej odpowiedz z String buffer ");
				// buf.append(line);
				// }
			} else {
				System.out.println("jestem w else");
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}