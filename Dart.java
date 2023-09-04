package indy;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;
import java.util.ArrayList;
import static indy.Constants.*;

/**
 * This is the Dart class. The dart is represented by a single thin rectangle, and it contains a variety
 * of properties relating to itself and also the specific Bloons that it is able to pop.
 */
public class Dart {

    private ArrayList<Bloon> bloonsHit;
    private Rectangle body;
    private Rotate rotate;
    private Pane pane;
    private double distTraveled;
    private int popCount;
    private int range;
    private boolean targetability;
    private boolean piercing;
    private boolean camo;

    /**
     * This is the Dart class' Constructor. It is associated with the dart Pane, and takes in various
     * arguments used to initialize and instantiate its relevant instance variables. It then sets up
     * the dart, passing in the pane and the coordinates from which the dart should be shot...
     */
    public Dart(Pane myPane, Double angle, double x, double y, int myRange, boolean canTarget,
                int maxPops, ArrayList<Bloon> bloonsHit, boolean piercingUpgrade, boolean canHitCamo){
        this.rotate = new Rotate(angle, x, y);
        this.piercing = piercingUpgrade;
        this.distTraveled = 0;
        this.pane = myPane;
        this.setUpDart(x, y, myPane);
        this.popCount = maxPops;
        this.range = myRange;
        this.bloonsHit = bloonsHit;
        this.targetability = canTarget;
        this.camo = canHitCamo;
    }

    /**
     * This method is called from the Constructor, and it visually sets up and adds the dart to the pane.
     * It also rotates the dart accordingly and limits the dart's range to a specified maximum value...
     */
    public void setUpDart(double x,  double y, Pane myPane){
        this.body = new Rectangle(x - DART_WIDTH/2, y - DART_HEIGHT/2, DART_WIDTH, DART_HEIGHT);
        this.body.getTransforms().add(this.rotate);
        this.pane.getChildren().add(this.body);
        if (this.range > MAX_DART_RANGE && this.targetability){
            this.range = MAX_DART_RANGE;
        }
    }

    /**
     * This method is called from the DartOrganizer, and it moves the dart in whichever direction it has been
     * rotated. When the dart reaches the end of its range, it returns false and the dart is removed entirely.
     */
    public boolean move(){
        this.body.setY(this.body.getY() - DART_SPEED);
        this.distTraveled += DART_SPEED;
        if (this.distTraveled > this.range){
            this.pane.getChildren().remove(this.body);
            return false;
        }
        return true;
    }

    /**
     * This method is called from the DartOrganizer, and it checks if the dart is currently colliding with
     * the Bloon that is passed in. If the dart is not colliding or if the Bloon and dart types are not
     * compatible, the method returns false. If there is an allowed collision, the method returns true...
     */
    public boolean checkCollision(Bloon bloon){
        if (bloon.getBody().intersects(this.body.getBoundsInParent())){
            if (bloon.getStrength() == LEAD_STRENGTH){
                this.popCount = 0;
                return false;
            }
            else {
                for (Bloon bloonHit : this.bloonsHit){
                    if (bloonHit == bloon){
                        return false;
                    }
                }
                this.bloonsHit.add(bloon);
                if (bloon.getCamo() && !this.camo){
                    return false;
                }
                this.popCount--;
            }
            return true;
        }
        return false;
    }

    /**
     * Mutator method to decrease the remaining pop count of the dart.
     */
    public void dropPop(){
        this.popCount--;
    }

    /**
     * Accessor method to get the body of the dart.
     */
    public Shape getDart(){
        return this.body;
    }

    /**
     * Accessor method to get the piercing property of the dart.
     */
    public boolean getPiercing(){
        return this.piercing;
    }

    /**
     * Accessor method to get the targeting property of the dart.
     */
    public boolean getTargetability(){
        return this.targetability;
    }

    /**
     * Accessor method to get the X value of the dart.
     */
    public double getX(){
        return (this.body.getY() + DART_WIDTH/2);
    }

    /**
     * Accessor method to get the Y value of the dart.
     */
    public double getY(){
        return (this.body.getX() + DART_HEIGHT/2);
    }

    /**
     * Accessor method to get the remaining pop count of the dart.
     */
    public int getPopCount(){
        return this.popCount;
    }

    /**
     * Accessor method to get the Rotation of the dart.
     */
    public Rotate getRotate(){
        return this.rotate;
    }

    /**
     * Accessor method to get the ArrayList of Bloons that the dart has already hit.
     */
    public ArrayList<Bloon> getBloonsHit(){
        return this.bloonsHit;
    }
}