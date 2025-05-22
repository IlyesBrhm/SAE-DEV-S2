package universite_paris8.iut.youadah.projet.modele;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class CoeurModel {
    private boolean enDegats = false;

    public boolean estEnDegats() {
        return enDegats;
    }

    public void signalerDegats(Runnable finDegatsCallback) {
        if (enDegats) return;

        enDegats = true;
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), e -> {
            enDegats = false;
            if (finDegatsCallback != null) {
                finDegatsCallback.run();
            }
        }));
        timeline.setCycleCount(1);
        timeline.play();
    }
}
