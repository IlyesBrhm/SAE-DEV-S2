package universite_paris8.iut.youadah.projet.modele;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Player {

    private final DoubleProperty x = new SimpleDoubleProperty();
    private final DoubleProperty y = new SimpleDoubleProperty();
    private double vitesseY;
    private boolean auSol;
    private boolean versLaDroite;
    private int pv;
    private Coeur coeur;
    private int pvArmure;
    private long dernierDegatFeu = 1;
    private Inventaire inventaire;
    private Objet objetPossede;

    private static final double SAUT = -3;
    private static final double VITESSE = 0.7;
    private static final int TAILLE_TUILE = 32;

    public Player(double startX, double startY, Inventaire inventaire) {
        this.x.set(startX);
        this.y.set(startY);
        this.vitesseY = 0;
        this.versLaDroite = true;
        this.pv = 5;
        this.coeur = new Coeur(5);
        this.pvArmure = 5;

        this.inventaire = inventaire;
        objetPossede = null;

    }

    public double getX() { return x.get(); }
    public void setX(double x) { this.x.set(x); }
    public DoubleProperty xProperty() { return x; }

    public double getY() { return y.get(); }
    public void setY(double y) { this.y.set(y); }
    public DoubleProperty yProperty() { return y; }

    public double getVitesseY() { return vitesseY; }
    public void setVitesseY(double vitesseY) { this.vitesseY = vitesseY; }

    public boolean estsVersLaDroite() { return versLaDroite; }

    public void setAuSol(boolean auSol) { this.auSol = auSol; }

    public void sauter() {
        if (auSol) {
            vitesseY = SAUT;
            auSol = false;
        }
    }

    public void deplacerGauche(Map carte) {
        double futurX = getX() - VITESSE;
        int tuileX = (int)(futurX / TAILLE_TUILE);
        int tuileY = (int)((getY() + TAILLE_TUILE - 1) / TAILLE_TUILE);

        if (tuileX >= 0 && !estSolide(carte.getTile(tuileY, tuileX))) {
            setX(futurX);
            versLaDroite = false;
        }
    }

    public void deplacerDroite(Map carte) {
        double futurX = getX() + VITESSE;
        int tuileX = (int)((futurX + TAILLE_TUILE - 1) / TAILLE_TUILE);
        int tuileY = (int)((getY() + TAILLE_TUILE - 1) / TAILLE_TUILE);

        if (tuileX < carte.getLargeur() && !estSolide(carte.getTile(tuileY, tuileX))) {
            setX(futurX);
            versLaDroite = true;
        }
    }

    public void incrementerPv(int pvEnPlus) {
        pv = Math.min(pv + pvEnPlus, 5);
    }

    public void decrementerPv(int pvEnMoins) {
        pv = Math.max(pv - pvEnMoins, 0);
    }

    public boolean estVivant(){
        if (pv > 0)
            return true;
        else
            return false;
    }

    public int getPv() {
        return pv; }

    public boolean estMort() {
        return coeur.estMort(); }

    public long getDernierDegatFeu() {
        return dernierDegatFeu; }

    public void setDernierDegatFeu(long t) {
        this.dernierDegatFeu = t; }

    private boolean estSolide(int id) {
        return id == 1 || id == 3 || id == 2;
    }

    public void mettreAJour(Map map) {
        final double GRAVITE = 0.08;
        double nouvelleY = getY();
        double nouvelleVitesseY = this.vitesseY + GRAVITE;
        nouvelleY += nouvelleVitesseY;

        int tuileY = (int)((nouvelleY + TAILLE_TUILE) / TAILLE_TUILE);
        int tuileXG = (int)(getX() / TAILLE_TUILE);
        int tuileXD = (int)((getX() + TAILLE_TUILE - 1) / TAILLE_TUILE);

        boolean solG = estSolide(map.getTile(tuileY, tuileXG));
        boolean solD = estSolide(map.getTile(tuileY, tuileXD));

        boolean auSolTemp;
        if ((tuileY < map.getHauteur()) && (solG || solD)) {
            nouvelleY = (tuileY - 1) * TAILLE_TUILE;
            nouvelleVitesseY = 0;
            auSolTemp = true;
        } else {
            auSolTemp = false;
        }

        double hauteurMax = map.getHauteur() * TAILLE_TUILE - TAILLE_TUILE;
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

    public int getPvArmure() {
        return pvArmure;
    }

    public void decrementerPvArmure(int valeur) {
        this.pvArmure = Math.max(0, this.pvArmure - valeur);
    }



    public void reinitialiser(double x, double y) {
        incrementerPv(5);
        this.x.set(x);
        this.y.set(y);
    }

    public void setObjetPossede(Objet objetPossede) {
        this.objetPossede = objetPossede;
    }

    public Objet getObjetPossede() {
        return objetPossede;
    }

}
