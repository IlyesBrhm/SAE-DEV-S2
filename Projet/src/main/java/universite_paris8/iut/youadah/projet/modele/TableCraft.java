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

    public List<Recette> getCraftables(Inventaire inventaire) {
        List<Recette> craftables = new ArrayList<>();
        for (Recette recette : recettes) {
            if (recette.estCraftable(inventaire)) {
                craftables.add(recette);
            }
        }
        return craftables;
    }


    public void crafter(Recette recette, Inventaire inventaire) {
        List<Objet> contenu = inventaire.getInventaire();
        List<String> composantsRestants = new java.util.ArrayList<>();

        for (Objet composant : recette.getComposants()) {
            composantsRestants.add(composant.getNom());
        }

        // Supprimer un par un les objets de l'inventaire
        for (int i = 0; i < contenu.size(); i++) {
            Objet obj = contenu.get(i);
            if (composantsRestants.contains(obj.getNom())) {
                contenu.remove(i);
                composantsRestants.remove(obj.getNom());
                i--; // Compense le remove
            }
            if (composantsRestants.isEmpty()) break;
        }

        // Si tous les ingrédients sont réunis, ajoute le résultat
        if (composantsRestants.isEmpty()) {
            inventaire.ajouterObjet(recette.getResultat());
            System.out.println("✔ Craft réussi : " + recette.getResultat().getNom());
        } else {
            System.out.println("❌ Craft annulé : ressources manquantes");
        }
    }


    public List<Recette> getRecettes() {
        return recettes;
    }
}