package universite_paris8.iut.youadah.projet.modele;

/**
 * Représente une carte du jeu, composée de tuiles codées par des entiers.
 */
public class Map {

    private int[][] terrain;

    public Map() {
        // Constructeur vide
    }

    /**
     * Génère un terrain de jeu selon une hauteur et une largeur données.
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
                    terrain[y][x] = 3; // terre
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




        poserRectangle(10,19,10,1,1);
        poserRectangle(10,20,10,1,7);
        poserRectangle(0,21,10,1,7);
        poserRectangle(20,21,10,1,7);

        poserRectangle(30, 19, 2, 1, 1);
        poserRectangle(32, 18, 2, 1, 1);
        poserRectangle(34, 17, 2, 1, 1);
        poserRectangle(36, 16, 20, 1, 1);
        poserRectangle(56, 15, 2, 1, 1);

        poserRectangle(32, 20, 50, 1, 3);
        poserRectangle(30, 20, 2, 1, 7);
        poserRectangle(32, 19, 2, 1, 7);
        poserRectangle(34, 19, 50, 1, 3);
        poserRectangle(34, 18, 2, 1, 7);
        poserRectangle(36, 18, 50, 1, 3);
        poserRectangle(36, 17, 20, 1, 7);
        poserRectangle(56, 17, 2, 1, 3);
        poserRectangle(56, 16, 2, 1, 7);


        return terrain;
    }

    public void poserBloc(int x, int y, int type) {
        if (terrain != null && y >= 0 && y < terrain.length && x >= 0 && x < terrain[0].length) {
            terrain[y][x] = type;
        }
    }

    public void poserLigne(int xDebut, int xFin, int y, int type) {
        for (int x = xDebut; x <= xFin; x++) {
            poserBloc(x, y, type);
        }
    }

    public void poserColonne(int x, int yDebut, int yFin, int type) {
        for (int y = yDebut; y <= yFin; y++) {
            poserBloc(x, y, type);
        }
    }

    public void poserRectangle(int xDebut, int yDebut, int largeur, int hauteur, int type) {
        for (int y = yDebut; y < yDebut + hauteur; y++) {
            for (int x = xDebut; x < xDebut + largeur; x++) {
                poserBloc(x, y, type);
            }
        }
    }

    public int getTile(int y, int x) {
        return terrain[y][x];
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