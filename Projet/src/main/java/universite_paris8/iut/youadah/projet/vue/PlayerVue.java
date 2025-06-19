package universite_paris8.iut.youadah.projet.vue;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import universite_paris8.iut.youadah.projet.modele.Armes.Arc;
import universite_paris8.iut.youadah.projet.modele.Armes.Epee;
import universite_paris8.iut.youadah.projet.modele.Player;

/**
 * Classe représentant la vue d'un joueur dans le jeu.
 * Elle gère l'affichage du joueur et de son équipement.
 */

public class  PlayerVue {
    private Image spriteDroite ;
    private Image spriteGauche;
    private final ImageView imageJoueur;

    private final Player joueur;
    private boolean isBlesse = false;

    private boolean versLaDroite = true;

    private final Image spritePiocheDroite = new Image(getClass().getResource("/images/persoRightPioche.png").toExternalForm());
    private final Image spritePiocheGauche = new Image(getClass().getResource("/images/PersoLeftPioche.png").toExternalForm());

    private final Image spritePotionDroite = new Image(getClass().getResource("/images/PersoRightPotion.png").toExternalForm());
    private final Image spritePotionGauche = new Image(getClass().getResource("/images/PersoLeftPotion.png").toExternalForm());

    private final Image spriteEpeeDroite = new Image(getClass().getResource("/images/PersoRightEpee.png").toExternalForm());
    private final Image spriteEpeeGauche = new Image(getClass().getResource("/images/PersoLeftEpee.png").toExternalForm());

    private final Image spriteArcDroite = new Image(getClass().getResource("/images/PersoRightArc.png").toExternalForm());
    private final Image spriteArcGauche = new Image(getClass().getResource("/images/PersoLeftArc.png").toExternalForm());

    /**
     * Constructeur de la classe PlayerVue.
     * @param joueur
     */
        public PlayerVue(Player joueur) {
            this.joueur = joueur;
            imageJoueur = new ImageView(spriteDroite);
            imageJoueur.setFitWidth(64);
            imageJoueur.setFitHeight(64);
            imageJoueur.translateXProperty().bind(joueur.xProperty().subtract(16).asObject());
            imageJoueur.translateYProperty().bind(joueur.yProperty().subtract(32).asObject());
            spriteDroite = new Image(getClass().getResource("/images/PersoRight.png").toExternalForm());
            spriteGauche = new Image(getClass().getResource("/images/PersoLeft.png").toExternalForm());

        }

    /**
     * Met à jour l'affichage du joueur en fonction de son état.
     * Change l'image affichée en fonction de la direction du joueur et de l'objet qu'il possède.
     */
    public void mettreAJourJoueur() {
            versLaDroite = joueur.estsVersLaDroite();

            if (!isBlesse) {
                imageJoueur.setImage(versLaDroite ? spriteDroite : spriteGauche);
            }

            if (joueur.getObjetPossede() != null) {
                String nomObjet = joueur.getObjetPossede().getNom().toLowerCase();

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

    public ImageView getNode() {
        return imageJoueur;
    }
}