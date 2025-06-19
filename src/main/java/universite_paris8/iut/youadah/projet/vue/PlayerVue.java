package universite_paris8.iut.youadah.projet.vue;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import universite_paris8.iut.youadah.projet.modele.Armes.Arc;
import universite_paris8.iut.youadah.projet.modele.Armes.Epee;
import universite_paris8.iut.youadah.projet.modele.Player;

/**
 * Classe responsable de l'affichage graphique du joueur
 * Gère les différentes images du joueur selon sa direction et l'objet qu'il tient
 */
public class PlayerVue {
    // Images de base du joueur (sans objet)
    private Image spriteDroite ;
    private Image spriteGauche;
    // Composant graphique qui affiche l'image du joueur
    private final ImageView imageJoueur;

    // Référence au modèle du joueur
    private final Player joueur;
    // Indique si le joueur est en état de blessure (pour l'animation)
    private boolean isBlesse = false;

    // Direction actuelle du joueur
    private boolean versLaDroite = true;

    // Images du joueur tenant une pioche
    private final Image spritePiocheDroite = new Image(getClass().getResource("/images/persoRightPioche.png").toExternalForm());
    private final Image spritePiocheGauche = new Image(getClass().getResource("/images/PersoLeftPioche.png").toExternalForm());

    // Images du joueur tenant une potion
    private final Image spritePotionDroite = new Image(getClass().getResource("/images/PersoRightPotion.png").toExternalForm());
    private final Image spritePotionGauche = new Image(getClass().getResource("/images/PersoLeftPotion.png").toExternalForm());

    // Images du joueur tenant une épée
    private final Image spriteEpeeDroite = new Image(getClass().getResource("/images/PersoRightEpee.png").toExternalForm());
    private final Image spriteEpeeGauche = new Image(getClass().getResource("/images/PersoLeftEpee.png").toExternalForm());

    // Images du joueur tenant un arc
    private final Image spriteArcDroite = new Image(getClass().getResource("/images/PersoRightArc.png").toExternalForm());
    private final Image spriteArcGauche = new Image(getClass().getResource("/images/PersoLeftArc.png").toExternalForm());

    /**
     * Constructeur de la vue du joueur
     * Initialise l'affichage graphique et lie la position de l'image à celle du joueur
     * 
     * @param joueur Le modèle du joueur à représenter graphiquement
     */
    public PlayerVue(Player joueur) {
        this.joueur = joueur;
        // Création du composant graphique pour afficher le joueur
        imageJoueur = new ImageView(spriteDroite);
        // Configuration de la taille de l'image
        imageJoueur.setFitWidth(64);
        imageJoueur.setFitHeight(64);
        // Liaison de la position de l'image à celle du joueur (avec décalage pour centrage)
        imageJoueur.translateXProperty().bind(joueur.xProperty().subtract(16).asObject());
        imageJoueur.translateYProperty().bind(joueur.yProperty().subtract(32).asObject());
        // Chargement des images de base du joueur
        spriteDroite = new Image(getClass().getResource("/images/PersoRight.png").toExternalForm());
        spriteGauche = new Image(getClass().getResource("/images/PersoLeft.png").toExternalForm());
    }

    /**
     * Met à jour l'apparence du joueur en fonction de sa direction et de l'objet qu'il tient
     * 
     * @param joueur Le modèle du joueur dont l'apparence doit être mise à jour
     */
    public void mettreAJourJoueur(Player joueur) {
        // Récupère la direction du joueur
        versLaDroite = joueur.estsVersLaDroite();

        // Si le joueur n'est pas blessé, affiche l'image de base selon sa direction
        if (!isBlesse) {
            imageJoueur.setImage(versLaDroite ? spriteDroite : spriteGauche);
        }

        // Si le joueur tient un objet, change son apparence en fonction de l'objet
        if (joueur.getObjetPossede() != null) {
            String nomObjet = joueur.getObjetPossede().getNom().toLowerCase();

            // Sélectionne l'image appropriée selon l'objet tenu et la direction
            if (nomObjet.equals("pioche")) {
                imageJoueur.setImage(versLaDroite ? spritePiocheDroite : spritePiocheGauche);
            } else if (nomObjet.contains("potion")) {
                imageJoueur.setImage(versLaDroite ? spritePotionDroite : spritePotionGauche);
            } else if (joueur.getObjetPossede() instanceof Epee) {
                imageJoueur.setImage(versLaDroite ? spriteEpeeDroite : spriteEpeeGauche);
            } else if (joueur.getObjetPossede() instanceof Arc) {
                imageJoueur.setImage(versLaDroite ? spriteArcDroite : spriteArcGauche);
            }
        }
    }

    /**
     * Récupère le composant graphique représentant le joueur
     * 
     * @return Le composant ImageView du joueur
     */
    public ImageView getNode() {
        return imageJoueur;
    }
}
