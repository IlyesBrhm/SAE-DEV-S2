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
        // Position du mob
        double x = getX();
        double y = getY();

        // Position en tuiles
        int tuileX = (int) (x / TAILLE_TUILE);
        int tuileY = (int) ((y + TAILLE_TUILE - 1) / TAILLE_TUILE);

        // Position du joueur
        double joueurX = joueur.getX();
        boolean joueurEstAGauche = joueurX < x;

        // Si le joueur est à gauche, on va vers la gauche, sinon vers la droite
        if (joueurEstAGauche) {
            versLaDroite = false;
        } else {
            versLaDroite = true;
        }

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
        System.out.println((int) getX());



        // === Gravité basique ===
        if (!estSolide(carte.getTile(tuileY + 1, tuileX))) {
            setY(getY() + 0.4); // tombe
        }
    }

    public void attaque(){
        System.out.println("Attaque du joueur !");
        if (joueur.getPvArmure() > 0) {
            joueur.decrementerPvArmure(pointAttaque);
            System.out.println("Armure touchée, PV armure restants : " + joueur.getPvArmure());
        } else {
            joueur.decrementerPv(pointAttaque);
            System.out.println("PV du joueur touchés, PV restants : " + joueur.getPv());
        }
        joueur.setX(joueur.getX() + 10);
    }


}
