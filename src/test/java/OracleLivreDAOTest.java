import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.example.dao.DAOFactory;
import org.example.dao.LivreDAO;
import org.example.dao.impl.OracleDAOFactory;
import org.example.pojo.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static java.sql.Types.INTEGER;

public class OracleLivreDAOTest extends TestCase{
    DAOFactory daoFactory;
    LivreDAO livreDao;
    Livre l;
    Exemplaire e1, e2;
    Connection c;

    @Override
    protected void setUp() throws Exception{
        super.setUp();
        daoFactory = new OracleDAOFactory();
        livreDao = daoFactory.getLivreDAO();
        l = new Livre(77, "toto");
        e1 = new Exemplaire();
        e1.setId(70);
        e1.setDulivre(l);
        e1.setPrix(70.0);
        l.addExemplaire(e1);
        e2 = new Exemplaire();
        e2.setId(71);
        e2.setPrix(100.0);
        e2.setDulivre(l);
        l.addExemplaire(e2);
        c = daoFactory.getConnection();
    }

    @Override
    protected void tearDown(){

    }


    public void testInsertLivre(){
        assertTrue(livreDao.insertLivre(l));
    }

    public void testUpdateLivre(){
        l.setTitre("new toto");
        l.getExemplaireList().get(0).setPrix(90.0);
        l.removeExemplaire(e2);
        assertTrue(livreDao.updateLivre(l));
    }

    public void testDeleteLivre(){
        assertTrue(livreDao.deleteLivre(l));
    }

    public void testLoadLivre(){
        Livre l = livreDao.loadLivre(1);
        assertEquals("La Terre entre nos mains", l.getTitre());
        assertEquals(26.5, l.getExemplaireList().get(0).getPrix());
        assertEquals(5 , l.getExemplaireList().size());
    }

    public static Test suite(){
        return new TestSuite(OracleLivreDAOTest.class);
    }


    /**
     * fonctions pour remettre la bd dans un état avant les tests
     */
    private void insertLivreForTest(){
        try {
            PreparedStatement cmdUpdate = c.prepareStatement("INSERT INTO LIVRE(ISBN, TITRE) VALUES(?, ?)");
            cmdUpdate.setInt(1, l.getIsbn());
            cmdUpdate.setString(2, l.getTitre());
            assertEquals(1, cmdUpdate.executeUpdate());
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

    }
}
