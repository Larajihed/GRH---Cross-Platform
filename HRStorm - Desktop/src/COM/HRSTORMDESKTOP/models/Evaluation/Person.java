/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package COM.HRSTORMDESKTOP.models.Evaluation;

/**
 *
 * @author FGH
 */
public class Person {
    
    private int id, age;
    private String nom, prenom;
    
    private Role role;

    public Person() {
    }

    public Person(int age, String nom, String prenom) {
        this.age = age;
        this.nom = nom;
        this.prenom = prenom;
    }
    
    public Person(int id, int age, String nom, String prenom) {
        this.id = id;
        this.age = age;
        this.nom = nom;
        this.prenom = prenom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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

    @Override
    public String toString() {
        return "Person{" + "id=" + id + ", age=" + age + ", nom=" + nom + ", prenom=" + prenom + '}';
    }
    
    
    
}
