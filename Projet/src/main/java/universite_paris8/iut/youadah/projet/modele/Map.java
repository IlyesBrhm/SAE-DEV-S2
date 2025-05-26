package universite_paris8.iut.youadah.projet.modele;

import java.io.FileWriter;
import java.io.IOException;

// cette class va contenir un tableau 2D qui va être notre map où chaque case contient un ID tuile
public class Map {

    private int[][] terrain;

    public Map() {}

    public int[][] creerTerrain(int hauteur, int largeur) {
        terrain = new int[hauteur][largeur];

        // Remplissage de base : ciel, herbe, terre
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

        // Nuages 5
        poserLigne(10, 13, 1, 4);
        poserLigne(6, 17, 2, 4);
        poserLigne(5, 16, 3, 4);
        poserLigne(6, 14, 4, 4);

        // Nuages 6
        poserLigne(40, 49, 1, 4);
        poserLigne(39, 50, 2, 4);
        poserLigne(38, 49, 3, 4);
        poserLigne(41, 47, 4, 4);


// pente montante (2 blocs au lieu de 4)
        for (int i = 0; i < 2; i++) {
            poserBloc(14 - i, 19 - i, 1); // pente
            for (int y = 20 - i; y < terrain.length; y++) {
                poserBloc(14 - i, y, 2); // terre
            }
        }

// sommet plat (plus petit)
        poserLigne(13 , 13, 17, 1);
        for (int x = 12; x <= 13; x++) {
            for (int y = 18; y < terrain.length; y++) {
                poserBloc(x, y, 2);
            }
        }

// descente douce
        for (int i = 1; i <= 2; i++) {
            poserBloc(13 - i, 17 + i, 1);
            for (int y = 18 + i; y < terrain.length; y++) {
                poserBloc(13 - i, y, 2);
            }
        }


        // Montagne stylée avec sommet plat
        for (int i = 0; i < 4; i++) {
            poserBloc(20 + i, 19 - i, 1); // pente
            for (int y = 20 - i; y < terrain.length; y++) {
                poserBloc(20 + i, y, 2); // terre
            }
        }

        // sommet plat
        poserLigne(24, 26, 15, 1);
        for (int x = 24; x <= 26; x++) {
            for (int y = 16; y < terrain.length; y++) {
                poserBloc(x, y, 2);
            }
        }

        // descente douce
        for (int i = 1; i <= 4; i++) {
            poserBloc(26 + i, 15 + i, 1);
            for (int y = 16 + i; y < terrain.length; y++) {
                poserBloc(26 + i, y, 2);
            }
        }



        // Château en pierre (placé à x = 40 pour être visible)
        int chateauX = 40;
        int chateauLargeur = 15;
        int chateauHauteur = 10;

        // Tours
        poserRectangle(chateauX, 10, 3, chateauHauteur, 3); // tour gauche
        poserRectangle(chateauX + chateauLargeur - 3, 10, 3, chateauHauteur, 3); // tour droite

        // Mur supérieur
        poserRectangle(chateauX + 3, 10, chateauLargeur - 6, 3, 3);

        // Porte
        poserBloc(chateauX + chateauLargeur / 2, 20, 1); // herbe
        poserBloc(chateauX + chateauLargeur / 2, 19, 0); // vide
        poserBloc(chateauX + chateauLargeur / 2, 18, 0); // vide

        // Drapeaux (feu)
        poserBloc(chateauX + 1, 9, 5); // feu gauche
        poserBloc(chateauX + chateauLargeur - 2, 9, 5); // feu droit

        // Sol du château
        for (int x = chateauX; x < chateauX + chateauLargeur; x++) {
            for (int y = 21; y < terrain.length; y++) {
                poserBloc(x, y, 2);
            }
        }

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
