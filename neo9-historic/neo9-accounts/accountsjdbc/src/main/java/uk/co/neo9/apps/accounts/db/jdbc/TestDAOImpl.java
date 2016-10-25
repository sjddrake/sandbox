package uk.co.neo9.apps.accounts.db.jdbc;

import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.SqlUpdate;


public class TestDAOImpl extends JdbcDaoSupport implements TestDAO { //, InitializingBean {
	
	private final static String SELECT_BY_NAME = "select * from test_table where name =?";
	private final static String UPDATE_SQL = "update test_table set name=? where testid=?";
	private final static String DELETE_SQL = "delete test_table set where testid=?";
	
	private SelectByName selectByName;
	private Update updater;
	private Delete deleter;
	
	
	@Override
	protected void initDao() throws Exception {
		super.initDao();
		selectByName = new SelectByName(getDataSource(), SELECT_BY_NAME);
		updater = new Update(getDataSource());
		deleter = new Delete(getDataSource());
	}
	
	
	public List<TestModel> getByName(String name) {
		return selectByName.execute(name);
	}
	
	
	public TestModel update(TestModel model){
		updater.update(new Object[]{model.getName(), new Integer(model.getId())});
		return model;
	}
	

	public void delete(TestModel model){
		deleter.update(model.getId());
	}
	
	private class SelectByName extends AbstractSelect {
		
		public SelectByName(DataSource dataSource, String sql) {
			super(dataSource, sql);
			declareParameter(new SqlParameter(Types.VARCHAR));
		}
		
	}

	
	private class Update extends SqlUpdate {
		
		public Update(DataSource dataSource){
			super(dataSource, UPDATE_SQL);
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.INTEGER));
		}
		
	}
	
	
	private class Delete extends SqlUpdate {
		
		public Delete(DataSource dataSource){
			super(dataSource, DELETE_SQL);
			declareParameter(new SqlParameter(Types.INTEGER));
		}
		
	}
	
}
