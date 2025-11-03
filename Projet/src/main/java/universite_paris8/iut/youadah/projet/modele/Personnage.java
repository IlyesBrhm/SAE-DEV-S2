// Personnage.java
package universite_paris8.iut.youadah.projet.modele;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Personnage {

    private final DoubleProperty x = new SimpleDoubleProperty();
    private final DoubleProperty y = new SimpleDoubleProperty();

    private char direction = 'i'; // 'd' pour droite 'g' pour gauche 'i' pour immobile

    private double vitesseY;
    protected boolean versLaDroite;
    private GameMap carte;

    private boolean auSol;
    public static final double SAUT = -3.5;
    public static final double VITESSE = 2;
    public static final int TAILLE_TUILE = 32;

    private Vie vie;

    public Personnage(double startX, double startY) {
        this.x.set(startX);
        this.y.set(startY);
        this.vitesseY = 0;
        this.versLaDroite = true;
        this.carte = null;
        this.vie = new Vie();
    }

    public void setCarte(GameMap carte) {
        this.carte = carte;
    }

    public double getX() { return x.get(); }
    public void setX(double x) { this.x.set(x); }
    public DoubleProperty xProperty() { return x; }

    public double getY() { return y.get(); }
    public void setY(double y) { this.y.set(y); }
    public DoubleProperty yProperty() { return y; }

    public boolean estsVersLaDroite() { return versLaDroite; }
    public Vie getVie() { return vie; }

    public void allerGauche() {
        this.direction = 'g';
    }

    public void allerDroite() {
        this.direction = 'd';
    }

    public void immobile() {
        this.direction = 'i';
    }

    public void sauter() {
        if (auSol) {
            vitesseY = SAUT;
            auSol = false;
        }
    }

    public void deplacer() {
        switch (this.direction) {
            case 'd': deplacerDroite(); break;
            case 'g': deplacerGauche(); break;
        }
    }

    /**
     * Déplace le personnage vers la gauche avec une vitesse personnalisée
     */
    protected void deplacerGauche(double vitesse) {
        if (carte == null) return;

        double futurX = getX() - vitesse;
        int tuileX = (int)(futurX / TAILLE_TUILE);
        int tuileY = (int)((getY() + TAILLE_TUILE - 1) / TAILLE_TUILE);

        if (tuileX >= 0 && !carte.estSolide(carte.getTile(tuileY, tuileX))) {
            setX(futurX);
            versLaDroite = false;
        }
    }

    /**
     * Déplace le personnage vers la droite avec une vitesse personnalisée
     */
    protected void deplacerDroite(double vitesse) {
        if (carte == null) return;

        double futurX = getX() + vitesse;
        int tuileX = (int)((futurX + TAILLE_TUILE - 1) / TAILLE_TUILE);
        int tuileY = (int)((getY() + TAILLE_TUILE - 1) / TAILLE_TUILE);

        if (tuileX < carte.getLargeur() && !carte.estSolide(carte.getTile(tuileY, tuileX))) {
            setX(futurX);
            versLaDroite = true;
        }
    }

    public void deplacerGauche() {
        deplacerGauche(VITESSE);
    }

    public void deplacerDroite() {
        deplacerDroite(VITESSE);
    }

    /**
     * Vérifie s'il y a un trou devant le personnage
     */
    protected boolean aTrouDevant() {
        if (carte == null) return false;

        int tuileY = (int)((getY() + TAILLE_TUILE - 1) / TAILLE_TUILE);
        int futurTuileX;

        if (versLaDroite) {
            futurTuileX = (int)((getX() + TAILLE_TUILE) / TAILLE_TUILE);
        } else {
            futurTuileX = (int)((getX() - 1) / TAILLE_TUILE);
        }

        if (futurTuileX < 0 || futurTuileX >= carte.getLargeur()) {
            return true;
        }

        return !carte.estSolide(carte.getTile(tuileY + 1, futurTuileX));
    }

    /**
     * Vérifie s'il y a un obstacle devant le personnage
     */
    protected boolean aObstacleDevant() {
        if (carte == null) return true;

        int tuileY = (int)((getY() + TAILLE_TUILE - 1) / TAILLE_TUILE);
        int futurTuileX;

        if (versLaDroite) {
            futurTuileX = (int)((getX() + TAILLE_TUILE) / TAILLE_TUILE);
            if (futurTuileX >= carte.getLargeur()) return true;
        } else {
            futurTuileX = (int)((getX() - 1) / TAILLE_TUILE);
            if (futurTuileX < 0) return true;
        }

        return carte.estSolide(carte.getTile(tuileY, futurTuileX));
    }

    /**
     * Applique la gravité si le personnage n'est pas au sol
     */
    protected void appliquerGravite() {
        if (carte == null) return;

        int tuileX = (int)(getX() / TAILLE_TUILE);
        int tuileY = (int)((getY() + TAILLE_TUILE - 1) / TAILLE_TUILE);

        if (!carte.estSolide(carte.getTile(tuileY + 1, tuileX))) {
            setY(getY() + 0.4);
        }
    }

    public void mettreAJour(GameMap map) {
        this.carte = map;
        if (carte == null) return;

        final double GRAVITE = 0.08;
        double nouvelleY = getY();
        double nouvelleVitesseY = this.vitesseY + GRAVITE;
        nouvelleY += nouvelleVitesseY;

        int tuileXG = (int)(getX() / TAILLE_TUILE);
        int tuileXD = (int)((getX() + TAILLE_TUILE - 1) / TAILLE_TUILE);

        boolean auSolTemp = false;

        if (nouvelleVitesseY > 0) {
            // Collision vers le bas (chute)
            int tuileYBas = (int)((nouvelleY + TAILLE_TUILE) / TAILLE_TUILE);
            if (tuileYBas < carte.getHauteur()) {
                boolean solG = carte.estSolide(carte.getTile(tuileYBas, tuileXG));
                boolean solD = carte.estSolide(carte.getTile(tuileYBas, tuileXD));
                if (solG || solD) {
                    nouvelleY = (tuileYBas - 1) * TAILLE_TUILE;
                    nouvelleVitesseY = 0;
                    auSolTemp = true;
                }
            }
        } else if (nouvelleVitesseY < 0) {
            // Collision vers le haut (saut sous un bloc)
            int tuileYHaut = (int)(nouvelleY / TAILLE_TUILE);
            if (tuileYHaut >= 0) {
                boolean hautG = carte.estSolide(carte.getTile(tuileYHaut, tuileXG));
                boolean hautD = carte.estSolide(carte.getTile(tuileYHaut, tuileXD));
                if (hautG || hautD) {
                    nouvelleY = (tuileYHaut + 1) * TAILLE_TUILE;
                    nouvelleVitesseY = 0;
                }
            }
        }

        // Limites verticales
        double hauteurMax = carte.getHauteur() * TAILLE_TUILE - TAILLE_TUILE;
        if (nouvelleY > hauteurMax) {
            nouvelleY = hauteurMax;
            nouvelleVitesseY = 0;
            auSolTemp = true;
        }

        if (nouvelleY < 0) {
            nouvelleY = 0;
            nouvelleVitesseY = 0;
        }

        setY(nouvelleY);
        this.vitesseY = nouvelleVitesseY;
        this.auSol = auSolTemp;
    }
}