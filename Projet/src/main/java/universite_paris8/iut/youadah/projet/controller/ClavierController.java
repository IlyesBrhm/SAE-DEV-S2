// Contrôleur qui gère les entrées clavier et met à jour les entités du jeu (joueur, ennemie, etc.)
package universite_paris8.iut.youadah.projet.controller;

import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import universite_paris8.iut.youadah.projet.modele.*;
import universite_paris8.iut.youadah.projet.vue.*;

import java.util.Set;

public class ClavierController {

    // Ensemble des touches actuellement appuyées
    private final Set<KeyCode> touchesAppuyees;

    // Références vers les modèles et vues du joueur et de l'ennemi
    private  Player joueur;
    private  PlayerVue joueurVue;
    private final Ennemie ennemie;
    private final EnnemieVue ennemieVue;

    // Vues pour les points de vie (cœurs) et l’armure (bouclier)
    private  CoeurVue coeurVue;
    private  BouclierVue bouclierVue;

    // Calque sur lequel sont placés les joueurs et éléments visuels
    private final Pane playerLayer;

    //Vue pour les objets au sol (ramassable)
    private ObjetAuSol objetAuSol;

    // Actions à exécuter en cas de mort ou de dégâts subis
    private final Runnable callbackMort;
    private final Runnable afficherDegat;

    private boolean craftVisible = false;
    private Inventaire inventaire;
    private InventaireVue inventaireVue;
    private Pane paneCraft;
    private Pane ath;
    private TableCraftVue tableCraftVue;

    private final GameMap carte;


    /**
     * Constructeur du contrôleur clavier.
     * @param touchesAppuyees
     * @param joueur
     * @param joueurVue
     * @param ennemie
     * @param ennemieVue
     * @param coeurVue
     * @param bouclierVue
     * @param objetAuSol
     * @param playerLayer
     * @param inventaire
     * @param inventaireVue
     * @param paneCraft
     * @param ath
     * @param tableCraftVue
     * @param callbackMort
     * @param afficherDegat
     * @param carte
     */
    public ClavierController(Set<KeyCode> touchesAppuyees,
                             Player joueur,
                             PlayerVue joueurVue,
                             Ennemie ennemie,
                             EnnemieVue ennemieVue,
                             CoeurVue coeurVue,
                             BouclierVue bouclierVue,
                             ObjetAuSol objetAuSol,
                             Pane playerLayer,
                             Inventaire inventaire,
                             InventaireVue inventaireVue,
                             Pane paneCraft,
                             Pane ath,
                             TableCraftVue tableCraftVue,

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
        this.inventaire = inventaire;
        this.inventaireVue = inventaireVue;
        this.paneCraft = paneCraft;
        this.ath = ath;
        this.tableCraftVue = tableCraftVue;

        this.objetAuSol = objetAuSol;
        this.callbackMort = callbackMort;
        this.afficherDegat = afficherDegat;
        this.carte = carte;
    }

    /**
     * Gère les touches appuyées et met à jour les entités du jeu.
     * @param imageView ImageView pour afficher l'objet possédé par le joueur
     */

    public void gererTouches(ImageView imageView) {
        for (KeyCode key : touchesAppuyees) {
            switch (key) {
                case Q, LEFT -> joueur.deplacerGauche(carte);
                case D, RIGHT -> joueur.deplacerDroite(carte);
                case Z, SPACE -> joueur.sauter();

                case E -> {
                    if (objetAuSol.ramasser(joueur, inventaire, playerLayer)) {
                        inventaireVue.maj();
                    }
                }

                case A -> {
                    Objet obj = joueur.getObjetPossede();
                    if (obj != null) {
                        int q = obj.getQuantite();
                        obj.setQuantite(1);
                        objetAuSol.deposerJoueur(obj, joueur, playerLayer);
                        obj.setQuantite(q - 1);

                        if (q <= 1) {
                            inventaire.getInventaire().remove(obj);
                            joueur.setObjetPossede(null);
                        } else {
                            joueur.setObjetPossede(obj);
                        }

                        joueurVue.mettreAJourJoueur();
                        ath.getChildren().clear();
                        inventaireVue.afficherInventaire();
                        inventaireVue.maj();
                    }
                }

                case C -> {
                    craftVisible = !craftVisible;

                    if (!ath.getChildren().contains(paneCraft)) {
                        ath.getChildren().add(paneCraft);
                    }

                    paneCraft.setVisible(craftVisible);
                    if (craftVisible) {
                        System.out.println("→ Affichage table de craft");
                        tableCraftVue.afficher();
                    }
                }

                case F1, F2, F3, F4, F5, F6 -> {
                    int index = key.ordinal() - KeyCode.F1.ordinal();
                    if (index < inventaire.getInventaire().size()) {
                        joueur.setObjetPossede(inventaire.getInventaire().get(index));
                        imageView.setX((index * 64) + 730);
                        ath.getChildren().remove(imageView);
                        ath.getChildren().add(imageView);
                    }
                }

                default -> {
                    // Rien à faire
                }
            }
        }

        joueur.mettreAJour(carte);
        joueurVue.mettreAJourJoueur();

        ennemieVue.mettreAJour(ennemie);
        ennemie.deplacementMob(carte);
        ennemie.mettreAJour(carte);

        if ((int) ennemie.getX() == (int) joueur.getX() && (int) ennemie.getY() == (int) joueur.getY()) {
            long maintenant = System.currentTimeMillis();
            if (maintenant - joueur.getDernierCoupRecu() > 1000) {
                ennemie.attaque(carte);
                joueur.setDernierCoupRecu(maintenant);
            }
        }

        coeurVue.mettreAJourPv(joueur.getPv());
        bouclierVue.mettreAJourPv(joueur.getPvArmure());

        int tuileX = (int) (joueur.getX() / 32);
        int tuileY = (int) (joueur.getY() / 32);

        if (carte.getTile(tuileY, tuileX) == 5 && !joueur.estMort()) {
            long maintenant = System.currentTimeMillis();
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

    /**
     * Configure les contrôles du clavier pour le joueur.
     * Définit les événements de pression et de relâchement des touches.
     */
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
