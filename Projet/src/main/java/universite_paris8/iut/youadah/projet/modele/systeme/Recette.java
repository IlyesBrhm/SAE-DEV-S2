package universite_paris8.iut.youadah.projet.modele.systeme;

import java.util.*;

public class Recette {
    private List<CaseInventaire> composants;
    private CaseInventaire resultat;

    public Recette(List<CaseInventaire> composants, CaseInventaire resultat) {
        this.composants = composants;
        this.resultat = resultat;
    }

    public List<CaseInventaire> getComposants() {
        return composants;
    }

    public CaseInventaire getResultat() {
        return resultat;
    }

    public boolean estCraftable(Inventaire inventaire) {
        System.out.println("=== Vérification de la recette ===");


        Map<String, Integer> quantitesNecessaires = new HashMap<>();
        for (CaseInventaire c : composants) {
            quantitesNecessaires.put(c.getObjet().getNom(), quantitesNecessaires.getOrDefault(c.getObjet().getNom(), 0) + 1);
        }


        Map<String, Integer> quantitesDisponibles = new HashMap<>();
        for (CaseInventaire c : inventaire.getInventaire()) {
            quantitesDisponibles.put(c.getObjet().getNom(), quantitesDisponibles.getOrDefault(c.getObjet().getNom(), 0) + c.getQuantite());
        }


        for (String nom : quantitesNecessaires.keySet()) {
            int requis = quantitesNecessaires.get(nom);
            int dispo = quantitesDisponibles.getOrDefault(nom, 0);
            if (dispo < requis) {
                System.out.println("Pas assez de : " + nom);
                return false;
            }
        }


        return true;
    }
}
