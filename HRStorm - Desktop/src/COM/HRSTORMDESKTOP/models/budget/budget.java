/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package COM.HRSTORMDESKTOP.models.budget;

import java.time.LocalDate;

/**
 *
 * @author ihebt
 */
public class budget {
    private int id;
    private float budget;
    private LocalDate date;
    private float prime;
    private float budget_materiel;
    private float budget_salaire;
    private float   budget_service;

    public budget(LocalDate date) {
        this.date = date;
    }

    public budget(float budget) {
        this.budget = budget;
    }

    public budget() {
    }   

    public budget(float budget, LocalDate date, float prime, float budget_materiel, float budget_salaire, float budget_service) {
        this.budget = budget;
        this.date = date;
        this.prime = prime;
        this.budget_materiel = budget_materiel;
        this.budget_salaire = budget_salaire;
        this.budget_service = budget_service;
    }
    

    public budget(int id, float budget, LocalDate date, float prime, float budget_materiel, float budget_salaire, float budget_service) {
        this.id = id;
        this.budget = budget;
        this.date = date;
        this.prime = prime;
        this.budget_materiel = budget_materiel;
        this.budget_salaire = budget_salaire;
        this.budget_service = budget_service;
    }

    public int getId() {
        return id;
    }

    public float getBudget() {
        return budget;
    }

    public LocalDate getDate() {
        return date;
    }

    public float getPrime() {
        return prime;
    }

    public float getBudget_materiel() {
        return budget_materiel;
    }

    public float getBudget_salaire() {
        return budget_salaire;
    }

    public float getBudget_service() {
        return budget_service;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBudget(float budget) {
        this.budget = budget;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setPrime(float prime) {
        this.prime = prime;
    }

    public void setBudget_materiel(float budget_materiel) {
        this.budget_materiel = budget_materiel;
    }

    public void setBudget_salaire(float budget_salaire) {
        this.budget_salaire = budget_salaire;
    }

    public void setBudget_service(float budget_service) {
        this.budget_service = budget_service;
    }

    @Override
    public String toString() {
        return "budget{" + "id=" + id + ", budget=" + budget + ", date=" + date + ", prime=" + prime + ", budget_materiel=" + budget_materiel + ", budget_salaire=" + budget_salaire + ", budget_service=" + budget_service + '}';
    }
    
}
