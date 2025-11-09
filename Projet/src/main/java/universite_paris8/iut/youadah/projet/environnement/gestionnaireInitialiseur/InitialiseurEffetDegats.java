package universite_paris8.iut.youadah.projet.environnement.gestionnaireInitialiseur;

import javafx.scene.layout.Pane;
import universite_paris8.iut.youadah.projet.controller.gestionnaire.GestionEffetDegats;

/**
 * Classe responsable de l'initialisation et de la configuration
 * du gestionnaire d'effets de dégâts (clignotement rouge, etc.)
 */
public class InitialiseurEffetDegats {

    private GestionEffetDegats gestionEffetDegats;

    /**
     * Initialise et configure le gestionnaire des effets de dégâts.
     * @param overlayRouge le Pane transparent utilisé pour les effets visuels (clignotement rouge)
     */
    public void initialiser(Pane overlayRouge) {
        // ✅ On récupère l’instance unique au lieu d’en créer une nouvelle
        gestionEffetDegats = GestionEffetDegats.getInstance();
        gestionEffetDegats.definirSuperposition(overlayRouge);
    }

    /**
     * Retourne l'instance unique du gestionnaire d'effets de dégâts.
     */
    public GestionEffetDegats getGestionEffetDegats() {
        return gestionEffetDegats;
    }
}
