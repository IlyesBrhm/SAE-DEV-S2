package universite_paris8.iut.youadah.projet.modele;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant l'inventaire du joueur.
 * Il peut contenir jusqu'à 6 objets.
 */

public class Inventaire {
    private List<Objet> inventaire;


    /**
     * Constructeur de la classe Inventaire.
     * Initialise l'inventaire avec une capacité maximale de 6 objets.
     * @param inventaire La liste d'objets qui compose l'inventaire.
     */
    public Inventaire() {
        inventaire = new ArrayList<>(6);
    }

    /**
     * Ajoute un nouvel objet à l'inventaire.
     * Si l'objet existe déjà, sa quantité est augmentée.
     * Si l'inventaire est plein, un message d'erreur est affiché.
     *
     * @param nouvelObjet L'objet à ajouter à l'inventaire.
     * @return true si l'objet a été ajouté ou mis à jour, false si l'inventaire est plein.
     */
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
