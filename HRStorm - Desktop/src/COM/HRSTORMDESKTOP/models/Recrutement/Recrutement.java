/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package COM.HRSTORMDESKTOP.models.Recrutement;

/**
 *
 * @author usoum
 */
public class Recrutement {
    private int id;
    private String titre;
    private String description;
    private int nbrposte;
    private float salaire;
    private String type;

    public Recrutement() {
    }

    public Recrutement(String titre, String description, int nbrposte, float salaire, String type) {
        this.titre = titre;
        this.description = description;
        this.nbrposte = nbrposte;
        this.salaire = salaire;
        this.type = type;
    }

    public Recrutement(int id, String titre, String description, int nbrposte, float salaire, String type) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.nbrposte = nbrposte;
        this.salaire = salaire;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNbrposte() {
        return nbrposte;
    }

    public void setNbrposte(int nbrposte) {
        this.nbrposte = nbrposte;
    }

    public float getSalaire() {
        return salaire;
    }

    public void setSalaire(float salaire) {
        this.salaire = salaire;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Recrutment{" + "id=" + id + ", titre=" + titre + ", description=" + description + ", nbrposte=" + nbrposte + ", salaire=" + salaire + ", type=" + type + '}';
    }
    
}
