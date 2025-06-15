package universite_paris8.iut.youadah.projet.modele;

import universite_paris8.iut.youadah.projet.vue.InventaireVue;

import java.util.ArrayList;
import java.util.List;

public class TableCraft {
    private List<Recette> recettes = new ArrayList<>();
    private InventaireVue inventaireVue;

    public void ajouterRecette(Recette recette) {
        recettes.add(recette);
    }

    public List<Objet> getCraftables(List<Objet> inventaire) {
        List<Objet> possibles = new ArrayList<>();
        for (Recette r : recettes) {
            if (r.peutEtreFabriqueAvec(inventaire)) {
                possibles.add(r.getResultat());
            }
        }
        return possibles;
    }

    public Objet crafter(Recette recette, Inventaire inventaire) {
        if (recette.peutEtreFabriqueAvec(inventaire.getInventaire())) {
            for (Objet o : recette.getComposants()) {
                inventaire.retirerObjet(o);
            }
            inventaire.ajouterObjet(recette.getResultat());
            return recette.getResultat();
        }
        return null;
    }

    public List<Recette> getRecettes() {
        return recettes;
    }
}
