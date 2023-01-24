package org.example.dao.impl;

import org.example.dao.ExemplaireDAO;
import org.example.pojo.Exemplaire;
import org.example.pojo.Livre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static java.sql.Types.DOUBLE;
import static java.sql.Types.INTEGER;

public class OracleExemplaireDAO implements ExemplaireDAO {

    OracleDAOFactory oracleDAOFactory;
    Connection c;

    public OracleExemplaireDAO(OracleDAOFactory oracleDAOFactory) {
        this.oracleDAOFactory = oracleDAOFactory;
        this.c = oracleDAOFactory.getConnection();
    }

    @Override
    public Boolean insertExemplaire(Exemplaire e) {
        try {
            PreparedStatement cmdUpdate = this.c.prepareStatement("INSERT INTO EXEMPLAIRE(ID, PRIX, DULIVRE, EMPRUNTEUR) VALUES(?, ?, ?, ?)");
            cmdUpdate.setInt(1, e.getId());
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

    @Override
    public Boolean updateExemplaire(Exemplaire e) {//TODO ici le problème
        System.out.println("id ==> " + e.getId());
        System.out.println("prix ==> " + e.getPrix());
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
            System.out.println("res in updateExemplaire ==> " + res);
            return res == 1;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

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

    @Override
    public Boolean deleteExemplaireByIdLivre(Livre l){
        int nbExemplaire = l.getExemplaireList().size();
        try {
            PreparedStatement cmdUpdate = this.c.prepareStatement("DELETE FROM EXEMPLAIRE WHERE DULIVRE = ?");
            cmdUpdate.setInt(1, l.getIsbn());
            int res = cmdUpdate.executeUpdate();
            System.out.println("nbExempalire ==> " + nbExemplaire);
            System.out.println("res in delete by livre ==> " + res);
            return true; // TODO on supprime deux exemplaire mais res = 1
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

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
}
