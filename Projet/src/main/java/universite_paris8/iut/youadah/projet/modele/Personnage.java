// Personnage.java
package universite_paris8.iut.youadah.projet.modele;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Personnage {

    private final DoubleProperty x = new SimpleDoubleProperty();
    private final DoubleProperty y = new SimpleDoubleProperty();
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

    public void sauter() {
        if (auSol) {
            vitesseY = SAUT;
            auSol = false;
        }
    }

    public Vie getVie() { return vie; }

    public void deplacerGauche() {
        if (carte == null) return;
        double futurX = getX() - VITESSE;
        int tuileX = (int)(futurX / TAILLE_TUILE);
        int tuileY = (int)((getY() + TAILLE_TUILE - 1) / TAILLE_TUILE);

        if (tuileX >= 0 && !carte.estSolide(carte.getTile(tuileY, tuileX))) {
            setX(futurX);
            versLaDroite = false;
        }
    }

    public void deplacerDroite() {
        if (carte == null) return;
        double futurX = getX() + VITESSE;
        int tuileX = (int)((futurX + TAILLE_TUILE - 1) / TAILLE_TUILE);
        int tuileY = (int)((getY() + TAILLE_TUILE - 1) / TAILLE_TUILE);

        if (tuileX < carte.getLargeur() && !carte.estSolide(carte.getTile(tuileY, tuileX))) {
            setX(futurX);
            versLaDroite = true;
        }
    }

    // ✅ Version simplifiée avec complexité réduite
    public void mettreAJour(GameMap map) {
        this.carte = map;
        if (carte == null) return;

        final double GRAVITE = 0.08;
        double nouvelleVitesseY = vitesseY + GRAVITE;
        double nouvelleY = getY() + nouvelleVitesseY;
        boolean auSolTemp = false;

        int tuileXG = (int)(getX() / TAILLE_TUILE);
        int tuileXD = (int)((getX() + TAILLE_TUILE - 1) / TAILLE_TUILE);

        if (nouvelleVitesseY > 0) {
            // Chute
            auSolTemp = gererCollisionBas(nouvelleY, tuileXG, tuileXD);
            if (auSolTemp) {
                nouvelleVitesseY = 0;
                nouvelleY = alignerSurSol(nouvelleY);
            }
        } else if (nouvelleVitesseY < 0) {
            // Saut vers le haut
            if (collisionHaut(nouvelleY, tuileXG, tuileXD)) {
                nouvelleVitesseY = 0;
                nouvelleY = alignerSousBloc(nouvelleY);
            }
        }

        // Gestion des limites verticales
        nouvelleY = limiterHauteur(nouvelleY);
        if (nouvelleY >= carte.getHauteur() * TAILLE_TUILE - TAILLE_TUILE) {
            nouvelleVitesseY = 0;
            auSolTemp = true;
        }

        setY(nouvelleY);
        vitesseY = nouvelleVitesseY;
        auSol = auSolTemp;
    }

    // 🔽 Sous-méthodes privées pour réduire la complexité 🔽

    private boolean gererCollisionBas(double nouvelleY, int tuileXG, int tuileXD) {
        int tuileYBas = (int)((nouvelleY + TAILLE_TUILE) / TAILLE_TUILE);
        if (tuileYBas >= carte.getHauteur()) return true;

        boolean solG = carte.estSolide(carte.getTile(tuileYBas, tuileXG));
        boolean solD = carte.estSolide(carte.getTile(tuileYBas, tuileXD));
        return solG || solD;
    }

    private boolean collisionHaut(double nouvelleY, int tuileXG, int tuileXD) {
        int tuileYHaut = (int)(nouvelleY / TAILLE_TUILE);
        if (tuileYHaut < 0) return false;

        boolean hautG = carte.estSolide(carte.getTile(tuileYHaut, tuileXG));
        boolean hautD = carte.estSolide(carte.getTile(tuileYHaut, tuileXD));
        return hautG || hautD;
    }

    private double alignerSurSol(double nouvelleY) {
        int tuileYBas = (int)((nouvelleY + TAILLE_TUILE) / TAILLE_TUILE);
        return (tuileYBas - 1) * TAILLE_TUILE;
    }

    private double alignerSousBloc(double nouvelleY) {
        int tuileYHaut = (int)(nouvelleY / TAILLE_TUILE);
        return (tuileYHaut + 1) * TAILLE_TUILE;
    }

    private double limiterHauteur(double nouvelleY) {
        double hauteurMax = carte.getHauteur() * TAILLE_TUILE - TAILLE_TUILE;
        if (nouvelleY > hauteurMax) return hauteurMax;
        if (nouvelleY < 0) return 0;
        return nouvelleY;
    }
}

