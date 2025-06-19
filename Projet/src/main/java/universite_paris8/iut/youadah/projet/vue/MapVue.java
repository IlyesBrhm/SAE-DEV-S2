package universite_paris8.iut.youadah.projet.vue;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.TilePane;
import javafx.scene.image.ImageView;
import universite_paris8.iut.youadah.projet.modele.GameMap;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant la vue de la carte du jeu.
 * Elle gère l'affichage des tuiles de la carte et leur mise à jour.
 */
public class MapVue {
    private  final int TAILLE_TUILE = 32;
    private List<ImageView> tuiles = new ArrayList<>();
    private static final int NB_COLONNES = 58;
    private GameMap carte;


    /**
     * Constructeur de la vue de la carte.
     * @param carte La carte du jeu à afficher.
     */
    public MapVue(GameMap carte) {
        this.carte = carte;
    }

    /**
     * Affiche la carte dans le TilePane fourni.
     * Vide d'abord le TilePane, puis ajoute les tuiles de la carte.
     *
     * @param tileMap Le TilePane dans lequel afficher la carte.
     */
    public void afficherCarte( TilePane tileMap) {
        tileMap.getChildren().clear();
        tuiles.clear();

        for (int y = 0; y < carte.getTerrain().length; y++) {
            for (int x = 0; x < carte.getTerrain()[y].length; x++) {
                ImageView tuile = new ImageView(chargerImageTuile(carte.getTerrain()[y][x]));
                tuile.setFitWidth(TAILLE_TUILE);
                tuile.setFitHeight(TAILLE_TUILE);
                tuiles.add(tuile);
                tileMap.getChildren().add(tuile);
            }
        }
    }

    /**
     * Met à jour une tuile spécifique de la carte avec un nouvel ID.
     *
     * @param x         La coordonnée x de la tuile à mettre à jour.
     * @param y         La coordonnée y de la tuile à mettre à jour.
     * @param nouveauID Le nouvel ID de la tuile.
     */
    public void mettreAJourTuile(int x, int y, int nouveauID) {
        int index = y * NB_COLONNES + x;
        if (index >= 0 && index < tuiles.size()) {
            ImageView tuile = tuiles.get(index);
            tuile.setImage(chargerImageTuile(nouveauID));
        }
    }

    /**
     * Charge l'image correspondant à l'ID de la tuile.
     *
     * @param id L'ID de la tuile.
     * @return L'image correspondante à l'ID.
     */
    public  Image chargerImageTuile(int id) {
        return switch (id) {
            case 0 -> charger("/images/Vide.png");   // ciel
            case 1 -> charger("/images/Herbe.png");  // herbe
            case 2 -> charger("/images/Terre.png");  // terre
            case 3 -> charger("/images/Pierre.png"); // pierre
            case 4 -> charger("/images/Nuage.png"); // décor
            case 5 -> charger("/images/Feu.png");
            case 6 -> charger("/images/Bois.png");
            default -> null;

        };
    }

    /**
     * Récupère le nom du bloc à une position spécifique sur la carte.
     *
     * @param x La coordonnée x du bloc.
     * @param y La coordonnée y du bloc.
     * @return Le nom du bloc à cette position.
     */
    public String getBloc(int x, int y) {
            int id = carte.getTerrain()[y][x];
            return switch (id) {
                case 0 -> "Vide";
                case 1 -> "Herbe";
                case 2 -> "Terre";
                case 3 -> "Pierre";
                case 4 -> "Nuage";
                case 5 -> "Feu";
                case 6 -> "Bois";

                default -> "Inconnu";
            };
    }

    /**
     * Charge une image à partir du chemin spécifié.
     *
     * @param chemin Le chemin de l'image à charger.
     * @return L'image chargée.
     */
    private  Image charger(String chemin) {
        return new Image(MapVue.class.getResource(chemin).toExternalForm());
    }

}