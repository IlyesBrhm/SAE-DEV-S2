package universite_paris8.iut.youadah.projet.modele;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Coeur {
    private final IntegerProperty pv;
    private final int pvMax;

    public Coeur(int pvMax) {
        this.pvMax = pvMax;
        this.pv = new SimpleIntegerProperty(pvMax);
    }

    public boolean estMort() {
        return pv.get() <= 0;
    }

}
