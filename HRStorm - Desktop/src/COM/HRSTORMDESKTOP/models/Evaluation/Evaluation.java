/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package COM.HRSTORMDESKTOP.models.Evaluation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 *
 * @author conta
 */
public class Evaluation {
    
private Integer id;

    @Override
    public String toString() {
        return "Evaluation{" + "id=" + id + ", date=" + date + ", commentaire=" + commentaire + ", experience=" + experience + ", level=" + level + ", competences=" + competences + ", poste=" + poste + "emp=" + employee + '}';
    }

private java.sql.Date date;

private String commentaire;

private Employee employee;

private Integer experience;

private String level;


private Collection<Competence> competences;


private Poste poste;

public Evaluation() {
    competences = new ArrayList<>();
}

public Integer getId() {
    return id;
}


public void setId(Integer id) {
    this.id = id;
}
public Date getDate() {
    return date;
}
public void setDate(java.sql.Date date) {
    this.date = date;
}


public String getCommentaire() {
    return commentaire;
}
public void setCommentaire(String commentaire) {
    this.commentaire = commentaire;
}


public Integer getExperience() {
    return experience;
}

public void setExperience(Integer experience) {
    this.experience = experience;
}

public String getLevel() {
    return level;
}

public void setLevel(String level) {
    this.level = level;
}

public Collection<Competence> getCompetences() {
    return competences;
}

public void setCompetences(Collection<Competence> competences) {
    this.competences = competences;
}

public Poste getPoste() {
    return poste;
}

public Employee getEmployee() {
    return employee;
}


public void setPoste(Poste poste) {
    this.poste = poste;
}

    public void setEmployee(Employee employee) {
        this.employee=employee;
    }



}
