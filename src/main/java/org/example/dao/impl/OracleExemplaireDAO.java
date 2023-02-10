package org.example.dao.impl;

import org.example.dao.ExemplaireDAO;
import org.example.dao.LivreDAO;
import org.example.pojo.Exemplaire;
import org.example.pojo.Livre;
import org.example.pojo.Personne;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Types.DOUBLE;
import static java.sql.Types.INTEGER;

/**
 * fourni les méthodes CRUD pour des exemplaires
 * @author GLUKHOV Maks
 */
public class OracleExemplaireDAO implements ExemplaireDAO {

    OracleDAOFactory oracleDAOFactory;
    Connection c;

    public OracleExemplaireDAO(OracleDAOFactory oracleDAOFactory) {
        this.oracleDAOFactory = oracleDAOFactory;
        this.c = oracleDAOFactory.getConnection();
    }

    /**
     * Prends un exemplaire en paramétre et l'insere dans la bd
     * @param e objet de la classe Exemplaire
     * @return boolean pour savoir si tout s'est bien passé
     */
    @Override
    public Boolean insertExemplaire(Exemplaire e) {
        try {
            PreparedStatement cmdUpdate = this.c.prepareStatement("INSERT INTO EXEMPLAIRE(ID, PRIX, DULIVRE, EMPRUNTEUR) VALUES(?, ?, ?, ?)");
            cmdUpdate.setInt(1, e.getId());
            //on vérifie chaque attribut pour bien remplir la requete
            if (e.getPrix() == null){
                cmdUpdate.setNull(2, DOUBLE);
            } else {
                cmdUpdate.setDouble(2, e.getPrix());
            }
            if (e.getDulivre() == null) {
                cmdUpdate.setNull(3, INTEGER);
            } else {
                cmdUpdate.setInt(3, e.getDulivre().getIsbn());
            }
            if (e.getEmprunteur() == null){
                cmdUpdate.setNull(4, INTEGER);
            } else {
                cmdUpdate.setInt(4, e.getEmprunteur().getId());
            }
            int res = cmdUpdate.executeUpdate();
            return res == 1;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Update un exemplaire dans la bd
     * @param e objet de la classe Exemplaire
     * @return boolean pour savoir si tout s'est bien passé
     */
    @Override
    public Boolean updateExemplaire(Exemplaire e) {
        try {
            PreparedStatement cmdUpdate = this.c.prepareStatement("UPDATE EXEMPLAIRE SET PRIX = ?, DULIVRE = ?, EMPRUNTEUR = ? WHERE ID = ?");
            if (e.getPrix() == null){
                cmdUpdate.setNull(1, DOUBLE);
            } else {
                cmdUpdate.setDouble(1, e.getPrix());
            }
            if (e.getDulivre() == null) {
                cmdUpdate.setNull(2, INTEGER);
            } else {
                cmdUpdate.setInt(2, e.getDulivre().getIsbn());
            }
            if (e.getEmprunteur() == null){
                cmdUpdate.setNull(3, INTEGER);
            } else {
                cmdUpdate.setInt(3, e.getEmprunteur().getId());
            }
            cmdUpdate.setInt(4, e.getId());
            int res = cmdUpdate.executeUpdate();
            return res == 1;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * supprime un exemplaire dans la bd d'après son id
     * @param e objet de la classe Exemplaire
     * @return boolean pour savoir si tout s'est bien passé
     */
    @Override
    public Boolean deleteExemplaire(Exemplaire e) {
        try {
            PreparedStatement cmdUpdate = this.c.prepareStatement("DELETE FROM EXEMPLAIRE WHERE ID = ?");
            cmdUpdate.setInt(1, e.getId());
            int res = cmdUpdate.executeUpdate();
            return res == 1;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * supprime tous les exemplaires d'une livre donnée
     * @param l objet de la classe Livre
     * @return boolean pour savoir si tout s'est bien passé
     */
    @Override
    public Boolean deleteExemplaireByIdLivre(Livre l){
        int nbExemplaire = l.getExemplaireList().size();
        try {
            PreparedStatement cmdUpdate = this.c.prepareStatement("DELETE FROM EXEMPLAIRE WHERE DULIVRE = ?");
            cmdUpdate.setInt(1, l.getIsbn());
            int res = cmdUpdate.executeUpdate();
            return true; //  on supprime deux exemplaires, mais res = 1, pq??????????????????
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * charge les exemplaires dans une liste de l'objet Livre donné d'après isbn
     * @param l objet de la classe Livre
     */
    @Override
    public void loadExemplaires(Livre l){
        try{
            PreparedStatement cmdUpdate = this.c.prepareStatement("SELECT * FROM EXEMPLAIRE WHERE DULIVRE = ?");
            cmdUpdate.setInt(1, l.getIsbn());
            ResultSet resultSet = cmdUpdate.executeQuery();
            while(resultSet.next()){
                l.addExemplaire(new Exemplaire(resultSet.getInt("ID"), resultSet.getDouble("PRIX"), l, null));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * créer l'id pour exemplaire grâce à sequence SQL
     * @return nouveau id pour exemplaire à insérer
     */
    @Override
    public int getNewIdExemplaire(){
        int id = 0;
        try{
            PreparedStatement stm = this.c.prepareStatement("SELECT SEQ_Exemplaire.nextval FROM DUAL");
            ResultSet res = stm.executeQuery();
            while(res.next()){
                id = res.getInt(1);
            }
            return id;
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    /**
     * la méthode returne une liste des exemplaires selon les critères de recherhce donnés
     * en tant que les attributs d'objet Exemplaire
     * @param e objet de la classe Exemplaire
     * @return exemplaireList
     */
    @Override
    public List<Exemplaire> findExemplaires(Exemplaire e){
        List<Exemplaire> exemplaireList = new ArrayList<>();
        LivreDAO livreDAO = oracleDAOFactory.getLivreDAO();
        String requeteSQL = "SELECT * FROM EXEMPLAIRE WHERE 1 = 1";  // on vérifie la présence des attributs dans l'objet e
        if (e.getId() != null){                                      // pour bien completer le prepareStatement
            requeteSQL += "AND ID = ?";
        }
        if (e.getPrix() != null) {
            requeteSQL += "AND PRIX = ?";
        }
        if (e.getDulivre() != null) {
            requeteSQL += "AND DULIVRE = ?";
        }
        if (e.getEmprunteur() != null){
            requeteSQL += "AND EMPRUNTEUR = ?";
        }
        int i = 1;
        try{
            PreparedStatement stm = this.c.prepareStatement(requeteSQL);
            if (e.getId() != null){                             // de même on vérifie les présences des attributs
                stm.setInt(i, e.getId());                       // pour avoir un bon index
                i++;
            }
            if (e.getPrix() != null) {
                stm.setDouble(i, e.getPrix());
                i++;
            }
            if (e.getDulivre() != null) {
                stm.setInt(i, e.getDulivre().getIsbn());
                i++;
            }
            if (e.getEmprunteur() != null){
                stm.setInt(i, e.getEmprunteur().getId());
            }
            ResultSet res = stm.executeQuery();
            while(res.next()){
                Exemplaire newExemplaire = new Exemplaire();
                newExemplaire.setId(res.getInt(1));
                newExemplaire.setPrix(res.getDouble(2));
                newExemplaire.setDulivre(livreDAO.loadLivre(res.getInt(3)));
                res.getInt(4);
                if(!res.wasNull()){      // parfois il y a des null dans un champ EMPRUNTEUR
                    Personne p = new Personne();
                    p.setId(res.getInt(4));
                    newExemplaire.setEmprunteur(new Personne());
                }
                exemplaireList.add(newExemplaire);
            }
            return exemplaireList;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
