package test;
import java.net.*;
import java.io.*;

public class HTTP_Post
{
    static URL u;
    public static void main(String args[])
    {
	String s=URLEncoder.encode("A Test string to send to a servlet");

	try
	{
	    HTTP_Post post = new HTTP_Post();
	    post.u = new URL("https://localhost:8443/Logowanie/wyszukajUtworu.jsp");
      
	    // Open the connection and prepare to POST
	    URLConnection uc = u.openConnection();
	    uc.setDoOutput(true);
	    uc.setDoInput(true);
	    uc.setAllowUserInteraction(false);

	    DataOutputStream dstream = new DataOutputStream(uc.getOutputStream());
      
	    // The POST line
	    dstream.writeBytes(s);
	    dstream.close();

	    // Read Response
	    InputStream in = uc.getInputStream();
	    int x;
	    while ( (x = in.read()) != -1)
	    {
		System.out.write(x);
	    }
	    in.close();

	    BufferedReader r = new BufferedReader(new InputStreamReader(in));
	    StringBuffer buf = new StringBuffer();
	    String line;
	    while ((line = r.readLine())!=null) {
		buf.append(line);
	    }

	}
	catch (IOException e)
	{ 
	    e.printStackTrace();	// should do real exception handling
	}
    }
}