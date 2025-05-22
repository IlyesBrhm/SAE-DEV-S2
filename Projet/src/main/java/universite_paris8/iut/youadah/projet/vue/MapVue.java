package universite_paris8.iut.youadah.projet.vue;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.TilePane;
import javafx.scene.image.ImageView;
public class MapVue {
    private  final int TAILLE_TUILE = 32;
    @FXML
    private TilePane tileMap;
    public MapVue() {

    }
    public  void afficherCarte(int[][] structure, TilePane tileMap) {
        for (int y = 0; y < structure.length; y++) {
            for (int x = 0; x < structure[y].length; x++) {
                ImageView tuile = new ImageView(chargerImageTuile(structure[y][x]));
                tuile.setFitWidth(TAILLE_TUILE);
                tuile.setFitHeight(TAILLE_TUILE);
                tileMap.getChildren().add(tuile);
            }
        }
    }
    public  Image chargerImageTuile(int id) {
        return switch (id) {
            case 0 -> charger("/images/Vide.png");   // ciel
            case 1 -> charger("/images/Herbe.png");  // herbe
            case 2 -> charger("/images/Terre.png");  // terre
            case 3 -> charger("/images/Pierre.png"); // pierre
            case 4 -> charger("/images/Nuage.png"); // décor
            case 5 -> charger("/images/Feu.png"); //Feu
            default -> null;

        };
    }

    private  Image charger(String chemin) {
        return new Image(MapVue.class.getResource(chemin).toExternalForm());
    }

}