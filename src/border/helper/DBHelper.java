package border.helper;


import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class DBHelper {
	JdbcTemplate jt;
	
	public static void TruncateDB(){
		// TODO: dataSource should not be fixed string, it should be stored somewhere in config files
		// get the datasource bean
		DataSource ds = (DataSource) ApplicationContextProvider.getContext().getBean("dataSource");
		JdbcTemplate jt = new JdbcTemplate(ds);
		// kill all the data and restart autoincrements
		jt.execute(" TRUNCATE SCHEMA PUBLIC RESTART IDENTITY AND COMMIT NO CHECK");
	}
}
