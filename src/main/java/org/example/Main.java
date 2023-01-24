package org.example;

import org.example.dao.DAOFactory;
import org.example.dao.LivreDAO;
import org.example.dao.impl.OracleDAOFactory;
import org.example.pojo.Livre;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args){
        DAOFactory daoFactory = new OracleDAOFactory();
        LivreDAO livreDao = daoFactory.getLivreDAO();
        Livre l = new Livre(13, "le hussard");
        livreDao.insertLivre(l);
        l.setTitre("new toto");
        livreDao.updateLivre(l);
        //livreDao.deleteLivre(l);
    }
}