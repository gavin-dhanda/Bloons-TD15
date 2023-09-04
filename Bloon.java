package indy;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import static indy.Constants.*;

/**
 * This is the Bloon class. It contains a variety of instance variables related to its shapes
 * and position tracking. There is only one class to represent all bloon types, so the set-up method
 * uses Factory Pattern to determine the shape and color of the bloon as it is popped and changes
 * appearance. The Bloons in this program are all contained within Nodes in the DoublyLinkedList.
 */
public class Bloon {

    private MoneyTracker bank;
    private Ellipse body;
    private Ellipse camoFilm;
    private Polygon tail;
    private Pane bloonPane;
    private Pane popPane;
    private int[][] path;
    private int checkpoint;
    private double distanceTraveled;
    private double spawnDelay;
    private double startingX;
    private double startingY;
    private double xMove;
    private double yMove;
    private int strength;
    private double speed;
    private boolean camo;
    private boolean andyMode;
    private ImageView andy;
    private Rectangle andyHealthBorder;
    private Rectangle andyHealth;
    private Rectangle andyHealthBack;
    private Pane andyPane;

    /**
     * This is the Bloon class' Constructor. It takes in the bloon, pop, and andy panes, and the
     * MoneyTracker, through association, and takes in arguments for the properties of the bloon
     * (camo, strength, etc.). The Constructor initializes these instance variables, sets up the
     * bloon's structural appearance based on if it's a camo/andy bloon, and sets an initial target.
     */
    public Bloon(Pane pane, Pane myPopPane, int myStrength, boolean isCamo, double xStart, double yStart,
                 double distanceTraveledStart, int checkpointStart, MoneyTracker myBank, Pane myAndyPane){
        this.path = MAP_PATH;
        this.checkpoint = checkpointStart;
        this.bloonPane = pane;
        this.popPane = myPopPane;
        this.andyPane = myAndyPane;
        this.bank = myBank;
        this.strength = myStrength;
        this.camo = isCamo;
        this.distanceTraveled = distanceTraveledStart;
        this.startingX = xStart;
        this.startingY = yStart;
        this.spawnDelay = 0;
        this.andyMode = false;

        // Bloon starts further left is it's an AndyBloon.
        this.tail = new Polygon(xStart, yStart+RED_Y1, xStart-TAIL_RAD,
                yStart+RED_Y2, xStart+TAIL_RAD, yStart+RED_Y2);
        if (this.strength == ANDY_STRENGTH){
            this.body = new Ellipse(xStart+ANDY_OFFSET, yStart, RED_XRAD, RED_YRAD);
            this.camoFilm = new Ellipse(xStart+ANDY_OFFSET, yStart, RED_XRAD, CAMO_FILM_HEIGHT);
            this.tail.setTranslateX(ANDY_OFFSET);
        }
        else {
            this.body = new Ellipse(xStart, yStart, RED_XRAD, RED_YRAD);
            this.camoFilm = new Ellipse(xStart, yStart, RED_XRAD, CAMO_FILM_HEIGHT);
        }
        this.bloonPane.getChildren().addAll(this.tail, this.body, this.camoFilm);

        // Visually remove the camo film if not camo.
        if (!this.camo){
            this.camoFilm.setFill(INVISIBLE);
        }

        this.setUpBloon();
        this.getNewTarget();
    }

