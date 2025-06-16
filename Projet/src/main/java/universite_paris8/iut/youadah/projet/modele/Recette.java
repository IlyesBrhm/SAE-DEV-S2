package universite_paris8.iut.youadah.projet.modele;

import java.util.ArrayList;
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
    public boolean estCraftable(Inventaire inventaire) {
        System.out.println("=== Vérification de la recette ===");

        System.out.println("→ Composants requis :");
        for (Objet o : composants) {
            System.out.println("- " + o.getNom());
        }

        System.out.println("→ Contenu de l'inventaire :");
        for (Objet o : inventaire.getInventaire()) {
            System.out.println("- " + o.getNom());
        }

        // Vérification réelle
        List<Objet> contenu = new ArrayList<>(inventaire.getInventaire());
        List<Objet> requis = new ArrayList<>(composants);

        for (Objet oRequis : new ArrayList<>(requis)) {
            boolean trouve = false;
            for (Objet obj : contenu) {
                if (obj.getNom().equals(oRequis.getNom())) {
                    contenu.remove(obj);
                    requis.remove(oRequis);
                    trouve = true;
                    break;
                }
            }
            if (!trouve) {
                System.out.println("❌ Ingrédient manquant : " + oRequis.getNom());
                return false;
            }
        }

        System.out.println("✔ Tous les ingrédients sont présents !");
        return true;
    }




}