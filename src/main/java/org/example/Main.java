package org.example;

import org.example.dao.DAOFactory;
import org.example.dao.impl.OracleDAOFactory;

/**
 * @author GLUKHOV Maks
 */
public class Main {
    public static void main(String[] args){
        DAOFactory daoFactory = new OracleDAOFactory();
        //daoFactory.executeNativeQuery("SELECT * FROM PERSONNE");
        //daoFactory.executeNativeQuery("SELECT * FROM EXEMPLAIRE");
        daoFactory.executeNativeQuery("SELECT * FROM LIVRE");
    }
}