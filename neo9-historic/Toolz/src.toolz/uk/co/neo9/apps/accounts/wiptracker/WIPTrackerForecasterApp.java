package uk.co.neo9.apps.accounts.wiptracker;

public class WIPTrackerForecasterApp {

	private WIPTrackerForecasterComponent engine = new WIPTrackerForecasterComponent();
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		WIPTrackerForecasterApp forecaster = new WIPTrackerForecasterApp();
		forecaster.go(args);

	}

	
	protected void go(String[] mainArgs){

		engine.go(mainArgs);
		
	}		
	
}
