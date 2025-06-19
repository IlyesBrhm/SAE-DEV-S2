package universite_paris8.iut.youadah.projet.modele;

/**
 * Classe représentant la carte du jeu.
 * Cette classe contient un tableau 2D qui représente la map où chaque case contient un ID de tuile.
 * Les IDs correspondent à différents types de blocs (vide, herbe, terre, etc.).
 */
public class GameMap {

    /**
     * Tableau 2D représentant le terrain du jeu.
     * Chaque élément contient un ID correspondant à un type de bloc.
     */
    private int[][] terrain;

    /**
     * Crée et initialise le terrain du jeu avec les différents types de blocs.
     * Cette méthode génère la structure de base du monde et place divers éléments comme
     * le ciel, l'herbe, la terre et les nuages.
     *
     * @param hauteur Hauteur du terrain en nombre de tuiles
     * @param largeur Largeur du terrain en nombre de tuiles
     * @return Le tableau 2D représentant le terrain créé
     */
    public int[][] creerTerrain(int hauteur, int largeur) {
        terrain = new int[hauteur][largeur];

        // Création de la structure de base du terrain
        for (int y = 0; y < hauteur; y++) {
            for (int x = 0; x < largeur; x++) {
                if (y < 20) {
                    terrain[y][x] = 0; // vide (ciel)
                } else if (y == 20) {
                    terrain[y][x] = 1; // herbe
                } else {
                    terrain[y][x] = 2; // terre
                }
            }
        }

        // Placement des nuages et autres éléments décoratifs

        // Nuage 3
        poserLigne(70, 107, 3, 4);
        poserLigne(69, 108, 4, 4);
        poserLigne(68, 109, 5, 4);
        poserLigne(68, 108, 6, 4);
        poserLigne(69, 107, 7, 4);
        poserLigne(73, 104, 8, 4);

        // Nuage 4
        poserLigne(120, 130, 3, 4);
        poserLigne(119, 131, 4, 4);
        poserLigne(118, 130, 5, 4);
        poserLigne(121, 128, 6, 4);
        poserLigne(122, 127, 7, 4);

        // Nuage 5
        poserLigne(10, 13, 1, 4);
        poserLigne(6, 17, 2, 4);
        poserLigne(5, 16, 3, 4);
        poserLigne(6, 14, 4, 4);

        // Nuage 6
        poserLigne(40, 49, 1, 4);
        poserLigne(39, 50, 2, 4);
        poserLigne(38, 49, 3, 4);
        poserLigne(41, 47, 4, 4);

        // Placement d'autres éléments du terrain
        poserRectangle(10, 18, 3, 2, 3);
        poserBloc(18, 19, 5);
        poserBloc(19, 19, 5);

        poserLigne(30, 35, 19, 6);

        return terrain;
    }

    /**
     * Place un bloc de type spécifié aux coordonnées données.
     * Vérifie que les coordonnées sont valides avant de placer le bloc.
     *
     * @param x    Coordonnée X où placer le bloc
     * @param y    Coordonnée Y où placer le bloc
     * @param type Type de bloc à placer (ID du bloc)
     */
    public void poserBloc(int x, int y, int type) {
        if (terrain != null && y >= 0 && y < terrain.length && x >= 0 && x < terrain[0].length) {
            terrain[y][x] = type;
        }
    }

    /**
     * Place une ligne horizontale de blocs du même type.
     *
     * @param xDebut Coordonnée X de début de la ligne
     * @param xFin   Coordonnée X de fin de la ligne
     * @param y      Coordonnée Y de la ligne
     * @param type   Type de bloc à placer (ID du bloc)
     */
    public void poserLigne(int xDebut, int xFin, int y, int type) {
        for (int x = xDebut; x <= xFin; x++) {
            poserBloc(x, y, type);
        }
    }

    /**
     * Place un rectangle de blocs du même type.
     *
     * @param xDebut  Coordonnée X du coin supérieur gauche du rectangle
     * @param yDebut  Coordonnée Y du coin supérieur gauche du rectangle
     * @param largeur Largeur du rectangle en nombre de blocs
     * @param hauteur Hauteur du rectangle en nombre de blocs
     * @param type    Type de bloc à placer (ID du bloc)
     */
    public void poserRectangle(int xDebut, int yDebut, int largeur, int hauteur, int type) {
        for (int y = yDebut; y < yDebut + hauteur; y++) {
            for (int x = xDebut; x < xDebut + largeur; x++) {
                poserBloc(x, y, type);
            }
        }
    }

    /**
     * Récupère le type de tuile aux coordonnées spécifiées.
     *
     * @param y Coordonnée Y de la tuile
     * @param x Coordonnée X de la tuile
     * @return L'ID du type de tuile à cette position
     */
    public int getTile(int y, int x) {
        return terrain[y][x];
    }

    /**
     * Vérifie si une tuile est libre (vide) aux coordonnées spécifiées.
     * Une tuile est considérée comme libre si son ID est 0.
     *
     * @param x Coordonnée X à vérifier
     * @param y Coordonnée Y à vérifier
     * @return true si la tuile est libre, false sinon
     */
    public boolean estTuileLibre(double x, double y) {
        int col = (int)(x / 32);
        int lig = (int)(y / 32);
        return getTile(lig, col) == 0;
    }

    /**
     * Récupère la largeur du terrain en nombre de tuiles.
     *
     * @return La largeur du terrain
     */
    public int getLargeur() {
        return terrain[0].length;
    }

    /**
     * Récupère la hauteur du terrain en nombre de tuiles.
     *
     * @return La hauteur du terrain
     */
    public int getHauteur() {
        return terrain.length;
    }

    /**
     * Récupère le tableau 2D représentant le terrain.
     *
     * @return Le tableau 2D du terrain
     */
    public int[][] getTerrain() {
        return terrain;
    }
}
