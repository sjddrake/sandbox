   import java.io.*;
   import java.util.zip.*;

public class ZipCreateExample{

public static void main(String[] args)  throws Exception  {

	final String root = "/Users/simondrake/Temp/";
	
// input file 
       FileInputStream in = new FileInputStream(root+"zipmeup.txt");
       
        // out put file 
       ZipOutputStream out = new ZipOutputStream(new FileOutputStream(root+"tmp.zip"));
       
         // name the file inside the zip  file 
         out.putNextEntry(new ZipEntry("zippedup.txt")); 

            byte[] b = new byte[1024];

        int count;

        while ((count = in.read(b)) > 0) {
            System.out.println();

         out.write(b, 0, count);
        }
        out.close();
        in.close();
        }
    }