package universite_paris8.iut.youadah.projet.modele;

import universite_paris8.iut.youadah.projet.vue.InventaireVue;

import java.util.*;

/**
 * Classe qui gère la table de craft et les recettes disponibles dans le jeu.
 * Permet d'ajouter des recettes, de vérifier quelles recettes sont craftables
 * avec l'inventaire actuel et d'effectuer le craft d'une recette.
 */
public class TableCraft {
    // Liste des recettes disponibles
    private List<Recette> recettes = new ArrayList<>();
    // Référence à la vue de l'inventaire
    private InventaireVue inventaireVue;

    /**
     * Ajoute une nouvelle recette à la liste des recettes disponibles.
     * @param recette La recette à ajouter
     */
    public void ajouterRecette(Recette recette) {
        recettes.add(recette);
    }

    /**
     * Détermine quelles recettes peuvent être craftées avec l'inventaire actuel.
     * @param inventaire L'inventaire du joueur
     * @return Une liste des recettes qui peuvent être craftées
     */
    public List<Recette> getCraftables(Inventaire inventaire) {
        List<Recette> craftables = new ArrayList<>();
        for (Recette recette : recettes) {
            if (recette.estCraftable(inventaire)) {
                craftables.add(recette);
            }
        }
        return craftables;
    }

    /**
     * Effectue le craft d'une recette en consommant les objets nécessaires
     * de l'inventaire et en ajoutant le résultat à l'inventaire.
     * @param recette La recette à crafter
     * @param inventaire L'inventaire du joueur
     */
    public void crafter(Recette recette, Inventaire inventaire) {
        // Compte le nombre d'objets requis pour chaque type d'objet
        Map<String, Integer> requis = new HashMap<>();
        for (Objet o : recette.getComposants()) {
            requis.put(o.getNom(), requis.getOrDefault(o.getNom(), 0) + 1);
        }

        // Retire les objets nécessaires de l'inventaire
        List<Objet> contenu = inventaire.getInventaire();
        for (String nom : requis.keySet()) {
            int restant = requis.get(nom);
            for (int i = 0; i < contenu.size() && restant > 0; i++) {
                Objet obj = contenu.get(i);
                if (obj.getNom().equals(nom)) {
                    int dispo = obj.getQuantite();
                    int utilise = Math.min(dispo, restant);
                    obj.decrementerQuantite(utilise);
                    restant -= utilise;
                    // Si la quantité d'un objet tombe à 0, on le retire de l'inventaire
                    if (obj.getQuantite() <= 0) {
                        contenu.remove(i);
                        i--;
                    }
                }
            }
        }

        // Ajoute le résultat du craft à l'inventaire
        inventaire.ajouterObjet(recette.getResultat());
        System.out.println("✔ Craft réussi : " + recette.getResultat().getNom());
    }

    /**
     * Retourne la liste de toutes les recettes disponibles.
     * @return La liste des recettes
     */
    public List<Recette> getRecettes() {
        return recettes;
    }
}
