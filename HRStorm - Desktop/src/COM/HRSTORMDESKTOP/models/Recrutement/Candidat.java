/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package COM.HRSTORMDESKTOP.models.Recrutement;

import java.util.Date;

/**
 *
 * @author usoum
 */
public class Candidat {
    private int id;
    private String nom;
    private String prenom;
    private int tel;
    private String email;

    public String getNometat() {
        return nometat;
    }

    public void setNometat(String nometat) {
        this.nometat = nometat;
    }
    private String lettre;
    private int etat;
    private Date datenaissance;
    private Recrutement recrutement;
    private String nometat;
    public Candidat() {
    }

    public Candidat(String nom, String prenom, int tel, String email, String lettre, int etat, Date datenaissance, Recrutement recrutement) {
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
        this.email = email;
        this.lettre = lettre;
        this.etat = etat;
        this.datenaissance = datenaissance;
        this.recrutement = recrutement;
    }

    public Candidat(int id, String nom, String prenom, int tel, String email, String lettre, int etat, Date datenaissance, Recrutement recrutement) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
        this.email = email;
        this.lettre = lettre;
        this.etat = etat;
        this.datenaissance = datenaissance;
        this.recrutement = recrutement;
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

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getTel() {
        return tel;
    }

    public void setTel(int tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLettre() {
        return lettre;
    }

    public void setLettre(String lettre) {
        this.lettre = lettre;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public Date getDatenaissance() {
        return datenaissance;
    }

    public void setDatenaissance(Date datenaissance) {
        this.datenaissance = datenaissance;
    }

    public Recrutement getRecrutement() {
        return recrutement;
    }

    public void setRecrutement(Recrutement recrutement) {
        this.recrutement = recrutement;
    }

    @Override
    public String toString() {
        return "Candidat{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", tel=" + tel + ", email=" + email + ", lettre=" + lettre + ", etat=" + etat + ", datenaissance=" + datenaissance + ", recrutement=" + recrutement + '}';
    }

    
}
