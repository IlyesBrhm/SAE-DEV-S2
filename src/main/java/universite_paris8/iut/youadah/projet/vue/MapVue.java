package universite_paris8.iut.youadah.projet.vue;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.TilePane;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsable de l'affichage et de la gestion visuelle de la carte du jeu.
 * Cette classe permet de représenter graphiquement une carte à partir d'une structure 2D d'entiers,
 * où chaque entier correspond à un type de tuile spécifique.
 */
public class MapVue {
    /** Taille en pixels de chaque tuile */
    private  final int TAILLE_TUILE = 32;
    /** Liste des ImageView représentant les tuiles de la carte */
    private List<ImageView> tuiles = new ArrayList<>();
    /** Nombre de colonnes dans la carte */
    private static final int NB_COLONNES = 58;
    /** Structure 2D représentant la carte, où chaque entier correspond à un type de tuile */
    private int[][] structure;


    /**
     * Constructeur de la classe MapVue.
     * 
     * @param structure Tableau 2D d'entiers représentant la structure de la carte
     */
    public MapVue(int[][] structure) {
        this.structure = structure;
    }

    /**
     * Affiche la carte complète dans le TilePane fourni.
     * Cette méthode efface d'abord le contenu existant, puis crée et ajoute
     * toutes les tuiles selon la structure de la carte.
     * 
     * @param tileMap Le TilePane dans lequel afficher la carte
     */
    public void afficherCarte( TilePane tileMap) {
        // Efface le contenu existant
        tileMap.getChildren().clear();
        tuiles.clear();

        // Parcourt la structure 2D pour créer et ajouter chaque tuile
        for (int y = 0; y < structure.length; y++) {
            for (int x = 0; x < structure[y].length; x++) {
                // Crée une nouvelle tuile avec l'image correspondant à l'ID dans la structure
                ImageView tuile = new ImageView(chargerImageTuile(structure[y][x]));
                // Définit la taille de la tuile
                tuile.setFitWidth(TAILLE_TUILE);
                tuile.setFitHeight(TAILLE_TUILE);
                // Ajoute la tuile à la liste et au TilePane
                tuiles.add(tuile);
                tileMap.getChildren().add(tuile);
            }
        }
    }

    /**
     * Met à jour l'image d'une tuile spécifique de la carte.
     * 
     * @param x Coordonnée X de la tuile à mettre à jour
     * @param y Coordonnée Y de la tuile à mettre à jour
     * @param nouveauID Nouvel identifiant de type de tuile
     */
    public void mettreAJourTuile(int x, int y, int nouveauID) {
        // Calcule l'index dans la liste de tuiles à partir des coordonnées 2D
        int index = y * NB_COLONNES + x;
        // Vérifie que l'index est valide
        if (index >= 0 && index < tuiles.size()) {
            // Récupère la tuile et met à jour son image
            ImageView tuile = tuiles.get(index);
            tuile.setImage(chargerImageTuile(nouveauID));
        }
    }

    /**
     * Charge l'image correspondant à un identifiant de tuile.
     * 
     * @param id Identifiant du type de tuile
     * @return L'image correspondante au type de tuile
     */
    public  Image chargerImageTuile(int id) {
        return switch (id) {
            case 0 -> charger("/images/Vide.png");   // ciel
            case 1 -> charger("/images/Herbe.png");  // herbe
            case 2 -> charger("/images/Terre.png");  // terre
            case 3 -> charger("/images/Pierre.png"); // pierre
            case 4 -> charger("/images/Nuage.png");  // décor
            case 5 -> charger("/images/Feu.png");    // feu
            case 6 -> charger("/images/Bois.png");   // bois
            default -> null;                         // type inconnu
        };
    }

    /**
     * Retourne le nom du bloc à une position spécifique.
     * 
     * @param x Coordonnée X du bloc
     * @param y Coordonnée Y du bloc
     * @return Le nom du bloc à la position spécifiée
     */
    public String getBloc(int x, int y) {
            // Récupère l'identifiant du bloc à la position spécifiée
            int id = structure[y][x];
            // Retourne le nom correspondant à l'identifiant
            return switch (id) {
                case 0 -> "Vide";    // Bloc de type vide/ciel
                case 1 -> "Herbe";   // Bloc d'herbe
                case 2 -> "Terre";   // Bloc de terre
                case 3 -> "Pierre";  // Bloc de pierre
                case 4 -> "Nuage";   // Bloc de nuage
                case 5 -> "Feu";     // Bloc de feu
                case 6 -> "Bois";    // Bloc de bois
                default -> "Inconnu"; // Type de bloc non reconnu
            };
    }

    /**
     * Méthode utilitaire pour charger une image à partir d'un chemin de ressource.
     * 
     * @param chemin Chemin de la ressource image
     * @return L'objet Image chargé
     */
    private  Image charger(String chemin) {
        return new Image(MapVue.class.getResource(chemin).toExternalForm());
    }

}
