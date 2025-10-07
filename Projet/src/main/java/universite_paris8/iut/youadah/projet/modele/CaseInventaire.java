package universite_paris8.iut.youadah.projet.modele;

public class CaseInventaire  {

    private Objet objet;
    private int quantite;

    public CaseInventaire(Objet objet){
        this.objet=objet;
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
        this.quantite = quantite;
    }

    public void incrementerQuantite(int x){
        quantite += x;
    }

    public void decrementerQuantite(int x){
        quantite -= x;
    }
}
