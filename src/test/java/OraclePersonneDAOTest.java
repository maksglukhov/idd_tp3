import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.example.dao.DAOFactory;
import org.example.dao.PersonneDAO;
import org.example.dao.impl.OracleDAOFactory;
import org.example.pojo.Personne;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OraclePersonneDAOTest extends TestCase {
    DAOFactory daoFactory;
    PersonneDAO personneDAO;
    Personne p;
    Connection c;
    @Override
    protected void setUp() throws Exception{
        super.setUp();
        daoFactory = new OracleDAOFactory();
        c = daoFactory.getConnection();
        personneDAO = daoFactory.getPersonneDAO();
        p = new Personne(77, "Snow", "John");
    }

    public void testInsertPersonne(){
        assertTrue(personneDAO.insertPersonne(p));
        deletePersonneAfterTest(p);
    }

    public void testUpdatePersonne(){
        insertPersonneForTest(p);
        p.setNom("Stark");
        p.setPrenom("Aria");
        assertTrue(personneDAO.updatePersonne(p));
        deletePersonneAfterTest(p);
    }

    public void testDeletePersonne(){
        insertPersonneForTest(p);
        assertTrue(personneDAO.deletePersonne(p));
    }

    public static Test suite(){return new TestSuite(OraclePersonneDAOTest.class);}

    /*
     * fonctions pour préparer la bd pour les tests et remettre dans l'etat initial après
     */

    private void insertPersonneForTest(Personne p){
        try{
            PreparedStatement cmdUpdate = this.c.prepareStatement("INSERT INTO PERSONNE(ID, NOM, PRENOM) VALUES(?,?,?)");
            cmdUpdate.setInt(1, p.getId());
            cmdUpdate.setString(2, p.getNom());
            cmdUpdate.setString(3, p.getPrenom());
            assertEquals(1, cmdUpdate.executeUpdate());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void deletePersonneAfterTest(Personne p){
        try{
            PreparedStatement cmdUpdate = this.c.prepareStatement("DELETE FROM PERSONNE WHERE ID = ?");
            cmdUpdate.setInt(1, p.getId());
            assertEquals(1, cmdUpdate.executeUpdate());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
