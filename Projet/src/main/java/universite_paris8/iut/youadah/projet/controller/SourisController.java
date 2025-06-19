// ClavierController.java
package universite_paris8.iut.youadah.projet.controller;

import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import universite_paris8.iut.youadah.projet.modele.*;
import universite_paris8.iut.youadah.projet.modele.Armes.Arc;
import universite_paris8.iut.youadah.projet.modele.Armes.Fleche;
import universite_paris8.iut.youadah.projet.modele.actions.Taper;
import universite_paris8.iut.youadah.projet.vue.*;

import java.util.List;
import java.util.Set;

import static universite_paris8.iut.youadah.projet.modele.Personnage.TAILLE_TUILE;

public class SourisController {

    private final Set<KeyCode> touchesAppuyees;
    private  Player joueur;
    private final Ennemie ennemie;
    private final EnnemieVue ennemieVue;
    private  CoeurVue coeurVue;
    private final Pane playerLayer;
    private Inventaire inventaire;
    private InventaireVue inventaireVue;
    private Pane ath;

    private final GameMap carte;
    private Pane overlayRouge;




    public SourisController(Set<KeyCode> touchesAppuyees,
                             Player joueur,
                             Ennemie ennemie,
                             EnnemieVue ennemieVue,
                             CoeurVue coeurVue,
                             Pane playerLayer,
                             Inventaire inventaire,
                             InventaireVue inventaireVue,
                             Pane ath,
                             GameMap carte,
                             Pane overlayRouge) {
        this.touchesAppuyees = touchesAppuyees;
        this.joueur = joueur;
        this.ennemie = ennemie;
        this.ennemieVue = ennemieVue;
        this.coeurVue = coeurVue;
        this.playerLayer = playerLayer;
        this.inventaire = inventaire;
        this.inventaireVue = inventaireVue;
        this.ath = ath;
        this.carte = carte;
        this.overlayRouge = overlayRouge;
    }

    public void gestionSouris(BarreDeVieVue barreVieEnnemi, MapVue carteVue) {
        ath.setOnMouseClicked(event -> {
            double cibleX = event.getX();
            double cibleY = event.getY();
            Objet objetUtilise = joueur.getObjetPossede();

            Taper taper = new Taper();
            taper.attaquerAvecEpee(joueur, List.of(ennemie), overlayRouge);
            barreVieEnnemi.mettreAJourPv(ennemie.getPv());

            if (ennemie.estMort()) {
                playerLayer.getChildren().removeAll(ennemieVue.getNode(), barreVieEnnemi.getNode());
            }

            if (objetUtilise != null) {
                if (objetUtilise instanceof Arc) {
                    tirerFleche(cibleX, cibleY, playerLayer);
                } else {
                    objetUtilise.utiliser((int)(cibleX / TAILLE_TUILE), (int)(cibleY / TAILLE_TUILE), carteVue);
                    if (objetUtilise instanceof Bloc || objetUtilise.getConsomable()) {
                        objetUtilise.decrementerQuantite(1);
                        if (objetUtilise.getQuantite() <= 0) {
                            inventaire.getInventaire().remove(objetUtilise);
                            joueur.setObjetPossede(null);
                        }
                    }
                    coeurVue.mettreAJourPv(joueur.getPv());
                }

                ath.getChildren().clear();
                inventaireVue.afficherInventaire();
                inventaireVue.maj();
            }

        });
    }

    public void tirerFleche(double cibleX, double cibleY, Pane couche) {
        Fleche fleche = new Fleche(
                joueur.getX(), joueur.getY(),
                cibleX, cibleY,
                List.of(ennemie),
                overlayRouge,
                2,
                carte // <--- passe ta GameMap ici
        );


        couche.getChildren().add(fleche.getNode());
        fleche.startAnimation();
    }



}
