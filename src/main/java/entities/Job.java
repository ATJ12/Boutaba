package entities;

public class Job {
    private int idjob;
    private String nom_job;
    private String specialite;
    private float salaire;
    private int idrecuteur;

    @Override
    public String toString() {
        return "Job{" +
                "idjob=" + idjob +
                ", nom_job='" + nom_job + '\'' +
                ", specialite='" + specialite + '\'' +
                ", salaire=" + salaire +
                ", idrecuteur=" + idrecuteur +
                '}';
    }

    public Job(int idjob, String nomJob, String specialite, float salaire, int idrecruteur) {}
    private String recruiterEmail;

    public Job() {
    }

    public String getRecruiterEmail() {
        return recruiterEmail;
    }

    public int getIdjob() {
        return idjob;
    }

    public void setIdjob(int idjob) {
        this.idjob = idjob;
    }

    public String getNom_job() {
        return nom_job;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public void setNom_job(String nom_job) {
        this.nom_job = nom_job;
    }

    public float getSalaire() {
        return salaire;
    }

    public void setSalaire(float salaire) {
        this.salaire = salaire;
    }

    public int getIdrecuteur() {
        return idrecuteur;
    }

    public void setIdrecuteur(int idrecuteur) {
        this.idrecuteur = idrecuteur;
    }
    public Job(String nom_job, String specialite, float salaire, int idrecuteur) {
        this.nom_job = nom_job;
        this.specialite = specialite;
        this.salaire = salaire;
        this.idrecuteur = idrecuteur;
    }
}
