package universite_paris8.iut.youadah.projet.modele;

import java.util.*;

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

        // Compter les quantités nécessaires pour chaque type d'objet
        Map<String, Integer> quantitesNecessaires = new HashMap<>();
        for (Objet o : composants) {
            quantitesNecessaires.put(o.getNom(), quantitesNecessaires.getOrDefault(o.getNom(), 0) + 1);
        }

        // Compter les quantités disponibles dans l'inventaire
        Map<String, Integer> quantitesDisponibles = new HashMap<>();
        for (Objet o : inventaire.getInventaire()) {
            quantitesDisponibles.put(o.getNom(), quantitesDisponibles.getOrDefault(o.getNom(), 0) + o.getQuantite());
        }

        // Comparer les deux
        for (String nom : quantitesNecessaires.keySet()) {
            int requis = quantitesNecessaires.get(nom);
            int dispo = quantitesDisponibles.getOrDefault(nom, 0);
            if (dispo < requis) {
                System.out.println("❌ Pas assez de : " + nom);
                return false;
            }
        }

        System.out.println("✔ Tous les ingrédients sont présents !");
        return true;
    }
}
