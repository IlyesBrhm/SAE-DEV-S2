package universite_paris8.iut.youadah.projet.modele;

import java.util.List;

public class Recette {
    private List<Objet> composants;
    private Objet resultat;

    public Recette(List<Objet> composants, Objet resultat) {
        this.composants = composants;
        this.resultat = resultat;
    }

    public List<Objet> getComposants() {
        return composants;
    }

    public Objet getResultat() {
        return resultat;
    }

    public boolean peutEtreFabriqueAvec(List<Objet> inventaire) {
        return inventaire.containsAll(composants);
    }
}