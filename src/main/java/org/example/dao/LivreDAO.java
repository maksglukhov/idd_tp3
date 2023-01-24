package org.example.dao;

import org.example.pojo.Livre;

import java.sql.SQLException;

public interface LivreDAO {
    Boolean insertLivre(Livre l);
    Boolean updateLivre(Livre l);
    Boolean deleteLivre(Livre l);

    Livre loadLivre(Integer Isbn);
}
