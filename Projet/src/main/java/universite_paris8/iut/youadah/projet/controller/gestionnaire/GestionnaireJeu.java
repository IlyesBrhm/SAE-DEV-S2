package universite_paris8.iut.youadah.projet.controller.gestionnaire;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import universite_paris8.iut.youadah.projet.environnement.Environnement;
import universite_paris8.iut.youadah.projet.environnement.gestionnaireInitialiseur.*;

public class GestionnaireJeu {

    private static final int HAUTEUR_CARTE = 32;
    private static final int LARGEUR_CARTE = 58;

    private final InitialiseurCarte initialiseurCarte;
    private final InitialiseurJoueur initialiseurJoueur;
    private final InitialiseurEnnemis initialiseurEnnemis;
    private final InitialiseurInventaire initialiseurInventaire;
    private final InitialiseurCraft initialiseurCraft;
    private final InitialiseurEffetDegats initialiseurEffetDegats;

    private Environnement environnement;
    private AnimationTimer boucleJeu;
    private boolean jeuActif = true;

    public GestionnaireJeu() {

        this.initialiseurCarte = new InitialiseurCarte(HAUTEUR_CARTE, LARGEUR_CARTE);
        this.initialiseurJoueur = new InitialiseurJoueur();
        this.initialiseurEnnemis = new InitialiseurEnnemis();
        this.initialiseurInventaire = new InitialiseurInventaire();
        this.initialiseurCraft = new InitialiseurCraft();
        this.initialiseurEffetDegats = new InitialiseurEffetDegats();
    }

    public void initialiserTout(TilePane carteTuiles, Pane calqueCouche,
                                Pane interfaceJeu, Pane superpositionRouge,
                                Runnable actionMort) {
        initialiseurCarte.initialiser(carteTuiles);

        initialiseurJoueur.initialiser(
                initialiseurCarte.getCarte(),
                calqueCouche,
                interfaceJeu
        );

        initialiseurEnnemis.initialiser(
                initialiseurCarte.getCarte(),
                initialiseurJoueur.getJoueur(),
                calqueCouche
        );

        environnement = new Environnement(
                calqueCouche,
                initialiseurCarte.getCarte(),
                initialiseurJoueur.getJoueur(),
                initialiseurEnnemis.getEnnemie(),
                initialiseurJoueur.getJoueurVue(),
                initialiseurEnnemis.getEnnemieVue(),
                initialiseurEnnemis.getBarreVieEnnemi(),
                initialiseurJoueur.getCoeurVue(),
                initialiseurJoueur.getBouclierVue(),
                superpositionRouge,
                actionMort,
                initialiseurEffetDegats.getGestionEffetDegats()::declencherClignotementRouge
        );


        initialiseurInventaire.initialiser(
                interfaceJeu,
                initialiseurCarte.getCarte(),
                initialiseurCarte.getCarteVue(),
                initialiseurJoueur.getJoueur(),
                environnement,
                calqueCouche
        );

        initialiseurCraft.initialiser(
                interfaceJeu,
                initialiseurInventaire.getInventaire(),
                initialiseurInventaire.getInventaireVue(),
                initialiseurCarte.getCarte(),
                initialiseurCarte.getCarteVue(),
                initialiseurJoueur.getJoueur(),
                environnement,
                calqueCouche
        );

        initialiseurEffetDegats.initialiser(superpositionRouge);

    }

    public void demarrerBoucle(Runnable actionTouches) {
        boucleJeu = new AnimationTimer() {
            @Override
            public void handle(long maintenant) {
                if (jeuActif) {
                    actionTouches.run();
                    environnement.unTour();
                }
            }
        };
        boucleJeu.start();
    }

    public void arreterBoucle() {
        if (boucleJeu != null) {
            boucleJeu.stop();
        }
    }

    public void pauserJeu() {
        jeuActif = false;
    }

    public void reprendreJeu() {
        jeuActif = true;
    }

    public void reinitialiserJoueur(Pane interfaceJeu, Pane calqueCouche) {
        initialiseurJoueur.reinitialiser(
                initialiseurCarte.getCarte(),
                interfaceJeu
        );

        initialiseurInventaire.reinitialiser(
                initialiseurCarte.getCarte(),
                initialiseurCarte.getCarteVue(),
                initialiseurJoueur.getJoueur(),
                environnement,
                calqueCouche
        );

        calqueCouche.getChildren().setAll(
                initialiseurJoueur.getBouclierVue().getBarreBouclier(),
                initialiseurJoueur.getJoueurVue().getNode(),
                initialiseurJoueur.getCoeurVue().getBarreVie(),
                initialiseurEnnemis.getEnnemieVue().getNode(),
                initialiseurEnnemis.getBarreVieEnnemi().getNode()
        );

        jeuActif = true;
    }

    public InitialiseurCarte getInitialiseurCarte() {
        return initialiseurCarte;
    }

    public InitialiseurJoueur getInitialiseurJoueur() {
        return initialiseurJoueur;
    }

    public InitialiseurEnnemis getInitialiseurEnnemis() {
        return initialiseurEnnemis;
    }

    public InitialiseurInventaire getInitialiseurInventaire() {
        return initialiseurInventaire;
    }

    public InitialiseurCraft getInitialiseurCraft() {
        return initialiseurCraft;
    }

    public Environnement getEnvironnement() {
        return environnement;
    }

    public boolean estJeuActif() {
        return jeuActif;
    }
}