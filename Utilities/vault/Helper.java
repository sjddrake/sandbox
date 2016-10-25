//
// Helper Super class.
//
// 20040615: JSG: Creation.
//






// Package statement.
package com.lloydstsb.chordiant.osp.helpers;






// Import statements.
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.StringTokenizer;

import javax.ejb.CreateException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.ValidationException;

import com.chordiant.service.ServiceException;
import com.chordiant.service.clientagent.ClientAgentHelper;
import com.lloydstsb.chordiant.osp.clientAgents.OrganisationDomainServiceClientAgent;
import com.lloydstsb.chordiant.osp.exceptions.InterComponentConnectionException;
import com.lloydstsb.chordiant.osp.exceptions.MissingParameterValuesException;
import com.lloydstsb.chordiant.osp.factories.OSPExceptionFactory;
import com.lloydstsb.chordiant.osp.messageobjects.FulfilPeriodicPaymentChangesForSTP;
import com.lloydstsb.crm.framework.ejb.HostGatewayService;
import com.lloydstsb.crm.framework.ejb.HostGatewayServiceHome;
import com.lloydstsb.framework.ea.loggingservice.LoggingService;
import com.lloydstsb.framework.ea.transactioncontext.TransactionContext;

// Class definition. 
public class Helper
{
	// ATTRIBUTES.
	private OrganisationDomainServiceClientAgent service_IFCodosClientAgent	= null;
	protected HostGatewayService hostGatewayService			= null;
	final static LoggingService logger = new LoggingService(Helper.class);
	
	
	// CONSTRUCTOR.
	public Helper() throws InterComponentConnectionException
	{
		super();
		try{
			service_IFCodosClientAgent = (OrganisationDomainServiceClientAgent)ClientAgentHelper.getClientAgent(OrganisationDomainServiceClientAgent.CLASS_NAME);
		}
		catch(ServiceException sE){
			throw OSPExceptionFactory.getInterComponentConnectionException("ERROR - Cannot set up Codos Client Agent.", sE);
		}
	}




	// METHODS.
	/**
	 * getReferenceToHostgatewayBean.
	 * Sets up a reference to the Hostgateway EJB.
	 * @return void
	 */
	protected HostGatewayService getReferenceToHostGatewayBean(TransactionContext pTransactionContext) throws InterComponentConnectionException, MissingParameterValuesException
	{
		// Local constants and variables.
		final String METHOD_NAME = "getReferenceToHostgatewayBean";
		HostGatewayServiceHome hostGatewayServiceHome = null;

		// Logging.
		if(logger.isEntryExitEnabled()){
			logger.entryExit(("Entered : " + METHOD_NAME));
		}

		// Validate input parameters.
		if (pTransactionContext == null){
			throw OSPExceptionFactory.getMissingParameterValuesException("Parameter pTransactionContext is null.");
		}

		// Get the bean reference.
		if (hostGatewayService == null){
			
			// Home not found either so create one
			InitialContext initialContext = null;
			Object reference = null;

			// get the home interface
			try{
				initialContext = new InitialContext(); 
				//reference = initialContext.lookup("HostGatewayServiceHome");
				reference = initialContext.lookup("ejb/com/lloydstsb/crm/framework/ejb/HostGatewayServiceHome");
				
				hostGatewayServiceHome = (HostGatewayServiceHome) PortableRemoteObject.narrow(reference, HostGatewayServiceHome.class );
				if(hostGatewayServiceHome != null){
					hostGatewayService = hostGatewayServiceHome.create();
				}
				else throw new Exception("Service Home EJB lookup returned null");
			}
			catch(NamingException ne){
				throw OSPExceptionFactory.getInterComponentConnectionException("ERROR - Naming Exception.", ne);
			}
			catch(ClassCastException cce){
				throw OSPExceptionFactory.getInterComponentConnectionException("ERROR - Class Cast Exception.", cce);
			}
			catch(CreateException ce){
				throw OSPExceptionFactory.getInterComponentConnectionException("ERROR - EJB Create Exception.", ce);
			}
			catch(RemoteException re){
				throw OSPExceptionFactory.getInterComponentConnectionException("ERROR - Remote Exception.", re);
			}
			catch(Exception e){
				throw OSPExceptionFactory.getInterComponentConnectionException("ERROR - getting reference to hostgateway EJB.", e);
			}
		}
		
		// Logging.
		if(logger.isEntryExitEnabled()){
			logger.entryExit(("Exited : " + METHOD_NAME));
		}
		
		// Return the result!
		return hostGatewayService;
	}
	
	
	
	
	/**
	 * "Flattens" a string by removing all carriage return This uses StringTokenizer
	 * and the default set of tokens as documented in the single arguement constructor.
	 *
	 * @param pInput a String to remove all whitespace.
	 * @return a String that has had all whitespace removed.
	 */	
	protected static String removeReturns(String pInput) throws MissingParameterValuesException
	{
		StringBuffer result = new StringBuffer();
		if (pInput == null){
			throw OSPExceptionFactory.getMissingParameterValuesException("Parameter pInput is null.");
		}
		StringTokenizer st = new StringTokenizer( pInput, "\n" );
		while ( st.hasMoreTokens()){
			result.append( st.nextToken() );
		}
		return result.toString();
	}


	

