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
public class depense {
   private int id;
   private budget b;
   private  String nom;
   private float montant;
   private LocalDate date;
   private String justificatif;
   private String categorie;

    public depense() {
    }

    public depense(budget b, String nom, float montant, LocalDate date, String justificatif, String categorie) {
        this.b = b;
        this.nom = nom;
        this.montant = montant;
        this.date = date;
        this.justificatif = justificatif;
        this.categorie = categorie;
    }
   

    public depense(int id, budget b, String nom, float montant, LocalDate date, String justificatif, String categorie) {
        this.id = id;
        this.b = b;
        this.nom = nom;
        this.montant = montant;
        this.date = date;
        this.justificatif = justificatif;
        this.categorie = categorie;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public budget getB() {
        return b;
    }

    public void setB(budget b) {
        this.b = b;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public float getMontant() {
        return montant;
    }

    public void setMontant(float montant) {
        this.montant = montant;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getJustificatif() {
        return justificatif;
    }

    public void setJustificatif(String justificatif) {
        this.justificatif = justificatif;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    
    @Override
    public String toString() {
        return "depense{" + "id=" + id + ", b=" + b + ", nom=" + nom + ", montant=" + montant + ", date=" + date + ", justificatif=" + justificatif + ", categorie=" + categorie + '}';
    }
   
    
}
