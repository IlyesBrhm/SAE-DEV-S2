package universite_paris8.iut.youadah.projet.modele;

import javafx.scene.layout.Pane;
import universite_paris8.iut.youadah.projet.controller.GestionEffetDegats;

import java.util.List;

public class Tirer {

    public Tirer() {}

    public void attaquerAvecArc(Player joueur, List<Player> ennemis, Pane overlay, Arc arc) {
        double positionXJoueur = joueur.getX();
        double positionYJoueur = joueur.getY();
        boolean regardeVersLaDroite = joueur.estsVersLaDroite();

        for (Player ennemi : ennemis) {
            if (ennemi == joueur) continue;

            double positionXEnnemi = ennemi.getX();
            double positionYEnnemi = ennemi.getY();

            boolean ennemiEstDansLaPortee = false;

            if (regardeVersLaDroite) {
                // Le joueur regarde vers la droite
                if (positionXEnnemi > positionXJoueur && positionXEnnemi <= positionXJoueur + arc.getPortee()) {
                    ennemiEstDansLaPortee = true;
                }
            } else {
                // Le joueur regarde vers la gauche
                if (positionXEnnemi < positionXJoueur && positionXEnnemi >= positionXJoueur - arc.getPortee()) {
                    ennemiEstDansLaPortee = true;
                }
            }

            boolean surMemeHauteur = Math.abs(positionYEnnemi - positionYJoueur) < 32;

            if (ennemiEstDansLaPortee && surMemeHauteur) {
                infligerDegats(ennemi, arc.getDegats());
                GestionEffetDegats.definirSuperposition(overlay);
                GestionEffetDegats.declencherClignotementRouge();
                break; // une cible a la fois
            }
        }
    }

    private void infligerDegats(Player cible, int degats) {
        if (cible.getPvArmure() > 0) {
            cible.decrementerPvArmure(degats);
        } else {
            cible.decrementerPv(degats);
        }
    }
}
