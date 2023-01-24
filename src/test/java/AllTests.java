import junit.framework.TestSuite;

public class AllTests extends TestSuite {

	
	public static TestSuite suite() {
	    TestSuite suite = new TestSuite("Ensemble des tests"); 
        suite.addTest(LivreTest.suite());
        suite.addTest(ExemplaireTest.suite());
        suite.addTest(PersonneTest.suite());
		suite.addTest(OracleLivreDAOTest.suite());
		suite.addTest(OraclePersonneDAOTest.suite());
	    return suite;
	  }


}
