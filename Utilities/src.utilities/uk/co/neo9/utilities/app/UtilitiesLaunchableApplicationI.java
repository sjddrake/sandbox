package uk.co.neo9.utilities.app;

public interface UtilitiesLaunchableApplicationI {

	void launch(String[] args);

	UtilitiesAppLaunchDetailsContainer getLaunchDetails(String[] args);

}
