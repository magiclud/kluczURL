import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Socket;
import java.security.KeyStore;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public class MainClass {
	public static void main(String args[]) throws Exception {
		int i, j, len, ci_nonanon;
		int N = 50, L = 100, R = 2;

		String host = "192.168.0.104";
		int port = 8004;
		PrintWriter out = new PrintWriter(System.out);
		SSLContext sslContext;

		sslContext = SSLContext.getInstance("SSL");
		sslContext.init(null, null, null);
		SSLContext.getDefault();

		try {

			out.println("ClientJsse: SSL client context created.");
			SSLSocketFactory factory = sslContext.getSocketFactory();
			String[] cipher_suites = sslContext.getSocketFactory()
					.getSupportedCipherSuites();

		//	out.println("ClientJsse: enabled cipher suites");
			for (i = 0, ci_nonanon = 0; i < cipher_suites.length; i++) {
				if (cipher_suites[i].indexOf("_anon_") < 0) {
					ci_nonanon++;
				}
		//		out.println("   " + cipher_suites[i]);
			}
			out.flush();
			SSLSocket ssl_sock;
			OutputStream ostr;
			InputStream istr;
			long t;
			String[] ecs = new String[1];
			byte buffer[] = new byte[L];

			out.println("\n ");
			for (int csi = 0; csi < ci_nonanon; csi++) {
				// Remove anonymous cipher suites.
				// TrustManager requires a personal certificate.
				ecs[0] = cipher_suites[csi];
				if (ecs[0].indexOf("_anon_") >= 0) {
					continue;
				}
				for (int n = 0; n < R; n++) {
					t = System.currentTimeMillis();
					try {
						ssl_sock = (SSLSocket) factory.createSocket(host, port);
						ssl_sock.setEnabledCipherSuites(ecs);

						ssl_sock.startHandshake();
						SSLSession session = ssl_sock.getSession();
						out.println(" ");
						out.println("\nClientJsse: SSL connection established");
						out.println("   cipher suite:       "
								+ session.getCipherSuite());
					} catch (Exception se) {
						//System.err.println(se);
						out.println(" ");
						out.println("\nClientJsse: can't connect using: "
								+ cipher_suites[csi] + "\n" + se);
						break;
					}

					if (L > 0) {
						istr = ssl_sock.getInputStream();
						ostr = ssl_sock.getOutputStream();
						for (j = 0; j < N; j++) {
							if ((j == N - 1) && (n == R - 1)
									&& (csi == ci_nonanon - 1))
								buffer[0] = (byte) -1;
							ostr.write(buffer, 0, L);
							for (len = 0;;) {
								try {
									if ((i = istr.read(buffer, len, L - len)) == -1) {
										out.println("ClientJsse: SSL connection dropped by partner.");
										istr.close();
										return;
									}
									// out.println("ClientJsse: " + i +
									// " bytes received.");
									if ((len += i) == L)
										break;
								} catch (InterruptedIOException e) {
									// out.println("ClientJsse: timeout.\n");
								}
							}
						}
					}
					out.println("Messages = " + N * 2 + "; Time = "
							+ (System.currentTimeMillis() - t));
					ssl_sock.close();
					out.println("ClientJsse: SSL connection closed.");
					out.flush();
				}
			}

			Thread.sleep(1500);

			// Example using plain sockets
			if (L > 0) {
				Socket sock = new Socket(host, port);
				out.println(" ");
				out.println("\nClientJsse: Plain Socket connection established.");
				istr = sock.getInputStream();
				ostr = sock.getOutputStream();
				t = System.currentTimeMillis();
				for (j = 0; j < N; j++) {
					ostr.write(buffer, 0, L);
					for (len = 0;;) {
						if ((i = istr.read(buffer, len, L - len)) == -1) {
							out.println("ClientJsse: connection dropped by partner.");
							return;
						}
						if ((len += i) == L)
							break;
					}
				}
				out.println("Messages = " + N * 2 + "; Time = "
						+ (System.currentTimeMillis() - t));
				sock.close();
				out.println("ClientJsse: Plain Socket connection closed.");
				Thread.sleep(1500);
			}
		} catch (Exception e) {
			out.println(e);
		}
		out.println(" ");
		out.println("ClientJsse: terminated.");
		out.flush();
	}// end main(...)
}// end class