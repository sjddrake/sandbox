/*
 * Created on 05-Oct-07
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package uk.co.neo9.sandbox.coautils;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Stack;

import uk.co.neo9.utilities.CommonConstants;

/**
 * @author Simon
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class LogBuffer {

	ArrayList buckets = new ArrayList();

	public static void main(String[] args) {
		
		LogBuffer b = new LogBuffer();
		b.test();
	}


	public void test() {

		
	}
	
	
	public boolean addLogLine(LogLine logLine){

		// get the top bucket
		Bucket topBucket = null;
		if (buckets.isEmpty()){
			topBucket = new Bucket(0);
			buckets.add(topBucket);
		} else {
			topBucket =(Bucket)buckets.get(buckets.size()-1);
		}

		// try adding the line to this bucket
		int addResult = topBucket.addLogLine(logLine,false);

		// if it didn't add ok, then cater accordingly...
		if (Bucket.RESULT_ADDED != addResult) {
			
			// if the top bucket is full, create a new bucket and stick it in there
			if (Bucket.RESULT_OVER == addResult) {
				Bucket newTopBucket = new Bucket(buckets.size());
				newTopBucket.addLogLine(logLine,true);
				buckets.add(newTopBucket);
				
			} else {	
				// else, if the line comes before this bucket, force it into the bucket below
				boolean notDone = true;
				int nextBucketIndex = buckets.size()-2; 
				if (nextBucketIndex < 0){
					notDone = false;
				}
				while (notDone) {
					
					Bucket nextBucket = (Bucket)buckets.get(nextBucketIndex);
					int nextAddResult = nextBucket.addLogLine(logLine,true);
					
					// with the forced add, if the line is 'OVER' it still
					// gets added (as its assumed under the higher bucket), 
					// but if its UNDER it doesn't get added so we can try 
					// adding it to the next bucket down
					if (Bucket.RESULT_UNDER == nextAddResult){
						nextBucketIndex--;
						if (nextBucketIndex < 0){
							notDone = false;
						}
					} else {
						// line was added sucessfuly
						notDone = false;
					}
				}
				//ScoobyDo there's a possibility of falling out underneath but it should never happen!
			}
		}

		return true;
	}	
	

	public void cleanBuckets(){
		for (Iterator iter = this.buckets.iterator(); iter.hasNext();) {
			Bucket bucket = (Bucket) iter.next();
			bucket.cleanBucket();				
		}
	}
	
	
	public void report (){
		StringBuffer buf = new StringBuffer();
			
		buf.append("---- BUFFER REPORT ----");
		buf.append(CommonConstants.NEWLINE);
				
		for (Iterator iter = this.buckets.iterator(); iter.hasNext();) {
			Bucket bucket = (Bucket) iter.next();
			buf.append(bucket.report());	
			buf.append(CommonConstants.NEWLINE);	
			
		}


		buf.append("- - - - - - - - - - - - - - - ");	
		buf.append(CommonConstants.NEWLINE);	
			
		System.out.println(buf.toString());		
	}



	public ArrayList getResidue(boolean exportAll){
	
		ArrayList residue = new ArrayList();
		int noOfBuckets = buckets.size();
		for (int i = noOfBuckets-1; i > -1; i--) {
			Bucket bucket = (Bucket) buckets.get(i);
			if (bucket.isReadyToEmpty() || exportAll){
				residue.addAll(bucket.lines);
				bucket.empty();
				buckets.remove(i);
			}
		}
		return residue;
	}

	
//
//	------------------------------ BUCKET CLASS -------------------------
//
	
	private class Bucket {
		
		public final static int RESULT_ADDED = 0;
		public final static int RESULT_OVER = 1;
		public final static int RESULT_UNDER = -1;
		
		private final int MAX_BUFFER_SIZE = 100;
		private final int BUFFER_HIGH_WATER = 600;
		private final int BUFFER_LOW_WATER = 100;
				
		ArrayList lines = new ArrayList();
		private boolean isDirty = false;
		private int ID = -1;
		private long timeCleaned = 0;
		
		
		private Bucket() {} // supposed to be hidden! 


		public Bucket(int bucketID) {
			ID = bucketID;
		}		
				
		public int addLogLine(LogLine logLine, boolean forcedAdd){
		
			int finalPosition = -1;
			int noOfLinesInBucket = lines.size();
			
			// first of all, find the place in the list that the line belongs in
			if (noOfLinesInBucket == 0) {
				finalPosition = 0;
			} else {
				for (int i = 0; (i < noOfLinesInBucket && finalPosition == -1); i++) {
					LogLine thisLine = (LogLine)lines.get(i);
					int result = logLine.timeStamp.compareTo(thisLine.timeStamp);
				//	int result = thisLine.timeStamp.compareTo(logLine.timeStamp);
					if (result < 0){
						// move on 
					} else {
						// record the position for this line to go in
						finalPosition = i;
					}
				}
			}
			

			
			// now we have the position, decide what to do
			int additionResult = RESULT_ADDED;
			if (finalPosition == -1){
				// hah! the line is 'UNDER' all the lines in the bucket!
				// simply return the 'UNDER' code
				additionResult = RESULT_UNDER;
			} else {
				// now, depending on whether this is a force or not, add it into the position
				if (forcedAdd){
					// always add in a forced add
					lines.add(finalPosition,logLine);
					isDirty = true;
					additionResult = RESULT_ADDED;
				} else {
					if (finalPosition == 0 ){
						// can add on top as long as the bucket isn't full
						if (noOfLinesInBucket < BUFFER_LOW_WATER){
							lines.add(finalPosition,logLine);
							isDirty = true;
							additionResult = RESULT_ADDED;
						} else {
							additionResult = RESULT_OVER;
						}
					} else {
						// always add if NOT on top
						lines.add(finalPosition,logLine);
						isDirty = true;
						additionResult = RESULT_ADDED;						
					}
				}
			}


			/* DEBUG */
			if (noOfLinesInBucket > BUFFER_HIGH_WATER){
				System.err.println("high water mark passed for bucket - "+ ID);
			}

			return additionResult;
		}		
		
		
		public boolean isBucketDirty(){
			timeCleaned = 0;
			return isDirty;
		}

		public void cleanBucket(){
			if (isDirty){
				isDirty = false;
				timeCleaned = new Date().getTime();
			}
		}
	
	
		public boolean isReadyToEmpty(){
			boolean ready = false;
			long now = new Date().getTime();
			if ((timeCleaned != 0) && (now-timeCleaned > 200)){
				ready = true;
			}
			return ready;
		}	
		
		public int getID(){
			return ID;
		}

		
		public void empty() {
			if (this.lines != null) {
				this.lines.clear();
			}
			boolean isDirty = false;
			long timeCleaned = 0;
		}
		
		
		public String report(){
			
			StringBuffer buf = new StringBuffer();
			
			buf.append("Bucket - ");
			buf.append(ID);
			buf.append(CommonConstants.NEWLINE);
			
			buf.append("IsDirty = ");
			buf.append(isDirty);
			buf.append(CommonConstants.NEWLINE);	

			buf.append("Ready to archive = ");
			buf.append(isReadyToEmpty());
			buf.append(CommonConstants.NEWLINE);	

			buf.append("NoOfLines = ");
			buf.append(lines.size());
			buf.append(CommonConstants.NEWLINE);				
			
			return buf.toString();
			
		}
		
	}
	
}
