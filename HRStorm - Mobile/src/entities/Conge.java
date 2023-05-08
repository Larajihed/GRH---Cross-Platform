/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;
import java.util.Date;

public class Conge {
    private float id;
    private String categorie;
    private String description;
    private String debut_m;
    private String fin_m;

    public Conge(float id, String categorie, String description, String debut_m, String fin_m) {
        this.id = id;
        this.categorie = categorie;
        this.description = description;
        this.debut_m = debut_m;
        this.fin_m = fin_m;
        
    }

    public Conge() {
    }

    public Conge(String categorie, String description, String debut_m, String fin_m) {
        this.categorie = categorie;
        this.description = description;
        this.debut_m = debut_m;
        this.fin_m = fin_m;
    }

    public float getId() {
        return id;
    }

    public void setId(float id) {
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

    public String getDebut_m() {
        return debut_m;
    }

    public void setDebut_m(String debut_m) {
        this.debut_m = debut_m;
    }

    public String getFin_m() {
        return fin_m;
    }

    public void setFin_m(String fin_m) {
        this.fin_m = fin_m;
    }

    @Override
    public String toString() {
        return "Conge{" + "id=" + id + ", categorie=" + categorie + ", description=" + description + ", debut_m=" + debut_m + ", fin_m=" + fin_m + '}';
    }
    
    
    
    
    
    
    
    

}