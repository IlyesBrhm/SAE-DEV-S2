package universite_paris8.iut.youadah.projet.controller;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import universite_paris8.iut.youadah.projet.modele.Ennemie;
import universite_paris8.iut.youadah.projet.modele.GameMap;
import universite_paris8.iut.youadah.projet.modele.Player;
import universite_paris8.iut.youadah.projet.vue.BouclierVue;
import universite_paris8.iut.youadah.projet.vue.CoeurVue;
import universite_paris8.iut.youadah.projet.vue.EnnemieVue;
import universite_paris8.iut.youadah.projet.vue.PlayerVue;

import java.util.Set;

public class ClavierController {

    private final Set<KeyCode> touchesAppuyees;
    private final Player joueur;
    private final PlayerVue joueurVue;
    private final Ennemie ennemie;
    private final EnnemieVue ennemieVue;
    private final CoeurVue coeurVue;
    private final BouclierVue bouclierVue;
    private final Pane playerLayer;
    private final Runnable callbackMort;
    private final Runnable afficherDegat;
    private final GameMap carte;
    private long maintenant;

    public ClavierController(Set<KeyCode> touchesAppuyees,
                             Player joueur,
                             PlayerVue joueurVue,
                             Ennemie ennemie,
                             EnnemieVue ennemieVue,
                             CoeurVue coeurVue,
                             BouclierVue bouclierVue,
                             Pane playerLayer,
                             Runnable callbackMort,
                             Runnable afficherDegat,
                             GameMap carte) {
        this.touchesAppuyees = touchesAppuyees;
        this.joueur = joueur;
        this.joueurVue = joueurVue;
        this.ennemie = ennemie;
        this.ennemieVue = ennemieVue;
        this.coeurVue = coeurVue;
        this.bouclierVue = bouclierVue;
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


        ennemieVue.mettreAJour(ennemie);
        ennemie.deplacementMob(carte);
        ennemie.mettreAJour(carte);
        if ((int) ennemie.getX() == (int) joueur.getX() && (int) ennemie.getY() == (int) joueur.getY()) {
            long maintenant = System.currentTimeMillis();
            if (maintenant - joueur.getDernierCoupRecu() > 1000) { // 1000ms = 1 seconde
                ennemie.attaque();  // Fait perdre des PV au joueur
                joueur.setDernierCoupRecu(maintenant); // On met à jour le dernier coup reçu
            }
        }

        coeurVue.mettreAJourPv(joueur.getPv());
        bouclierVue.mettreAJourPv(joueur.getPvArmure());  // Changé ici

        int tuileX = (int) (joueur.getX() / 32);
        int tuileY = (int) (joueur.getY() / 32);

        if (carte.getTile(tuileY, tuileX) == 5 && !joueur.estMort()) {
            maintenant = System.currentTimeMillis();
            if (maintenant - joueur.getDernierDegatFeu() > 1000) {
                joueur.setDernierDegatFeu(maintenant);

                if (joueur.getPvArmure() > 0) {
                    joueur.decrementerPvArmure(1);
                    bouclierVue.mettreAJourPv(joueur.getPvArmure());  // Changé ici
                } else {
                    joueur.decrementerPv(1);
                    coeurVue.mettreAJourPv(joueur.getPv());
                }

                afficherDegat.run();

                if (joueur.getPv() <= 0) {
                    callbackMort.run();
                }
            }
        }
        if (joueur.getPv() <= 0) {
            callbackMort.run();
        }
    }

    public void configurerControles() {
        playerLayer.setFocusTraversable(true);

        playerLayer.setOnKeyPressed(event -> touchesAppuyees.add(event.getCode()));
        playerLayer.setOnKeyReleased(event -> touchesAppuyees.remove(event.getCode()));
    }
}