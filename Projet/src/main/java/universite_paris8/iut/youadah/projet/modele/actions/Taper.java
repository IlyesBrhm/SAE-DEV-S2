package universite_paris8.iut.youadah.projet.modele.actions;

import universite_paris8.iut.youadah.projet.controller.GestionEffetDegats;
import javafx.scene.layout.Pane;
import universite_paris8.iut.youadah.projet.modele.Armes.Epee;
import universite_paris8.iut.youadah.projet.modele.Player;

import java.util.List;

public class Taper {

    private static final double PORTEE_EPEE = 40.0; // portée en px
    private static final int DEGATS_EPEE = 1;

    public Taper() {
    }

    public void attaquerAvecEpee(Player joueur, List<Player> ennemis, Pane overlay) {
        Object objet = joueur.getObjetPossede();

        if (objet == null || !(objet instanceof Epee)) { return;}

        double positionXJoueur = joueur.getX();
        double positionYJoueur = joueur.getY();
        boolean regardeVersLaDroite = joueur.estsVersLaDroite();

        for (Player ennemi : ennemis) {
            if (ennemi == joueur) {
                continue; //pas s’attaquer lui même
            }

            double positionXEnnemi = ennemi.getX();
            double positionYEnnemi = ennemi.getY();

            // Vérifie si l’ennemi est dans la portée de l’épée
            boolean ennemiEstDevant = false;
            if (regardeVersLaDroite) {
                //droite
                ennemiEstDevant = positionXEnnemi > positionXJoueur && positionXEnnemi < positionXJoueur + PORTEE_EPEE;
            } else {
                //gauche
                ennemiEstDevant = positionXEnnemi < positionXJoueur && positionXEnnemi > positionXJoueur - PORTEE_EPEE;
            }

            // même hauteur que le joueur ?
            boolean ennemiSurMemeHauteur = Math.abs(positionYEnnemi - positionYJoueur) < 32;

            if (ennemiEstDevant && ennemiSurMemeHauteur) {
                infligerDegats(ennemi);
                GestionEffetDegats.definirSuperposition(overlay);
                GestionEffetDegats.declencherClignotementRouge();
            }
        }
    }

    private void infligerDegats(Player cible) {
        if (cible.getPvArmure() > 0) {
            cible.decrementerPvArmure(DEGATS_EPEE);
        } else {
            cible.decrementerPv(DEGATS_EPEE);
        }
    }
}