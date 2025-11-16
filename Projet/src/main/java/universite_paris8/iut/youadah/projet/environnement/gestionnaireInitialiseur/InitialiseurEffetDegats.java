package universite_paris8.iut.youadah.projet.environnement.gestionnaireInitialiseur;

import javafx.scene.layout.Pane;
import universite_paris8.iut.youadah.projet.controller.gestionnaire.GestionEffetDegats;

/**
 * Classe responsable de l'initialisation et de la configuration
 * du gestionnaire d'effets de dégâts (clignotement rouge, etc.)
 */
public class InitialiseurEffetDegats {

    private GestionEffetDegats gestionEffetDegats;


    public void initialiser(Pane overlayRouge) {

        gestionEffetDegats = GestionEffetDegats.getInstance();
        gestionEffetDegats.definirSuperposition(overlayRouge);
    }


    public GestionEffetDegats getGestionEffetDegats() {
        return gestionEffetDegats;
    }
}
