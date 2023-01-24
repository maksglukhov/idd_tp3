package org.example.dao.impl;

import org.example.dao.ExemplaireDAO;
import org.example.dao.LivreDAO;
import org.example.dao.utils.Utils;
import org.example.pojo.Exemplaire;
import org.example.pojo.Livre;

import java.sql.*;
import java.util.Optional;

public class OracleLivreDAO implements LivreDAO {

    OracleDAOFactory oracleDAOFactory;
    Connection c;

    public OracleLivreDAO(OracleDAOFactory oracleDAOFactory) {
        this.oracleDAOFactory = oracleDAOFactory;
        this.c = oracleDAOFactory.getConnection();
    }

    @Override
    public Boolean insertLivre(Livre l){
        int res1 = 0;
        int res2 = 0;
        try {
            PreparedStatement cmdUpdate = this.c.prepareStatement("INSERT INTO LIVRE (ISBN, TITRE) VALUES (?, ?)");
            cmdUpdate.setInt(1, l.getIsbn());
            cmdUpdate.setString(2, l.getTitre());
            res1 = cmdUpdate.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Utils utils = new Utils();
        res2 = addExemplaire(l);
        return res1 == 1 && res2 == 1;
    }

    @Override
    public Boolean updateLivre(Livre l) {
        int res1 = 0;
        int res2 = 0;
        try{
            PreparedStatement cmdUpdate = this.c.prepareStatement("UPDATE LIVRE SET TITRE = ? WHERE ISBN = ?");
            cmdUpdate.setString(1, l.getTitre());
            cmdUpdate.setInt(2, l.getIsbn());
            res1 = cmdUpdate.executeUpdate();
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        res2 = updateExemplaire(l);
        return res1 == 1 && res2 == 1;
    }

    @Override
    public Boolean deleteLivre(Livre l) {
        int nbExemplaire = l.getExemplaireList().size();
        System.out.println("nbExempalire in deleteLivre ==> " + nbExemplaire);
        int res1 = 0;
        int res2 = deleteAllExemplaires(l); // tout d'abord il faut supprimer les exemplaires pour éviter les erreus avec foreign key
        try{
            PreparedStatement cmdUpdate = this.c.prepareStatement("DELETE FROM LIVRE WHERE ISBN = ?");
            cmdUpdate.setInt(1, l.getIsbn());
            res1 = cmdUpdate.executeUpdate();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res1 == 1 && res2 == 1;
    }

    @Override
    public Livre loadLivre(Integer isbn){
        Livre l = new Livre();
        try{
            PreparedStatement cmdUpdate = this.c.prepareStatement("SELECT * FROM LIVRE WHERE ISBN = ?");
            cmdUpdate.setInt(1, isbn);
            ResultSet resultSet = cmdUpdate.executeQuery();
            while(resultSet.next()){
                l.setIsbn(isbn);
                l.setTitre(resultSet.getString("TITRE"));
            }
            loadExemplaires(l);
            return l;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    /*
    //////METHODS EXTERNES POUR FACILITER LA LECTURE DU CODE
     */
    private int addExemplaire(Livre l) {
        int res = 0;
        ExemplaireDAO exemplaireDAO = oracleDAOFactory.getExemplaireDAO();
        if (l.getExemplaireList().size() ==0){
            res = 1;
        }else {
            for (Exemplaire exemplaire: l.getExemplaireList()){
                Boolean resBool = exemplaireDAO.insertExemplaire(exemplaire);
                if (!resBool){
                    res = 0;
                    break;
                }else {
                    res = 1;
                }
            }
        }
        return res;
    }

    private int updateExemplaire(Livre l){
        int res = 0;
        ExemplaireDAO exemplaireDAO = oracleDAOFactory.getExemplaireDAO();
        if (l.getExemplaireList() == null){
            Boolean resBool = exemplaireDAO.deleteExemplaireByIdLivre(l);
            if (resBool){
                return 1;
            } else {
                return res;
            }
        } else {
            int resTemp = deleteAllExemplaires(l);
            for (Exemplaire exemplaire: l.getExemplaireList()){
                Boolean resBool = exemplaireDAO.insertExemplaire(exemplaire);
                if (!resBool){
                    res = 0;
                    break;
                }else {
                    res = 1;
                }
            }
        }
        return res;
    }

    private int deleteAllExemplaires(Livre l){
        ExemplaireDAO exemplaireDAO = oracleDAOFactory.getExemplaireDAO();
        Boolean res = exemplaireDAO.deleteExemplaireByIdLivre(l);
        if(res){
            return 1;
        } else {
            return 0;
        }
    }

    private void loadExemplaires(Livre l){
        ExemplaireDAO exemplaireDAO = oracleDAOFactory.getExemplaireDAO();
        exemplaireDAO.loadExemplaires(l);
    }
}
