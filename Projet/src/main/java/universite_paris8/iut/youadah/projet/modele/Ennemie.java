package universite_paris8.iut.youadah.projet.modele;

public class Ennemie extends Personnage {
    private Player joueur;
    private int pointAttaque;

    public Ennemie(double startX, double startY, int pointAttaque, Player joueur) {
        super(startX, startY);
        this.pointAttaque = pointAttaque;
        this.joueur = joueur;
    }

    public void deplacementMob(GameMap carte) {
        double x = getX();
        double y = getY();
        double joueurX = joueur.getX();
        double joueurY = joueur.getY();

        double distanceJoueur = Math.abs(joueurX - x);
        if (distanceJoueur < 28 && Math.abs(joueurY - y) < 32) {
            return; // Collision basique : trop proche du joueur
        }

        int tuileX = (int) (x / TAILLE_TUILE);
        int tuileY = (int) ((y + TAILLE_TUILE - 1) / TAILLE_TUILE);

        boolean joueurEstAGauche = joueurX < x;
        versLaDroite = !joueurEstAGauche;

        if (versLaDroite) {
            double futurX = x + 0.4;
            int futurTuileX = (int) ((futurX + TAILLE_TUILE - 1) / TAILLE_TUILE);

            boolean obstacle = estSolide(carte.getTile(tuileY, futurTuileX));
            boolean bord = futurTuileX >= carte.getLargeur();
            boolean trouDevant = !estSolide(carte.getTile(tuileY + 1, futurTuileX));

            if (!bord && !obstacle && !trouDevant) {
                setX(futurX);
            } else {
                versLaDroite = false;
            }

        } else {
            double futurX = x - 0.4;
            int futurTuileX = (int) (futurX / TAILLE_TUILE);

            boolean obstacle = estSolide(carte.getTile(tuileY, futurTuileX));
            boolean bord = futurTuileX < 0;
            boolean trouDevant = !estSolide(carte.getTile(tuileY + 1, futurTuileX));

            if (!bord && !obstacle && !trouDevant) {
                setX(futurX);
            } else {
                versLaDroite = true;
            }
        }

        if (!estSolide(carte.getTile(tuileY + 1, tuileX))) {
            setY(getY() + 0.4);
        }
    }

    /**
     * Méthode pour attaquer le joueur.
     * @param carte
     */
    public void attaque(GameMap carte) {
        System.out.println("Attaque du joueur !");
        if (joueur.getPvArmure() > 0) {
            joueur.decrementerPvArmure(pointAttaque);
            System.out.println("Armure touchée, PV armure restants : " + joueur.getPvArmure());
        } else {
            joueur.decrementerPv(pointAttaque);
            System.out.println("PV du joueur touchés, PV restants : " + joueur.getPv());
        }

        // Knockback intelligent
        double knockback = 10;
        double newX = joueur.getX() < this.getX() ? joueur.getX() - knockback : joueur.getX() + knockback;

        if (carte.estTuileLibre(newX, joueur.getY())) {
            joueur.setX(newX);
        }
    }
}
