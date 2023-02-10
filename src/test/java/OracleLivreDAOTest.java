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
import java.util.List;


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
        deleteLivreForTest(l);
    }

    public void testUpdateLivre(){
        insertLivreForTest(l); // on insere une livre avant faire update
        l.setTitre("new toto");
        l.getExemplaireList().get(0).setPrix(90.0);
        l.removeExemplaire(e2);
        assertTrue(livreDao.updateLivre(l)); // on fait update
        deleteLivreForTest(l); // on supprime livre après le test
    }

    public void testDeleteLivre(){
        insertLivreForTest(l);
        assertTrue(livreDao.deleteLivre(l));
    }

    public void testLoadLivre(){
        Livre l = livreDao.loadLivre(1);
        assertEquals("La Terre entre nos mains", l.getTitre());
        assertEquals(26.5, l.getExemplaireList().get(0).getPrix());
        assertEquals(5 , l.getExemplaireList().size());
    }

    public void testFindLivres(){
        Livre l = new Livre();
        l.setTitre("le hussard");
        List<Livre> livreList = livreDao.findLivres(l);
        //on sait qu'il y a 4 livre avec ce titre parce que le script a été executé juste avant les tests
        assertEquals(4, livreList.size());
        for (Livre livre :livreList){
            assertEquals("le hussard", livre.getTitre());
        }
    }

    public void testFindLivresComplexe(){
        List<Livre> livreList = livreDao.findLivresComplexe("TITRE", "like", "ss");
        assertEquals(5, livreList.size());
    }

    public static Test suite(){
        return new TestSuite(OracleLivreDAOTest.class);
    }

    /**
     * fonctions pour préprarer la bd pour les tests et remettre dans l'etat intial après
     */
    private void insertLivreForTest(Livre l){
        try {
            PreparedStatement cmdUpdate = c.prepareStatement("INSERT INTO LIVRE(ISBN, TITRE) VALUES(?, ?)");
            cmdUpdate.setInt(1, l.getIsbn());
            cmdUpdate.setString(2, l.getTitre());
            assertEquals(1, cmdUpdate.executeUpdate());
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

    }

    private void deleteLivreForTest(Livre l){
        deleteExemplaireAfterTest(l); // il faut supprimer les exemplaires si il en existe
        try {
            PreparedStatement cmdUpdate = c.prepareStatement("DELETE FROM LIVRE WHERE ISBN = ?");
            cmdUpdate.setInt(1, l.getIsbn());
            assertEquals(1, cmdUpdate.executeUpdate());
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

    }

    private void deleteExemplaireAfterTest(Livre l){
        int size = l.getExemplaireList().size();
        try {
            PreparedStatement cmdUpdate = c.prepareStatement("DELETE FROM EXEMPLAIRE WHERE DULIVRE = ?");
            cmdUpdate.setInt(1, l.getIsbn());
            assertEquals(size, cmdUpdate.executeUpdate());
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
