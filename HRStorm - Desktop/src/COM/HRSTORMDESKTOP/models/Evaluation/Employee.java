/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package COM.HRSTORMDESKTOP.models.Evaluation;

import java.util.Arrays;


public class Employee {
    
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String nomsociete;
    private Boolean isVerified;
    private String password;
    private String roles;

    public Employee() {
    }

    public Employee(int id) {
        this.id = id;
    }

    public Employee(String nom, String prenom, String email, String nomsociete, Boolean isVerified, String password, String roles) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.nomsociete = nomsociete;
        this.isVerified = isVerified;
        this.password = password;
        this.roles = roles;
    }

    // Getters and setters

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNomsociete() {
        return nomsociete;
    }

    public void setNomsociete(String nomsociete) {
        this.nomsociete = nomsociete;
    }

    public Boolean getVerified() {
        return isVerified;
    }

    public void setVerified(Boolean verified) {
        isVerified = verified;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    // toString method

    @Override
    public String toString() {
        return nom;
    }
}
