package universite_paris8.iut.youadah.projet.modele.systeme;

import universite_paris8.iut.youadah.projet.modele.objet.Objet;

import java.util.ArrayList;
import java.util.List;

public class Inventaire {
    private List<CaseInventaire> inventaire;

    public Inventaire() {
        inventaire = new ArrayList<>(6);
    }

    public boolean ajouterObjet(CaseInventaire nouvelCase) {
        for (CaseInventaire caseInventaire : inventaire) {
            if (caseInventaire.equals(nouvelCase)) {
                caseInventaire.incrementerQuantite(nouvelCase.getQuantite());
                return true;
            }
        }

        if (inventaire.size() < 6) {
            inventaire.add(nouvelCase);
            return true;
        } else {
            System.out.println("Inventaire plein");
            return false;
        }
    }

    public void retirerObjet(CaseInventaire caseARetirer) {
        for (int i = 0; i < inventaire.size(); i++) {
            CaseInventaire caseInventaire = inventaire.get(i);
            if (caseInventaire.equals(caseARetirer)) {
                if (caseInventaire.getQuantite() > 1) {
                    caseInventaire.decrementerQuantite(1);
                } else {
                    inventaire.remove(i);
                }
                return;
            }
        }
    }

    public CaseInventaire trouverCase(Objet objet) {
        for (CaseInventaire caseInventaire : inventaire) {
            // Utiliser equals au lieu de == pour comparer les objets
            if (caseInventaire.getObjet().equals(objet)) {
                return caseInventaire;
            }
        }
        return null;
    }

    public List<CaseInventaire> getInventaire() {
        return inventaire;
    }
}