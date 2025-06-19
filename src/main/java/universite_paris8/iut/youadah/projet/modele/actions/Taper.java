// Taper.java
package universite_paris8.iut.youadah.projet.modele.actions;

// Importation des classes nécessaires
import universite_paris8.iut.youadah.projet.controller.GestionEffetDegats;
import javafx.scene.layout.Pane;
import universite_paris8.iut.youadah.projet.modele.Armes.Epee;
import universite_paris8.iut.youadah.projet.modele.Personnage;
import universite_paris8.iut.youadah.projet.modele.Player;

import java.util.List;

/**
 * Classe représentant l'action de taper avec une épée.
 * Elle vérifie si une cible est dans la portée de l'épée et inflige des dégâts.
 */
public class Taper {

    // Portée d'attaque de l'épée (en pixels)
    private static final double PORTEE_EPEE = 40.0;

    // Dégâts infligés par l'épée
    private static final int DEGATS_EPEE = 1;

    // Constructeur par défaut
    public Taper() {}

    /**
     * Méthode principale pour attaquer avec une épée.
     * Vérifie si le joueur possède une épée, et tente de frapper les cibles à proximité.
     *
     * @param joueur Le joueur qui attaque
     * @param cibles La liste des personnages potentiellement touchés
     * @param overlay Pane utilisé pour afficher un effet visuel de blessure
     */
    public void attaquerAvecEpee(Player joueur, List<Personnage> cibles, Pane overlay) {
        // Récupère l'objet que possède le joueur
        Object objet = joueur.getObjetPossede();

        // Si le joueur ne possède pas d'objet ou que ce n'est pas une épée, on quitte
        if (objet == null || !(objet instanceof Epee)) return;

        // Récupère la position du joueur
        double positionXJoueur = joueur.getX();
        double positionYJoueur = joueur.getY();

        // Vérifie si le joueur regarde vers la droite
        boolean regardeVersLaDroite = joueur.estsVersLaDroite();

        // Parcourt toutes les cibles possibles
        for (Personnage cible : cibles) {
            if (cible == joueur) continue; // Ignore le joueur lui-même

            // Récupère la position de la cible
            double positionXCible = cible.getX();
            double positionYCible = cible.getY();

            // Vérifie si la cible est devant le joueur, en fonction de l'orientation
            boolean cibleDevant = regardeVersLaDroite
                    ? positionXCible > positionXJoueur && positionXCible < positionXJoueur + PORTEE_EPEE
                    : positionXCible < positionXJoueur && positionXCible > positionXJoueur - PORTEE_EPEE;

            // Vérifie que la cible est à peu près à la même hauteur (ex : 32 pixels de tolérance)
            boolean memeHauteur = Math.abs(positionYCible - positionYJoueur) < 32;

            // Si la cible est dans la zone de frappe
            if (cibleDevant && memeHauteur) {
                System.out.println("TOUCHÉ !");
                infligerDegats(cible); // Applique les dégâts

                // Affiche un effet rouge pour signaler un coup
                GestionEffetDegats.definirSuperposition(overlay);
                GestionEffetDegats.declencherClignotementRouge();
            }
        }
    }

    /**
     * Inflige les dégâts à un personnage : commence par l'armure, puis la vie.
     *
     * @param cible Le personnage touché
     */
    private void infligerDegats(Personnage cible) {
        if (cible.getPvArmure() > 0) {
            cible.decrementerPvArmure(DEGATS_EPEE); // Dégâts absorbés par l'armure
        } else {
            cible.decrementerPv(DEGATS_EPEE); // Sinon, on touche directement la vie
        }
    }
} // Fin Taper.java
