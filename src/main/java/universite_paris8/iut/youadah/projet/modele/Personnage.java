package universite_paris8.iut.youadah.projet.modele;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * Classe représentant un personnage dans le jeu
 * Gère les déplacements, les collisions, la vie et les objets possédés
 */
public class Personnage {

    // Propriétés pour la position X et Y du personnage (utilisées pour le binding JavaFX)
    private final DoubleProperty x = new SimpleDoubleProperty();
    private final DoubleProperty y = new SimpleDoubleProperty();
    // Vitesse verticale du personnage (pour les sauts et chutes)
    private double vitesseY;
    // Indique si le personnage est au sol ou en l'air
    private boolean auSol;
    // Direction du personnage (droite ou gauche)
    protected boolean versLaDroite;
    // Points de vie du personnage
    private int pv;
    // Points de vie de l'armure
    private int pvArmure;
    // Timestamp du dernier dégât de feu reçu
    private long dernierDegatFeu = 1;
    // Objet actuellement possédé par le personnage
    private Objet objetPossede;
    // Timestamp du dernier coup reçu
    private long dernierCoupRecu;

    // Constantes pour les mouvements et la taille
    // Valeur négative pour le saut (vers le haut)
    public static final double SAUT = -3.5;
    // Vitesse de déplacement horizontal
    public static final double VITESSE = 2;
    // Taille d'une tuile de la carte en pixels
    public static final int TAILLE_TUILE = 32;

    /**
     * Constructeur du personnage
     * @param startX Position initiale X
     * @param startY Position initiale Y
     */
    public Personnage(double startX, double startY) {
        this.x.set(startX);
        this.y.set(startY);
        this.vitesseY = 0;
        this.versLaDroite = true;
        this.pv = 5;
        this.pvArmure = 5;
        objetPossede = null;
        this.dernierCoupRecu = 0;
    }

    // Getters et setters pour les coordonnées X et Y
    public double getX() { return x.get(); }
    public void setX(double x) { this.x.set(x); }
    public DoubleProperty xProperty() { return x; }

    public double getY() { return y.get(); }
    public void setY(double y) { this.y.set(y); }
    public DoubleProperty yProperty() { return y; }

    /**
     * Vérifie si le personnage est orienté vers la droite
     * @return true si le personnage regarde vers la droite, false sinon
     */
    public boolean estsVersLaDroite() { return versLaDroite; }

    /**
     * Récupère le timestamp du dernier coup reçu
     * @return timestamp du dernier coup reçu
     */
    public long getDernierCoupRecu() {
        return dernierCoupRecu;
    }

    /**
     * Définit le timestamp du dernier coup reçu
     * @param temps timestamp à définir
     */
    public void setDernierCoupRecu(long temps) {
        this.dernierCoupRecu = temps;
    }

    /**
     * Fait sauter le personnage s'il est au sol
     */
    public void sauter() {
        if (auSol) {
            vitesseY = SAUT;
            auSol = false;
        }
    }

    /**
     * Déplace le personnage vers la gauche si possible
     * @param carte La carte du jeu pour vérifier les collisions
     */
    public void deplacerGauche(GameMap carte) {
        // Calcule la future position X
        double futurX = getX() - VITESSE;
        // Convertit la position en coordonnées de tuile
        int tuileX = (int)(futurX / TAILLE_TUILE);
        int tuileY = (int)((getY() + TAILLE_TUILE - 1) / TAILLE_TUILE);

        // Vérifie si le déplacement est possible (pas de collision avec un bloc solide)
        if (tuileX >= 0 && !estSolide(carte.getTile(tuileY, tuileX))) {
            setX(futurX);
            versLaDroite = false;
        }
    }

    /**
     * Déplace le personnage vers la droite si possible
     * @param carte La carte du jeu pour vérifier les collisions
     */
    public void deplacerDroite(GameMap carte) {
        // Calcule la future position X
        double futurX = getX() + VITESSE;
        // Convertit la position en coordonnées de tuile
        int tuileX = (int)((futurX + TAILLE_TUILE - 1) / TAILLE_TUILE);
        int tuileY = (int)((getY() + TAILLE_TUILE - 1) / TAILLE_TUILE);

        // Vérifie si le déplacement est possible (pas de collision avec un bloc solide)
        if (tuileX < carte.getLargeur() && !estSolide(carte.getTile(tuileY, tuileX))) {
            setX(futurX);
            versLaDroite = true;
        }
    }

    /**
     * Augmente les points de vie du personnage
     * @param pvEnPlus Nombre de points de vie à ajouter
     */
    public void incrementerPv(int pvEnPlus) {
        // Limite le maximum de points de vie à 5
        pv = Math.min(pv + pvEnPlus, 5);
    }

    /**
     * Diminue les points de vie du personnage
     * @param pvEnMoins Nombre de points de vie à retirer
     */
    public void decrementerPv(int pvEnMoins) {
        // Empêche les points de vie de devenir négatifs
        pv = Math.max(pv - pvEnMoins, 0);
    }

