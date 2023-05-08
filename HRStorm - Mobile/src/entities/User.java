/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

/**
 *
 * @author conta
 */
public class User {
    private int id;
    private String email;
    private String roles;
    private String password;
    private boolean isVerified;
    private String nom;
    private String prenom;
    private String nomSociete;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isIsVerified() {
        return isVerified;
    }

    public void setIsVerified(boolean isVerified) {
        this.isVerified = isVerified;
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

    public String getNomSociete() {
        return nomSociete;
    }

    public void setNomSociete(String nomSociete) {
        this.nomSociete = nomSociete;
    }

    public User(int id, String email, String roles, String password, boolean isVerified, String nom, String prenom, String nomSociete) {
        this.id = id;
        this.email = email;
        this.roles = roles;
        this.password = password;
        this.isVerified = isVerified;
        this.nom = nom;
        this.prenom = prenom;
        this.nomSociete = nomSociete;
    }

    // Getters and setters
    // ...
}

