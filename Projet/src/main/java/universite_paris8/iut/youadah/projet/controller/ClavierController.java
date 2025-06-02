package universite_paris8.iut.youadah.projet.controller;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import universite_paris8.iut.youadah.projet.modele.*;
import universite_paris8.iut.youadah.projet.vue.*;

import java.util.Set;

public class ClavierController {

    private final Set<KeyCode> touchesAppuyees;
    private final Player joueur;
    private final PlayerVue joueurVue;
    private final CoeurVue coeurVue;
    private final CoeurVue coeurVueArmure;
    private final Pane playerLayer;
    private final Runnable callbackMort;
    private final Runnable afficherDegat;
    private final GameMap carte;
    private final Inventaire inventaire;
    private final InventaireVue inventaireVue;
    private final Pane ath;
    private final TableCraftVue tableCraftVue;

    private boolean craftVisible = false;
    private final ImageView imageSelect;

    public ClavierController(Set<KeyCode> touchesAppuyees,
                             Player joueur,
                             PlayerVue joueurVue,
                             CoeurVue coeurVue,
                             CoeurVue coeurVueArmure,
                             Pane playerLayer,
                             Runnable callbackMort,
                             Runnable afficherDegat,
                             GameMap carte,
                             Inventaire inventaire,
                             InventaireVue inventaireVue,
                             Pane ath,
                             TableCraftVue tableCraftVue) {
        this.touchesAppuyees = touchesAppuyees;
        this.joueur = joueur;
        this.joueurVue = joueurVue;
        this.coeurVue = coeurVue;
        this.coeurVueArmure = coeurVueArmure;
        this.playerLayer = playerLayer;
        this.callbackMort = callbackMort;
        this.afficherDegat = afficherDegat;
        this.carte = carte;
        this.inventaire = inventaire;
        this.inventaireVue = inventaireVue;
        this.ath = ath;
        this.tableCraftVue = tableCraftVue;

        Image image = new Image(getClass().getResource("/images/inventory selected.png").toExternalForm());
        imageSelect = new ImageView(image);
        imageSelect.setFitHeight(64);
        imageSelect.setFitWidth(64);
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

        int tuileX = (int) (joueur.getX() / 32);
        int tuileY = (int) (joueur.getY() / 32);

        if (carte.getTile(tuileY, tuileX) == 5 && !joueur.estMort()) {
            long maintenant = System.currentTimeMillis();
            if (maintenant - joueur.getDernierDegatFeu() > 1000) {
                joueur.setDernierDegatFeu(maintenant);

                if (joueur.getPvArmure() > 0) {
                    joueur.decrementerPvArmure(1);
                    coeurVueArmure.mettreAJourPv(joueur.getPvArmure());
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
    }

    public void configurerControles() {
        playerLayer.setFocusTraversable(true);

        playerLayer.setOnKeyPressed(event -> {
            touchesAppuyees.add(event.getCode());
            switch (event.getCode()) {
                case K -> {
                    joueur.decrementerPv(1);
                    coeurVue.mettreAJourPv(joueur.getPv());
                }
                case G -> {
                    joueur.incrementerPv(1);
                    coeurVue.mettreAJourPv(joueur.getPv());
                }
                case C -> {
                    craftVisible = !craftVisible;
                    tableCraftVue.getPane().setVisible(craftVisible);
                    if (craftVisible) tableCraftVue.afficher();
                }
                case ESCAPE -> tableCraftVue.getPane().setVisible(false);
                case F1, F2, F3, F4, F5, F6 -> {
                    int index = event.getCode().ordinal() - KeyCode.F1.ordinal();
                    if (index < inventaire.getInventaire().size()) {
                        joueur.setObjetPossede(inventaire.getInventaire().get(index));
                        imageSelect.setX((index * 64) + 730);
                        ath.getChildren().remove(imageSelect);
                        ath.getChildren().add(imageSelect);
                    }
                }
            }
        });

        playerLayer.setOnKeyReleased(event -> touchesAppuyees.remove(event.getCode()));

        ath.setOnMouseClicked(event -> {
            int x = (int) (event.getX() / 32);
            int y = (int) (event.getY() / 32);
            Objet objet = joueur.getObjetPossede();
            if (objet != null) {
                objet.utiliser(x, y);
                if (objet.getConsomable()) {
                    inventaire.getInventaire().remove(objet);
                    joueur.setObjetPossede(null);
                    ath.getChildren().clear();
                    inventaireVue.afficherInventaire();
                    inventaireVue.maj();
                }
                coeurVue.mettreAJourPv(joueur.getPv());
            }
        });
    }
}