package universite_paris8.iut.youadah.projet.vue;

import javafx.scene.image.Image;
import universite_paris8.iut.youadah.projet.modele.Map;
import universite_paris8.iut.youadah.projet.modele.Player;

public class MapRenderer {

    private static final double GRAVITE = 0.08;
    private static final int TAILLE_TUILE = 32;

    public static Image chargerImageTuile(int id) {
        return switch (id) {
            case 0 -> charger("/images/Vide.png");   // ciel
            case 1 -> charger("/images/Herbe.png");  // herbe
            case 2 -> charger("/images/Terre.png");  // terre
            case 3 -> charger("/images/Pierre.png"); // pierre
            case 4 -> charger("/images/Nuage.png");  // décor
            default -> null;
        };
    }

    private static Image charger(String chemin) {
        return new Image(MapRenderer.class.getResource(chemin).toExternalForm());
    }

    public static void mettreAJourJoueur(Player joueur, Map map) {
        double y = joueur.getY();
        double vitesseY = joueur.getVitesseY() + GRAVITE;
        y += vitesseY;

        int tuileY = (int)((y + TAILLE_TUILE) / TAILLE_TUILE);
        int tuileXG = (int)(joueur.getX() / TAILLE_TUILE);
        int tuileXD = (int)((joueur.getX() + TAILLE_TUILE - 1) / TAILLE_TUILE);

        boolean solG = estSolide(map.getTile(tuileY, tuileXG));
        boolean solD = estSolide(map.getTile(tuileY, tuileXD));

        boolean auSol;
        if ((tuileY < map.getHauteur()) && (solG || solD)) {
            y = (tuileY - 1) * TAILLE_TUILE;
            vitesseY = 0;
            auSol = true;
        } else {
            auSol = false;
        }

        // Bord bas
        double hauteurMax = map.getHauteur() * TAILLE_TUILE - TAILLE_TUILE;
        if (y > hauteurMax) {
            y = hauteurMax;
            vitesseY = 0;
            auSol = true;
        }

        // Bord haut
        if (y < 0) {
            y = 0;
            vitesseY = 0;
        }

        joueur.setY(y);
        joueur.setVitesseY(vitesseY);
        joueur.setAuSol(auSol);
    }

    private static boolean estSolide(int id) {
        return id == 1 || id == 3;
    }
}
