package universite_paris8.iut.youadah.projet.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import universite_paris8.iut.youadah.projet.controller.gestionnaire.GestionEffetDegats;
import universite_paris8.iut.youadah.projet.controller.gestionnaire.GestionnaireEntrees;
import universite_paris8.iut.youadah.projet.controller.gestionnaire.GestionnaireJeu;
import universite_paris8.iut.youadah.projet.modele.Armes.Fleche;
import universite_paris8.iut.youadah.projet.modele.actions.Taper;
import universite_paris8.iut.youadah.projet.modele.systeme.CaseInventaire;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    @FXML private TilePane tileMap;
    @FXML private Pane playerLayer;
    @FXML private Label messageMort;
    @FXML private Button boutonQuitter;
    @FXML private Button boutonReapparaitre;
    @FXML private Pane overlayRouge;
    @FXML private Pane ath;

    private final GaussianBlur effetFlou = new GaussianBlur(10);

    private GestionnaireJeu gestionnaire;
    private GestionnaireEntrees entrees;
    private boolean estMort = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gestionnaire = new GestionnaireJeu();
        gestionnaire.initialiserTout(tileMap, playerLayer, ath, overlayRouge, this::mourir);

        entrees = new GestionnaireEntrees(
                gestionnaire.getInitialiseurJoueur().getJoueur(),
                gestionnaire.getInitialiseurInventaire().getInventaire()
        );

        configurerActions();
        configurerEvenements();

        gestionnaire.demarrerBoucle(() -> entrees.appliquerMouvements());

        playerLayer.setFocusTraversable(true);
        playerLayer.requestFocus();
    }

    private void configurerActions() {
        entrees.getTouches().definirActionRamasser(this::ramasserObjet);
        entrees.getTouches().definirActionDeposer(this::deposerObjet);
        entrees.getTouches().definirActionBasculerCraft(() ->
                gestionnaire.getInitialiseurCraft().basculerVisibilite()
        );
        entrees.getTouches().definirActionSelection(this::selectionnerSlot);

        entrees.getSouris().definirActionAttaqueEpee(this::attaquerAvecEpee);
        entrees.getSouris().definirActionTirArc(this::tirerFleche);
        entrees.getSouris().definirActionUtilisationObjet(this::utiliserObjet);
    }

    private void configurerEvenements() {
        playerLayer.setOnKeyPressed(entrees::traiterAppuiTouche);
        playerLayer.setOnKeyReleased(entrees::traiterRelachementTouche);
        ath.setOnMouseClicked(entrees::traiterClicSouris);
    }

    private void ramasserObjet() {
        var joueur = gestionnaire.getInitialiseurJoueur().getJoueur();
        var environnement = gestionnaire.getEnvironnement();
        var inventaire = gestionnaire.getInitialiseurInventaire().getInventaire();

        if (joueur.ramasser(environnement, inventaire)) {
            gestionnaire.getInitialiseurInventaire().getInventaireVue().maj();
        }
    }

    private void deposerObjet() {
        var joueur = gestionnaire.getInitialiseurJoueur().getJoueur();
        if (joueur.getObjetPossede() == null) return;

        var inventaire = gestionnaire.getInitialiseurInventaire().getInventaire();
        CaseInventaire caseReelle = inventaire.trouverCase(joueur.getObjetPossede());
        if (caseReelle == null) return;

        joueur.deposerObjetEnMain(gestionnaire.getEnvironnement());
        caseReelle.decrementerQuantite(1);

        if (caseReelle.estVide()) {
            inventaire.getInventaire().remove(caseReelle);
            joueur.setObjetPossede(null);
        }

        gestionnaire.getInitialiseurJoueur().getJoueurVue().mettreAJourJoueur(joueur);
        rafraichirInventaire();
    }

    private void selectionnerSlot(KeyCode touche) {
        int index = touche.ordinal() - KeyCode.F1.ordinal();
        var inventaire = gestionnaire.getInitialiseurInventaire().getInventaire();

        if (index >= 0 && index < inventaire.getInventaire().size()) {
            var joueur = gestionnaire.getInitialiseurJoueur().getJoueur();
            joueur.setObjetPossede(inventaire.getInventaire().get(index).getObjet());

            var selection = gestionnaire.getInitialiseurInventaire().getSelectionInventaire();
            selection.setX((index * 64) + 730);
            ath.getChildren().remove(selection);
            ath.getChildren().add(selection);
        }
    }

    private void attaquerAvecEpee() {
        var joueur = gestionnaire.getInitialiseurJoueur().getJoueur();
        var ennemie = gestionnaire.getInitialiseurEnnemis().getEnnemie();

        Taper taper = new Taper(joueur, List.of(ennemie), overlayRouge);
        taper.executer();


        GestionEffetDegats.getInstance().declencherClignotementRouge();


        var barreVie = gestionnaire.getInitialiseurEnnemis().getBarreVieEnnemi();
        barreVie.mettreAJourPv(ennemie.getVie().getPv());

        if (ennemie.getVie().estMort()) {
            playerLayer.getChildren().removeAll(
                    gestionnaire.getInitialiseurEnnemis().getEnnemieVue().getNode(),
                    barreVie.getNode()
            );
        }
    }

    private void tirerFleche(double cibleX, double cibleY) {
        var joueur = gestionnaire.getInitialiseurJoueur().getJoueur();
        var ennemie = gestionnaire.getInitialiseurEnnemis().getEnnemie();
        var carte = gestionnaire.getInitialiseurCarte().getCarte();

        Fleche fleche = new Fleche(
                joueur.getX(), joueur.getY(),
                cibleX, cibleY,
                List.of(ennemie),
                overlayRouge,
                2,
                carte
        );
        playerLayer.getChildren().add(fleche.getNode());
        fleche.startAnimation();
    }

    private void utiliserObjet(CaseInventaire caseUtilisee, int tuileX, int tuileY) {
        caseUtilisee.getObjet().utiliser(tuileX, tuileY);

        var inventaire = gestionnaire.getInitialiseurInventaire().getInventaire();
        CaseInventaire caseReelle = inventaire.trouverCase(caseUtilisee.getObjet());

        if (caseReelle != null && entrees.getSouris().doitConsommerObjet(caseReelle)) {
            caseReelle.decrementerQuantite(1);

            if (caseReelle.estVide()) {
                inventaire.getInventaire().remove(caseReelle);
                gestionnaire.getInitialiseurJoueur().getJoueur().setObjetPossede(null);
            }
        }

        var coeurVue = gestionnaire.getInitialiseurJoueur().getCoeurVue();
        var joueur = gestionnaire.getInitialiseurJoueur().getJoueur();
        coeurVue.mettreAJourPv(joueur.getVie().getPv());

        rafraichirInventaire();
    }

    private void rafraichirInventaire() {
        var bouclier = gestionnaire.getInitialiseurJoueur().getBouclierVue();
        var selection = gestionnaire.getInitialiseurInventaire().getSelectionInventaire();
        var craft = gestionnaire.getInitialiseurCraft().getPanneauCraft();

        ath.getChildren().removeIf(node ->
                node != craft && node != bouclier.getBarreBouclier() && node != selection
        );

        var inventaireVue = gestionnaire.getInitialiseurInventaire().getInventaireVue();
        inventaireVue.afficherInventaire();
        inventaireVue.maj();

        if (!ath.getChildren().contains(craft)) {
            ath.getChildren().add(craft);
        }
    }

    private void mourir() {
        if (estMort) return;

        estMort = true;
        messageMort.setVisible(true);
        boutonQuitter.setVisible(true);
        boutonReapparaitre.setVisible(true);

        var joueurVue = gestionnaire.getInitialiseurJoueur().getJoueurVue();
        joueurVue.getNode().setVisible(false);

        tileMap.setEffect(effetFlou);
        playerLayer.setEffect(effetFlou);
        gestionnaire.pauserJeu();
    }

    @FXML
    private void reapparaitre() {
        estMort = false;
        gestionnaire.reinitialiserJoueur(ath, playerLayer);

        boutonQuitter.setVisible(false);
        boutonReapparaitre.setVisible(false);
        messageMort.setVisible(false);
        tileMap.setEffect(null);
        playerLayer.setEffect(null);

        rafraichirInventaire();
        entrees.reinitialiser();
        gestionnaire.reprendreJeu();
    }

    @FXML
    private void quitterJeu() {
        System.exit(0);
    }
}