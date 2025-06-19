package universite_paris8.iut.youadah.projet.tests;


import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import universite_paris8.iut.youadah.projet.modele.GameMap;
import universite_paris8.iut.youadah.projet.modele.Personnage;


/**
 * Classe de test pour la classe Personnage.
 * Utilise JUnit pour les tests unitaires.
 */
public class PersonnageTest {

    private Personnage perso;
    private GameMap carte;

    // Dummy GameMap de test
    private static class DummyMap extends GameMap {
        private final int[][] map;

        public DummyMap(int width, int height) {
            this.map = new int[height][width];
        }

        @Override
        public int getTile(int y, int x) {
            if (y < 0 || y >= map.length || x < 0 || x >= map[0].length)
                return 1; // Simule un mur hors limite
            return map[y][x];
        }

        @Override
        public int getLargeur() {
            return map[0].length;
        }

        @Override
        public int getHauteur() {
            return map.length;
        }

        public void setTile(int y, int x, int value) {
            map[y][x] = value;
        }
    }

    @Before
    public void setUp() {
        perso = new Personnage(64, 64); // 2 tuiles x 2 tuiles
        carte = new DummyMap(10, 10);
    }

    @Test
    public void testSautEtGravite() {
        ((DummyMap) carte).setTile(3, 2, 1); // Sol à y=3
        perso.sauter(); // Devrait changer vitesseY
        assertFalse(perso.estMort());
        double yAvant = perso.getY();
        perso.mettreAJour(carte);
        assertTrue(perso.getY() < yAvant); // Le personnage monte
    }

    @Test
    public void testDeplacementGaucheSansObstacle() {
        double positionInitiale = perso.getX();
        perso.deplacerGauche(carte);
        assertTrue(perso.getX() < positionInitiale);
        assertFalse(perso.estsVersLaDroite());
    }

    @Test
    public void testDeplacementDroiteSansObstacle() {
        double positionInitiale = perso.getX();
        perso.deplacerDroite(carte);
        assertTrue(perso.getX() > positionInitiale);
        assertTrue(perso.estsVersLaDroite());
    }

    @Test
    public void testPvEtArmure() {
        perso.decrementerPv(3);
        assertEquals(2, perso.getPv());
        perso.incrementerPv(4);
        assertEquals(5, perso.getPv()); // Ne dépasse pas 5

        perso.decrementerPvArmure(2);
        assertEquals(3, perso.getPvArmure());
        perso.decrementerPvArmure(10);
        assertEquals(0, perso.getPvArmure()); // Pas négatif
    }

    @Test
    public void testEstMort() {
        perso.decrementerPv(5);
        assertTrue(perso.estMort());
    }


    @Test
    public void testCollisionSol() {
        DummyMap map = (DummyMap) carte;
        // Mettre du sol à y=3 (96px), x=2
        map.setTile(3, 2, 1);
        perso.setY(80);
        perso.mettreAJour(map);
        assertEquals(64, perso.getY(), 0.1); // Reposé au sol
    }

    @Test
    public void testCollisionPlafond() {
        DummyMap map = (DummyMap) carte;
        map.setTile(1, 2, 1); // Bloc au-dessus
        perso.setY(64);
        perso.sauter();
        perso.mettreAJour(map);
        assertTrue(perso.getY() >= 64); // Ne passe pas à travers
    }

    @Test
    public void testDernierDegatEtCoup() {
        perso.setDernierCoupRecu(5000);
        assertEquals(5000, perso.getDernierCoupRecu());

        perso.setDernierDegatFeu(7000);
        assertEquals(7000, perso.getDernierDegatFeu());
    }
}
