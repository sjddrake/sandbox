package uk.co.neo9.apps.accounts.budget.model;

public class DiarisedItem {

	private DiaryPointId pointId;
	
	
	public DiarisedItem() {
		super();
	}
	
	public DiarisedItem(int year, int month) {
		super();
		DiaryPointId point = new DiaryPointId();
		point.setYear(year);
		point.setMonth(month);
		pointId = point;
	}	
	
	
	public DiaryPointId getPointId() {
		return pointId;
	}

	public void setPointId(DiaryPointId pointId) {
		this.pointId = pointId;
	}

	

	
	public int getMonthPointId() {
		return pointId.getMonthPointId();
	}


	   public int getYear() {
	        return pointId.getYear();
	    }


	    public int getMonth() {
	        return pointId.getMonth();
	    }
	
	
}
