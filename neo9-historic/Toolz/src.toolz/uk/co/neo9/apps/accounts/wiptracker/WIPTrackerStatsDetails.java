package uk.co.neo9.apps.accounts.wiptracker;

public class WIPTrackerStatsDetails {
	
	private boolean isValid = false;
	private boolean isIncluded = false;
	private int unknownScore = 0;
	private int ignoreScore = 0;
	
	public boolean isIncludedButInvalid() {
		return isIncluded&!isValid;
	}	
	
	public boolean isValid() {
		return isValid;
	}
	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}
	public boolean isIncluded() {
		return isIncluded;
	}
	public void setIncluded(boolean isIncluded) {
		this.isIncluded = isIncluded;
	}
	public int getUnknownScore() {
		return unknownScore;
	}
	public void setUnknownScore(int unknownScore) {
		this.unknownScore = unknownScore;
	}
	public void setUnknownScore() {
		this.unknownScore = 1;
	}
	public int getIgnoreScore() {
		return ignoreScore;
	}
	public void setIgnoreScore(int ignoreScore) {
		this.ignoreScore = ignoreScore;
	}
	public void setIgnoreScore() {
		this.ignoreScore = 1;
	}
	
	
}
