import java.io.IOException;

import uk.co.neo9.utilities.file.FileServer;

import com.sun.org.apache.bcel.internal.classfile.ClassParser;
import com.sun.org.apache.bcel.internal.classfile.JavaClass;
import com.sun.org.apache.bcel.internal.classfile.Method;
import com.sun.org.apache.bcel.internal.util.Class2HTML;


public class DumpJavaFileElementNames {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		doIt();

	}

	public static void doIt() {
	
		
		String folderToProcess = "Y:/openTournamentCore/target/classes/uk/co/jmcg/opentournament/service/";
		processFolder(folderToProcess);
	}
	
	
	public static void go() {
	
		
		String classFilename = "Y:/openTournamentCore/target/classes/uk/co/jmcg/opentournament/service/TournamentService.class";
		String outputFilename = "D:/Temp (D)/dump.txt";
		try {
			ClassParser parser = new ClassParser(classFilename );
			JavaClass javaClassDetails = parser.parse();
			javaClassDetails.dump(outputFilename);
			
			Method[] methods = javaClassDetails.getMethods();
			for (int i = 0; i < methods.length; i++) {
				Method method = methods[i];
				if (method.isPublic()) {
					System.out.println(method.toString());
					// System.out.println(method.getName());
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	private static void processFolder(String folderName){
		
		// get the file list for the folder
		String[] javaClassFilesNames = FileServer.listFolderContents(folderName,".class");
		
		// for each file, dump out 
		for (int i = 0; i < javaClassFilesNames.length; i++) {
			String fileDetails = javaClassFilesNames[i];
			exportJavaFileMethodNames(folderName+fileDetails);
		}
		
		
	}
	
	
	
	private static void exportJavaFileMethodNames(String fileDetails) {
		
		StringBuilder buff = new StringBuilder();
		buff.append("\n========================================\n");
		buff.append("File: "+fileDetails);
		buff.append("\n\n");
		try {
			ClassParser parser = new ClassParser(fileDetails );
			JavaClass javaClassDetails = parser.parse();
			// javaClassDetails.dump(outputFilename);
			
			Method[] methods = javaClassDetails.getMethods();
			for (int i = 0; i < methods.length; i++) {
				Method method = methods[i];
				if (method.isPublic()) {
					buff.append(method.toString());
					buff.append("\n");
					// System.out.println(method.getName());
				}
			}
			
		} catch (IOException e) {
			System.err.println("Failed to process file: "+fileDetails);
			e.printStackTrace();
		}
		
		buff.append("\n");
		
		System.out.println(buff);
	}


	public static void goToo() {
		
		
		String classFilename = "Y:/openTournamentCore/target/classes/uk/co/jmcg/opentournament/service/TournamentService.class";
		String outputFolder = "D:/Temp (D)/";
		String outputFilename = outputFolder+"dump.txt";
		JavaClass javaClassDetails = null;
		try {
			ClassParser parser = new ClassParser(classFilename );
			javaClassDetails = parser.parse();
			javaClassDetails.dump(outputFilename);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		try {
			Class2HTML reportWriter = new Class2HTML(javaClassDetails, outputFolder);
			// reportWriter.
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
