package universite_paris8.iut.youadah.projet.modele;

import universite_paris8.iut.youadah.projet.vue.InventaireVue;

import java.util.*;

/**
 * Classe représentant une table de craft dans le jeu.
 * Permet de gérer les recettes et de crafter des objets à partir des composants disponibles dans l'inventaire.
 */
public class TableCraft {
    private List<Recette> recettes = new ArrayList<>();

    /**
     * Constructeur de la classe TableCraft.
     * Initialise la table de craft avec une liste vide de recettes.
     */
    public void ajouterRecette(Recette recette) {
        recettes.add(recette);
    }

    /**
     * Méthode pour crafter un objet à partir d'une recette et des composants disponibles dans l'inventaire.
     * Si les composants nécessaires sont présents, l'objet est créé et ajouté à l'inventaire.
     *
     * @param recette   La recette à utiliser pour le craft.
     * @param inventaire L'inventaire du joueur contenant les objets disponibles.
     */
    public void crafter(Recette recette, Inventaire inventaire) {
        Map<String, Integer> requis = new HashMap<>();
        for (Objet o : recette.getComposants()) {
            requis.put(o.getNom(), requis.getOrDefault(o.getNom(), 0) + 1);
        }

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
                    if (obj.getQuantite() <= 0) {
                        contenu.remove(i);
                        i--;
                    }
                }
            }
        }

        inventaire.ajouterObjet(recette.getResultat());
        System.out.println("✔ Craft réussi : " + recette.getResultat().getNom());
    }

    public List<Recette> getRecettes() {
        return recettes;
    }
}
