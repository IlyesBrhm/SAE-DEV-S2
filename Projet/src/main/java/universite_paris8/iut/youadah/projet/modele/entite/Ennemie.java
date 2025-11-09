package universite_paris8.iut.youadah.projet.modele.entite;

import universite_paris8.iut.youadah.projet.modele.monde.GameMap;

public class Ennemie extends Personnage {
    private Player joueur;
    private int pointAttaque;

    public Ennemie(double startX, double startY, int pointAttaque, Player joueur) {
        super(startX, startY);
        this.pointAttaque = pointAttaque;
        this.joueur = joueur;
    }


    public void deplacementMob(GameMap carte) {
        if (collisionAvecJoueur()) return;

        int tuileX = (int) (getX() / TAILLE_TUILE);
        int tuileY = (int) ((getY() + TAILLE_TUILE - 1) / TAILLE_TUILE);

        majDirectionVersJoueur();

        deplacerHorizontalement(carte, tuileY, versLaDroite);
        appliquerGravite(carte, tuileY, tuileX);
    }


    private boolean collisionAvecJoueur() {
        double distanceX = Math.abs(joueur.getX() - getX());
        double distanceY = Math.abs(joueur.getY() - getY());
        return distanceX < 28 && distanceY < 32;
    }

    private void majDirectionVersJoueur() {
        versLaDroite = joueur.getX() >= getX();
    }

    private void deplacerHorizontalement(GameMap carte, int tuileY, boolean versLaDroite) {
        double delta = versLaDroite ? 0.4 : -0.4;
        double futurX = getX() + delta;

        int futurTuileX = versLaDroite
                ? (int) ((futurX + TAILLE_TUILE - 1) / TAILLE_TUILE)
                : (int) (futurX / TAILLE_TUILE);

        boolean obstacle = carte.estSolide(carte.getTile(tuileY, futurTuileX));
        boolean bord = versLaDroite ? futurTuileX >= carte.getLargeur() : futurTuileX < 0;
        boolean trouDevant = !carte.estSolide(carte.getTile(tuileY + 1, futurTuileX));

        if (!bord && !obstacle && !trouDevant) {
            setX(futurX);
        } else {
            // Inversion de direction si on rencontre un mur, un bord ou un trou
            this.versLaDroite = !versLaDroite;
        }
    }

    private void appliquerGravite(GameMap carte, int tuileY, int tuileX) {
        if (!carte.estSolide(carte.getTile(tuileY + 1, tuileX))) {
            setY(getY() + 0.4);
        }
    }

    public void attaque(GameMap carte) {
        System.out.println("Attaque du joueur !");
        if (joueur.getVie().getPvArmure() > 0) {
            joueur.getVie().decrementerPvArmure(pointAttaque);
            System.out.println("Armure touchée, PV armure restants : " + joueur.getVie().getPvArmure());
        } else {
            joueur.getVie().decrementerPv(pointAttaque);
            System.out.println("PV du joueur touchés, PV restants : " + joueur.getVie().getPv());
        }

        // Knockback intelligent
        double knockback = 10;
        double newX = joueur.getX() < this.getX() ? joueur.getX() - knockback : joueur.getX() + knockback;

        if (carte.estTuileLibre(newX, joueur.getY())) {
            joueur.setX(newX);
        }
    }
}


