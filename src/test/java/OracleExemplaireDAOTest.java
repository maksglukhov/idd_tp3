import junit.framework.TestCase;
import org.example.dao.DAOFactory;
import org.example.dao.ExemplaireDAO;
import org.example.dao.impl.OracleDAOFactory;
import org.example.pojo.Exemplaire;
import org.example.pojo.Livre;
import org.example.pojo.Personne;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static java.sql.Types.INTEGER;

public class OracleExemplaireDAOTest extends TestCase {
    DAOFactory daoFactory;
    ExemplaireDAO exemplaireDAO;
    Exemplaire e;
    Personne p;
    Livre l;
    Connection c;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        daoFactory = new OracleDAOFactory();
        exemplaireDAO = daoFactory.getExemplaireDAO();
        l = new Livre(1, "La Terre entre nos mains");
        c = daoFactory.getConnection();
        e = new Exemplaire();
        e.setId(exemplaireDAO.getNewIdExemplaire());
        e.setPrix(7.7);
        e.setDulivre(l);
        e.setEmprunteur(null);
        l.setExemplaireList(new ArrayList<>());
        l.addExemplaire(e);
    }

    @Override
    protected void tearDown(){

    }

    public void testInsertExemplaire(){
        assertTrue(exemplaireDAO.insertExemplaire(e));
        deleteExemplaireAfterTest(e);
    }


    public void testUpdateExemplaire(){
        Exemplaire eCopy = e;
        insertExemplaireForTest(e);
        e.setPrix(10.10);
        assertTrue(exemplaireDAO.updateExemplaire(e));
        deleteExemplaireAfterTest(e);
    }

    public void testDeleteExemplaire(){
        insertExemplaireForTest(e);
        assertTrue(exemplaireDAO.deleteExemplaire(e));
    }


    /**
     * fonctions pour remettre la bd dans un état avant les tests
     */
    private void insertExemplaireForTest(Exemplaire e) {
        try {
            PreparedStatement cmdUpdate = c.prepareStatement("INSERT INTO EXEMPLAIRE(ID, PRIX, DULIVRE, EMPRUNTEUR) VALUES(?, ?, ?, ?)");
            cmdUpdate.setInt(1, e.getId());
            cmdUpdate.setDouble(2, e.getPrix());
            cmdUpdate.setInt(3, e.getDulivre().getIsbn());
            cmdUpdate.setNull(4, INTEGER);
            assertEquals(1, cmdUpdate.executeUpdate());
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void deleteExemplaireAfterTest(Exemplaire e) {
        System.out.println("id exemplaire " + e.getId());
        try {
            PreparedStatement cmdUpdate = c.prepareStatement("DELETE FROM EXEMPLAIRE WHERE ID = ?"); //TODO ajouter currval de livre à la place de 16
            cmdUpdate.setInt(1, e.getId());
            assertEquals(1, cmdUpdate.executeUpdate());
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
