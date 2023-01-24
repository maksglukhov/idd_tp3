package org.example.pojo;

public class Exemplaire {
    private Integer id;
    private Double prix;
    private Livre dulivre;
    private Personne emprunteur;

    public Exemplaire() {
    }

    public Exemplaire(Integer id, Double prix, Livre dulivre, Personne emprunteur) {
        this.id = id;
        this.prix = prix;
        this.dulivre = dulivre;
        this.emprunteur = emprunteur;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public Livre getDulivre() {
        return dulivre;
    }

    public void setDulivre(Livre dulivre) {
        this.dulivre = dulivre;
    }

    public Personne getEmprunteur() {
        return emprunteur;
    }

    public void setEmprunteur(Personne emprunter) {
        this.emprunteur = emprunter;
    }
}
