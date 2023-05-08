package entities;

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
    
    //private Collection<Evaluation> evaluations;
    
    public Poste() {
        this.competences = new ArrayList<>();
       // this.evaluations = new ArrayList<>();
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


}
