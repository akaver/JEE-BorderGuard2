package subordinates;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import subordinates.dao.SetupDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"file:WebContent/WEB-INF/beans.xml",
		"file:WebContent/WEB-INF/datasource-tx-jpa.xml"})
public class ExampleTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Resource
	private SetupDao setupDao;

	//@Test
	@Rollback(false)
	public void insertData() {
		setupDao.insertSampleData();
	}

    // 1
    //   1-1
    //     1-1-1
    //     1-1-2
    //   1-2
    // 2

    // allumine: kõigile va. oma alluvad, nende alluvad jne
    // alluvad: kõigile va olemasolevad, va tee juureni.

	@Test
	public void aTest() {
		List<Person> p = setupDao.getPossibleSuperiors("1-1");
		System.out.println(p);
	}
	
	//leiame võimalikud alluvad
	@Test
	public void getPossibleSubordinates() {
		List<Person> all = setupDao.getPossibleSubordinates("1-1");
		System.out.println(all);		
	}
}
