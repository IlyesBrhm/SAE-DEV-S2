package universite_paris8.iut.youadah.projet.modele;

import javafx.scene.layout.Pane;
import universite_paris8.iut.youadah.projet.vue.ObjetVue;

import java.util.ArrayList;

/**
 * Classe qui représente un objet posé au sol dans le jeu
 * Gère le placement, le ramassage et le dépôt d'objets dans le monde du jeu
 */
public class ObjetAuSol {
    // Taille d'une tuile en pixels pour le positionnement des objets
    private final int TAILLE_TUILE = 32;
    // Liste statique qui contient tous les objets au sol dans le jeu
    private static final ArrayList<ObjetAuSol> objetsAuSol = new ArrayList<>();

    // Position de l'objet sur la grille du jeu
    private int x, y;
    // Représentation visuelle de l'objet
    private ObjetVue objetVue;

    /**
     * Constructeur pour créer un nouvel objet au sol
     * 
     * @param x Position X sur la grille du jeu
     * @param y Position Y sur la grille du jeu
     * @param pane Conteneur graphique où l'objet sera affiché
     * @param objet L'objet à placer au sol
     */
    public ObjetAuSol(int x, int y, Pane pane, Objet objet) {
        this.x = x;
        this.y = y;
        this.objetVue = new ObjetVue(objet);

        // Configuration de l'apparence visuelle de l'objet
        objetVue.getImageView().setFitWidth(26);
        objetVue.getImageView().setFitHeight(26);
        // Positionnement de l'objet sur la grille en multipliant par la taille d'une tuile
        objetVue.getImageView().setLayoutX(x * TAILLE_TUILE);
        objetVue.getImageView().setLayoutY(y * TAILLE_TUILE);

        // Ajout de l'objet à la liste des objets au sol
        objetsAuSol.add(this);
        // Ajout de la représentation visuelle à l'interface graphique
        pane.getChildren().add(objetVue.getImageView());
    }

    /**
     * Permet au joueur de ramasser les objets qui sont à sa position
     * 
     * @param joueur Le joueur qui ramasse les objets
     * @param inventaire L'inventaire où les objets seront ajoutés
     * @param pane Le conteneur graphique où les objets sont affichés
     * @return true si au moins un objet a été ramassé, false sinon
     */
    public static boolean ramasser(Player joueur, Inventaire inventaire, Pane pane) {
        // Conversion des coordonnées du joueur en coordonnées de la grille
        int joueurX = (int) (joueur.getX() / 32);
        int joueurY = (int) (joueur.getY() / 32);

        // Liste pour stocker les objets à ramasser
        ArrayList<ObjetAuSol> aRamasser = new ArrayList<>();
        boolean auMoinsUnRamasse = false;

        // Parcours une copie de la liste pour éviter les problèmes de modification pendant l'itération
        for (ObjetAuSol o : new ArrayList<>(objetsAuSol)) {
            // Vérifie si l'objet est à la même position que le joueur
            if (o.x == joueurX && o.y == joueurY) {
                // Tente d'ajouter l'objet à l'inventaire
                boolean ajoute = inventaire.ajouterObjet(o.objetVue.getObjet());
                if (ajoute) {
                    // Si l'objet a été ajouté à l'inventaire, le retire de l'affichage
                    pane.getChildren().remove(o.objetVue.getImageView());
                    aRamasser.add(o);
                    auMoinsUnRamasse = true;
                } else {
                    // Si l'inventaire est plein, affiche un message
                    System.out.println("Inventaire plein : impossible de ramasser " + o.objetVue.getObjet().getNom());
                }
            }
        }

        // Retire tous les objets ramassés de la liste des objets au sol
        objetsAuSol.removeAll(aRamasser);
        return auMoinsUnRamasse;
    }

    /**
     * Dépose un objet au sol à la position du joueur
     * 
     * @param objet L'objet à déposer
     * @param joueur Le joueur qui dépose l'objet
     * @param pane Le conteneur graphique où l'objet sera affiché
     */
    public static void deposerJoueur(Objet objet, Player joueur, Pane pane) {
        // Conversion des coordonnées du joueur en coordonnées de la grille
        int x = (int) (joueur.getX() / 32);
        int y = (int) (joueur.getY() / 32);
        // Création d'un nouvel objet au sol à la position du joueur
        new ObjetAuSol(x, y, pane, objet);
    }

    /**
     * Dépose un objet au sol à une position spécifique
     * 
     * @param objet L'objet à déposer
     * @param x Position X sur la grille du jeu
     * @param y Position Y sur la grille du jeu
     * @param pane Le conteneur graphique où l'objet sera affiché
     */
    public static void deposer(Objet objet, int x, int y, Pane pane) {
        // Création d'un nouvel objet au sol aux coordonnées spécifiées
        new ObjetAuSol(x, y, pane, objet);
    }
}
