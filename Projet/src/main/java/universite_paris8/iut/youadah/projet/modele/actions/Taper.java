// Taper.java
package universite_paris8.iut.youadah.projet.modele.actions;

import universite_paris8.iut.youadah.projet.controller.GestionEffetDegats;
import javafx.scene.layout.Pane;
import universite_paris8.iut.youadah.projet.modele.Armes.Epee;
import universite_paris8.iut.youadah.projet.modele.Personnage;
import universite_paris8.iut.youadah.projet.modele.Player;

import java.util.List;

/**
 * Classe représentant l'action de taper avec une épée.
 * Permet au joueur d'attaquer des cibles situées devant lui dans une certaine portée.
 */
public class Taper {

    private static final double PORTEE_EPEE = 40.0;
    private static final int DEGATS_EPEE = 1;


    /**
     * Tente d'attaquer des cibles avec l'épée du joueur.
     * Vérifie si le joueur possède une épée et si les cibles sont dans la portée d'attaque.
     *
     * @param joueur Le joueur qui attaque.
     * @param cibles La liste des cibles potentielles (Personnage).
     * @param overlay La superposition pour les effets visuels (rouge).
     */
    public void attaquerAvecEpee(Player joueur, List<Personnage> cibles, Pane overlay) {
        Object objet = joueur.getObjetPossede();
        if (objet == null || !(objet instanceof Epee)) return;

        double positionXJoueur = joueur.getX();
        double positionYJoueur = joueur.getY();
        boolean regardeVersLaDroite = joueur.estsVersLaDroite();

        for (Personnage cible : cibles) {
            if (cible == joueur) continue;

            double positionXCible = cible.getX();
            double positionYCible = cible.getY();

            boolean cibleDevant = regardeVersLaDroite
                    ? positionXCible > positionXJoueur && positionXCible < positionXJoueur + PORTEE_EPEE
                    : positionXCible < positionXJoueur && positionXCible > positionXJoueur - PORTEE_EPEE;

            boolean memeHauteur = Math.abs(positionYCible - positionYJoueur) < 32;

            if (cibleDevant && memeHauteur) {
                System.out.println("TOUCHÉ !");
                infligerDegats(cible);
                GestionEffetDegats.definirSuperposition(overlay);
                GestionEffetDegats.declencherClignotementRouge();
            }

        }
    }

    /**
     * Inflige des dégâts à la cible.
     * Si la cible a de l'armure, les dégâts sont soustraits de l'armure,
     * sinon ils sont soustraits des points de vie.
     *
     * @param cible La cible à attaquer (Personnage).
     */
    private void infligerDegats(Personnage cible) {
        if (cible.getPvArmure() > 0) {
            cible.decrementerPvArmure(DEGATS_EPEE);
        } else {
            cible.decrementerPv(DEGATS_EPEE);
        }
    }
} // Fin Taper.java
