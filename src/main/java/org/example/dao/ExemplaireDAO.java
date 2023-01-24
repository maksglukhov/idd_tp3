package org.example.dao;

import org.example.pojo.Exemplaire;
import org.example.pojo.Livre;

public interface ExemplaireDAO {
    Boolean insertExemplaire(Exemplaire e);
    Boolean updateExemplaire(Exemplaire e);
    Boolean deleteExemplaire(Exemplaire e);
    Boolean deleteExemplaireByIdLivre(Livre l);

    void loadExemplaires(Livre l);
}
