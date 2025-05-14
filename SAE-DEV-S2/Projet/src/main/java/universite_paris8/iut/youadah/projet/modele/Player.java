package universite_paris8.iut.youadah.projet.modele;

import javafx.scene.image.Image;

public class Player {
    public double x, y;       // Position en pixels
    public double velocityY;  // Vitesse verticale
    public boolean onGround;
    public boolean facingRight = true;

    private Image imgRight;
    private Image imgLeft;

    private static final double GRAVITY = 0.3;
    private static final double JUMP_VELOCITY = -5;
    private static final double MOVE_SPEED = 1.0;
    private static final int TILE_SIZE = 32;

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

        // Collision au sol
        int tileX = (int)(x / TILE_SIZE);
        int tileY = (int)((y + TILE_SIZE) / TILE_SIZE); // position des pieds

        if (tileY < map.getHauteur() && tileX < map.getLargeur() && tileX >= 0) {
            int tileBelow = map.getTile(tileY, tileX); // terrain[y][x]

            if (tileBelow == 1 || tileBelow == 2) { // herbe ou terre
                y = tileY * TILE_SIZE - TILE_SIZE;
                velocityY = 0;
                onGround = true;
            } else {
                onGround = false;
            }
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
