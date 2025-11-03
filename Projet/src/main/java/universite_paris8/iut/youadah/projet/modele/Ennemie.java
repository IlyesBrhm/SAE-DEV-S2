// Ennemie.java
package universite_paris8.iut.youadah.projet.modele;

public class Ennemie extends Personnage {
    private Player joueur;
    private int pointAttaque;
    private static final double VITESSE_ENNEMIE = 0.4;

    public Ennemie(double startX, double startY, int pointAttaque, Player joueur) {
        super(startX, startY);
        this.pointAttaque = pointAttaque;
        this.joueur = joueur;
    }

    /**
     * Gère le déplacement de l'ennemi vers le joueur
     */
    public void deplacementMob(GameMap carte) {
        if (carte == null || joueur == null) return;

        double distanceJoueur = Math.abs(joueur.getX() - getX());

        // Si trop proche du joueur, attaquer au lieu de se déplacer
        if (distanceJoueur < 28 && Math.abs(joueur.getY() - getY()) < 32) {
            return;
        }

        // Déterminer la direction vers le joueur
        boolean joueurEstAGauche = joueur.getX() < getX();
        versLaDroite = !joueurEstAGauche;

        // Vérifier les obstacles avant de se déplacer
        if (aObstacleDevant() || aTrouDevant()) {
            versLaDroite = !versLaDroite;
        }

        // Déplacer dans la direction appropriée
        if (versLaDroite) {
            deplacerDroite(VITESSE_ENNEMIE);
        } else {
            deplacerGauche(VITESSE_ENNEMIE);
        }

        // Appliquer la gravité
        appliquerGravite();
    }

    /**
     * Attaque le joueur en infligeant des dégâts et appliquant un knockback
     */
    public void attaque(GameMap carte) {
        if (joueur == null) return;

        System.out.println("Attaque du joueur !");

        // Infliger les dégâts
        if (joueur.getVie().getPvArmure() > 0) {
            joueur.getVie().decrementerPvArmure(pointAttaque);
            System.out.println("Armure touchée, PV armure restants : " + joueur.getVie().getPvArmure());
        } else {
            joueur.getVie().decrementerPv(pointAttaque);
            System.out.println("PV du joueur touchés, PV restants : " + joueur.getVie().getPv());
        }

        // Appliquer le knockback
        appliquerKnockback(carte);
    }

    /**
     * Applique un effet de recul au joueur après une attaque
     */
    private void appliquerKnockback(GameMap carte) {
        if (carte == null) return;

        double knockback = 10;
        double newX = joueur.getX() < this.getX()
                ? joueur.getX() - knockback
                : joueur.getX() + knockback;

        if (carte.estTuileLibre(newX, joueur.getY())) {
            joueur.setX(newX);
        }
    }
}