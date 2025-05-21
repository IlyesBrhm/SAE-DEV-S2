package universite_paris8.iut.youadah.projet.modele;

public class Coeur {
    private int pv;
    private final int pvMax;

    public Coeur(int pvMax) {
        this.pvMax = pvMax;
        this.pv = pvMax;
    }

    public int getPv() {
        return pv;
    }

    public void subirDegats(int degats) {
        this.pv = Math.max(0, pv - degats);
    }

    public void soigner(int soin) {
        this.pv = Math.min(pvMax, pv + soin);
    }

    public boolean estMort() {
        return pv <= 0;
    }

    public void reinitialiser() {
        this.pv = pvMax;
    }
}
