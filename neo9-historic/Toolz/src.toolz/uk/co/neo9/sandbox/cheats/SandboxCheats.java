package uk.co.neo9.sandbox.cheats;

import java.util.Iterator;
import java.util.List;

public class SandboxCheats {

	public static void outputSimpleLines(List endpointLines) {
		for (Iterator iter = endpointLines.iterator(); iter.hasNext();) {
			Object line = iter.next();
			System.out.println(line.toString());
		}
		
	}
	
}
