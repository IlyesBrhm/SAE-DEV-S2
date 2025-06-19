package universite_paris8.iut.youadah.projet.modele;

/**
 * Classe représentant la carte du jeu.
 * Elle gère la création du terrain et les interactions avec les tuiles.
 */
public class GameMap {

    private int[][] terrain;

    /**
     * Crée un terrain de jeu avec une hauteur et une largeur spécifiées.
     * Le terrain est initialisé avec des valeurs représentant le ciel, l'herbe et la terre.
     * Des nuages et d'autres éléments sont également ajoutés au terrain.
     *
     * @param hauteur Hauteur du terrain en tuiles.
     * @param largeur Largeur du terrain en tuiles.
     * @return Un tableau 2D représentant le terrain créé.
     */
    public int[][] creerTerrain(int hauteur, int largeur) {
        terrain = new int[hauteur][largeur];

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


//nuages 3
        poserLigne(70, 107, 3, 4);
        poserLigne(69, 108, 4, 4);
        poserLigne(68, 109, 5, 4);
        poserLigne(68, 108, 6, 4);
        poserLigne(69, 107, 7, 4);
        poserLigne(73, 104, 8, 4);

//nuages 4
        poserLigne(120, 130, 3, 4);
        poserLigne(119, 131, 4, 4);
        poserLigne(118, 130, 5, 4);
        poserLigne(121, 128, 6, 4);
        poserLigne(122, 127, 7, 4);

//nuages 5
        poserLigne(10, 13, 1, 4);
        poserLigne(6, 17, 2, 4);
        poserLigne(5, 16, 3, 4);
        poserLigne(6, 14, 4, 4);

//nuages 6
        poserLigne(40, 49, 1, 4);
        poserLigne(39, 50, 2, 4);
        poserLigne(38, 49, 3, 4);
        poserLigne(41, 47, 4, 4);

        poserRectangle(10,18,3,2,3);
        poserBloc(18,19,5);
        poserBloc(19,19,5);

        poserLigne(30,35,19,6);

        return terrain;
    }

    /**
     * Pose un bloc à une position spécifique sur le terrain.
     * Vérifie que les coordonnées sont valides avant de poser le bloc.
     *
     * @param x Coordonnée x du bloc.
     * @param y Coordonnée y du bloc.
     * @param type Type de bloc à poser (par exemple, 1 pour herbe, 2 pour terre, etc.).
     */
    public void poserBloc(int x, int y, int type) {
        if (terrain != null && y >= 0 && y < terrain.length && x >= 0 && x < terrain[0].length) {
            terrain[y][x] = type;
        }
    }

    /**
     * Pose une ligne de blocs horizontale sur le terrain.
     * Les blocs sont posés entre les coordonnées xDebut et xFin à la hauteur y.
     *
     * @param xDebut Coordonnée x de début de la ligne.
     * @param xFin Coordonnée x de fin de la ligne.
     * @param y Hauteur à laquelle poser la ligne.
     * @param type Type de bloc à poser (par exemple, 1 pour herbe, 2 pour terre, etc.).
     */
    public void poserLigne(int xDebut, int xFin, int y, int type) {
        for (int x = xDebut; x <= xFin; x++) {
            poserBloc(x, y, type);
        }
    }


    /**
     * Pose un rectangle de blocs sur le terrain.
     * @param xDebut
     * @param yDebut
     * @param largeur
     * @param hauteur
     * @param type
     */
    public void poserRectangle(int xDebut, int yDebut, int largeur, int hauteur, int type) {
        for (int y = yDebut; y < yDebut + hauteur; y++) {
            for (int x = xDebut; x < xDebut + largeur; x++) {
                poserBloc(x, y, type);
            }
        }
    }

    /**
     * Récupère la tuile à une position spécifique sur le terrain.
     * @param y
     * @param x
     * @return
     */
    public int getTile(int y, int x) {
        return terrain[y][x];
    }


    /**
     * Vérifie si une tuile est libre à la position (x, y).
     * Une tuile est considérée libre si elle a la valeur 0.
     *
     * @param x Coordonnée x de la tuile.
     * @param y Coordonnée y de la tuile.
     * @return true si la tuile est libre, false sinon.
     */
    public boolean estTuileLibre(double x, double y) {
        int col = (int)(x / 32);
        int lig = (int)(y / 32);
        return getTile(lig, col) == 0;
    }



    public int getLargeur() {

        return terrain[0].length;
    }

    public int getHauteur() {

        return terrain.length;
    }


    public int[][] getTerrain() {

        return terrain;
    }
}