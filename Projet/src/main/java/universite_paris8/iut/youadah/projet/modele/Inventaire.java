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

    public List<Objet> getInventaire() {
        return inventaire;
    }
}