    /**
     * This method is called from the Constructor, and every time a bloon is popped whose strength
     * is above 1. It redefines the style and appearance of the bloon to match its new strength using
     * Factory Pattern, and if it's an AndyBloon, it sets it up accordingly.
     */
    private void setUpBloon(){
        switch (this.strength){
            case RED_STRENGTH:
                this.setBloon(RED_XRAD, RED_YRAD, RED_COLOR, Color.MAROON, RED_SPEED,
                        new Polygon(this.startingX, this.startingY + RED_Y1,
                                this.startingX-TAIL_RAD, this.startingY + RED_Y2,
                                this.startingX+TAIL_RAD, this.startingY + RED_Y2));
                break;
            case BLUE_STRENGTH:
                this.setBloon(BLUE_XRAD, BLUE_YRAD, BLUE_COLOR, Color.BLUE, BLUE_SPEED,
                        new Polygon(this.startingX, this.startingY + BLUE_Y1,
                                this.startingX-TAIL_RAD, this.startingY + BLUE_Y2,
                                this.startingX+TAIL_RAD, this.startingY + BLUE_Y2));
                break;
            case GREEN_STRENGTH:
                this.setBloon(GREEN_XRAD, GREEN_YRAD, GREEN_COLOR, Color.GREEN, GREEN_SPEED,
                        new Polygon(this.startingX, this.startingY + GREEN_Y1,
                                this.startingX-TAIL_RAD, this.startingY + GREEN_Y2,
                                this.startingX+TAIL_RAD, this.startingY + GREEN_Y2));
                break;
            case YELLOW_STRENGTH:
                this.setBloon(YELLOW_XRAD, YELLOW_YRAD, YELLOW_COLOR, YELLOW_BORDER, YELLOW_SPEED,
                        new Polygon(this.startingX, this.startingY + YELLOW_Y1,
                                this.startingX-TAIL_RAD, this.startingY + YELLOW_Y2,
                                this.startingX+TAIL_RAD, this.startingY + YELLOW_Y2));
                break;
            case PINK_STRENGTH:
                this.setBloon(PINK_XRAD, PINK_YRAD, PINK_COLOR, PINK_BORDER, PINK_SPEED,
                        new Polygon(this.startingX, this.startingY + PINK_Y1,
                                this.startingX-TAIL_RAD, this.startingY + PINK_Y2,
                                this.startingX+TAIL_RAD, this.startingY + PINK_Y2));
                break;
            case BLACK_STRENGTH:
                this.setBloon(BLACK_XRAD, BLACK_YRAD, BLACK_COLOR, Color.BLACK, BLUE_SPEED,
                        new Polygon(this.startingX, this.startingY + BLACK_Y1,
                                this.startingX-TAIL_RAD, this.startingY + BLACK_Y2,
                                this.startingX+TAIL_RAD, this.startingY + BLACK_Y2));
                break;
            case LEAD_STRENGTH:
                this.setBloon(BLUE_XRAD, BLUE_YRAD, Color.DARKGRAY, BLACK_COLOR, RED_SPEED,
                        new Polygon(this.startingX, this.startingY + BLUE_Y1,
                                this.startingX-TAIL_RAD, this.startingY + BLUE_Y2,
                                this.startingX+TAIL_RAD, this.startingY + BLUE_Y2));
                break;
            case ANDY_STRENGTH:
                this.setBloon(ANDY_XRAD, ANDY_YRAD, Color.BLACK, Color.BLACK, ANDY_SPEED,
                        new Polygon(this.startingX, this.startingY, this.startingX,
                                this.startingY, this.startingX, this.startingY));
                this.andyMode = true;
                this.setUpAndy();
                break;
            default:
                break;
        }
    }

    /**
     * This method is called from the above setUpBloon method if the bloon's strength is equal to
     * that of an AndyBloon. It visually sets up both the AndyBloon image and the health bar...
     */
    private void setUpAndy(){
        this.andy = new ImageView(new Image("indy/A.N.D.Y.png"));
        this.andy.setPreserveRatio(false);
        this.andy.setFitWidth(ANDY_DIMENSIONS);
        this.andy.setFitHeight(ANDY_DIMENSIONS);
        this.andy.setX(ANDY_START);
        this.andy.setY(ANDY_START);

        this.andyHealthBorder = new Rectangle(HEALTH_X, HEALTH_Y, HEALTH_WIDTH, HEALTH_HEIGHT);
        this.andyHealthBorder.setStroke(Color.BLACK);
        this.andyHealthBorder.setStrokeWidth(HEALTH_BORDER);
        this.andyHealthBorder.setFill(INVISIBLE);

        this.andyHealth = new Rectangle(HEALTH_X, HEALTH_Y, HEALTH_WIDTH, HEALTH_HEIGHT);
        this.andyHealth.setFill(HEALTH_COLOR);

        this.andyHealthBack = new Rectangle(HEALTH_X, HEALTH_Y, HEALTH_WIDTH, HEALTH_HEIGHT);
        this.andyHealthBack.setFill(Color.LIGHTGRAY);

        this.andyPane.getChildren().addAll(this.andy, this.andyHealthBack,
                this.andyHealth, this.andyHealthBorder);
    }

