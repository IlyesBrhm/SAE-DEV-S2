package universite_paris8.iut.youadah.projet.modele;

public class CaseInventaire {
    private Objet objet;
    private int quantite;

    public CaseInventaire(Objet objet){
        this.objet = objet;
        quantite = 1;
    }

    public Objet getObjet() {
        return objet;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setObjet(Objet objet) {
        this.objet = objet;
    }

    public void setQuantite(int quantite) {
        this.quantite = Math.max(0, quantite);
    }

    public void incrementerQuantite(int x){
        quantite += x;
    }

    public void decrementerQuantite(int x){
        quantite = Math.max(0, quantite - x);
    }

    public boolean estVide() {
        return quantite <= 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CaseInventaire)) return false;
        CaseInventaire autre = (CaseInventaire) o;
        return objet.equals(autre.objet);
    }
}