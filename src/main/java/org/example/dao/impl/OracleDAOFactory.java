package org.example.dao.impl;

import org.example.dao.DAOFactory;
import org.example.dao.ExemplaireDAO;
import org.example.dao.LivreDAO;
import org.example.dao.PersonneDAO;

import java.sql.Connection;
import java.lang.Class;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

public class OracleDAOFactory implements DAOFactory {

    LivreDAO livreDAO;
    PersonneDAO personneDAO;

    ExemplaireDAO exemplaireDAO;


    @Override
    public LivreDAO getLivreDAO() {
        if (livreDAO == null){
            livreDAO = new OracleLivreDAO(this);
        }
        return livreDAO;
    }

    @Override
    public PersonneDAO getPersonneDAO(){
        if(personneDAO == null){
            personneDAO = new OraclePersonneDAO(this);
        }
        return personneDAO;
    }

    @Override
    public ExemplaireDAO getExemplaireDAO(){
        if (exemplaireDAO == null){
            exemplaireDAO = new OracleExemplaireDAO(this);
        }
        return exemplaireDAO;
    }

    public Connection getConnection(){
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            return DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:orclcdb",
                    "dao",
                    "dao"
            );
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


}
