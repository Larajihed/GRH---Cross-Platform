/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package COM.HRSTORMDESKTOP.models.Planning;

import java.sql.Date;

/**
 *
 * @author houss
 */
public class Planning {
    public int id;
    public Date date_debut,datefin;
    public String description,nom;

    public Planning() {
    }

    public Planning(int id, String nom,  String description,Date date_debut, Date datefin) {
        this.id = id;
        this.date_debut = date_debut;
        this.datefin = datefin;
        this.description = description;
        this.nom = nom;
    }

    public Planning(Date date_debut, Date datefin, String description, String nom) {
        this.date_debut = date_debut;
        this.datefin = datefin;
        this.description = description;
        this.nom = nom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(Date date_debut) {
        this.date_debut = date_debut;
    }

    public Date getDatefin() {
        return datefin;
    }

    public void setDatefin(Date datefin) {
        this.datefin = datefin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "Planning{" + "id=" + id + ", date_debut=" + date_debut + ", datefin=" + datefin + ", description=" + description + ", nom=" + nom + '}';
    }
    
    
    
}
