package universite_paris8.iut.aclaudio.demo.world;

public class Map {

    private int width;
    private int height;
    private int[][] tiles;

    public static final int Vide = 0;
    public static final int Terre = 1;
    public static final int Pierre = 2;
    public static final int Nuage = 3;
    public static final int Herbe = 4;
    public static final int Arbre = 5;


    public Map(int width, int height) {
        this.width = width;
        this.height = height;
        tiles = new int[width][height];
        initializeMap();
        generateClouds();
        generateStones();
        generateTrees(10);
    }

    private void initializeMap() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (y < 20) {
                    tiles[x][y] = Vide;
                } else if (y == 20) {
                    tiles[x][y] = Herbe;
                } else {
                    tiles[x][y] = Terre;
                }
            }
        }
    }

    private void generateClouds() {
        int[][] cloudPattern = {
                {0,0,0,1,1,1,1,1,1,0,0,0},
                {0,0,1,1,1,1,1,1,1,1,0,0},
                {0,1,1,1,1,1,1,1,1,1,1,0},
                {0,0,1,1,1,1,1,1,1,1,0,0},
                {0,0,0,1,1,1,1,1,1,0,0,0}
        };

        int patternHeight = cloudPattern.length;
        int patternWidth = cloudPattern[0].length;
        int spacingX = 20;
        int spacingY = 6;

        for (int startX = 0; startX < width - patternWidth; startX += spacingX) {
            for (int startY = 0; startY < 10 - patternHeight; startY += spacingY) {
                int offsetY = (int)(Math.random() * 2);
                for (int y = 0; y < patternHeight; y++) {
                    for (int x = 0; x < patternWidth; x++) {
                        if (cloudPattern[y][x] == 1) {
                            tiles[startX + x][startY + y + offsetY] = Nuage;
                        }
                    }
                }
            }
        }
    }

    private void generateTrees(int numberOfTrees) {
        for (int i = 0; i < numberOfTrees; i++) {
            int x = 2 + (int)(Math.random() * (width - 4));
            int baseY = 20;

            if (tiles[x][baseY] == Herbe) {
                tiles[x][baseY - 1] = Arbre; // On place juste le sommet du tronc (tout en haut)
            }
        }
    }


    private void generateStones() {
        for (int y = 21; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // Plus on descend, plus la probabilité augmente
                double profondeur = (double)(y - 20) / (height - 20); // entre 0 (haut) et 1 (bas)
                double chance = profondeur * 0.8; // jusqu’à 80% de chance tout en bas
                if (Math.random() < chance) {
                    tiles[x][y] = Pierre;
                }
            }
        }
    }

    public int getTile(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return tiles[x][y];
        }
        return Vide;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
