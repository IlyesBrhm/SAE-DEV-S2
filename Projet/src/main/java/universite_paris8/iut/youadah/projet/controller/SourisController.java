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




    /**
     * Constructeur du contrôleur de la souris.
     *
     * @param joueur Le joueur principal.
     * @param ennemie L'ennemi ciblé.
     * @param ennemieVue La vue de l'ennemi.
     * @param coeurVue La vue des cœurs du joueur.
     * @param playerLayer La couche où le joueur et les ennemis sont affichés.
     * @param inventaire L'inventaire du joueur.
     * @param inventaireVue La vue de l'inventaire.
     * @param ath La zone d'action du joueur (barre d'outils).
     * @param carte La carte du jeu.
     * @param overlayRouge L'overlay rouge pour les effets visuels.
     */
    public SourisController( Player joueur,
                             Ennemie ennemie,
                             EnnemieVue ennemieVue,
                             CoeurVue coeurVue,
                             Pane playerLayer,
                             Inventaire inventaire,
                             InventaireVue inventaireVue,
                             Pane ath,
                             GameMap carte,
                             Pane overlayRouge) {
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

    /**
     * Gère les interactions de la souris pour attaquer un ennemi ou utiliser un objet.
     *
     * @param barreVieEnnemi La vue de la barre de vie de l'ennemi.
     * @param carteVue La vue de la carte du jeu.
     */
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

    /**
     * Tire une flèche vers une cible spécifiée.
     *
     * @param cibleX La position X de la cible.
     * @param cibleY La position Y de la cible.
     * @param couche La couche où la flèche sera affichée.
     */
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