//public class Personnage {
//
//    private final DoubleProperty x = new SimpleDoubleProperty();
//    private final DoubleProperty y = new SimpleDoubleProperty();
//    private double vitesseY;
//    protected boolean versLaDroite;
//    private GameMap carte;
//
//    private boolean auSol;
//    public static final double SAUT = -3.5;
//    public static final double VITESSE = 2;
//    public static final int TAILLE_TUILE = 32;
//
//    private Vie vie;
//
//    public Personnage(double startX, double startY) {
//        this.x.set(startX);
//        this.y.set(startY);
//        this.vitesseY = 0;
//        this.versLaDroite = true;
//        //  Ancienne ligne BUGGÉE: this.carte = carte;  (il n'existe pas de paramètre 'carte')
//        this.carte = null; // on l’injectera via setCarte(...) ou via mettreAJour(map)
//        this.vie = new Vie();
//    }
//
//    // ✅ Ajoute ce setter pour injecter la carte dès l'initialisation
//    public void setCarte(GameMap carte) {
//        this.carte = carte;
//    }
//
//    public double getX() { return x.get(); }
//    public void setX(double x) { this.x.set(x); }
//    public DoubleProperty xProperty() { return x; }
//
//    public double getY() { return y.get(); }
//    public void setY(double y) { this.y.set(y); }
//    public DoubleProperty yProperty() { return y; }
//
//    public boolean estsVersLaDroite() { return versLaDroite; }
//
//    public void sauter() {
//        if (auSol) {
//            vitesseY = SAUT;
//            auSol = false;
//        }
//    }
//
//    public Vie getVie() { return vie; }
//
//    public void deplacerGauche() {
//        if (carte == null) return; // sécurité supplémentaire
//        double futurX = getX() - VITESSE;
//        int tuileX = (int)(futurX / TAILLE_TUILE);
//        int tuileY = (int)((getY() + TAILLE_TUILE - 1) / TAILLE_TUILE);
//
//        if (tuileX >= 0 && !carte.estSolide(carte.getTile(tuileY, tuileX))) {
//            setX(futurX);
//            versLaDroite = false;
//        }
//    }
//
//    public void deplacerDroite() {
//        if (carte == null) return; // sécurité supplémentaire
//        double futurX = getX() + VITESSE;
//        int tuileX = (int)((futurX + TAILLE_TUILE - 1) / TAILLE_TUILE);
//        int tuileY = (int)((getY() + TAILLE_TUILE - 1) / TAILLE_TUILE);
//
//        if (tuileX < carte.getLargeur() && !carte.estSolide(carte.getTile(tuileY, tuileX))) {
//            setX(futurX);
//            versLaDroite = true;
//        }
//    }
//
//    // ( Aligne la logique : on stocke la map dans le champ 'carte' et on l'utilise partout
//    public void mettreAJour(GameMap map) {
//        this.carte = map;              // <-- évite 'carte' null
//        if (carte == null) return;     // garde-fou
//
//        final double GRAVITE = 0.08;
//        double nouvelleY = getY();
//        double nouvelleVitesseY = this.vitesseY + GRAVITE;
//        nouvelleY += nouvelleVitesseY;
//
//        int tuileXG = (int)(getX() / TAILLE_TUILE);
//        int tuileXD = (int)((getX() + TAILLE_TUILE - 1) / TAILLE_TUILE);
//
//        boolean auSolTemp = false;
//
//        if (nouvelleVitesseY > 0) {
//            // Collision vers le bas (chute)
//            int tuileYBas = (int)((nouvelleY + TAILLE_TUILE) / TAILLE_TUILE);
//            if (tuileYBas < carte.getHauteur()) {
//                boolean solG = carte.estSolide(carte.getTile(tuileYBas, tuileXG));
//                boolean solD = carte.estSolide(carte.getTile(tuileYBas, tuileXD));
//                if (solG || solD) {
//                    nouvelleY = (tuileYBas - 1) * TAILLE_TUILE;
//                    nouvelleVitesseY = 0;
//                    auSolTemp = true;
//                }
//            }
//        } else if (nouvelleVitesseY < 0) {
//            // Collision vers le haut (saut sous un bloc)
//            int tuileYHaut = (int)(nouvelleY / TAILLE_TUILE);
//            if (tuileYHaut >= 0) {
//                boolean hautG = carte.estSolide(carte.getTile(tuileYHaut, tuileXG));
//                boolean hautD = carte.estSolide(carte.getTile(tuileYHaut, tuileXD));
//                if (hautG || hautD) {
//                    nouvelleY = (tuileYHaut + 1) * TAILLE_TUILE;
//                    nouvelleVitesseY = 0;
//                }
//            }
//        }
//
//        // Limites verticales
//        double hauteurMax = carte.getHauteur() * TAILLE_TUILE - TAILLE_TUILE;
//        if (nouvelleY > hauteurMax) {
//            nouvelleY = hauteurMax;
//            nouvelleVitesseY = 0;
//            auSolTemp = true;
//        }
//
//        if (nouvelleY < 0) {
//            nouvelleY = 0;
//            nouvelleVitesseY = 0;
//        }
//
//        setY(nouvelleY);
//        this.vitesseY = nouvelleVitesseY;
//        this.auSol = auSolTemp;
//    }
//}
