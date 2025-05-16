package universite_paris8.iut.youadah.projet.vue;

import javafx.scene.image.Image;

/**
 * Classe utilitaire pour charger les images des tuiles selon leur identifiant.
 */
public class MapRenderer {

    public static Image chargerImageTuile(int id) {
        return switch (id) {

            /*
            case 0 -> charger("/images/Vide.png");   // ciel
            case 1 -> charger("/images/Herbe.png");  // herbe
            case 2 -> charger("/images/Terre.png");  // terre
            case 3 -> charger("/images/Pierre.png"); // pierre
            case 4 -> charger("/images/Nuage.png");  // décor
            case 5 -> charger("/images/HerbeRight.png");  // hebre coin droit
            case 6 -> charger("/images/HerbeLeft.png");  // herbe coin gauche
             */

            case 0 -> charger("/images/Vide.png");
            case 1 -> charger("/images/dungeon/floor/dirt_full_new.png");
            case 3 -> charger("/images/dungeon/floor/dirt_1_new.png");
            case 4 -> charger("/images/Nuage.png");
            case 7 -> charger("/images/dungeon/floor/dirt_south_old.png");


            default -> null;
        };
    }

    private static Image charger(String chemin) {
        return new Image(MapRenderer.class.getResource(chemin).toExternalForm());
    }
}
