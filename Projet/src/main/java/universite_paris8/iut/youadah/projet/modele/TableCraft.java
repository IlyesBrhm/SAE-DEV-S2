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
        for (CaseInventaire c : recette.getComposants()) {
            requis.put(c.getObjet().getNom(), requis.getOrDefault(c.getObjet().getNom(), 0) + 1);
        }

        List<CaseInventaire> contenu = inventaire.getInventaire();
        for (String nom : requis.keySet()) {
            int restant = requis.get(nom);
            for (int i = 0; i < contenu.size() && restant > 0; i++) {
                CaseInventaire caseInventaire = contenu.get(i);
                if (caseInventaire.getObjet().getNom().equals(nom)) {
                    int dispo = caseInventaire.getQuantite();
                    int utilise = Math.min(dispo, restant);
                    caseInventaire.decrementerQuantite(utilise);
                    restant -= utilise;
                    if (caseInventaire.getQuantite() <= 0) {
                        contenu.remove(i);
                        i--;
                    }
                }
            }
        }

        inventaire.ajouterObjet(recette.getResultat());
        System.out.println("✔ Craft réussi : " + recette.getResultat().getObjet().getNom());
    }

    public List<Recette> getRecettes() {
        return recettes;
    }
}
