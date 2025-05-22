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

    public int getPv() {
        return pv.get();
    }

    public IntegerProperty pvProperty() {
        return pv;
    }

    public void subirDegats(int degats) {
        pv.set(Math.max(0, pv.get() - degats));
    }

    public void soigner(int soin) {
        pv.set(Math.min(pvMax, pv.get() + soin));
    }

    public boolean estMort() {
        return pv.get() <= 0;
    }

    public void reinitialiser() {
        pv.set(pvMax);
    }
}
