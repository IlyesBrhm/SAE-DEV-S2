package universite_paris8.iut.aclaudio.demo.Player;

import javafx.scene.image.Image;
import universite_paris8.iut.aclaudio.demo.world.Map;

public class Player {
    public double x, y;       // Position en pixels
    public double velocityY; // Vitesse verticale
    public boolean onGround;
    public boolean facingRight = true;

    private Image imgRight;
    private Image imgLeft;

    private static final double GRAVITY = 0.03;
    private static final double JUMP_VELOCITY = -2.5;
    private static final double MOVE_SPEED = 10.0;


    public Player(double startX, double startY) {
        this.x = startX;
        this.y = startY;
        this.velocityY = 0;

        imgRight = new Image(getClass().getResource("/images/PersoRight.png").toExternalForm());
        imgLeft = new Image(getClass().getResource("/images/PersoLeft.png").toExternalForm());
    }

    public void update(Map map) {
        velocityY += GRAVITY;
        y += velocityY;

        int tileX = (int)(x / 32);
        int tileY = (int)((y + 32) / 32); // Pieds du perso

        if (tileY < map.getHeight() && map.getTile(tileX, tileY) == Map.Herbe) {
            y = tileY * 32 - 32;
            velocityY = 0;
            onGround = true;
        } else {
            onGround = false;
        }
    }

    public void moveLeft() {
        x -= MOVE_SPEED;
        facingRight = false;
    }

    public void moveRight() {
        x += MOVE_SPEED;
        facingRight = true;
    }

    public void jump() {
        if (onGround) {
            velocityY = JUMP_VELOCITY;
            onGround = false;
        }
    }

    public Image getCurrentImage() {
        return facingRight ? imgRight : imgLeft;
    }
}
