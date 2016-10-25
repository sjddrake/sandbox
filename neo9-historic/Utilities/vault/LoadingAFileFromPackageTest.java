

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;


public class LoadingAFileFromPackageTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
	    InputStream stream = LoadingAFileFromPackageTest.class.getClass().getResourceAsStream("/order-example.xml");
	    System.out.println(stream);
	    try {
	    	String txt = convertStreamToString(stream);
	    	System.out.println(txt);

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static String convertStreamToString(InputStream is) throws IOException {

		if (is != null) {
			Writer writer = new StringWriter();

			char[] buffer = new char[1024];
			try {
				Reader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
				int n;
				while ((n = reader.read(buffer)) != -1) {
					writer.write(buffer, 0, n);
				}
			} finally {
				is.close();
			}
			return writer.toString();
		} else {
			return "";
		}
	}
	

}
