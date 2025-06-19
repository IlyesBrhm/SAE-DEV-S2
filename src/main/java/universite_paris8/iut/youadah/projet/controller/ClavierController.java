// Contrôleur qui gère les entrées clavier et met à jour les entités du jeu (joueur, ennemie, etc.)
package universite_paris8.iut.youadah.projet.controller;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import universite_paris8.iut.youadah.projet.modele.*;
import universite_paris8.iut.youadah.projet.vue.*;

import java.util.Set;

public class ClavierController {

    // Ensemble des touches actuellement appuyées
    private final Set<KeyCode> touchesAppuyees;

    // Références vers les modèles et vues du joueur et de l'ennemi
    private Player joueur;
    private PlayerVue joueurVue;
    private final Ennemie ennemie;
    private final EnnemieVue ennemieVue;

    // Vues pour les points de vie (cœurs) et l’armure (bouclier)
    private CoeurVue coeurVue;
    private BouclierVue bouclierVue;

    // Calque sur lequel sont placés les joueurs et éléments visuels
    private final Pane playerLayer;

    // Actions à exécuter en cas de mort ou de dégâts subis
    private final Runnable callbackMort;
    private final Runnable afficherDegat;

    // Référence à la carte du jeu (pour collision, type de bloc, etc.)
    private final GameMap carte;

    // Constructeur de la classe : injection de toutes les dépendances nécessaires
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

    // Méthode principale appelée à chaque "frame" ou boucle du jeu pour traiter les entrées et mises à jour
    public void gererTouches() {

        // Gestion des déplacements du joueur selon les touches appuyées
        if (touchesAppuyees.contains(KeyCode.Q) || touchesAppuyees.contains(KeyCode.LEFT)) {
            joueur.deplacerGauche(carte);  // Aller à gauche
        }
        if (touchesAppuyees.contains(KeyCode.D) || touchesAppuyees.contains(KeyCode.RIGHT)) {
            joueur.deplacerDroite(carte);  // Aller à droite
        }
        if (touchesAppuyees.contains(KeyCode.Z) || touchesAppuyees.contains(KeyCode.SPACE)) {
            joueur.sauter();  // Sauter
        }

        // Mise à jour des positions physiques et de la vue du joueur
        joueur.mettreAJour(carte);
        joueurVue.mettreAJourJoueur(joueur);

        // Mise à jour de l’ennemie : déplacement, logique, animation
        ennemieVue.mettreAJour(ennemie);
        ennemie.deplacementMob(carte);
        ennemie.mettreAJour(carte);

        // --- Gestion collision entre joueur et ennemie ---
        // Vérifie si les coordonnées du joueur et de l’ennemie sont identiques (approche basique)
        if ((int) ennemie.getX() == (int) joueur.getX() && (int) ennemie.getY() == (int) joueur.getY()) {
            long maintenant = System.currentTimeMillis();  // Temps actuel en millisecondes

            // Délai minimum d'1 seconde entre deux attaques pour éviter spam de dégâts
            if (maintenant - joueur.getDernierCoupRecu() > 1000) {
                ennemie.attaque(carte);  // L’ennemie inflige des dégâts
                joueur.setDernierCoupRecu(maintenant);  // Enregistre le moment de l’attaque
            }
        }

        // --- Mise à jour des barres de vie (UI) ---
        coeurVue.mettreAJourPv(joueur.getPv());
        bouclierVue.mettreAJourPv(joueur.getPvArmure());

        // --- Gestion des dégâts si le joueur marche sur un bloc de feu (valeur 5 sur la carte) ---
        int tuileX = (int) (joueur.getX() / 32);  // Coordonnée de la tuile X
        int tuileY = (int) (joueur.getY() / 32);  // Coordonnée de la tuile Y

        // Si c’est une tuile feu et que le joueur n’est pas mort
        if (carte.getTile(tuileY, tuileX) == 5 && !joueur.estMort()) {
            long maintenant = System.currentTimeMillis();

            // Délai minimum d'1 seconde entre deux brûlures
            if (maintenant - joueur.getDernierDegatFeu() > 1000) {
                joueur.setDernierDegatFeu(maintenant);  // Met à jour le dernier moment où il a pris feu

                // Priorité : d’abord endommager l’armure, ensuite les points de vie
                if (joueur.getPvArmure() > 0) {
                    joueur.decrementerPvArmure(1);
                    bouclierVue.mettreAJourPv(joueur.getPvArmure());
                } else {
                    joueur.decrementerPv(1);
                    coeurVue.mettreAJourPv(joueur.getPv());
                }

                afficherDegat.run();  // Déclenche l’effet visuel ou sonore de dégâts

                // Si les points de vie tombent à 0 ou moins → fin du jeu
                if (joueur.getPv() <= 0) {
                    callbackMort.run();  // Exécute le code associé à la mort
                }
            }
        }

        // Sécurité supplémentaire : si le joueur est mort par un autre moyen
        if (joueur.getPv() <= 0) {
            callbackMort.run();
        }
    }

    // Méthode pour lier les événements clavier au calque graphique principal
    public void configurerControles() {
        playerLayer.setFocusTraversable(true);  // Autorise le calque à recevoir le focus clavier

        // Ajoute la touche appuyée à l’ensemble
        playerLayer.setOnKeyPressed(event -> touchesAppuyees.add(event.getCode()));

        // Retire la touche relâchée de l’ensemble
        playerLayer.setOnKeyReleased(event -> touchesAppuyees.remove(event.getCode()));
    }

    // Méthodes pour modifier dynamiquement les références du joueur et des vues
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

} // Fin de la classe ClavierController