	// Formats an amount containing leading zeroes and no decimal place to
	// strip the zeroes and insert a decimal point.	
	protected String formatAmount(String pUnformattedAmount) throws MissingParameterValuesException
	{
		// Validate.
		if ((pUnformattedAmount == null) || (pUnformattedAmount.trim().length() == 0)){		
			throw OSPExceptionFactory.getMissingParameterValuesException("Parameter pUnformattedAmount is null or zero-length.");
		}
		
		// Format the amount.
		StringBuffer stringBuffer = new StringBuffer(pUnformattedAmount);
		stringBuffer.insert(stringBuffer.length() - 2, '.');
		BigDecimal bigDecimal = new BigDecimal(stringBuffer.toString()); 
		
		// Return the result.
		return bigDecimal.toString();
	}
	
	
	
	
	// Format the double to string to send to CBS.
	protected String doubleToString(double pDouble)
	{
		BigDecimal formatted = new BigDecimal(pDouble * 100);
		formatted = formatted.setScale(0, BigDecimal.ROUND_HALF_UP);
		return formatted.toString();
	}
	
	
	
	
	// Get the CBS application group number for the channel out let identifier, (sort code).
	protected String getCBSApplicationGroupNumber(String userName, String authenticationToken, TransactionContext context) throws MissingParameterValuesException, InterComponentConnectionException
	{
		String applicationGroupNumber = null;
		// No validation in this method as it is a pass through method.
		try{
			applicationGroupNumber = new Integer(service_IFCodosClientAgent.getCBSApplicationGroupForSortCode(userName, authenticationToken, context, context.getChannelOutletIdentifier())).toString();
		}
		catch(MissingParameterValuesException mPE){
			throw mPE;
		}
		catch(InterComponentConnectionException iCCE){
			throw iCCE;
		}
		return "0" + applicationGroupNumber;
	}
	
	
	
	
	// Validate Input.
	protected void validateInput(String userName, String authenticationToken, TransactionContext context, Object pRequest) throws InterComponentConnectionException, MissingParameterValuesException
	{
		// Local variables.
		JAXBContext inputJAXBContext	= null;
		
		// Validate the input parameters.
		if (userName == null){
			throw OSPExceptionFactory.getMissingParameterValuesException("Parameter 1, userName is null.");
		}
		if (authenticationToken == null){
			throw OSPExceptionFactory.getMissingParameterValuesException("Parameter 2, authenticationToken name is null.");
		}
		if (context == null){
			throw OSPExceptionFactory.getMissingParameterValuesException("Parameter 3, context is null.");
		}
		if (pRequest == null){
			throw OSPExceptionFactory.getMissingParameterValuesException("Parameter 4, pRequest is null.");
		}
		if (!(pRequest instanceof FulfilPeriodicPaymentChangesForSTP)){
			try{
				inputJAXBContext = JAXBContext.newInstance("com.lloydstsb.chordiant.osp.messageobjects");
				if (!inputJAXBContext.createValidator().validate(pRequest)){
					throw OSPExceptionFactory.getMissingParameterValuesException("Parameter 4, pRequest is invalid.");
				}
			}
			catch(ValidationException jaxbE){
				throw OSPExceptionFactory.getMissingParameterValuesException("Parameter 4, pRequest is invalid.");
			}		
			catch(JAXBException jaxbE){
				throw new InterComponentConnectionException("JAXB", jaxbE);
			}
		}
	}
}