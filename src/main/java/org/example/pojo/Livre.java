package org.example.pojo;

import java.util.ArrayList;
import java.util.List;

public class Livre {
    private Integer isbn;
    private String titre;

    private List<Exemplaire> exemplaireList = new ArrayList<>();

    public Livre() {
    }

    public Livre(Integer isbn, String titre) {
        this.isbn = isbn;
        this.titre = titre;
    }

    public Integer getIsbn() {
        return isbn;
    }

    public void setIsbn(Integer isbn) {
        this.isbn = isbn;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public List<Exemplaire> getExemplaireList() {
        return exemplaireList;
    }

    public void setExemplaireList(List<Exemplaire> exemplaireList) {
        this.exemplaireList = exemplaireList;
    }

    public void addExemplaire(Exemplaire e){
        this.exemplaireList.add(e);
    }

    public void removeExemplaire(Exemplaire e){
        this.exemplaireList.remove(e);
    }
}
