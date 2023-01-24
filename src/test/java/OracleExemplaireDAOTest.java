import junit.framework.TestCase;
import org.example.dao.DAOFactory;
import org.example.dao.ExemplaireDAO;
import org.example.dao.impl.OracleDAOFactory;
import org.example.pojo.Exemplaire;
import org.example.pojo.Livre;
import org.example.pojo.Personne;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

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
        e = new Exemplaire();
        e.setId(77);
        e.setPrix(7.7);
        l = new Livre(1, "La Terre entre nos mains");
        e.setDulivre(l);
        e.setEmprunteur(null);
        l.setExemplaireList(new ArrayList<>());
        c = daoFactory.getConnection();
    }

    public void testInsertExemplaire(){
        l.addExemplaire(e);
        assertTrue(exemplaireDAO.insertExemplaire(e));
        try {
            PreparedStatement cmdUpdate = c.prepareStatement("DELETE FROM EXEMPLAIRE WHERE ID = ?");
            cmdUpdate.setInt(1, e.getId());
            assertEquals(1, cmdUpdate.executeUpdate());
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void testUpdateExemplaire(){
        l.removeExemplaire(e);
        e.setPrix(10.10);
        l.addExemplaire(e);
        assertTrue(exemplaireDAO.updateExemplaire(e));
    }

    public void testDeleteExemplaire(){
        l.removeExemplaire(e);
        assertTrue(exemplaireDAO.deleteExemplaire(e));
    }
}
