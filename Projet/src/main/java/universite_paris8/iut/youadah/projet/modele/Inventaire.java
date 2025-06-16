package universite_paris8.iut.youadah.projet.modele;

import java.util.ArrayList;
import java.util.List;

public class Inventaire {
    private List<Objet> inventaire;

    public Inventaire() {
        inventaire = new ArrayList<>(6);
    }

    public void ajouterObjet(Objet nouvelObjet) {
        for (Objet objet : inventaire) {
            if (objet.equals(nouvelObjet)) {
                objet.incrementerQuantite(nouvelObjet.getQuantite());
                return;
            }
        }

        if (inventaire.size() < 6) {
            inventaire.add(nouvelObjet);
        } else {
            System.out.println("Inventaire plein");
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
