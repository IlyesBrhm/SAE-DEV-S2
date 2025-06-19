package universite_paris8.iut.youadah.projet.modele;

import java.util.*;

/**
 * Classe représentant une recette de craft dans le jeu.
 * Elle contient une liste d'objets nécessaires pour créer un objet final.
 */
public class Recette {
    private List<Objet> composants;
    private Objet resultat;

    /**
     * Constructeur de la classe Recette.
     * @param composants La liste des objets nécessaires pour réaliser la recette.
     * @param resultat L'objet qui sera créé en réalisant cette recette.
     */
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

    /**
     * Vérifie si la recette peut être réalisée avec les objets disponibles dans l'inventaire.
     * @param inventaire L'inventaire du joueur contenant les objets disponibles.
     * @return true si la recette peut être réalisée, false sinon.
     */
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
