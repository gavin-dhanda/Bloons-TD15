package indy;

import javafx.geometry.Bounds;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import java.util.ArrayList;
import static indy.Constants.*;

/**
 * This is the Cannonball class, which inherits from the Dart class. It is represented by a single Circle,
 * and it has fewer variable properties than the Dart class from which it inherits. A cannonball always
 * ends in an explosion, either upon contact with any Bloon or when it reaches the end of its range.
 */
public class Cannonball extends Dart {

    private DoublyLinkedList myBloons;
    private Circle ball;
    private Pane cannonballPane;
    private double distanceTraveled;
    private int range;
    private int bigBoom;

    /**
     * This is the Cannonball class' Constructor. It is associated with the DoublyLinkedList of Bloons
     * and takes in various properties necessary for the Dart's constructor. It calls the super's
     * Constructor and then initializes all of its relevant instance variables...
     */
    public Cannonball (Pane myPane, Double angle, double x, double y, int myRange, boolean canTarget,
                       int maxPops, ArrayList<Bloon> bloonsHit, boolean piercingUp, boolean canHitCamo,
                       DoublyLinkedList bloons, int boomSize){
        super(myPane, angle, x, y, myRange, canTarget, maxPops, bloonsHit, piercingUp, canHitCamo);

        this.cannonballPane = myPane;
        this.distanceTraveled = 0;
        this.range = myRange;
        this.myBloons = bloons;
        this.bigBoom = boomSize;
    }

    /**
     * This method is overridden from the superclass because the cannonball explodes on contact with
     * any type of Bloon, making its collision checking much simpler than that of the dart. If it does
     * have a collision, it creates an Explosion but always returns false because the cannonball can
     * not go through Bloons and continue traveling.
     */
    @Override
    public boolean checkCollision(Bloon bloon){
        if (bloon.getBody().intersects(this.ball.getBoundsInParent())){
            this.cannonballPane.getChildren().remove(this.ball);
            new Explosion(bloon.getBody().getCenterX(), bloon.getBody().getCenterY() +
                    EXPLOSION_OFFSET, this.cannonballPane, this.myBloons, this.bigBoom);
            super.dropPop();
        }
        return false;
    }

    /**
     * This method is overridden from the superclass because the cannonball must move its Circle as
     * opposed to the Dart's Rectangle. Instead of simply disappearing when it reaches the end of its
     * range, the Cannonball also creates an explosion at its location upon death.
     */
    @Override
    public boolean move(){
        this.ball.setCenterY(this.ball.getCenterY() - CANNONBALL_SPEED);
        this.distanceTraveled += CANNONBALL_SPEED;
        if (this.distanceTraveled > this.range){
            this.cannonballPane.getChildren().remove(this.ball);

            // Make a new explosion at the Cannonball's last location.
            Bounds bounds = this.ball.getBoundsInParent();
            double x = ((bounds.getMaxX() + bounds.getMinX()) / 2);
            double y = ((bounds.getMaxY() + bounds.getMinY()) / 2);
            new Explosion(x, y, this.cannonballPane, this.myBloons, this.bigBoom);

            return false;
        }
        return true;
    }

    /**
     * This method is overridden from the superclass because the cannonball is now made up of a Circle,
     * as opposed to a thin Rectangle, and it is never targeting so doesn't need to limit the max range.
     */
    @Override
    public void setUpDart(double x, double y, Pane pane){
        this.ball = new Circle(x, y, CANNONBALL_RADIUS);
        this.ball.getTransforms().add(super.getRotate());
        pane.getChildren().add(this.ball);
    }

    /**
     * This accessor method is overridden to get the X center of the Cannonball's Circle.
     */
    @Override
    public double getX(){
        return this.ball.getCenterX();
    }

    /**
     * This accessor method is overridden to get the Y center of the Cannonball's Circle.
     */
    @Override
    public double getY(){
        return this.ball.getCenterY();
    }

    /**
     * This accessor method is overridden to get the Cannonball's Circle (as opposed to Rectangle).
     */
    @Override
    public Shape getDart(){
        return this.ball;
    }
}