package uk.co.neo9.apps.accounts.budget.load;

public class BudgetLoadParams {
	
	private String fileNameAndPath;

	public BudgetLoadParams(String fileNameAndPath) {
		super();
		this.fileNameAndPath = fileNameAndPath;
	}

	public String getFileNameAndPath() {
		return fileNameAndPath;
	}

	public void setFileNameAndPath(String fileNameAndPath) {
		this.fileNameAndPath = fileNameAndPath;
	}
	
	

}
