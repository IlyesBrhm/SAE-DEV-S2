package universite_paris8.iut.youadah.projet.modele;

import javafx.scene.image.Image;

/**
 * Représente le joueur dans le jeu avec ses mouvements, sa position, et son apparence.
 */
public class Player {

    public double x, y;            // Position du joueur
    private double vitesseY;       // Vitesse verticale (gravité)
    private boolean auSol;         // Le joueur est-il sur une tuile solide ?
    private boolean versLaDroite;  // Direction du regard
    private int pv;

    private Image spriteDroite;
    private Image spriteGauche;

    private static final double GRAVITE = 0.05;
    private static final double SAUT = -3.5;
    private static final double VITESSE = 1.0;
    private static final int TAILLE_TUILE = 32;

    public Player(double startX, double startY) {
        this.x = startX;
        this.y = startY;
        this.vitesseY = 0;
        this.versLaDroite = true;
        pv = 5;


        spriteDroite = new Image(getClass().getResource("/images/PersoRight.png").toExternalForm());
        spriteGauche = new Image(getClass().getResource("/images/PersoLeft.png").toExternalForm());
    }

    public double getX() {
        return x-16;
    }

    public double getY() {
        return y;
    }

    public Image getSpriteActuel() {
        return versLaDroite ? spriteDroite : spriteGauche;
    }

    public void mettreAJour(Map carte) {
        vitesseY += GRAVITE;
        y += vitesseY;

        // Deux tuiles horizontales sous le joueur (pied gauche et pied droit)
        int tuileXGauche = (int)(x / TAILLE_TUILE);
        int tuileXDroite = (int)((x + TAILLE_TUILE - 1) / TAILLE_TUILE);
        int tuileY = (int)((y + TAILLE_TUILE - 1) / TAILLE_TUILE); // pieds

        boolean collisionGauche = estSolide(carte.getTile(tuileY, tuileXGauche));
        boolean collisionDroite = estSolide(carte.getTile(tuileY, tuileXDroite));

        if ((tuileY < carte.getHauteur()) && (collisionGauche || collisionDroite)) {
            y = tuileY * TAILLE_TUILE - TAILLE_TUILE;
            vitesseY = 0;
            auSol = true;
        } else {
            auSol = false;
        }

        // Empêche de sortir en bas
        double hauteurMax = (carte.getHauteur() * TAILLE_TUILE) - TAILLE_TUILE;
        if (y > hauteurMax) {
            y = hauteurMax;
            vitesseY = 0;
            auSol = true;
        }

        // Empêche de sortir en haut
        if (y < 0) {
            y = 0;
            vitesseY = 0;
        }
    }


    public void sauter() {
        if (auSol) {
            vitesseY = SAUT;
            auSol = false;
        }
    }

    public void deplacerGauche(Map carte) {
        double futurX = x - VITESSE;
        int tuileX = (int)(futurX / TAILLE_TUILE);
        int tuileY = (int)((y + TAILLE_TUILE - 1) / TAILLE_TUILE);

        if (tuileX >= 0 && !estSolide(carte.getTile(tuileY, tuileX))) {
            x = futurX;
            versLaDroite = false;
        }
    }

    public void deplacerDroite(Map carte) {
        double futurX = x+ VITESSE;
        int tuileX = (int)((futurX + TAILLE_TUILE - 1) / TAILLE_TUILE);
        int tuileY = (int)((y + TAILLE_TUILE - 1) / TAILLE_TUILE);

        if (tuileX < carte.getLargeur() && !estSolide(carte.getTile(tuileY, tuileX))) {
            x = futurX;
            versLaDroite = true;
        }
    }

    public void incrementerPv(int pvEnPlus){
        pv = pv+pvEnPlus;
    }

    public void decrementerPv(int pvEnMoin){
        pv = pv-pvEnMoin;
    }

    private boolean estSolide(int id) {
        return id == 1 || id == 3;
    }

    public int getPv() {
        return pv;
    }
}