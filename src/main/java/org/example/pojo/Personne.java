package org.example.pojo;

import java.util.ArrayList;
import java.util.List;

public class Personne {
    private Integer id;
    private String nom;
    private String prenom;

    private List<Exemplaire> exemplaireList = new ArrayList<>();

    public Personne() {
    }

    public Personne(Integer id, String nom, String prenom) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public List<Exemplaire> getExemplaireList() {
        return exemplaireList;
    }

    public void setExemplaireList(List<Exemplaire> exemplaireList) {
        this.exemplaireList = exemplaireList;
    }

    public void addExemplaireEmprunte(Exemplaire e){
        this.exemplaireList.add(e);
    }

    public void removeExemplaireEmprunte(Exemplaire e){
        this.exemplaireList.remove(e);
    }
}
