package universite_paris8.iut.youadah.projet.modele.actions;

import javafx.scene.layout.Pane;
import universite_paris8.iut.youadah.projet.controller.GestionEffetDegats;
import universite_paris8.iut.youadah.projet.modele.Armes.Arc;
import universite_paris8.iut.youadah.projet.modele.Player;

import java.util.List;

/**
 * Classe représentant l'action de tir à l'arc.
 * Permet de détecter si un ennemi est dans la portée de tir
 * et d'appliquer les dégâts si c'est le cas.
 */
public class Tirer {

    // Constructeur vide
    public Tirer() {}

    /**
     * Permet à un joueur de tirer avec son arc.
     * Si un ennemi est dans la direction du joueur, dans la portée, et à peu près à la même hauteur, il subit des dégâts.
     *
     * @param joueur  Le joueur qui tire
     * @param ennemis Liste de tous les joueurs (incluant ennemis et alliés)
     * @param overlay Pane pour les effets visuels (ex: clignotement rouge)
     * @param arc     L'arc utilisé pour tirer (contient portée et dégâts)
     */
    public void attaquerAvecArc(Player joueur, List<Player> ennemis, Pane overlay, Arc arc) {
        double positionXJoueur = joueur.getX();
        double positionYJoueur = joueur.getY();
        boolean regardeVersLaDroite = joueur.estsVersLaDroite();

        // On parcourt tous les autres joueurs pour voir s'ils sont touchables
        for (Player ennemi : ennemis) {
            if (ennemi == joueur) continue; // On ne se tire pas dessus soi-même

            double positionXEnnemi = ennemi.getX();
            double positionYEnnemi = ennemi.getY();

            boolean ennemiEstDansLaPortee = false;

            // Vérifie si l’ennemi est dans la direction du regard du joueur et dans la portée
            if (regardeVersLaDroite) {
                if (positionXEnnemi > positionXJoueur && positionXEnnemi <= positionXJoueur + arc.getPortee()) {
                    ennemiEstDansLaPortee = true;
                }
            } else {
                if (positionXEnnemi < positionXJoueur && positionXEnnemi >= positionXJoueur - arc.getPortee()) {
                    ennemiEstDansLaPortee = true;
                }
            }

            // Vérifie que l'ennemi est à peu près à la même hauteur (tolérance de 32 pixels)
            boolean surMemeHauteur = Math.abs(positionYEnnemi - positionYJoueur) < 32;

            // Si les conditions sont réunies, on inflige les dégâts et affiche un effet
            if (ennemiEstDansLaPortee && surMemeHauteur) {
                infligerDegats(ennemi, arc.getDegats());                         // Réduit les PV ou armure
                GestionEffetDegats.definirSuperposition(overlay);               // Définit la zone d’effet visuel
                GestionEffetDegats.declencherClignotementRouge();              // Déclenche un effet visuel (dégâts)
                break; // On ne touche qu’un seul ennemi à la fois
            }
        }
    }

    /**
     * Applique les dégâts à un joueur : prioritairement à l’armure s’il en a.
     * @param cible Joueur à blesser
     * @param degats Quantité de dégâts
     */
    private void infligerDegats(Player cible, int degats) {
        if (cible.getPvArmure() > 0) {
            cible.decrementerPvArmure(degats); // D'abord sur l'armure
        } else {
            cible.decrementerPv(degats);       // Sinon sur la vie
        }
    }
}