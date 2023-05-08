package COM.HRSTORMDESKTOP.models.Evaluation;

public class Competence {
    private Integer id;
    private String nom;
    private String description;

    public Competence() {
    }

    public Competence(int id,String nom, String description) {
        this.nom = nom;
        this.id=id;
        this.description = description;
    }

    public Competence(String nom, String description) {
        this.nom = nom;
        this.description = description;
    }
    
    public String getNom() {
        return nom;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Competence{" + "id=" + id + ", nom=" + nom + ", description=" + description + '}';
    }

   
}
