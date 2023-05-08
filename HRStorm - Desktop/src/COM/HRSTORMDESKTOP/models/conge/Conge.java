/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package COM.HRSTORMDESKTOP.models.Conge;

/**
 *
 * @author Marwen
 */
import java.time.LocalDate;
import java.util.Date;

public class Conge {
    private int id;
    private int id_user_id;
    private String categorie;
    private String description;
    private int etat;
    private String debut;
    private String fin;



    public Conge(String categorie, String description, int id_user_id, String debut, String fin) {
        this.id = id;
        this.id_user_id = id_user_id;
        this.categorie = categorie;
        this.description = description;
        this.etat = etat;
        this.debut = debut;
        this.fin = fin;
    }

    public Conge() {
    }

    public Conge(String string, String string0, String string1, java.sql.Date date, java.sql.Date date0, int aInt, int aInt0, int aInt1) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public int getId_User() {
        return id_user_id;
    }

    public void setId_User(int id_user_id) {
        this.id_user_id = id_user_id;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public String getDebut() {
        return debut;
    }

    public void setDebut(String debut) {
        this.debut = debut;
    }

    public String getFin() {
        return fin;
    }

    public void setFin(String fin) {
        this.fin = fin;
    }

    @Override
    public String toString() {
        return "Conge{" + "id=" + id + ", id_user_id=" + id_user_id + ", categorie=" + categorie + ", description=" + description + ", etat=" + etat + ", debut=" + debut + ", fin=" + fin + '}';
    }
    
    
    
    
    
    

   
}