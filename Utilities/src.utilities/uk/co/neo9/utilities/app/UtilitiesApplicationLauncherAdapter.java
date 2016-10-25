package uk.co.neo9.utilities.app;

public class UtilitiesApplicationLauncherAdapter implements UtilitiesApplicationLauncherI{

	public void setupLauncher(UtilitiesLaunchableApplicationI app, String[] args) {
		
		app.launch(args);
		
	}


}
