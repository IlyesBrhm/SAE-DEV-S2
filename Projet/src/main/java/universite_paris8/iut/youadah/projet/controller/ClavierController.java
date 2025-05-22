package universite_paris8.iut.youadah.projet.controller;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import universite_paris8.iut.youadah.projet.modele.Map;
import universite_paris8.iut.youadah.projet.modele.Player;
import universite_paris8.iut.youadah.projet.vue.CoeurVue;
import universite_paris8.iut.youadah.projet.vue.PlayerVue;

import java.util.Set;

public class ClavierController {

    private final Set<KeyCode> touchesAppuyees;
    private final Player joueur;
    private final PlayerVue joueurVue;
    private final CoeurVue coeurVue;
    private final Pane playerLayer;
    private final Runnable callbackMort;
    private final Runnable afficherDegat;
    private final Map carte;

    public ClavierController(Set<KeyCode> touchesAppuyees,
                             Player joueur,
                             PlayerVue joueurVue,
                             CoeurVue coeurVue,
                             Pane playerLayer,
                             Runnable callbackMort,
                             Runnable afficherDegat,
                             Map carte) {
        this.touchesAppuyees = touchesAppuyees;
        this.joueur = joueur;
        this.joueurVue = joueurVue;
        this.coeurVue = coeurVue;
        this.playerLayer = playerLayer;
        this.callbackMort = callbackMort;
        this.afficherDegat = afficherDegat;
        this.carte = carte;
    }

    public void gererTouches() {
        if (touchesAppuyees.contains(KeyCode.Q) || touchesAppuyees.contains(KeyCode.LEFT)) {
            joueur.deplacerGauche(carte);
        }
        if (touchesAppuyees.contains(KeyCode.D) || touchesAppuyees.contains(KeyCode.RIGHT)) {
            joueur.deplacerDroite(carte);
        }
        if (touchesAppuyees.contains(KeyCode.Z) || touchesAppuyees.contains(KeyCode.SPACE)) {
            joueur.sauter();
        }

        joueur.mettreAJour(carte);
        joueurVue.mettreAJourJoueur(joueur);

        // 🔥 Dégâts du feu (type de tuile = 5)
        int tuileX = (int)(joueur.getX() / 32);
        int tuileY = (int)(joueur.getY() / 32);

        if (carte.getTile(tuileY, tuileX) == 5 && !joueur.estMort()) {
            long maintenant = System.currentTimeMillis();
            if (maintenant - joueur.getDernierDegatFeu() > 1000) {
                joueur.decrementerPv(1);
                joueur.setDernierDegatFeu(maintenant);
                coeurVue.mettreAJourPv(joueur.getPv());
                afficherDegat.run(); // Déclenche GestionEffetDegats
                if (joueur.getPv() <= 0) {
                    callbackMort.run();
                }
            }
        }
    }

    public void configurerControles() {
        playerLayer.setFocusTraversable(true);

        playerLayer.setOnKeyPressed(event -> touchesAppuyees.add(event.getCode()));
        playerLayer.setOnKeyReleased(event -> touchesAppuyees.remove(event.getCode()));
    }
}
