import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.example.dao.DAOFactory;
import org.example.dao.PersonneDAO;
import org.example.dao.impl.OracleDAOFactory;
import org.example.pojo.Personne;

public class OraclePersonneDAOTest extends TestCase {
    DAOFactory daoFactory;
    PersonneDAO personneDAO;
    Personne p;
    @Override
    protected void setUp() throws Exception{
        super.setUp();
        daoFactory = new OracleDAOFactory();
        personneDAO = daoFactory.getPersonneDAO();
        p = new Personne(77, "Snow", "John");
    }

    public void testInsertPersonne(){
        assertTrue(personneDAO.insertPersonne(p));
    }

    public void testUpdatePersonne(){
        p.setNom("Stark");
        p.setPrenom("Aria");
        assertTrue(personneDAO.updatePersonne(p));
    }

    public void testDeletePersonne(){
        assertTrue(personneDAO.deletePersonne(p));
    }

    public static Test suite(){return new TestSuite(OraclePersonneDAOTest.class);}
}
