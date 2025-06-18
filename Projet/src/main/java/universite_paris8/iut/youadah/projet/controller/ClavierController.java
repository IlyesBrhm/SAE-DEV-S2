// ClavierController.java
package universite_paris8.iut.youadah.projet.controller;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import universite_paris8.iut.youadah.projet.modele.*;
import universite_paris8.iut.youadah.projet.vue.*;

import java.util.Set;

public class ClavierController {

    private final Set<KeyCode> touchesAppuyees;
    private  Player joueur;
    private  PlayerVue joueurVue;
    private final Ennemie ennemie;
    private final EnnemieVue ennemieVue;
    private  CoeurVue coeurVue;
    private  BouclierVue bouclierVue;
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

        // Gestion collision entre joueur et ennemie
        if ((int) ennemie.getX() == (int) joueur.getX() && (int) ennemie.getY() == (int) joueur.getY()) {
            long maintenant = System.currentTimeMillis();
            if (maintenant - joueur.getDernierCoupRecu() > 1000) {
                ennemie.attaque(carte);  // au lieu de juste ennemie.attaque()

                joueur.setDernierCoupRecu(maintenant);
            }
        }

        // Mise à jour des barres de vie
        coeurVue.mettreAJourPv(joueur.getPv());
        bouclierVue.mettreAJourPv(joueur.getPvArmure());

        // Dégâts de feu si le joueur est sur un bloc feu (5)
        int tuileX = (int) (joueur.getX() / 32);
        int tuileY = (int) (joueur.getY() / 32);

        if (carte.getTile(tuileY, tuileX) == 5 && !joueur.estMort()) {
            maintenant = System.currentTimeMillis();
            if (maintenant - joueur.getDernierDegatFeu() > 1000) {
                joueur.setDernierDegatFeu(maintenant);

                if (joueur.getPvArmure() > 0) {
                    joueur.decrementerPvArmure(1);
                    bouclierVue.mettreAJourPv(joueur.getPvArmure());
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
    public void setJoueur(Player joueur) {
        this.joueur = joueur;
    }

    public void setJoueurVue(PlayerVue joueurVue) {
        this.joueurVue = joueurVue;
    }

    public void setCoeurVue(CoeurVue coeurVue) {
        this.coeurVue = coeurVue;
    }

    public void setBouclierVue(BouclierVue bouclierVue) {
        this.bouclierVue = bouclierVue;
    }

} // Fin ClavierController propre
