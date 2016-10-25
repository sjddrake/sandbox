package com.lloydstsb.chordiant.osp.helpers;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;

import com.lloydstsb.chordiant.osp.messageobjects.PaymentSchedule;

/**
        *******************************************************************************
        * This document is the property of Lloyds TSB Group plc. All rights reserved. *
        *                                                                             *
        * Version	Date			Author			Change Record/Description	      *     
        * =======   ====            ======			=========================         *
        * 1.0       12/05/2003		David Cleaver   Initial                           *
        *                                                                             *
        *******************************************************************************

 */
public class Utilities {
	
	public static InputStream stringToInputStream(String aString) {
		InputStream retVal = null;

		try {
			retVal = new ByteArrayInputStream(aString.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			retVal = new ByteArrayInputStream(aString.getBytes());
		}
	
		return retVal;
	}
	
	public static String removePrefixFromString(String fullString, String dividePoint)
	{
		String retVal = "";
		
		int index = fullString.indexOf(dividePoint);
		
		
		return fullString.substring(index);
	}
	 /**
     * "Flattens" a string by removing all carriage return This uses StringTokenizer
     * and the default set of tokens as documented in the single arguement constructor.
     *
     * @param input a String to remove all whitespace.
     * @return a String that has had all whitespace removed.
     */
    public static String removeReturns( String input )
    {
        StringBuffer result = new StringBuffer();
        if ( input != null )
        {
            StringTokenizer st = new StringTokenizer( input, "\n" );
            while ( st.hasMoreTokens() )
            {
                result.append( st.nextToken() );
            }
        }
        return result.toString();
    }
    
    public static BigDecimal bigDecimalParse(String value) throws Exception{
    		return new BigDecimal(value);
    
    }
    
    
    public static String bigDecimalPrint(BigDecimal value) throws Exception{

		final int scale = 2;
		
		String bigDecString = null;
		StringBuffer sB = null;
		
		String dV = null;
		StringBuffer str = null;
		bigDecString = value.toString();
				
		sB = new StringBuffer(bigDecString);
		
		/*
		sB.toString();
		
		double d = Double.parseDouble(sB.toString());
		d = roundAndScale(d, scale);
		dV = Double.toString(d);
		
		StringBuffer _sB = new StringBuffer(dV.toString());
		
		for(int i=0; i<_sB.length(); i++ ){
			try {		
				if(_sB.charAt(i) == '.') {
					if((_sB.length() - i) <= scale  ) {
						_sB.append('0');
						str = new StringBuffer(_sB.toString());
						_sB = str;
					}

				}
			
			}
			catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		*/
		/*
		*	IN 99 - Wrong Amounts returned
		*	12/08/2003 E. Munyiri
		*	Where amounts equal or exceed 10million, the value returned is in exponential
		*	format which is not recognised by the presentation layer.
		*	Hence the logic to perform the parseDouble and roundAndScale has been removed
		*	and the string value of the Amount is being returned.
		*/
		boolean isDecimalPointFound = false;
		for (int i=sB.length()-1;i>=0;i--)
		{
			if (sB.charAt(i) == '.')
			{
				isDecimalPointFound = true;
				if((sB.length() - i) == scale) 
				{
					sB.append('0');
				}
				break;
			}
		}
		if (!isDecimalPointFound)
		{
			/* 	the toString on the BigDecimal value does not print out the .00 where
			*	the decimal digits are 0, therefore these are appended to the string value.
			*/
			sB.append(".00");		
		}	

		return sB.toString();
	
	}	
		

	public static double roundAndScale(double startValue, int places) {
		
		double multiplier = calculateMultiplier(places);
		double ourNoughtPointFive = 0.50000000001;
		double absoluteValue = Math.abs(startValue);
		double valueToBeFloored = (absoluteValue * multiplier) + ourNoughtPointFive;
		double roundedValue = Math.floor(valueToBeFloored);
		double returnValue = roundedValue / multiplier;
		if (startValue < 0) returnValue = -returnValue; 
		return returnValue;
		
	}
	
	
	private static double calculateMultiplier(int scale) {
		double retval = 1;
		for (int i = 0; i < scale; i++) {
			retval = retval * 10;
		}
		return retval;
	}
	
	public static List sortListSubjectToTermOrder(List list) {
		
		Collections.sort(list, new Comparator() { 		
			public int compare(Object o1, Object o2) {													
				Integer psn1 = new Integer(((PaymentSchedule)o1).getTermOrder());
					
				Integer psn2 = new Integer(((PaymentSchedule)o2).getTermOrder());
														
				return (psn1.compareTo(psn2));
			}

		});

		if(list.size() > 3) {
			int lastIndex = list.size() - 1;
	
			list = list.subList(0, 3);
		}

		return list;

	}
	

}
