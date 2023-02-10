package org.example.dao.impl;

import org.example.dao.ExemplaireDAO;
import org.example.dao.LivreDAO;
import org.example.dao.utils.Utils;
import org.example.pojo.Exemplaire;
import org.example.pojo.Livre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * fourni les méthodes CRUD pour des livres
 * @author GLUKHOV Maks
 */
public class OracleLivreDAO implements LivreDAO {

    OracleDAOFactory oracleDAOFactory;
    Connection c;

    public OracleLivreDAO(OracleDAOFactory oracleDAOFactory) {
        this.oracleDAOFactory = oracleDAOFactory;
        this.c = oracleDAOFactory.getConnection();
    }

    /**
     * insére une livre dans la bd
     * @param l objet de la classe Livre
     * @return boolean pour savoir si tout s'est bien passé
     */
    @Override
    public Boolean insertLivre(Livre l){
        int res1 = 0;
        int res2 = 0;
        try {
            PreparedStatement cmdUpdate = this.c.prepareStatement("INSERT INTO LIVRE (ISBN, TITRE) VALUES (?, ?)");
            cmdUpdate.setInt(1, l.getIsbn());
            cmdUpdate.setString(2, l.getTitre());
            res1 = cmdUpdate.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Utils utils = new Utils();
        res2 = addExemplaire(l);
        return res1 == 1 && res2 == 1;
    }

    /**
     * update d'une livre dans la bd ainsi l'appel de la fonction update exemplaire
     * @param l objet de la classe Livre
     * @return boolean pour savoir si tout s'est bien passé
     */
    @Override
    public Boolean updateLivre(Livre l) {
        int res1 = 0;
        int res2 = 0;
        try{
            PreparedStatement cmdUpdate = this.c.prepareStatement("UPDATE LIVRE SET TITRE = ? WHERE ISBN = ?");
            cmdUpdate.setString(1, l.getTitre());
            cmdUpdate.setInt(2, l.getIsbn());
            res1 = cmdUpdate.executeUpdate();
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        res2 = updateExemplaire(l);
        return res1 == 1 && res2 == 1;
    }

    /**
     * supprime une livre de la bd ainsi que tous les exemplaires de cette livre
     * @param l objet de la classe Livre
     * @return boolean pour savoir si tout s'est bien passé
     */
    @Override
    public Boolean deleteLivre(Livre l) {
        int nbExemplaire = l.getExemplaireList().size();
        int res1 = 0;
        int res2 = deleteAllExemplaires(l); // tout d'abord il faut supprimer les exemplaires pour éviter les erreus avec foreign key
        try{
            PreparedStatement cmdUpdate = this.c.prepareStatement("DELETE FROM LIVRE WHERE ISBN = ?");
            cmdUpdate.setInt(1, l.getIsbn());
            res1 = cmdUpdate.executeUpdate();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res1 == 1 && res2 == 1;
    }

    /**
     * load les données d'une livre dans la bd dans un objet de la classe Livre
     * @param isbn isbn de la livre à rechercher dans la bd
     * @return l un objet de la classe Livre avec les attributs récupérés dans la bd
     */
    @Override
    public Livre loadLivre(Integer isbn){
        Livre l = new Livre();
        try{
            PreparedStatement cmdUpdate = this.c.prepareStatement("SELECT * FROM LIVRE WHERE ISBN = ?");
            cmdUpdate.setInt(1, isbn);
            ResultSet resultSet = cmdUpdate.executeQuery();
            while(resultSet.next()){
                l.setIsbn(isbn);
                l.setTitre(resultSet.getString("TITRE"));
            }
            loadExemplaires(l);
            return l;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * La méthode permet d'obtenir une liste avec les livres qui correspondent
     * à critère de recherche (dans notre cas c'est titre)
     * ainsi que charger les exemplaires pour chaque livre de la liste
     * @param l objet de la classe Livre avec le titre à rechercher
     * @return listLivre une liste avec tous les livres correspondent à la recherche
     * avec ses exemplaires
     */
    @Override
    public List<Livre> findLivres(Livre l){
        List<Livre> listLivre = new ArrayList<>();
        String requeteSQL = "SELECT * FROM LIVRE WHERE 1 = 1";   // on vérifie si les attributs sont presents
        if (l.getIsbn() != null){                                // dans l'objet livre et on ajoute les conditions
            requeteSQL += "AND ISBN = ?";                        // correspondents dans preparedStatement
        }
        if (l.getTitre() != null){
            requeteSQL += "AND TITRE = ?";
        }
        int i = 1;
        try{
            PreparedStatement cmdUpdate = this.c.prepareStatement(requeteSQL);
            if (l.getIsbn() != null){                   // de même on vérifie les attributs pour bien gérer index
                cmdUpdate.setInt(i, l.getIsbn());
                i++;
            }
            if (l.getTitre() != null){
                cmdUpdate.setString(i, l.getTitre());
            }
            ResultSet resultSet = cmdUpdate.executeQuery();
            while(resultSet.next()){
                Livre newLivre = new Livre(resultSet.getInt(1), resultSet.getString(2));
                loadExemplaires(newLivre);
                listLivre.add(newLivre);
            }
            return listLivre;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * méthode retourne une liste des livres trouvées à partir les paramétrés fournis
     * @param attribut nom de la colonne
     * @param operateur opérateur de comparaison sql
     * @param valeur valeur à comparer
     * @return une liste de livres trouvées
     */
    @Override
    public List<Livre> findLivresComplexe(String attribut, String operateur, String valeur){
        String requeteSQL = "SELECT * FROM LIVRE WHERE 1 = 1";
        List<Livre> listLivre = new ArrayList<>();
        try{
            switch(operateur){
                case "supp":
                    if(attribut.equalsIgnoreCase("isbn")){
                        requeteSQL += "AND ISBN > " + valeur;
                        break;
                    }
                    break;
                case "inf":
                    if(attribut.equalsIgnoreCase("isbn")){
                        requeteSQL += "AND ISBN < " + valeur;
                        break;
                    }
                    break;
                case "like":
                    if(attribut.equalsIgnoreCase("titre")){
                        requeteSQL += "AND TITRE LIKE '%" + valeur + "%'";
                        break;
                    }
                    break;
                default:
                    throw new SQLException("Vous critéres des recherche ne peuvent pas être traités");
            }
            PreparedStatement cmdUpdate = this.c.prepareStatement(requeteSQL);
            ResultSet resultSet = cmdUpdate.executeQuery();
            while(resultSet.next()){
                Livre newLivre = new Livre(resultSet.getInt(1), resultSet.getString(2));
                loadExemplaires(newLivre);
                listLivre.add(newLivre);
            }
            return listLivre;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    /*
    ////// METHODS EXTERNES POUR FACILITER LE CODE
     */

    /**
     * une méthode pour ajouter tous les exemplaires d'une liste d'une livre dans la bd
     * en utilisant la méthode insertExemplaire d'exemplaireDAO
     * @param l objet de la classe Livre contenant une liste des exemplaires à insérer
     * @return res 1 si tout s'est bien passé, 0 sinon
     */
    private int addExemplaire(Livre l) {
        int res = 0;
        ExemplaireDAO exemplaireDAO = oracleDAOFactory.getExemplaireDAO();
        if (l.getExemplaireList().size() ==0){
            res = 1;
        }else {
            for (Exemplaire exemplaire: l.getExemplaireList()){               // on parcourt une liste pour
                Boolean resBool = exemplaireDAO.insertExemplaire(exemplaire); // effectuer un insert pour chaque élément(exemplaire)
                if (!resBool){
                    res = 0;
                    break;
                }else {
                    res = 1;
                }
            }
        }
        return res;
    }


    /**
     * la méthode pour update les exemplaires d'une livre en utilisant
     * la méthode deleteExemplaireByIdLivre d'exemplaireDAO
     * si une liste est vide alors on supprime tout
     * sinon on supprime quand même tout parce qu'on ne sait pas
     * et on appelle la méthode addExemplaire
     * quels sont les exemplaires qui ont été changés
     * on peut proposer la solution avec Audit Trail
     * ou noter les changements dans un fichier pour suivre les modifs
     * @param l objet de la classe Livre contenant une liste des exemplaires à traiter
     * @return res 1 si tout s'est bien passé, 0 sinon
     */
    private int updateExemplaire(Livre l){
        int res = 0;
        ExemplaireDAO exemplaireDAO = oracleDAOFactory.getExemplaireDAO();
        if (l.getExemplaireList() == null){
            Boolean resBool = exemplaireDAO.deleteExemplaireByIdLivre(l);
            if (resBool){
                return 1;
            } else {
                return res;
            }
        } else {
            int resTemp = deleteAllExemplaires(l);
            res = addExemplaire(l);
        }
        return res;
    }

    /**
     * cette méthode supprime tous les exemplaires d'une livre
     * @param l objet de la classe Livre
     * @return res 1 si tout s'est bien passé, 0 sinon
     */
    private int deleteAllExemplaires(Livre l){
        ExemplaireDAO exemplaireDAO = oracleDAOFactory.getExemplaireDAO();
        Boolean res = exemplaireDAO.deleteExemplaireByIdLivre(l);
        if(res){
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * une couche qu'appelle la méthode loadExemplaires d'exemplaireDAO
     * pour charger la liste d'un objet Livre
     * @param l objer de la classe Livre avec isbn
     */
    private void loadExemplaires(Livre l){
        ExemplaireDAO exemplaireDAO = oracleDAOFactory.getExemplaireDAO();
        exemplaireDAO.loadExemplaires(l);
    }
}
