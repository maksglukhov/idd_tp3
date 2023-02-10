package org.example.dao;

import org.example.pojo.Livre;

import java.sql.SQLException;
import java.util.List;

public interface LivreDAO {
    Boolean insertLivre(Livre l);
    Boolean updateLivre(Livre l);
    Boolean deleteLivre(Livre l);
    Livre loadLivre(Integer Isbn);
    List<Livre> findLivres(Livre l);
    List<Livre> findLivresComplexe(String attribut, String operateur, String valeur);
}
