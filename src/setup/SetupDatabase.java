package setup;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.SQLExec;

public class SetupDatabase {

	public static void main(String[] args) throws Exception {
		SetupDatabase app = new SetupDatabase();
		
		app.createSchema();
		
		System.out.println("success!");
	}	
	
    public void createSchema() {
        String path = FileUtil.getPathRelativeTo("schema.sql", this.getClass());
        
        executeSql(path, getProperties());
    }

    private Properties getProperties() {
		InputStream is = this.getClass().getResourceAsStream("/application.properties");		
		Properties prop = new Properties();
		try {
			prop.load(is);
		} catch (IOException e) {
			throw new RuntimeException();
		}
    	return prop;
    }
    
    private void executeSql(String sqlFilePath, Properties prop) {

        final class SqlExecuter extends SQLExec {
            public SqlExecuter() {
                Project project = new Project();
                project.init();
                setProject(project);
                setTaskType("sql");
                setTaskName("sql");
            }
        }

        SqlExecuter executer = new SqlExecuter();
        executer.setSrc(new File(sqlFilePath));
        executer.setDriver(prop.getProperty("jdbcDriver"));
        executer.setUserid(prop.getProperty("username"));
        executer.setPassword(prop.getProperty("password"));
        executer.setUrl(prop.getProperty("jdbcUrl"));
        executer.execute();
    }

}
