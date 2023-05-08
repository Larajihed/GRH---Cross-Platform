package COM.HRSTORMDESKTOP.models.Evaluation;

import java.util.ArrayList;
import java.util.Collection;

public class Poste {
    

    private Integer id;
    
    private String nom;
    
    private String missions;
    
    private String description;
    
    private Collection<Competence> competences;
    
    private Float salaireMax;
    
    private Float salaireMin;

    public Poste(String nom, String missions, String description, Collection<Competence> competences, Float salaireMax, Float salaireMin) {
        this.nom = nom;
        this.missions = missions;
        this.description = description;
        this.competences = competences;
        this.salaireMax = salaireMax;
        this.salaireMin = salaireMin;
    }
    
    //private Collection<Evaluation> evaluations;
    
    public Poste() {
        this.competences = new ArrayList<>();
       // this.evaluations = new ArrayList<>();
    }

    public Poste(int posteId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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

    public String getMissions() {
        return missions;
    }

    public void setMissions(String missions) {
        this.missions = missions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Collection<Competence> getCompetences() {
        return competences;
    }

    public void setCompetences(Collection<Competence> competences) {
        this.competences = competences;
    }

    public Float getSalaireMax() {
        return salaireMax;
    }

    public void setSalaireMax(Float salaireMax) {
        this.salaireMax = salaireMax;
    }

    public Float getSalaireMin() {
        return salaireMin;
    }

    public void setSalaireMin(Float salaireMin) {
        this.salaireMin = salaireMin;
    }


  //  public Collection<Evaluation> getEvaluations() {
    //    return evaluations;
    //}

    //public void setEvaluations(Collection<Evaluation> evaluations) {
       // this.evaluations = evaluations;
    //}
/*
    @Override
    public String toString() {
        return "Poste{" + "id=" + id + ", nom=" + nom + ", missions=" + missions + ", description=" + description + ", competences=" + competences + ", salaireMax=" + salaireMax + ", salaireMin=" + salaireMin + '}';
    }
*/
    
       @Override
    public String toString() {
        return this.nom; // or any other field that you want to display in the ComboBox
    }


}
