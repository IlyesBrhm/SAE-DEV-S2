package universite_paris8.iut.youadah.projet.modele;

/**
 * Classe représentant un ennemi dans le jeu
 * Hérite de la classe Personnage
 */
public class Ennemie extends Personnage {
    private Player joueur;      // Référence au joueur pour le suivre et l'attaquer
    private int pointAttaque;   // Points de dégâts infligés lors d'une attaque

    /**
     * Constructeur de la classe Ennemie
     * @param startX Position initiale X de l'ennemi
     * @param startY Position initiale Y de l'ennemi
     * @param pointAttaque Points de dégâts que l'ennemi peut infliger
     * @param joueur Référence au joueur que l'ennemi va cibler
     */
    public Ennemie(double startX, double startY, int pointAttaque, Player joueur) {
        super(startX, startY);
        this.pointAttaque = pointAttaque;
        this.joueur = joueur;
    }

    /**
     * Gère le déplacement de l'ennemi en fonction de la position du joueur et des obstacles
     * @param carte La carte du jeu contenant les informations sur les tuiles
     */
    public void deplacementMob(GameMap carte) {
        // Récupération des positions actuelles
        double x = getX();
        double y = getY();
        double joueurX = joueur.getX();
        double joueurY = joueur.getY();

        // Vérification de la distance avec le joueur pour éviter les collisions
        double distanceJoueur = Math.abs(joueurX - x);
        if (distanceJoueur < 28 && Math.abs(joueurY - y) < 32) {
            return; // Collision basique : trop proche du joueur
        }

        // Calcul de la tuile actuelle de l'ennemi
        int tuileX = (int) (x / TAILLE_TUILE);
        int tuileY = (int) ((y + TAILLE_TUILE - 1) / TAILLE_TUILE);

        // Détermination de la direction à prendre en fonction de la position du joueur
        boolean joueurEstAGauche = joueurX < x;
        versLaDroite = !joueurEstAGauche;

        // Déplacement vers la droite
        if (versLaDroite) {
            double futurX = x + 0.4;  // Calcul de la future position X
            int futurTuileX = (int) ((futurX + TAILLE_TUILE - 1) / TAILLE_TUILE);

            // Vérification des obstacles et des bords de la carte
            boolean obstacle = estSolide(carte.getTile(tuileY, futurTuileX));
            boolean bord = futurTuileX >= carte.getLargeur();
            boolean trouDevant = !estSolide(carte.getTile(tuileY + 1, futurTuileX));

            // Déplacement si possible, sinon changement de direction
            if (!bord && !obstacle && !trouDevant) {
                setX(futurX);
            } else {
                versLaDroite = false;
            }

        } 
        // Déplacement vers la gauche
        else {
            double futurX = x - 0.4;  // Calcul de la future position X
            int futurTuileX = (int) (futurX / TAILLE_TUILE);

            // Vérification des obstacles et des bords de la carte
            boolean obstacle = estSolide(carte.getTile(tuileY, futurTuileX));
            boolean bord = futurTuileX < 0;
            boolean trouDevant = !estSolide(carte.getTile(tuileY + 1, futurTuileX));

            // Déplacement si possible, sinon changement de direction
            if (!bord && !obstacle && !trouDevant) {
                setX(futurX);
            } else {
                versLaDroite = true;
            }
        }

        // Gestion de la gravité - chute si pas de sol sous l'ennemi
        if (!estSolide(carte.getTile(tuileY + 1, tuileX))) {
            setY(getY() + 0.4);
        }
    }

    /**
     * Méthode permettant à l'ennemi d'attaquer le joueur
     * @param carte La carte du jeu pour vérifier les déplacements après l'attaque
     */
    public void attaque(GameMap carte) {
        System.out.println("Attaque du joueur !");

        // Attaque l'armure en priorité si elle existe
        if (joueur.getPvArmure() > 0) {
            joueur.decrementerPvArmure(pointAttaque);
            System.out.println("Armure touchée, PV armure restants : " + joueur.getPvArmure());
        } 
        // Sinon attaque directement les points de vie
        else {
            joueur.decrementerPv(pointAttaque);
            System.out.println("PV du joueur touchés, PV restants : " + joueur.getPv());
        }

        // Knockback intelligent - repousse le joueur dans la direction opposée à l'ennemi
        double knockback = 10;
        double newX = joueur.getX() < this.getX() ? joueur.getX() - knockback : joueur.getX() + knockback;

        // Applique le knockback seulement si la nouvelle position est valide
        if (carte.estTuileLibre(newX, joueur.getY())) {
            joueur.setX(newX);
        }
    }
}
