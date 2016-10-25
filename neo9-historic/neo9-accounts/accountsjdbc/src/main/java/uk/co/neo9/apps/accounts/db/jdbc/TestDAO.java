package uk.co.neo9.apps.accounts.db.jdbc;

import java.util.List;

public interface TestDAO {
	
	public List<TestModel> getByName(String name);

}
