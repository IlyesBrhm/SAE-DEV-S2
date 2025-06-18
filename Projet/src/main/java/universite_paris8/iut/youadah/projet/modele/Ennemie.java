package universite_paris8.iut.youadah.projet.modele;

public class Ennemie extends Personnage {
    public Ennemie(double startX, double startY) {
        super(startX, startY);
    }

    public void deplacementMob(GameMap carte, Player joueur) {
        // Position du mob
        double x = getX();
        double y = getY();

        // Position en tuiles
        int tuileX = (int) (x / TAILLE_TUILE);
        int tuileY = (int) ((y + TAILLE_TUILE - 1) / TAILLE_TUILE);

        // Position du joueur
        double joueurX = joueur.getX();
        boolean joueurEstAGauche = joueurX < x;

        // === Changement de direction si bord ou obstacle devant ===
        if (versLaDroite) {
            double futurX = x + 0.4;
            int futurTuileX = (int) ((futurX + TAILLE_TUILE - 1) / TAILLE_TUILE);

            boolean obstacle = estSolide(carte.getTile(tuileY, futurTuileX));
            boolean bord = futurTuileX >= carte.getLargeur();
            boolean trouDevant = !estSolide(carte.getTile(tuileY + 1, futurTuileX));

            if (bord || obstacle || trouDevant) {
                versLaDroite = false;
            } else {
                setX(futurX);
            }

        } else { // Gauche
            double futurX = x - 0.4;
            int futurTuileX = (int) (futurX / TAILLE_TUILE);

            boolean obstacle = estSolide(carte.getTile(tuileY, futurTuileX));
            boolean bord = futurTuileX < 0;
            boolean trouDevant = !estSolide(carte.getTile(tuileY + 1, futurTuileX));

            if (bord || obstacle || trouDevant) {
                versLaDroite = true;
            } else {
                setX(futurX);
            }
        }

    if (Math.abs(joueurX - x) < 5 * TAILLE_TUILE) {
        versLaDroite = joueurEstAGauche ? false : true;
    }

        // === Gravité basique ===
        if (!estSolide(carte.getTile(tuileY + 1, tuileX))) {
            setY(getY() + 0.4); // tombe
        }
    }


}
