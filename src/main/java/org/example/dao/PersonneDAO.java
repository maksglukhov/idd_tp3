package org.example.dao;

import org.example.pojo.Personne;

public interface PersonneDAO {
    Boolean insertPersonne(Personne p);
    Boolean updatePersonne(Personne p);
    Boolean deletePersonne(Personne p);
}