    /**
     * This method is called from the above setUpBloon, taking in necessary parameters to resize and
     * recolor the balloon when it changes strength. It recreates the tail to avoid translation issues
     * and adjusts the camo film's color if the bloon is a camo bloon.
     */
    private void setBloon(double xRad, double yRad, Color fill, Color border,
                          double newSpeed, Polygon tailShape){
        // Get the current tail position.
        double transX = this.tail.getTranslateX();
        double transY = this.tail.getTranslateY();

        // Remove the tail, reshape it, and re-add it to the pane.
        this.bloonPane.getChildren().removeAll(this.tail, this.body, this.camoFilm);
        this.tail = tailShape;
        this.bloonPane.getChildren().addAll(this.tail, this.body, this.camoFilm);

        // Set style of the tail and body to match new bloon type.
        this.tail.setTranslateX(this.tail.getTranslateX() + transX);
        this.tail.setTranslateY(this.tail.getTranslateY() + transY);
        this.tail.setStrokeWidth(BLOON_BORDER_WIDTH);
        this.body.setStrokeWidth(BLOON_BORDER_WIDTH);
        this.body.setRadiusX(xRad);
        this.body.setRadiusY(yRad);

        // Color the different bloon parts based on camo status.
        if (this.camo){
            this.camoFilm.setRadiusX(xRad);
            this.camoFilm.setStroke(border);
            this.camoFilm.setFill(fill);

            this.body.setFill(CAMO_LIGHT);
            this.body.setStroke(CAMO_DARK);
            this.tail.setFill(CAMO_LIGHT);
            this.tail.setStroke(CAMO_LIGHT);
        }
        else{
            this.body.setFill(fill);
            this.body.setStroke(border);
            this.tail.setFill(fill);
            this.tail.setStroke(border);
        }

        this.speed = newSpeed;
        this.getNewTarget();
    }

