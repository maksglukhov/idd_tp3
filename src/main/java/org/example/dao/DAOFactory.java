package org.example.dao;

import org.example.pojo.Livre;

import java.sql.Connection;

public interface DAOFactory {
    LivreDAO getLivreDAO();

    PersonneDAO getPersonneDAO();

    ExemplaireDAO getExemplaireDAO();

    Connection getConnection();

    void executeNativeQuery(String requeteSQL);
}
