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

    private Image spriteDroite;
    private Image spriteGauche;

    private static final double GRAVITE = 0.05;
    private static final double SAUT = -5;
    private static final double VITESSE = 1.3;
    private static final int TAILLE_TUILE = 32;

    public Player(double startX, double startY) {
        this.x = startX;
        this.y = startY;
        this.vitesseY = 0;
        this.versLaDroite = true;

        // Chargement des sprites
        spriteDroite = new Image(getClass().getResource("/images/PersoRight.png").toExternalForm());
        spriteGauche = new Image(getClass().getResource("/images/PersoLeft.png").toExternalForm());
    }

    public double getX() {
        return x;
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

        int tuileX = (int)(x / TAILLE_TUILE);
        int tuileY = (int)((y + TAILLE_TUILE) / TAILLE_TUILE); // Position des pieds

        if (tuileY < carte.getHauteur() && tuileX >= 0 && tuileX < carte.getLargeur()) {
            int idTuile = carte.getTile(tuileY, tuileX);
            if (estSolide(idTuile)) {
                y = tuileY * TAILLE_TUILE - TAILLE_TUILE;
                vitesseY = 0;
                auSol = true;
            } else {
                auSol = false;
            }
        } else {
            auSol = false;
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
        double futurX = x + VITESSE;
        int tuileX = (int)((futurX + TAILLE_TUILE - 1) / TAILLE_TUILE);
        int tuileY = (int)((y + TAILLE_TUILE - 1) / TAILLE_TUILE);

        if (tuileX < carte.getLargeur() && !estSolide(carte.getTile(tuileY, tuileX))) {
            x = futurX;
            versLaDroite = true;
        }
    }

    private boolean estSolide(int id) {
        return id == 1 || id == 2; // Herbe ou terre
    }
}
