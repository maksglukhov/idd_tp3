package org.example.dao.impl;

import org.example.dao.PersonneDAO;
import org.example.pojo.Personne;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OraclePersonneDAO implements PersonneDAO {

    OracleDAOFactory oracleDAOFactory;
    Connection c;

    public OraclePersonneDAO(OracleDAOFactory oracleDAOFactory) {
        this.oracleDAOFactory = oracleDAOFactory;
        this.c = oracleDAOFactory.getConnection();
    }

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
