package org.example.dao.impl;

import org.example.dao.PersonneDAO;
import org.example.pojo.Personne;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


/**
 * fourni les méthodes CRUD pour des personnes (emprunteurs)
 * @author GLUKHOV Maks
 */
public class OraclePersonneDAO implements PersonneDAO {

    OracleDAOFactory oracleDAOFactory;
    Connection c;

    public OraclePersonneDAO(OracleDAOFactory oracleDAOFactory) {
        this.oracleDAOFactory = oracleDAOFactory;
        this.c = oracleDAOFactory.getConnection();
    }

    /**
     * insére une personne dans la bd
     * @param p objet de la classe Personne
     * @return boolean pour savoir si tout s'est bien passé
     */
    @Override
    public Boolean insertPersonne(Personne p) {
        try{
            PreparedStatement cmdUpdate = this.c.prepareStatement("INSERT INTO PERSONNE(ID, NOM, PRENOM) VALUES(?,?,?)");
            cmdUpdate.setInt(1, p.getId());
            cmdUpdate.setString(2, p.getNom());
            cmdUpdate.setString(3, p.getPrenom());
            int res = cmdUpdate.executeUpdate();
            return res == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * update d'une personne dans la bd
     * @param p objet de la classe Personne
     * @return boolean pour savoir si tout s'est bien passé
     */
    @Override
    public Boolean updatePersonne(Personne p) {
        try{
            PreparedStatement cmdUpdate = this.c.prepareStatement("UPDATE PERSONNE SET NOM = ?, PRENOM = ? WHERE ID = ?");
            cmdUpdate.setString(1, p.getNom());
            cmdUpdate.setString(2, p.getPrenom());
            cmdUpdate.setInt(3, p.getId());
            int res = cmdUpdate.executeUpdate();
            return res == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * supprime une personne de la bd
     * @param p objet de la classe Personne
     * @return boolean pour savoir si tout s'est bien passé
     */
    @Override
    public Boolean deletePersonne(Personne p) {
        try{
            PreparedStatement cmdUpdate = this.c.prepareStatement("DELETE FROM PERSONNE WHERE ID = ?");
            cmdUpdate.setInt(1, p.getId());
            int res = cmdUpdate.executeUpdate();
            return res == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
