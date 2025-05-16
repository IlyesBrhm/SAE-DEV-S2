package universite_paris8.iut.youadah.projet.modele;

import javafx.scene.image.Image;

public class Player {

    private double x, y;            // Position
    private double vitesseY;
    private boolean auSol;
    private boolean versLaDroite;
    private int pv;

    private final Image spriteDroite;
    private final Image spriteGauche;

    private static final double SAUT = -3.5;
    private static final double VITESSE = 1.0;
    private static final int TAILLE_TUILE = 32;

    public Player(double startX, double startY) {
        this.x = startX;
        this.y = startY;
        this.vitesseY = 0;
        this.versLaDroite = true;
        this.pv = 5;

        spriteDroite = new Image(getClass().getResource("/images/PersoRight.png").toExternalForm());
        spriteGauche = new Image(getClass().getResource("/images/PersoLeft.png").toExternalForm());
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getVitesseY() {
        return vitesseY;
    }

    public void setVitesseY(double vitesseY) {
        this.vitesseY = vitesseY;
    }

    public boolean isAuSol() {
        return auSol;
    }

    public void setAuSol(boolean auSol) {
        this.auSol = auSol;
    }

    public boolean isVersLaDroite() {
        return versLaDroite;
    }

    public Image getSpriteActuel() {
        return versLaDroite ? spriteDroite : spriteGauche;
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

    public void incrementerPv(int pvEnPlus) {
        pv = Math.min(pv + pvEnPlus, 5);
    }

    public void decrementerPv(int pvEnMoins) {
        pv = Math.max(pv - pvEnMoins, 0);
    }

    public int getPv() {
        return pv;
    }

    private boolean estSolide(int id) {
        return id == 1 || id == 3;
    }
}
