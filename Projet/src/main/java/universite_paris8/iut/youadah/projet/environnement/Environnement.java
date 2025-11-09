package universite_paris8.iut.youadah.projet.environnement;

import javafx.scene.layout.Pane;
import universite_paris8.iut.youadah.projet.modele.entite.Ennemie;
import universite_paris8.iut.youadah.projet.modele.entite.Player;
import universite_paris8.iut.youadah.projet.modele.monde.GameMap;
import universite_paris8.iut.youadah.projet.modele.monde.ObjetAuSol;
import universite_paris8.iut.youadah.projet.modele.objet.Objet;
import universite_paris8.iut.youadah.projet.modele.systeme.CaseInventaire;
import universite_paris8.iut.youadah.projet.modele.systeme.Inventaire;
import universite_paris8.iut.youadah.projet.vue.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Environnement {

    private static final int TAILLE_TUILE = 32;

    private final Pane playerLayer;
    private final List<ObjetAuSol> objetsAuSol;
    private final GameMap carte;
    private final Player joueur;
    private final Ennemie ennemie;
    private final PlayerVue joueurVue;
    private final EnnemieVue ennemieVue;
    private final BarreDeVieVue barreVieEnnemi;
    private final CoeurVue coeurVue;
    private final BouclierVue bouclierVue;
    private final Pane overlayRouge;
    private final Runnable callbackMort;
    private final Runnable afficherDegat;

    private long dernierCoupEnnemi = 0;
    private final long delaiEntreCoups = 1_000_000_000;
    private long dernierDegatFeu = 0;
    private final long delaiDegatFeu = 1_000_000_000;

    public Environnement(Pane playerLayer, GameMap carte, Player joueur, Ennemie ennemie,
                         PlayerVue joueurVue, EnnemieVue ennemieVue, BarreDeVieVue barreVieEnnemi,
                         CoeurVue coeurVue, BouclierVue bouclierVue, Pane overlayRouge,
                         Runnable callbackMort, Runnable afficherDegat) {
        this.playerLayer = playerLayer;
        this.objetsAuSol = new ArrayList<>();
        this.carte = carte;
        this.joueur = joueur;
        this.ennemie = ennemie;
        this.joueurVue = joueurVue;
        this.ennemieVue = ennemieVue;
        this.barreVieEnnemi = barreVieEnnemi;
        this.coeurVue = coeurVue;
        this.bouclierVue = bouclierVue;
        this.overlayRouge = overlayRouge;
        this.callbackMort = callbackMort;
        this.afficherDegat = afficherDegat;
    }

    public void unTour() {
        joueur.mettreAJour(carte);
        joueurVue.mettreAJourJoueur(joueur);

        ennemieVue.mettreAJour(ennemie);
        ennemie.deplacementMob(carte);
        ennemie.mettreAJour(carte);

        double distance = Math.hypot(joueur.getX() - ennemie.getX(), joueur.getY() - ennemie.getY());
        long maintenant = System.nanoTime();
        if (!ennemie.getVie().estMort() && distance < 32 && maintenant - dernierCoupEnnemi > delaiEntreCoups) {
            ennemie.attaque(carte);
            dernierCoupEnnemi = maintenant;
        }

        int tuileX = (int) (joueur.getX() / TAILLE_TUILE);
        int tuileY = (int) (joueur.getY() / TAILLE_TUILE);
        if (carte.getTile(tuileY, tuileX) == 5 && !joueur.getVie().estMort() && maintenant - dernierDegatFeu > delaiDegatFeu) {
            appliquerDegatFeu();
            dernierDegatFeu = maintenant;
        }

        coeurVue.mettreAJourPv(joueur.getVie().getPv());
        bouclierVue.mettreAJourPv(joueur.getVie().getPvArmure());
        barreVieEnnemi.mettreAJourPv(ennemie.getVie().getPv());

        if (joueur.getVie().getPv() <= 0) {
            callbackMort.run();
        }
        if (ennemie.getVie().estMort()) {
            playerLayer.getChildren().removeAll(ennemieVue.getNode(), barreVieEnnemi.getNode());
        }
    }

    private void appliquerDegatFeu() {
        if (joueur.getVie().getPvArmure() > 0) {
            joueur.getVie().decrementerPvArmure(1);
        } else {
            joueur.getVie().decrementerPv(1);
        }
        afficherDegat.run();
    }

    public ObjetAuSol deposer(Objet objet, int xTuile, int yTuile) {
        ObjetAuSol o = new ObjetAuSol(xTuile, yTuile, playerLayer, objet);
        objetsAuSol.add(o);
        return o;
    }

    public ObjetAuSol deposerDepuisJoueur(Objet objet, Player joueur) {
        int x = (int) (joueur.getX() / TAILLE_TUILE);
        int y = (int) (joueur.getY() / TAILLE_TUILE);
        return deposer(objet, x, y);
    }

    public boolean ramasserSurTuile(int xTuile, int yTuile, Inventaire inventaire) {
        boolean ramasse = false;
        Iterator<ObjetAuSol> it = objetsAuSol.iterator();
        while (it.hasNext()) {
            ObjetAuSol o = it.next();

            if (o.getXTuile() == xTuile && o.getYTuile() == yTuile) {
                boolean ajoute = inventaire.ajouterObjet(new CaseInventaire(o.getObjet()));
                if (ajoute) {
                    playerLayer.getChildren().remove(o.getObjetVue().getImageView());
                    it.remove();
                    ramasse = true;
                }
            }
        }
        return ramasse;
    }

    public boolean ramasserAutourDuJoueur(Player joueur, Inventaire inventaire) {
        boolean ramasse = false;
        Iterator<ObjetAuSol> it = objetsAuSol.iterator();

        while (it.hasNext()) {
            ObjetAuSol o = it.next();

            double objetX = o.getXTuile() * TAILLE_TUILE;
            double objetY = o.getYTuile() * TAILLE_TUILE;

            double distance = Math.hypot(joueur.getX() - objetX, joueur.getY() - objetY);

            if (distance < 40) {
                boolean ajoute = inventaire.ajouterObjet(new CaseInventaire(o.getObjet()));
                if (ajoute) {
                    playerLayer.getChildren().remove(o.getObjetVue().getImageView());
                    it.remove();
                    ramasse = true;
                    break;
                }
            }
        }

        return ramasse;
    }
}