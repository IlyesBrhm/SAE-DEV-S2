package universite_paris8.iut.youadah.projet.modele;

import java.util.ArrayList;
import java.util.List;

public class Inventaire {
    private List<Objet> inventaire;

    public Inventaire() {
        inventaire = new ArrayList<>(6);
    }

    public boolean ajouterObjet(Objet nouvelObjet) {
        for (Objet objet : inventaire) {
            if (objet.equals(nouvelObjet)) {
                objet.incrementerQuantite(nouvelObjet.getQuantite());
                return true;
            }
        }

        if (inventaire.size() < 6) {
            inventaire.add(nouvelObjet);
            return true;
        } else {
            System.out.println("Inventaire plein");
            return false;
        }
    }

    public void retirerObjet(Objet objetARetirer) {
        for (int i = 0; i < inventaire.size(); i++) {
            Objet objet = inventaire.get(i);
            if (objet.equals(objetARetirer)) {
                if (objet.getQuantite() > 1) {
                    objet.decrementerQuantite(1);
                } else {
                    inventaire.remove(i);
                }
                return;
            }
        }
    }

    public List<Objet> getInventaire() {
        return inventaire;
    }
}