    /**
     * This method is called from the BloonOrganizer. The bloon has a brief pause when it is first
     * spawned so that duplicated bloons are offset slightly from one another, and then they are moved.
     * When they reach a checkpoint in the track, they re-orient themselves to the next checkpoint and
     * continue moving until they reach the final checkpoint, at which point the method returns false,
     * and they are deleted both graphically and logically.
     */
    public boolean move(){
        // Pause the balloon when it is spawned so separate stacked bloons...
        if (this.spawnDelay < 1){
            this.spawnDelay += SPAWN_DELAY_INCREMENT;
        }
        else {
            int moved = 0;
            // Move the bloon and check if it's reached a checkpoint, depending on direction.
            if ((this.xMove > 0 && (this.body.getCenterX() - this.path[checkpoint][0] < 0)) ||
                    (this.xMove < 0 && this.body.getCenterX() - this.path[checkpoint][0] > 0)){
                moved += this.moveBloon(this.xMove, 0);
            }
            if ((this.yMove > 0 && (this.body.getCenterY() - this.path[checkpoint][1] < 0)) ||
                    (this.yMove < 0 && this.body.getCenterY() - this.path[checkpoint][1] > 0)){
                moved += this.moveBloon(0, this.yMove);
            }

            // If the balloon stops moving...
            if (moved == 0){
                // Set location to checkpoint to account for movement error.
                this.body.setCenterX(this.path[checkpoint][0]);
                this.body.setCenterY(this.path[checkpoint][1]);

                // Either update checkpoint or remove the bloon.
                if (this.checkpoint < this.path.length - 1) {
                    this.checkpoint++;
                    this.getNewTarget();
                }
                else {
                    this.bloonPane.getChildren().removeAll(this.body, this.tail);
                    this.andyPane.getChildren().removeAll(this.andy,
                            this.andyHealthBorder, this.andyHealth, this.andyHealthBack);
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * This method is called from the move method above. It eliminates a few lines of repetitive
     * code and moves the balloon's components according to the offset values passed in.
     */
    private int moveBloon(double offsetX, double offsetY){
        this.body.setCenterX(this.body.getCenterX() + offsetX);
        this.camoFilm.setTranslateX(this.camoFilm.getTranslateX() + offsetX);
        this.tail.setTranslateX(this.tail.getTranslateX() + offsetX);

        this.body.setCenterY(this.body.getCenterY() + offsetY);
        this.camoFilm.setTranslateY(this.camoFilm.getTranslateY() + offsetY);
        this.tail.setTranslateY(this.tail.getTranslateY() + offsetY);

        // Move andy image if it's an andy bloon.
        if (this.andyMode){
            this.andy.setX(this.body.getCenterX() - ANDY_DIMENSIONS/2);
            this.andy.setY(this.body.getCenterY() - ANDY_DIMENSIONS/2);
        }

        this.distanceTraveled += (Math.abs(offsetX) + Math.abs(offsetY));
        return 1;
    }

    /**
     * This method is called when the bloon is created and whenever it reaches a checkpoint. It
     * reassigns the xMove and yMove variables so that the bloon switches direction and begins to
     * move towards the next track checkpoint.
     */
    private void getNewTarget(){
        double xDiff = this.path[checkpoint][0] - this.body.getCenterX();
        double yDiff = this.path[checkpoint][1] - this.body.getCenterY();

        double angle = Math.atan(yDiff/xDiff);
        this.yMove = Math.abs(this.speed * Math.sin(angle));
        this.xMove = Math.abs(this.speed * Math.cos(angle));

        // Adjust the sign of the movement accordingly.
        if (xDiff < 0) {
            this.xMove *= -1;
        }
        if (yDiff < 0) {
            this.yMove *= -1;
        }
    }

    /**
     * This method is called when the bloon is it by a dart or an explosion that can pop it. It reduces
     * its strength (by two if the dart is piercing), adjusts AndyBloon's health bar if necessary, and
     * creates a pop animation and sets up a new bloon if necessary. If strength == 0, the method returns
     * false and the bloon is removed logically and graphically.
     */
    public boolean pop(boolean piercing){
        // Piercing only affects bloons between 6 and 1 strength...
        if (piercing && this.strength > 1 && this.strength <= PINK_STRENGTH){
            this.strength--;
            this.bank.modifyCash(1);
        }
        this.strength--;
        this.bank.modifyCash(1);

        // Decrease Andy's health bar.
        if (this.andyMode && this.strength >= ANDY_POP){
            this.andyHealth.setWidth(HEALTH_WIDTH-(ANDY_STRENGTH-(float)this.strength)
                    /(ANDY_STRENGTH-ANDY_POP)*HEALTH_WIDTH);
        }

        // Andy is popped when his strength reaches a threshold...
        if (this.strength < ANDY_POP){
            new PopAnimation(this.body.getCenterX(), this.body.getCenterY(), this.popPane);
            this.andyPane.getChildren().removeAll(this.andy, this.andyHealth,
                    this.andyHealthBack, this.andyHealthBorder);
            this.setUpBloon();
        }

        // If strength is 0, remove the bloon.
        if (this.strength == 0){
            this.bloonPane.getChildren().removeAll(this.tail, this.body, this.camoFilm);
            return true;
        }
        return false;
    }

    /**
     * Accessor method to get the ellipse to check for collisions.
     */
    public Ellipse getBody(){
        return this.body;
    }

    /**
     * Accessor method to get the X center of the bloon's main ellipse.
     */
    public double getX(){
        return this.body.getCenterX();
    }

    /**
     * Accessor method to get the Y center of the bloon's main ellipse.
     */
    public double getY(){
        return this.body.getCenterY();
    }

    /**
     * Accessor method to get the strength of the bloon.
     */
    public int getStrength(){
        return this.strength;
    }

    /**
     * Accessor method to get the bloon's camo property.
     */
    public boolean getCamo(){
        return this.camo;
    }

    /**
     * Accessor method to get the bloon's distance traveled to re-order bloons.
     */
    public int getDistanceTraveled(){
        return (int) this.distanceTraveled;
    }

    /**
     * Accessor method to get the bloon's current target checkpoint.
     */
    public int getCheckpoint(){
        return this.checkpoint;
    }
}