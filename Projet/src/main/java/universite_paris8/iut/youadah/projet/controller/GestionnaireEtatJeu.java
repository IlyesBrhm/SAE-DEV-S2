package universite_paris8.iut.youadah.projet.controller;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import universite_paris8.iut.youadah.projet.modele.Player;
import universite_paris8.iut.youadah.projet.vue.CoeurVue;
import universite_paris8.iut.youadah.projet.vue.PlayerVue;

public class GestionnaireEtatJeu {

    private final Player joueur;
    private final PlayerVue joueurVue;
    private final CoeurVue coeurVue;
    private final CoeurVue coeurVueArmure;
    private final Label messageMort;
    private final Button boutonQuitter;
    private final Button boutonReapparaitre;
    private final TilePane tileMap;
    private final Pane coucheJoueur;
    private final GaussianBlur flou;
    private boolean estMort = false;

    public GestionnaireEtatJeu(Player joueur, PlayerVue joueurVue, CoeurVue coeurVue, CoeurVue coeurVueArmure,
                               Label messageMort, Button boutonQuitter, Button boutonReapparaitre,
                               TilePane tileMap, Pane coucheJoueur, GaussianBlur flou) {
        this.joueur = joueur;
        this.joueurVue = joueurVue;
        this.coeurVue = coeurVue;
        this.coeurVueArmure = coeurVueArmure;
        this.messageMort = messageMort;
        this.boutonQuitter = boutonQuitter;
        this.boutonReapparaitre = boutonReapparaitre;
        this.tileMap = tileMap;
        this.coucheJoueur = coucheJoueur;
        this.flou = flou;
    }

    public void mourir() {
        if (estMort) return;
        estMort = true;
        messageMort.setVisible(true);
        boutonQuitter.setVisible(true);
        boutonReapparaitre.setVisible(true);
        joueurVue.getNode().setVisible(false);
        tileMap.setEffect(flou);
        coucheJoueur.setEffect(flou);
    }

    public void reapparaitre() {
        estMort = false;
        joueur.reinitialiser(5 * 32, 19 * 32);
        joueurVue.remettrePersoDeBase(joueur);
        coeurVue.mettreAJourPv(joueur.getPv());
        coeurVueArmure.mettreAJourPv(joueur.getPvArmure());
        boutonQuitter.setVisible(false);
        boutonReapparaitre.setVisible(false);
        messageMort.setVisible(false);
        tileMap.setEffect(null);
        coucheJoueur.setEffect(null);
        joueurVue.getNode().setVisible(true);
    }

    public boolean estMort() {
        return estMort;
    }
}