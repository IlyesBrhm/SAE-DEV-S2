package universite_paris8.iut.youadah.projet.modele;

import java.util.*;

/**
 * Classe représentant une recette de craft dans le jeu.
 * Une recette est composée d'une liste d'objets nécessaires (composants)
 * et d'un objet résultant du craft (résultat).
 */
public class Recette {
    // Liste des objets nécessaires pour réaliser cette recette
    private List<Objet> composants;
    // Objet obtenu après le craft de cette recette
    private Objet resultat;

    /**
     * Constructeur de la classe Recette.
     * @param composants Liste des objets nécessaires pour réaliser la recette
     * @param resultat Objet obtenu après le craft
     */
    public Recette(List<Objet> composants, Objet resultat) {
        this.composants = composants;
        this.resultat = resultat;
    }

    /**
     * Retourne la liste des composants nécessaires pour cette recette.
     * @return Liste des objets composants
     */
    public List<Objet> getComposants() {
        return composants;
    }

    /**
     * Retourne l'objet résultant du craft de cette recette.
     * @return L'objet résultat
     */
    public Objet getResultat() {
        return resultat;
    }

    /**
     * Vérifie si la recette peut être craftée avec l'inventaire actuel.
     * Compare les quantités nécessaires de chaque type d'objet avec
     * les quantités disponibles dans l'inventaire.
     * 
     * @param inventaire L'inventaire à vérifier
     * @return true si tous les composants sont disponibles en quantité suffisante, false sinon
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

        // Comparer les quantités nécessaires avec les quantités disponibles
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
