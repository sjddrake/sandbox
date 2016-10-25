package uk.co.neo9.apps.accounts.wiptracker;

import uk.co.neo9.utilities.app.UtilitiesApplicationLauncherI;
import uk.co.neo9.utilities.gui.QuickFileChooserAppLauncher;

public class WIPTrackerTotalsGenertorFileChooserLaunch {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		WIPTrackerTotalsGeneratorApp generator = new WIPTrackerTotalsGeneratorApp();
		UtilitiesApplicationLauncherI launcher = new QuickFileChooserAppLauncher();
		launcher.setupLauncher(generator, args);

	}

}
