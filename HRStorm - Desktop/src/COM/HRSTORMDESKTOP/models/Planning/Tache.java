/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package COM.HRSTORMDESKTOP.models.Planning;

/**
 *
 * @author houss
 */
public class Tache {
    public int id;
    public String nom,description,priorite,planning_nom;
    public Planning planning;
  

    public Tache() {
    }

    public Tache(int id, String nom, String description, String priorite, Planning planning) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.priorite = priorite;
        this.planning = planning;
    }

    public Tache(String nom, String description, String priorite, Planning planning) {
        this.nom = nom;
        this.description = description;
        this.priorite = priorite;
        this.planning = planning;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriorite() {
        return priorite;
    }

    public void setPriorite(String priorite) {
        this.priorite = priorite;
    }

    public Planning getPlanning() {
        return planning;
    }

    public void setPlanning(Planning planning) {
        this.planning = planning;
    }

    public String getPlanning_nom() {
        return planning_nom;
    }

    public void setPlanning_nom(String planning_nom) {
        this.planning_nom = planning_nom;
    }
    
    

    @Override
    public String toString() {
        return "Tache{" + "id=" + id + ", nom=" + nom + ", description=" + description + ", priorite=" + priorite + ", planning=" + planning.toString() + '}';
    }
    
    
    
    
}