    /**
     * Récupère les points de vie actuels du personnage
     * @return Nombre de points de vie
     */
    public int getPv() {
        return pv; 
    }

    /**
     * Vérifie si le personnage est mort
     * @return true si le personnage est mort, false sinon
     */
    public boolean estMort() {
        if (pv > 0)
            return false;
        else
            return true;
    }

    /**
     * Récupère le timestamp du dernier dégât de feu reçu
     * @return Timestamp du dernier dégât de feu
     */
    public long getDernierDegatFeu() {
        return dernierDegatFeu;
    }

    /**
     * Définit le timestamp du dernier dégât de feu reçu
     * @param t Nouveau timestamp
     */
    public void setDernierDegatFeu(long t) {
        this.dernierDegatFeu = t; 
    }

    /**
     * Vérifie si une tuile est solide (bloque le passage)
     * @param id Identifiant de la tuile
     * @return true si la tuile est solide, false sinon
     */
    public boolean estSolide(int id) {
        // Les IDs 1, 2, 3 et 6 correspondent à des blocs solides
        return id == 1 || id == 3 || id == 2 || id==6 ;
    }

    /**
     * Met à jour la position du personnage en appliquant la gravité et en gérant les collisions
     * @param map La carte du jeu pour vérifier les collisions
     */
    public void mettreAJour(GameMap map) {
        // Constante de gravité qui tire le personnage vers le bas
        final double GRAVITE = 0.08;
        // Calcul de la nouvelle position Y avec la gravité
        double nouvelleY = getY();
        double nouvelleVitesseY = this.vitesseY + GRAVITE;
        nouvelleY += nouvelleVitesseY;

        // Calcul des tuiles à gauche et à droite du personnage (pour vérifier les collisions)
        int tuileXG = (int)(getX() / TAILLE_TUILE);
        int tuileXD = (int)((getX() + TAILLE_TUILE - 1) / TAILLE_TUILE);

        boolean auSolTemp = false;

        if (nouvelleVitesseY > 0) {
            // Collision vers le bas (chute)
            int tuileYBas = (int)((nouvelleY + TAILLE_TUILE) / TAILLE_TUILE);
            if (tuileYBas < map.getHauteur()) {
                // Vérifie si les tuiles sous le personnage sont solides
                boolean solG = estSolide(map.getTile(tuileYBas, tuileXG));
                boolean solD = estSolide(map.getTile(tuileYBas, tuileXD));
                if (solG || solD) {
                    // Ajuste la position pour que le personnage soit juste au-dessus du sol
                    nouvelleY = (tuileYBas - 1) * TAILLE_TUILE;
                    nouvelleVitesseY = 0;
                    auSolTemp = true;
                }
            }
        } else if (nouvelleVitesseY < 0) {
            // Collision vers le haut (saut sous un bloc)
            int tuileYHaut = (int)(nouvelleY / TAILLE_TUILE);
            if (tuileYHaut >= 0) {
                // Vérifie si les tuiles au-dessus du personnage sont solides
                boolean hautG = estSolide(map.getTile(tuileYHaut, tuileXG));
                boolean hautD = estSolide(map.getTile(tuileYHaut, tuileXD));
                if (hautG || hautD) {
                    // Ajuste la position pour que le personnage soit juste sous le bloc
                    nouvelleY = (tuileYHaut + 1) * TAILLE_TUILE;
                    nouvelleVitesseY = 0;
                }
            }
        }

        // Gestion des limites verticales de la carte
        double hauteurMax = map.getHauteur() * TAILLE_TUILE - TAILLE_TUILE;
        if (nouvelleY > hauteurMax) {
            // Empêche le personnage de sortir par le bas de la carte
            nouvelleY = hauteurMax;
            nouvelleVitesseY = 0;
            auSolTemp = true;
        }

        if (nouvelleY < 0) {
            // Empêche le personnage de sortir par le haut de la carte
            nouvelleY = 0;
            nouvelleVitesseY = 0;
        }

        // Applique les nouvelles valeurs calculées
        setY(nouvelleY);
        this.vitesseY = nouvelleVitesseY;
        this.auSol = auSolTemp;
    }


    /**
     * Récupère les points de vie de l'armure du personnage
     * @return Nombre de points de vie de l'armure
     */
    public int getPvArmure() {
        return pvArmure;
    }

    /**
     * Diminue les points de vie de l'armure du personnage
     * @param valeur Nombre de points à retirer de l'armure
     */
    public void decrementerPvArmure(int valeur) {
        // Empêche les points de vie de l'armure de devenir négatifs
        this.pvArmure = Math.max(0, this.pvArmure - valeur);
    }

    /**
     * Définit l'objet actuellement possédé par le personnage
     * @param objetPossede Nouvel objet à posséder
     */
    public void setObjetPossede(Objet objetPossede) {
        this.objetPossede = objetPossede;
    }

    /**
     * Récupère l'objet actuellement possédé par le personnage
     * @return Objet possédé ou null si aucun
     */
    public Objet getObjetPossede() {
        return objetPossede;
    }
}
