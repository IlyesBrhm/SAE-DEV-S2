package universite_paris8.iut.youadah.projet.modele;
import java.util.ArrayList;

public class Map {
        private int[][] terrain;

        public Map() {
        }
    public int[][] creerTerrain(int hauteur, int largeur) {
        terrain = new int[hauteur][largeur];
        for (int y = 0; y < hauteur; y++) {
            for (int x = 0; x < largeur; x++) {
                if (y < 20) {
                    terrain[y][x] = 0; // ciel
                } else if (y == 20) {
                    terrain[y][x] = 1; // herbe
                } else {
                    terrain[y][x] = 2; // sable
                }
            }
        }
        return this.terrain;
    }

        public int getTile(int x, int y) {
            return terrain[x][y];
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



