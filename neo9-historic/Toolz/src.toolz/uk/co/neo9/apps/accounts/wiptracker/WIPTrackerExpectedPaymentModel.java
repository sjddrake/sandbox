package uk.co.neo9.apps.accounts.wiptracker;

public class WIPTrackerExpectedPaymentModel extends WIPTrackerTotalsModel {
	

	public String getActualPaymentId() {
		return super.getCustomField1();
	}

	public void setActualPaymentId(String actualPaymentId) {
		super.setCustomField1(actualPaymentId);
	}

}
