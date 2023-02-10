package org.example.dao.impl;

import org.example.dao.DAOFactory;
import org.example.dao.ExemplaireDAO;
import org.example.dao.LivreDAO;
import org.example.dao.PersonneDAO;

import java.sql.*;
import java.lang.Class;

/**
 * classe DAO factory qui fournie les objets nécessaires pour travailler avec la bd
 * @author GLUKHOV Maks
 */
public class OracleDAOFactory implements DAOFactory {

    LivreDAO livreDAO;
    PersonneDAO personneDAO;

    ExemplaireDAO exemplaireDAO;

    /**
     * @return livreDao si existe sinon on le crée
     */
    @Override
    public LivreDAO getLivreDAO() {
        if (livreDAO == null){
            livreDAO = new OracleLivreDAO(this);
        }
        return livreDAO;
    }

    /**
     *
     * @return personneDAO si existe sinon on ke crée
     */
    @Override
    public PersonneDAO getPersonneDAO(){
        if(personneDAO == null){
            personneDAO = new OraclePersonneDAO(this);
        }
        return personneDAO;
    }

    /**
     *
     * @return ExemplaireDAO si existe sinon on le crée
     */
    @Override
    public ExemplaireDAO getExemplaireDAO(){
        if (exemplaireDAO == null){
            exemplaireDAO = new OracleExemplaireDAO(this);
        }
        return exemplaireDAO;
    }

    /**
     *
     * @return la connection vers la basse de données
     * !!! changer les identifiants pour votre base de données !!!
     */
    @Override
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

    /**
     * la méthode prend en paramétre la requete sql et produite la sortie en console de données reçus
     * @param requeteSQL
     */
    @Override
    public void executeNativeQuery(String requeteSQL){
        try{
            PreparedStatement cmdUpdate = this.getConnection().prepareStatement(requeteSQL);
            ResultSet resultSet = cmdUpdate.executeQuery();
            if (requeteSQL.contains("FROM LIVRE")){
                System.out.println("ISBN, TITRE");
                System.out.println("-----------");
                while(resultSet.next()){
                    System.out.println(resultSet.getInt(1) + ", " + resultSet.getString(2));
                }
            }
            if (requeteSQL.contains("FROM EXEMPLAIRE")){
                System.out.println("ID, PRIX, DULIVRE, EMPRUNTEUR");
                System.out.println("-----------------------------");
                while(resultSet.next()){
                    String sortie = resultSet.getInt(1) + ",  " +
                            resultSet.getDouble(2) + ", "
                            + resultSet.getInt(3);

                    resultSet.getInt(4);
                    if (!resultSet.wasNull()){
                        sortie += ",       " + resultSet.getInt(4);
                    } else {
                        sortie += ",       <null>";
                    }
                    System.out.println(sortie);
                }
            }
            if (requeteSQL.contains("FROM PERSONNE")){
                System.out.println("ID, NOM, PRENOM");
                System.out.println("---------------");
                while(resultSet.next()){
                    System.out.println(resultSet.getInt(1) + ", " + resultSet.getString(2) + ", " + resultSet.getString(3));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
