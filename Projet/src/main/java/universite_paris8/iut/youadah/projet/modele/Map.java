package universite_paris8.iut.youadah.projet.modele;

// cette class va contenir un tableau 2D qui va etre notre map ou chque case contient un ID tuile
public class Map {

    private int[][] terrain;

    public Map() {

    }

    // Donc ici c la creation du terrain , on initialise le terrain et on creer une boucle pour le remplir avec les différent type de bloc
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
        return terrain;
    }
    // juste ici on a creer des methodes pour nous faciliter a creer des objets et des formes sur la map
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
    // retourne une tuiles
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