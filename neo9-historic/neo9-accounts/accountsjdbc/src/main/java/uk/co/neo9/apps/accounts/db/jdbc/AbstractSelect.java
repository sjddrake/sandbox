package uk.co.neo9.apps.accounts.db.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.jdbc.object.MappingSqlQuery;

public class AbstractSelect extends MappingSqlQuery<TestModel>{
	
	public AbstractSelect(DataSource dataSource, String sql) {
		super(dataSource, sql);
	}
	

	@Override
	protected TestModel mapRow(ResultSet rs, int rownum) throws SQLException {

		TestModel model = new TestModel();
		
		model.setId(rs.getInt("testid"));
		model.setName(rs.getString("name"));
		
		return model;
	}

}
