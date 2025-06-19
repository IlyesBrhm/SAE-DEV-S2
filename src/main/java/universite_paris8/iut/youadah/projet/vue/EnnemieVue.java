package universite_paris8.iut.youadah.projet.vue;

import javafx.scene.image.Image;
import universite_paris8.iut.youadah.projet.modele.Ennemie;
import universite_paris8.iut.youadah.projet.modele.Personnage;

/**
 * Classe responsable de l'affichage graphique des ennemis dans le jeu
 * Hérite de PersonnageVue pour réutiliser les fonctionnalités de rendu des personnages
 */
public class EnnemieVue extends PersonnageVue {

    /**
     * Constructeur de la vue d'ennemi
     * Initialise les sprites pour les mouvements vers la droite et la gauche
     * 
     * @param ennemie Le modèle d'ennemi à représenter graphiquement
     */
    public EnnemieVue(Ennemie ennemie) {
        super(ennemie);
        // Chargement des images pour les déplacements à droite et à gauche
        spriteDroite = new Image(getClass().getResource("/images/PersoRight.png").toExternalForm());
        spriteGauche = new Image(getClass().getResource("/images/PersoLeft.png").toExternalForm());
    }

}
