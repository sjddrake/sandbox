package uk.co.neo9.sandbox;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import uk.co.neo9.sandbox.DIRListLine;

import junit.framework.TestCase;

public class DIRListLineObjectMother {

	public static DIRListLine getMP3Line() {
		String line1Text = null;
		line1Text = "H:\\_ Henrick\\Accept\\Staying A Life (disk 2)\\02 - Brave.mp3";
		DIRListLine line1 = new DIRListLine(line1Text);
		return line1;
	}	
	
	
	public static DIRListLine getWMALine() {
		String line1Text = null;
		line1Text = "H:\\_ Henrick\\Accept\\Staying A Life (disk 2)\\02 - Brave.wma";
		DIRListLine line1 = new DIRListLine(line1Text);
		return line1;
	}	
	
	public static DIRListLine getFolderLine() {
		String line1Text = null;
		line1Text = "H:\\_ Henrick\\Accept\\Staying A Life (disk 2)";
		DIRListLine line1 = new DIRListLine(line1Text);
		return line1;
	}		
	
	public static DIRListLine getNonMusicFileLine() {
		String line1Text = null;
		line1Text = "H:\\_ Henrick\\Accept\\Staying A Life (disk 2)\\contents.txt";
		DIRListLine line1 = new DIRListLine(line1Text);
		return line1;
	}	
	
	public static DIRListLine getLine1() {
		String line1Text = null;
		line1Text = "H:\\_ Henrick\\Accept\\Staying A Life (disk 2)\\02 - Brave.mp3";
		DIRListLine line1 = new DIRListLine(line1Text);
		return line1;
	}
	
	public static String getLine1TextToMatch(){
		return "Brave";
	}
	
	public static DIRListLine getLine2() {
		String line2Text = null;
		line2Text = "H:\\_ Henrick\\Accept\\Staying A Life (disk 2)\\03 - Restless And Wild.mp3";
		DIRListLine line2 = new DIRListLine(line2Text);	
		return line2;
	}	

	public static String getLine2TextToMatch(){
		return "RestlessAndWild";
	}	

	
	public static DIRListLine getLine3() {
		String lineText = null;
		lineText = "H:\\_ Henrick\\Scorpions\\Blackout (Remastered)\\08 Ta White.mp3";
		DIRListLine line = new DIRListLine(lineText);	
		return line;
	}	

	public static String getLine3TextToMatch(){
		return "TaWhite";
	}		
	
	public static Vector getDIRList_Line1Line2() {
		Vector lineList = new Vector();
		
		lineList.add(DIRListLineObjectMother.getLine1());
		lineList.add(DIRListLineObjectMother.getLine2());
		
		return lineList;
	}	
	
	public static List getDIRListTextToMatch_Line1Line2() {
		List lineList = new ArrayList();
		
		lineList.add(DIRListLineObjectMother.getLine1TextToMatch());
		lineList.add(DIRListLineObjectMother.getLine2TextToMatch());
		
		return lineList;
	}		
	
	
	public static DIRListLine buildLine(String text) {
		DIRListLine line = new DIRListLine(text);	
		return line;
	}


	public static Vector getDIRList_Line2Line3() {
		Vector lineList = new Vector();
		
		lineList.add(DIRListLineObjectMother.getLine2());
		lineList.add(DIRListLineObjectMother.getLine3());
		
		return lineList;
	}


	public static List getDIRListTextToMatch_Line2Line3() {
		List lineList = new ArrayList();
		
		lineList.add(DIRListLineObjectMother.getLine2TextToMatch());
		lineList.add(DIRListLineObjectMother.getLine3TextToMatch());
		
		return lineList;
	}
}
