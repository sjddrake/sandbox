package uk.co.neo9.utilities;

import uk.co.neo9.utilities.app.UtilitiesAppLaunchDetailsContainer;
import uk.co.neo9.utilities.app.UtilitiesApplicationLauncherAdapter;
import uk.co.neo9.utilities.app.UtilitiesApplicationLauncherI;
import uk.co.neo9.utilities.app.UtilitiesLaunchableApplicationI;

public class UtilitiesLaunchableTestApp implements
		UtilitiesLaunchableApplicationI {

	
	public static void main(String[] args) {
		
		// use the application launcher
		
		UtilitiesApplicationLauncherI launcher = new UtilitiesApplicationLauncherAdapter();
		UtilitiesLaunchableTestApp app = new UtilitiesLaunchableTestApp();
		launcher.setupLauncher(app,args);
	
	}

	public void launch(String[] args) {
		
		System.out.println("UtilitiesLaunchableTestApp launched with the following args:");
		if (args == null) {
			System.out.println("NULL args");
		} else {
			System.out.println(args.length+" arguments:");
			for (int i = 0; i < args.length; i++) {
				String arg = args[i];
				System.out.println("arg"+i+": "+arg);
				
			}
		}
	}

	public UtilitiesAppLaunchDetailsContainer getLaunchDetails(String[] args) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
