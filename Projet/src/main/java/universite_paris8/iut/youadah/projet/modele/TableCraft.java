package universite_paris8.iut.youadah.projet.modele;

import universite_paris8.iut.youadah.projet.vue.InventaireVue;

import java.util.*;

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
