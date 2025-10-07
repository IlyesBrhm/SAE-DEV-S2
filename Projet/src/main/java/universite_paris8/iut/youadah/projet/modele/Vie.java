package universite_paris8.iut.youadah.projet.modele;

public class Vie {
    private int pv;
    private int pvArmure;
    private long dernierDegatFeu = 1;
    private Objet objetPossede;
    private long dernierCoupRecu;

    public Vie(){
        this.pv = 5;
        this.pvArmure = 5;
        objetPossede = null;
        this.dernierCoupRecu = 0;
    }
    public void incrementerPv(int pvEnPlus) {
        pv = Math.min(pv + pvEnPlus, 5);
    }

    public void decrementerPv(int pvEnMoins) {
        pv = Math.max(pv - pvEnMoins, 0);
    }

    public int getPv() {
        return pv; }
    public int getPvArmure() {
        return pvArmure;
    }

    public void decrementerPvArmure(int valeur) {
        this.pvArmure = Math.max(0, this.pvArmure - valeur);
    }

    public void setObjetPossede(Objet objetPossede) {
        this.objetPossede = objetPossede;
    }

    public Objet getObjetPossede() {
        return objetPossede;
    }
    public long getDernierDegatFeu() {
        return dernierDegatFeu;
    }

    public void setDernierDegatFeu(long t) {
        this.dernierDegatFeu = t; }

    public boolean estMort() {
        if (pv > 0)
            return false;
        else
            return true;
    }
}
