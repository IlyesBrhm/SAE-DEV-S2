package universite_paris8.iut.youadah.projet.modele;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Personnage {

    private final DoubleProperty x = new SimpleDoubleProperty();
    private final DoubleProperty y = new SimpleDoubleProperty();
    private double vitesseY;
    private boolean auSol;
    protected boolean versLaDroite;
    private int pv;
    private int pvArmure;
    private long dernierDegatFeu = 1;
    private long dernierCoupRecu;

    public static final double SAUT = -3.5;
    public static final double VITESSE = 2;
    public static final int TAILLE_TUILE = 32;

    public Personnage(double startX, double startY) {
        this.x.set(startX);
        this.y.set(startY);
        this.vitesseY = 0;
        this.versLaDroite = true;
        this.pv = 5;
        this.pvArmure = 5;
        this.dernierCoupRecu = 0;
    }

    public double getX() { return x.get(); }
    public void setX(double x) { this.x.set(x); }
    public DoubleProperty xProperty() { return x; }

    public double getY() { return y.get(); }
    public void setY(double y) { this.y.set(y); }
    public DoubleProperty yProperty() { return y; }

    /**
     * Vérifie si le personnage est orienté vers la droite.
     * @return true si le personnage est orienté vers la droite, false sinon.
     */
    public boolean estsVersLaDroite() { return versLaDroite; }


    /**
     * Récupère le temps du dernier coup reçu.
     * @return le temps du dernier coup reçu en millisecondes.
     */
    public long getDernierCoupRecu() {
        return dernierCoupRecu;
    }


    /**
     * Définit le temps du dernier coup reçu.
     * @param temps le temps du dernier coup reçu en millisecondes.
     */
    public void setDernierCoupRecu(long temps) {
        this.dernierCoupRecu = temps;
    }


    /**
     * Fait sauter le personnage s'il est au sol.
     * La vitesse verticale est définie à une valeur négative pour simuler le saut.
     */
    public void sauter() {
        if (auSol) {
            vitesseY = SAUT;
            auSol = false;
        }
    }




    /**
     * Déplace le personnage vers la gauche.
     * Vérifie si le déplacement est possible en fonction de la carte.
     * @param carte La carte du jeu pour vérifier les collisions.
     */
    public void deplacerGauche(GameMap carte) {
        double futurX = getX() - VITESSE;
        int tuileX = (int)(futurX / TAILLE_TUILE);
        int tuileY = (int)((getY() + TAILLE_TUILE - 1) / TAILLE_TUILE);

        if (tuileX >= 0 && !estSolide(carte.getTile(tuileY, tuileX))) {
            setX(futurX);
            versLaDroite = false;
        }
    }

    /**
     * Déplace le personnage vers la droite.
     * Vérifie si le déplacement est possible en fonction de la carte.
     * @param carte La carte du jeu pour vérifier les collisions.
     */
    public void deplacerDroite(GameMap carte) {
        double futurX = getX() + VITESSE;
        int tuileX = (int)((futurX + TAILLE_TUILE - 1) / TAILLE_TUILE);
        int tuileY = (int)((getY() + TAILLE_TUILE - 1) / TAILLE_TUILE);

        if (tuileX < carte.getLargeur() && !estSolide(carte.getTile(tuileY, tuileX))) {
            setX(futurX);
            versLaDroite = true;
        }
    }

    /**
     * Incrémente les points de vie du personnage.
     * Assure que les points de vie ne dépassent pas 5.
     * @param pvEnPlus Nombre de points de vie à ajouter.
     */
    public void incrementerPv(int pvEnPlus) {
        pv = Math.min(pv + pvEnPlus, 5);
    }

    /**
     * Décrémente les points de vie du personnage.
     * Assure que les points de vie ne descendent pas en dessous de zéro.
     * @param pvEnMoins Nombre de points de vie à soustraire.
     */
    public void decrementerPv(int pvEnMoins) {
        pv = Math.max(pv - pvEnMoins, 0);
    }


    public int getPv() {
        return pv; }

    /**
     * Vérifie si le personnage est mort.
     * Un personnage est considéré mort si ses points de vie sont inférieurs ou égaux à zéro.
     * @return true si le personnage est mort, false sinon.
     */
    public boolean estMort() {
        if (pv > 0)
            return false;
        else
            return true;
    }

    /**
     * Vérifie si le personnage est au sol.
     * @return true si le personnage est au sol, false sinon.
     */
    public long getDernierDegatFeu() {
        return dernierDegatFeu;
    }

    /**
     * Définit le temps du dernier dégât de feu reçu.
     * @param t le temps du dernier dégât de feu en millisecondes.
     */
    public void setDernierDegatFeu(long t) {
        this.dernierDegatFeu = t; }

    /**
     * Vérifie si une tuile est solide.
     * @param id
     * @return true si la tuile est solide, false sinon.
     */
    public boolean estSolide(int id) {
        return id == 1 || id == 3 || id == 2 || id==6 ;
    }

    /**
     * Met à jour la position du personnage en fonction de la gravité et des collisions.
     * Gère les collisions avec le sol, le plafond et les bords de la carte.
     * @param map La carte du jeu pour vérifier les collisions.
     */
    public void mettreAJour(GameMap map) {
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
            if (tuileYBas < map.getHauteur()) {
                boolean solG = estSolide(map.getTile(tuileYBas, tuileXG));
                boolean solD = estSolide(map.getTile(tuileYBas, tuileXD));
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
                boolean hautG = estSolide(map.getTile(tuileYHaut, tuileXG));
                boolean hautD = estSolide(map.getTile(tuileYHaut, tuileXD));
                if (hautG || hautD) {
                    nouvelleY = (tuileYHaut + 1) * TAILLE_TUILE;
                    nouvelleVitesseY = 0;
                }
            }
        }

        // Limites verticales
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
    public void setPv(int pv) {
        this.pv = pv;
    }

    /**
     * Décrémente les points de vie de l'armure du personnage.
     * Assure que les points de vie de l'armure ne descendent pas en dessous de zéro.
     * @param valeur Nombre de points de vie à soustraire de l'armure.
     */
    public void decrementerPvArmure(int valeur) {
        this.pvArmure = Math.max(0, this.pvArmure - valeur);
    }
    public void setPvArmure(int pvArmure) {
        this.pvArmure = pvArmure;
    }


}