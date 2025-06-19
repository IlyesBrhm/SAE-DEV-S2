package universite_paris8.iut.youadah.projet.modele;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe qui gère l'inventaire du joueur
 * Permet de stocker, ajouter et retirer des objets
 */
public class Inventaire {
    // Liste qui contient les objets de l'inventaire
    private List<Objet> inventaire;

    /**
     * Constructeur de l'inventaire
     * Initialise une liste vide avec une capacité de 6 objets
     */
    public Inventaire() {
        inventaire = new ArrayList<>(6);
    }

    /**
     * Ajoute un objet à l'inventaire
     * Si l'objet existe déjà, incrémente sa quantité
     * Sinon, ajoute l'objet si l'inventaire n'est pas plein
     * 
     * @param nouvelObjet L'objet à ajouter à l'inventaire
     * @return true si l'objet a été ajouté, false sinon
     */
    public boolean ajouterObjet(Objet nouvelObjet) {
        // Vérifie si l'objet existe déjà dans l'inventaire
        for (Objet objet : inventaire) {
            if (objet.equals(nouvelObjet)) {
                // Si l'objet existe, augmente sa quantité
                objet.incrementerQuantite(nouvelObjet.getQuantite());
                return true;
            }
        }

        // Si l'inventaire n'est pas plein, ajoute le nouvel objet
        if (inventaire.size() < 6) {
            inventaire.add(nouvelObjet);
            return true;
        } else {
            // Si l'inventaire est plein, affiche un message et retourne false
            System.out.println("Inventaire plein");
            return false;
        }
    }

    /**
     * Retire un objet de l'inventaire
     * Si la quantité est supérieure à 1, décrémente la quantité
     * Sinon, retire complètement l'objet de l'inventaire
     * 
     * @param objetARetirer L'objet à retirer de l'inventaire
     */
    public void retirerObjet(Objet objetARetirer) {
        // Parcourt l'inventaire pour trouver l'objet à retirer
        for (int i = 0; i < inventaire.size(); i++) {
            Objet objet = inventaire.get(i);
            if (objet.equals(objetARetirer)) {
                // Si la quantité est supérieure à 1, décrémente
                if (objet.getQuantite() > 1) {
                    objet.decrementerQuantite(1);
                } else {
                    // Sinon, retire complètement l'objet
                    inventaire.remove(i);
                }
                return;
            }
        }
    }

    /**
     * Retourne la liste des objets dans l'inventaire
     * 
     * @return La liste des objets dans l'inventaire
     */
    public List<Objet> getInventaire() {
        return inventaire;
    }
}
